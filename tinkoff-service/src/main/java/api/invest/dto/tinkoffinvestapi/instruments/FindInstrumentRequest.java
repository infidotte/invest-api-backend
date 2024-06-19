package api.invest.dto.tinkoffinvestapi.instruments;

import api.invest.dto.tinkoffinvestapi.type.InstrumentType;

public record FindInstrumentRequest(
        String query,
        InstrumentType instrumentType,
        boolean apiTradeAvailableFlag
) {
}
