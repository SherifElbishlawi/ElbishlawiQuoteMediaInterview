package com.quotemedia.interview.quoteservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, QuoteId> {

    @Query("SELECT q FROM Quote q WHERE q.symbol = :symbol ORDER BY q.day DESC")
    List<Quote> findAllBySymbolOrderByDayDesc(@Param("symbol") String symbol);

    default Optional<Quote> findLatestBySymbol(String symbol) {
        List<Quote> quotes = findAllBySymbolOrderByDayDesc(symbol);
        return quotes.isEmpty() ? Optional.empty() : Optional.of(quotes.get(0));
    }
}