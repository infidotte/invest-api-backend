package api.invest.dto.tinkoffinvestapi.type;

import lombok.Getter;

@Getter
public enum AccountType {
    ACCOUNT_TYPE_UNSPECIFIED("Тип аккаунта не определён"),
    ACCOUNT_TYPE_TINKOFF("Брокерский счёт Тинькофф"),
    ACCOUNT_TYPE_TINKOFF_IIS("ИИС счёт"),
    ACCOUNT_TYPE_INVEST_BOX("Инвесткопилка");

    private final String description;

    AccountType(String description) {
        this.description = description;
    }
}
