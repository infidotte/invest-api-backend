package api.invest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "")
public record JwtResponseDto(
        String accessToken,
        String refreshToken) {
}
