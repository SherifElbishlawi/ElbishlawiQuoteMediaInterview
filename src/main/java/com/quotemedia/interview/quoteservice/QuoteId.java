package com.quotemedia.interview.quoteservice;

import java.io.Serializable;
import java.time.LocalDate;

public class QuoteId implements Serializable {
    public QuoteId() {
    }

    private String symbol;
    private LocalDate day;

    // Getters, setters

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }
}
