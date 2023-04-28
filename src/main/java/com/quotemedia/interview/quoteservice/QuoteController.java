package com.quotemedia.interview.quoteservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/symbols")
public class QuoteController {

    @Autowired
    private QuoteService quoteService;

    @GetMapping("/{symbol}/quotes/latest")
    public ResponseEntity<Object> getLatestQuote(@PathVariable("symbol") String symbol) {
        if (!isValidSymbol(symbol)) {
            return ResponseEntity.badRequest().body("Invalid symbol");
        }

        Optional<Quote> quote = quoteService.findLatestBySymbol(symbol);

        if (quote.isPresent()) {
            return ResponseEntity.ok(quote.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private boolean isValidSymbol(String symbol) {
        return Pattern.matches("^[A-Za-z]{2,6}$", symbol);
    }
}
