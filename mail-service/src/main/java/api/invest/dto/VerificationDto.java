package api.invest.dto;

public record VerificationDto(
        String toAddress,
        String username,
        String code
) {
}
