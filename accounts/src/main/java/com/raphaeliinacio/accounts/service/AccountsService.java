package com.raphaeliinacio.accounts.service;

import com.raphaeliinacio.accounts.AccountPresentation;
import com.raphaeliinacio.accounts.AccountsRepository;
import com.raphaeliinacio.accounts.client.HabitPresentation;
import com.raphaeliinacio.accounts.client.RecordRepresentation;
import com.raphaeliinacio.accounts.repository.HabitRepository;
import com.raphaeliinacio.accounts.repository.RecordRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccountsService {

    @Autowired
    private HabitRepository repository;

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private AccountsRepository accountsRepository;

    public AccountPresentation getAccountById(Long id) {

        log.info("..: Buscando uma account, {} ", id);

        var account = accountsRepository.findById(id);

        if (account.isEmpty()) {
            log.info("..: Account não encontrada, {} :.. ", id);
            return null;
        }

        if (Objects.isNull(account.get().getMyHabits()) || account.get().getMyHabits().isEmpty()) {
            return AccountPresentation.fromDomain(account.get(), Collections.emptyList());
        }

        log.info("..: Buscando habitos, {} ", id);
        List<HabitPresentation> habitPresentations = repository.allHabitsByIds(account.get().getMyHabits());

        log.info("..: Buscando registros, {} ", id);
        List<RecordRepresentation> recordsByAccount = recordRepository.getRecordsByAccount(account.get().getId());


        if (Objects.nonNull(recordsByAccount)) {
            habitPresentations
                    .parallelStream()
                    .forEach(habitPresentation -> habitPresentation.addRecords(
                            recordsByAccount
                                    .parallelStream()
                                    .filter(recordRepresentation -> Objects.equals(habitPresentation.getId(), recordRepresentation.getHabitId()))
                                    .collect(Collectors.toList())));
        }

        log.info("..: Registos encontrados, {} ", habitPresentations);

        return AccountPresentation.fromDomain(account.get(), habitPresentations);
    }

}
