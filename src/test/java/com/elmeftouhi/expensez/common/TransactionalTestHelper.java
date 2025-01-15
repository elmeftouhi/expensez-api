package com.elmeftouhi.expensez.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.transaction.support.TransactionTemplate;

@TestComponent
public class TransactionalTestHelper {

    @Autowired
    private TransactionTemplate transactionTemplate;

    public void runInTransaction(RunnableWithException runnable) {
        transactionTemplate.execute(status -> {
            try {
                runnable.run();
                return null;
            }catch (Exception exception){
                status.setRollbackOnly();
                throw new RuntimeException(exception);
            }
        });
    }

    public <R> R runInTransaction(SupplierWithException<R> supplier) {
        return transactionTemplate.execute(status -> {
            try {
                return supplier.get();
            }catch (Exception exception){
                status.setRollbackOnly();
                throw new RuntimeException(exception);
            }
        });
    }

    @FunctionalInterface
    public interface RunnableWithException {
        void run() throws Exception;
    }

    @FunctionalInterface
    public interface SupplierWithException<R> {
        R get() throws Exception;
    }
}
