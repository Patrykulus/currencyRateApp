package pl.rate.currencyRateApp.models;

import java.time.LocalDate;

public class ExchangeRate {
    private LocalDate date;
    private double rate;

    public ExchangeRate(LocalDate date, double rate) {
        this.date = date;
        this.rate = rate;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                ", date=" + date +
                ", rate=" + rate +
                '}';
    }
}
