package api.invest.client.tinkoffinvestapi;

import api.invest.dto.tinkoffinvestapi.sandbox.CloseSandboxAccountRequest;
import api.invest.dto.tinkoffinvestapi.sandbox.GetSandboxAccountsResponse;
import api.invest.dto.tinkoffinvestapi.sandbox.OpenSandboxAccountRequest;
import api.invest.dto.tinkoffinvestapi.sandbox.OpenSandboxAccountResponse;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(
        name = "${clients.tinkoff-invest-api-sandbox.name}",
        url = "${clients.tinkoff-invest-api-sandbox.url}",
        path = "${clients.tinkoff-invest-api-sandbox.path}")
public interface SandboxServiceReactiveFeignClient {
    /**
     * Метод регистрации счета в песочнице.
     *
     * @param openSandboxAccountRequest Запрос открытия счета в песочнице.
     * @return Номер открытого счёта в песочнице.
     */
    @PostMapping("/OpenSandboxAccount")
    Mono<OpenSandboxAccountResponse> openSandboxAccount(
            @RequestBody OpenSandboxAccountRequest openSandboxAccountRequest);

    /**
     * Метод получения счетов в песочнице.
     *
     * @return Дто со списком счетов.
     */
    @PostMapping("/GetSandboxAccounts")
    Mono<GetSandboxAccountsResponse> getSandBoxAccounts(HttpEntity<?> empty);

    /**
     * Метод закрытия счета в песочнице.
     *
     * @param closeSandboxOrderRequest Запрос закрытия счета в песочнице.
     */
    @PostMapping("/CloseSandboxAccount")
    Mono<Void> closeSandboxAccount(CloseSandboxAccountRequest closeSandboxOrderRequest);
}
