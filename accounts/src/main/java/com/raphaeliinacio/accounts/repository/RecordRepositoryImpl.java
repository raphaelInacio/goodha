package com.raphaeliinacio.accounts.repository;

import com.raphaeliinacio.accounts.client.RecordRepresentation;
import com.raphaeliinacio.accounts.client.RecordServiceClient;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecordRepositoryImpl implements RecordRepository {

    private static final String BACKEND = "records-service";
    @Autowired
    private RecordServiceClient recordServiceClient;

    @Override
    public List<RecordRepresentation> getRecordsByAccount(Long accountId) {
        return recordServiceClient.getRecordsByAccount(accountId);
    }
}
