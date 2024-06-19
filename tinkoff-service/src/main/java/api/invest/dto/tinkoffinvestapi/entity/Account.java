package api.invest.dto.tinkoffinvestapi.entity;

import api.invest.dto.tinkoffinvestapi.type.AccessLevel;
import api.invest.dto.tinkoffinvestapi.type.AccountStatus;
import api.invest.dto.tinkoffinvestapi.type.AccountType;
import io.swagger.v3.oas.annotations.media.Schema;

public record Account(
        @Schema(description = "Информация о счёте.")
        String id,
        @Schema(description = "Тип счёта.")
        AccountType type,
        @Schema(description = "Название счёта.")
        String name,
        @Schema(description = "Статус счёта.")
        AccountStatus status,
        @Schema(description = "Дата открытия счёта в часовом поясе UTC.")
        String openedDate,
        @Schema(description = "Дата закрытия счёта в часовом поясе UTC.")
        String closedDate,
        @Schema(description = "Уровень доступа к счёту.")
        AccessLevel accessLevel
) {
}
