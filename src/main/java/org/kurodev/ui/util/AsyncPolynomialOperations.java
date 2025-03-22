package org.kurodev.ui.util;

import org.kurodev.fields.FiniteField;
import org.kurodev.fields.Table;
import org.kurodev.polynomials.MathsUtil;
import org.kurodev.polynomials.Polynomial;
import org.kurodev.ui.components.table.TableType;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncPolynomialOperations {
    private static final Executor DAEMON_EXECUTOR = Executors.newCachedThreadPool(runnable -> {
        Thread thread = new Thread(runnable);
        thread.setName("AsyncPolynomialOperations-Thread");
        thread.setDaemon(true);
        return thread;
    });

    public static CompletableFuture<List<Polynomial>> findIrreducibles(int base, int power) {
        CompletableFuture<List<Polynomial>> future = new CompletableFuture<>();
        future.completeAsync(() -> MathsUtil.findIrreducible(power, base), DAEMON_EXECUTOR);
        return future;
    }

    public static CompletableFuture<Table> calculateTable(FiniteField field, TableType type) {
        CompletableFuture<Table> future = new CompletableFuture<>();
        switch (type) {
            case ADDITION -> future.completeAsync(field::computeAdditionTable, DAEMON_EXECUTOR);
            case MULTIPLICATION -> future.completeAsync(field::computeMultiplicationTable, DAEMON_EXECUTOR);
        }
        return future;
    }
}
