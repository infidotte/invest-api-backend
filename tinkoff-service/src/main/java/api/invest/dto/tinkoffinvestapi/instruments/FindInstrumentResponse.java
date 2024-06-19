package api.invest.dto.tinkoffinvestapi.instruments;

import api.invest.dto.tinkoffinvestapi.entity.InstrumentShort;

import java.util.List;

public record FindInstrumentResponse(List<InstrumentShort> instruments) {}

