package api.invest.dto.tinkoffinvestapi.type;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "Статус счёта.")
public enum AccountStatus {
    ACCOUNT_STATUS_UNSPECIFIED("Статус счёта не определён"),
    ACCOUNT_STATUS_NEW("Новый, в процессе открытия"),
    ACCOUNT_STATUS_OPEN("Открытый и активный счёт"),
    ACCOUNT_STATUS_CLOSED("Закрытый счёт");

    private final String description;

    AccountStatus(String description) {
        this.description = description;
    }
}