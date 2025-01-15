package com.elmeftouhi.expensez.expense;

import com.elmeftouhi.expensez.common.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ExpenseControllerTest extends IntegrationTest {

    protected ExpenseControllerTest() {
        super(HttpMethod.GET, "/expenses");
    }

    @Test
    void shouldGetAll_whenEndpoint() throws Exception{
        aMockMvcRequest().execute().andExpect(status().isOk());
    }

}
