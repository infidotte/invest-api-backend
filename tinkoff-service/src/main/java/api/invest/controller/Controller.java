package api.invest.controller;

import api.invest.client.tinkoffinvestapi.SandboxServiceReactiveFeignClient;
import api.invest.dto.tinkoffinvestapi.sandbox.GetSandboxAccountsResponse;
import api.invest.dto.tinkoffinvestapi.sandbox.CloseSandboxAccountRequest;
import api.invest.dto.tinkoffinvestapi.sandbox.OpenSandboxAccountRequest;
import api.invest.dto.tinkoffinvestapi.sandbox.OpenSandboxAccountResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/tinkoff-service")
@RequiredArgsConstructor
public class Controller {
    private static final Logger log = LoggerFactory.getLogger(Controller.class);
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private final SandboxServiceReactiveFeignClient tinkoffInvestApi;

    @GetMapping("/open-account")
    public Mono<OpenSandboxAccountResponse> openAccount(@RequestParam OpenSandboxAccountRequest request) {
        return tinkoffInvestApi.openSandboxAccount(request);
    }

    @GetMapping("/close-account")
    public Mono<Void> closeAccount(@RequestParam String accountId) {
        return tinkoffInvestApi.closeSandboxAccount(new CloseSandboxAccountRequest(accountId));
    }

    @GetMapping("/get-accounts")
    public Mono<GetSandboxAccountsResponse> getAccounts() {
        return tinkoffInvestApi.getSandBoxAccounts(HttpEntity.EMPTY);
    }
}
