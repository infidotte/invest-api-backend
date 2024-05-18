package api.invest.service;

import api.invest.dto.AuthorizationRequestDto;
import api.invest.dto.JwtResponseDto;
import api.invest.dto.RefreshJwtRequestDto;
import api.invest.dto.RegistrationRequestDto;
import api.invest.entity.Role;
import api.invest.entity.User;
import api.invest.mapper.UserMapper;
import api.invest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

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

    public Mono<JwtResponseDto> login(AuthorizationRequestDto requestDto) {
        return userRepository.findByUsername(requestDto.username())
                .filter(u -> passwordEncoder.matches(requestDto.password(), u.getPassword()))
                .switchIfEmpty(Mono.error(()->{throw new RuntimeException("Wrong password");}))
                .map(this::generateTokens)
                .doOnError(throwable -> {
                    throw new RuntimeException(throwable);
                });
    }

    public Mono<JwtResponseDto> registration(RegistrationRequestDto requestDto) {
        return Mono.just(requestDto)
                .map(this::prepareUserToSave)
                .doOnNext(user -> log.info("before save {}", user))
                .flatMap(userRepository::save)
                .map(this::generateTokens)
                .doOnNext(user -> log.info("Registered user: {}", user))
                .doOnError(throwable -> {
                    throw new RuntimeException(throwable);
                });
    }
    public User prepareUserToSave(RegistrationRequestDto requestDto) {
        User user = userMapper.toUser(requestDto);
        user.setRole(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(requestDto.password()));
        user.setVerifyCode(random.nextInt(100000, 999999));
        return user;
    }

    public JwtResponseDto generateTokens(User user) {
        return new JwtResponseDto(provider.generateAccessToken(user), provider.generateRefreshToken(user));
    }

    public Mono<JwtResponseDto> refresh(String refreshToken) {
        if (provider.isRefreshTokenValid(refreshToken)){
            String username = provider.getRefreshClaims(refreshToken).getSubject();
            return userRepository.findByUsername(username)
                    .map(this::generateTokens)
                    .doOnError(throwable -> {
                        throw new RuntimeException(throwable);
                    });
        }
        throw new RuntimeException("");
    }
}
