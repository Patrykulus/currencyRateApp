package pl.rate.currencyRateApp.services;

import org.springframework.stereotype.Service;
import pl.rate.currencyRateApp.clients.NbpApiClient;
import pl.rate.currencyRateApp.models.Code;
import pl.rate.currencyRateApp.models.ExchangeRate;

import java.time.LocalDate;
import java.util.List;

@Service
public final class ExchangeRateService {
    private NbpApiClient nbpApiClient;
    private static ExchangeRateService INSTANCE;


    private ExchangeRateService(NbpApiClient nbpApiClient) {
        this.nbpApiClient = nbpApiClient;
    }
    public static ExchangeRateService getInstance(NbpApiClient nbpApiClient) {
        if(INSTANCE == null) {
            INSTANCE = new ExchangeRateService(nbpApiClient);
        }

        return INSTANCE;
    }
    public List<ExchangeRate> getExchangeRates(Code currencyCode, LocalDate startDate, LocalDate endDate) {
        return nbpApiClient.getExchangeRates(currencyCode, startDate, endDate);
    }
}
