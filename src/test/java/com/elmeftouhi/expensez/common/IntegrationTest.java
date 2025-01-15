package com.elmeftouhi.expensez.common;

import com.elmeftouhi.expensez.core.jpa.BasicEntity;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "/sql/clear_tables.sql")
@Import({TransactionalTestHelper.class, FlywayConfiguration.class})
@ActiveProfiles({"test"})
public abstract class IntegrationTest {

    @Autowired
    protected EntityManager entityManager;
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    private TransactionalTestHelper transactionalTestHelper;

    private final HttpMethod httpMethod;
    private final String urlTemplate;


    protected IntegrationTest(HttpMethod httpMethod, String urlTemplate) {
        this.httpMethod = httpMethod;
        this.urlTemplate = urlTemplate;
    }

    protected MockMvcRequestBuilder aMockMvcRequest() { return aMockMvcRequest(httpMethod, urlTemplate); }

    protected MockMvcRequestBuilder aMockMvcRequest(HttpMethod httpMethod, String urlTemplate){
        return new MockMvcRequestBuilder(mockMvc, httpMethod, urlTemplate);
    }

    protected void runInTransaction(TransactionalTestHelper.RunnableWithException runnable) {
        transactionalTestHelper.runInTransaction(runnable);
    }

    protected <R> R runInTransaction(TransactionalTestHelper.SupplierWithException<R> supplier) {
        return transactionalTestHelper.runInTransaction(supplier);
    }

    protected void flush() {
        entityManager.flush();
    }

    protected <T extends BasicEntity> T persist(T entity) {
        return persistInternal(entity);
    }

    private <T> T persistInternal(T entity) {
        return runInTransaction( () -> {
            entityManager.persist(entity);
            return entity;
        });
    }
}
