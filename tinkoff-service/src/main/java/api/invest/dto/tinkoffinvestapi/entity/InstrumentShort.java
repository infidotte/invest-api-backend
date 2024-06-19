package api.invest.dto.tinkoffinvestapi.entity;

import api.invest.dto.tinkoffinvestapi.type.InstrumentType;

public record InstrumentShort(
        String isin,
        String figi,
        String ticker,
        String classCode,
        String instrumentType,
        String name,
        String uid,
        String positionUid,
        InstrumentType instrumentKind,
        boolean apiTradeAvailableFlag,
        boolean forIisFlag,
        String first1minCandleDate,
        String first1dayCandleDate,
        boolean forQualInvestorFlag,
        boolean weekendFlag,
        boolean blockedTcaFlag
) {}
