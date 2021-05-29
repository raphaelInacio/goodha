package com.raphaeliinacio.accounts.repository;

import com.raphaeliinacio.accounts.client.HabitPresentation;
import com.raphaeliinacio.accounts.client.HabitServiceClient;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HabitRepositoryImpl implements HabitRepository {

    private static final String BACKEND = "habit-service";

    @Autowired
    HabitServiceClient habitServiceClient;

    @Override
    @CircuitBreaker(name = BACKEND)
    @RateLimiter(name = BACKEND)
    @Bulkhead(name = BACKEND)
    @Retry(name = BACKEND)
    @TimeLimiter(name = BACKEND)
    public HabitPresentation getHabitById(Long id) {
        return habitServiceClient.getHabitById(id);
    }

    @CircuitBreaker(name = BACKEND)
    @RateLimiter(name = BACKEND)
    @Bulkhead(name = BACKEND)
    @Retry(name = BACKEND)
    @TimeLimiter(name = BACKEND)
    public List<HabitPresentation> allHabitsByIds(List<Long> ids) {
        return habitServiceClient.allHabitsByIds(ids);
    }
}
