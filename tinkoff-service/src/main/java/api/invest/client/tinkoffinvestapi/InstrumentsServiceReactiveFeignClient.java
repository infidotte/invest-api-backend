package api.invest.client.tinkoffinvestapi;

import api.invest.dto.tinkoffinvestapi.instruments.FindInstrumentRequest;
import api.invest.dto.tinkoffinvestapi.instruments.FindInstrumentResponse;
import org.springframework.web.bind.annotation.PostMapping;
import reactivefeign.spring.config.ReactiveFeignClient;

@ReactiveFeignClient(
        name = "${clients.tinkoff-invest-api-production.name}",
        url = "${clients.tinkoff-invest-api-production.url}",
        path = "${clients.tinkoff-invest-api-production.path}")
public interface InstrumentsServiceReactiveFeignClient {
    @PostMapping("/InstrumentsService/FindInstruments")
    FindInstrumentResponse findInstrument(FindInstrumentRequest findInstrumentRequest);
}
