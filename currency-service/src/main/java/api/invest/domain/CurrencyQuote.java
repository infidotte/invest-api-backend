package api.invest.domain;

public record CurrencyQuote(
        String numCode,
        String charCode,
        String nominal,
        String name,
        String value
) {
}
