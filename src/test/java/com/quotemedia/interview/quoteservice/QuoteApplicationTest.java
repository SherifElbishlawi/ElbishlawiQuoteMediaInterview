package com.quotemedia.interview.quoteservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class QuoteApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuoteController quoteController;

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
}
