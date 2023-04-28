package com.quotemedia.interview.quoteservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class QuoteService {

    @Autowired
    private QuoteRepository quoteRepository;
    @Cacheable(value = "quotes", key = "#symbol")
    public Optional<Quote> findLatestBySymbol(String symbol) {
        return quoteRepository.findLatestBySymbol(symbol);
    }@Cacheable(value = "highestAsk", key = "#day")
    public List<Quote> findTopByDayOrderByAskDesc(LocalDate day) {
        return quoteRepository.findTopByDayOrderByAskDesc(day);
    }
}
