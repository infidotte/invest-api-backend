package api.invest.dto;

public record EmailDto(
        String toAddress,
        String subject,
        String content
) {
}
