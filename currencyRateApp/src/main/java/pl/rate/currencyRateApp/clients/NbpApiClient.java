package pl.rate.currencyRateApp.clients;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.rate.currencyRateApp.models.Code;
import pl.rate.currencyRateApp.models.ExchangeRate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class NbpApiClient {
    private static final String BASE_URL = "http://api.nbp.pl/api/exchangerates/rates/a/%s/%s/%s/";

    public List<ExchangeRate> getExchangeRates(Code currencyCode, LocalDate startDate, LocalDate endDate) {
        String url = String.format(BASE_URL, currencyCode, startDate, endDate);
        RestTemplate restTemplate = new RestTemplate();
        NbpResponse nbpResponse = restTemplate.getForObject(url, NbpResponse.class);

        List<ExchangeRate> exchangeRates = new ArrayList<>();
        for (NbpRate nbpRate : nbpResponse.getRates()) {
            exchangeRates.add(new ExchangeRate(nbpRate.getEffectiveDate(), nbpRate.getMid()));
        }

        return exchangeRates;
    }

    private static class NbpResponse {
        private List<NbpRate> rates;
        public List<NbpRate> getRates() {
            return rates;
        }

        public void setRates(List<NbpRate> rates) {
            this.rates = rates;
        }
    }

    private static class NbpRate {
        private String no;
        private LocalDate effectiveDate;
        private double mid;

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public LocalDate getEffectiveDate() {
            return effectiveDate;
        }

        public void setEffectiveDate(LocalDate effectiveDate) {
            this.effectiveDate = effectiveDate;
        }

        public double getMid() {
            return mid;
        }

        public void setMid(double mid) {
            this.mid = mid;
        }
    }
}
