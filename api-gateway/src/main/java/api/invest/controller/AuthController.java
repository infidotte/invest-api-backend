package api.invest.controller;

import api.invest.dto.RegistrationRequestDto;
import api.invest.entity.Role;
import api.invest.entity.User;
import api.invest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;

    @PostMapping("/login")
    public Mono<ResponseEntity> login(@RequestBody RequestEntity requestEntity) {
        return Mono.just(ResponseEntity.ok().body("success"));
    }

    @PostMapping("/registration")
    public Mono<User> registration(@RequestBody RegistrationRequestDto requestDto) {
        return Mono.just(userRepository.save(new User(UUID.randomUUID(), requestDto.username(), requestDto.email(), requestDto.password(), Role.ROLE_USER)));
    }

    @PostMapping("/refresh")
    public Mono<ResponseEntity> refresh(@RequestBody RequestEntity requestEntity) {
        return Mono.just(ResponseEntity.ok().body("success"));
    }
}
