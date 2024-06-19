package api.invest.dto.tinkoffinvestapi.type;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "Уровень доступа к счёту.")
public enum AccessLevel {
    ACCOUNT_ACCESS_LEVEL_UNSPECIFIED("Уровень доступа не определён"),
    ACCOUNT_ACCESS_LEVEL_FULL_ACCESS("Полный доступ к счёту"),
    ACCOUNT_ACCESS_LEVEL_READ_ONLY("Доступ с уровнем прав 'только чтение'"),
    ACCOUNT_ACCESS_LEVEL_NO_ACCESS("Доступ отсутствует");

    private final String description;

    AccessLevel(String description) {
        this.description = description;
    }
}