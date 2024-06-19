package api.invest.dto.tinkoffinvestapi.sandbox;

import api.invest.dto.tinkoffinvestapi.entity.Account;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record GetSandboxAccountsResponse(
        @Schema(description = "Список счетов пользователя.")
        List<Account> accounts
) {}
