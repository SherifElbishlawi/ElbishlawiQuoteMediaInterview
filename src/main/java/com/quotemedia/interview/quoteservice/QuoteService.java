package com.quotemedia.interview.quoteservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class QuoteService {

    @Autowired
    private QuoteRepository quoteRepository;

    public Optional<Quote> findLatestBySymbol(String symbol) {
        return quoteRepository.findLatestBySymbol(symbol);
    }
}
