package api.invest.controller;

import api.invest.domain.CurrencyQuote;
import api.invest.service.CbrService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/currency-service")
@RequiredArgsConstructor
public class Controller {
    private final CbrService cbrService;

    @GetMapping("/quotes")
    public Mono<Map<String, CurrencyQuote>> getTodayQuotes(@RequestParam(required = false) LocalDate date) {
        return cbrService.getQuotesForDate(date);
    }
}
