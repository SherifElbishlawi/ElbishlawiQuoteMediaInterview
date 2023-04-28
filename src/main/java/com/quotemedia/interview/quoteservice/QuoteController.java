package com.quotemedia.interview.quoteservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/symbols")
public class    QuoteController {

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
    private static final Logger logger = LoggerFactory.getLogger(QuoteController.class);

    @GetMapping("/highest-ask/{day}")
    public ResponseEntity<?> getHighestAsk(@PathVariable String day) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsedDay = LocalDate.parse(day, formatter);
            List<Quote> quotes = quoteService.findTopByDayOrderByAskDesc(parsedDay);
            if (!quotes.isEmpty()) {
                return new ResponseEntity<>(quotes.get(0), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No values found for the specified date.", HttpStatus.NOT_FOUND);
            }
        } catch (DateTimeParseException e) {
            logger.error("Invalid date format: {}", day, e);
            return new ResponseEntity<>("Invalid date format. Please use yyyy-MM-dd format.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Error while retrieving highest ask for the given day", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private boolean isValidSymbol(String symbol) {
        return Pattern.matches("^[A-Za-z]{2,6}$", symbol);
    }
}
