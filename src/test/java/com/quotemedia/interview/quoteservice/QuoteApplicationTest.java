package com.quotemedia.interview.quoteservice;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureMockMvc
public class QuoteApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuoteController quoteController;

    @Autowired
    private CacheManager cacheManager;
    
    @Test
    public void testGetLatestQuote_validSymbol() throws Exception {
        mockMvc.perform(get("/symbols/MSFT/quotes/latest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.symbol", is("MSFT")))
                .andExpect(jsonPath("$.bid", is(2.51)))
                .andExpect(jsonPath("$.ask", is(2.96)));
    }

    @Test
    public void testGetLatestQuote_invalidSymbol() throws Exception {
        mockMvc.perform(get("/symbols/XYZ/quotes/latest"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetLatestQuote_symbolTooShort() throws Exception {
        mockMvc.perform(get("/symbols/A/quotes/latest"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetLatestQuote_symbolTooLong() throws Exception {
        mockMvc.perform(get("/symbols/ABCDEFG/quotes/latest"))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testHighestAsk() throws Exception {
        LocalDate date = LocalDate.of(2020, 1, 1);
        mockMvc.perform(get("/symbols/highest-ask/{date}", date))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.symbol", is("FB")))
                .andExpect(jsonPath("$.ask", is(5.66)));

        // Test caching
        Quote cachedQuote = (Quote) cacheManager.getCache("highestAsk").get(date).get();
        assertThat(cachedQuote.getSymbol()).isEqualTo("FB");
        assertThat(cachedQuote.getAsk()).isEqualByComparingTo("5.66");
    }

    @Test
    public void testHighestAskWithInvalidDateFormat() throws Exception {
        mockMvc.perform(get("/symbols/highest-ask/2020-13-32"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid date format. Please use 'yyyy-MM-dd' format."));
    }

    @Test
    public void testHighestAskWithNoData() throws Exception {
        LocalDate date = LocalDate.of(2030, 1, 1);
        mockMvc.perform(get("/symbols/highest-ask/{date}", date))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No data found for the specified date."));
    }
}
