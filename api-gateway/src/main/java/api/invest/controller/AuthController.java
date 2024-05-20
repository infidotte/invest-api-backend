package api.invest.controller;

import api.invest.dto.AuthorizationRequestDto;
import api.invest.dto.JwtResponseDto;
import api.invest.dto.RefreshJwtRequestDto;
import api.invest.dto.RegistrationRequestDto;
import api.invest.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public Mono<JwtResponseDto> login(@RequestBody AuthorizationRequestDto requestDto) {
        return authService.login(requestDto);
    }

    @PostMapping("/registration")
    public Mono<Void> registration(@RequestBody RegistrationRequestDto requestDto) {
        return authService.registration(requestDto);
    }

    @PostMapping("/refresh")
    public Mono<JwtResponseDto> refresh(@RequestBody RefreshJwtRequestDto requestDto) {
        return authService.refresh(requestDto.refreshToken());
    }

    @GetMapping("/verify")
    public Mono<Void> verify(@RequestParam(name = "username") String username, @RequestParam(name = "code") int code) {
        return authService.verify(username, code);
    }
}
