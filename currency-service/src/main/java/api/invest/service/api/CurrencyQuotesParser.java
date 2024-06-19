package api.invest.service.api;

import api.invest.domain.CurrencyQuote;

import java.util.Map;

public interface CurrencyQuotesParser {
    Map<String, CurrencyQuote> parse(String quotes);
}
