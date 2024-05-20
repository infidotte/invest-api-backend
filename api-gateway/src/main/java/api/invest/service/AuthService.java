package api.invest.service;

import api.invest.client.MailServiceReactiveFeignClient;
import api.invest.dto.AuthorizationRequestDto;
import api.invest.dto.JwtResponseDto;
import api.invest.dto.RegistrationRequestDto;
import api.invest.dto.VerificationDto;
import api.invest.entity.Role;
import api.invest.entity.User;
import api.invest.mapper.UserMapper;
import api.invest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final Random random = new SecureRandom();
    private final JwtProvider provider;
    private final MailServiceReactiveFeignClient mailService;

    public Mono<JwtResponseDto> login(AuthorizationRequestDto requestDto) {
        return userRepository.findByUsername(requestDto.username())
                .filter(u -> passwordEncoder.matches(requestDto.password(), u.getPassword()))
                .switchIfEmpty(Mono.error(() -> {
                    throw new RuntimeException("Wrong password");
                }))
                .map(this::generateTokens)
                .doOnError(throwable -> {
                    throw new RuntimeException(throwable);
                });
    }

    public Mono<Void> registration(RegistrationRequestDto requestDto) {
        return sendVerification(prepareUserToSave(requestDto));
//        return Mono.just(requestDto)
//                .map(this::prepareUserToSave)
//                .flatMap(userRepository::save)
//                .doOnNext(this::sendVerification)
//                .doOnNext(user -> log.info("Registered user: {}", user))
//                .doOnError(throwable -> {
//                    throw new RuntimeException(throwable.getMessage());
//                }).then();
    }

    public Mono<JwtResponseDto> refresh(String refreshToken) {
        if (provider.isRefreshTokenValid(refreshToken)) {
            String username = provider.getRefreshClaims(refreshToken).getSubject();
            return userRepository.findByUsername(username)
                    .map(this::generateTokens)
                    .doOnError(throwable -> {
                        throw new RuntimeException(throwable.getMessage());
                    });
        }
        throw new RuntimeException("");
    }

    public Mono<Void> verify(String username, int code) {
        return userRepository.findByUsername(username)
                .doOnNext(user -> {
                    int userCode = user.getVerifyCode();
                    if (userCode != code || userCode == 0)
                        throw new RuntimeException("Wrong code");
                })
                .flatMap(user -> {
                    user.setVerifyCode(1);
                    user.setEnabled(true);
                    return userRepository.save(user);
                })
                .then();
    }

    private Mono<Void> sendVerification(User user) {
        return mailService.send(new VerificationDto(user.getEmail(), user.getUsername(), user.getVerifyCode())).then();
    }

    private User prepareUserToSave(RegistrationRequestDto requestDto) {
        User user = userMapper.toUser(requestDto);
        user.setRole(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(requestDto.password()));
        user.setVerifyCode(random.nextInt(100000, 999999));
        return user;
    }

    private JwtResponseDto generateTokens(User user) {
        return new JwtResponseDto(provider.generateAccessToken(user), provider.generateRefreshToken(user));
    }
}
