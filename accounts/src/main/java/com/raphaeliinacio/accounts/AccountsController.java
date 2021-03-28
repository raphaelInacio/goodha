package com.raphaeliinacio.accounts;

import com.raphaeliinacio.accounts.client.HabitPresentation;
import com.raphaeliinacio.accounts.client.HabitServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/accounts")
@Slf4j
public class AccountsController {

    @Autowired
    private AccountsRepository repository;

    @Autowired
    private HabitServiceClient client;

    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<AccountPresentation> creteAccount(@RequestBody AccountPresentation presentation) {
        log.info("..: Criando uma nova account, {} ", presentation);
        var accountByEmail = repository.findByEmail(presentation.getEmail());

        if (accountByEmail.isPresent()) {
            log.info("..: Conta de e-mail cadastrada, insira um novo e-mail, {} ", presentation);
            return ResponseEntity.unprocessableEntity().build();
        }

        Account newAccount = repository.save(mapper.map(presentation, Account.class));

        log.info("..: Account criada com sucesso :.. , {} ", newAccount);
        return new ResponseEntity(mapper.map(newAccount, AccountPresentation.class), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountPresentation> getAccount(@PathVariable Long id) {
        log.info("..: Buscando uma nova account, {} ", id);
        var account = repository.findById(id);

        if (account.isEmpty()) {
            log.info("..: Account não encontrada, {} :.. ", id);
            return ResponseEntity.noContent().build();
        }

        if (Objects.isNull(account.get().getMyHabits()) || account.get().getMyHabits().isEmpty()) {
            return ResponseEntity.ok(AccountPresentation.fromDomain(account.get(), Collections.emptyList()));
        }

        List<HabitPresentation> habitPresentations = client.allHabitsByIds(account.get().getMyHabits());

        log.info("..: Account encontrada, {} :.. ", account);

        return ResponseEntity.ok(AccountPresentation.fromDomain(account.get(), habitPresentations));
    }

    @GetMapping
    public ResponseEntity<List<AccountPresentation>> getAccounts() {
        log.info("..: Listando todas accounts");

        var accounts = repository.findAll();

        List<AccountPresentation> accountPresentations = accounts
                .stream()
                .parallel()
                .filter(Objects::nonNull)
                .map(account -> {
                    if (Objects.isNull(account.getMyHabits())) {
                        return AccountPresentation.fromDomain(account, Collections.EMPTY_LIST);
                    }
                    List<HabitPresentation> habitPresentations = client.allHabitsByIds(account.getMyHabits());
                    return AccountPresentation.fromDomain(account, habitPresentations);
                })
                .collect(Collectors.toList());


        log.info("..: Accounts encontradas, {} :.. ", accountPresentations);

        return ResponseEntity.ok(accountPresentations);
    }

    @PostMapping("/{accountId}/add-habit")
    public ResponseEntity<Void> addHabit(
            @PathVariable("accountId") Long accountId,
            @RequestBody AddHabitPresentation addHabitPresentation) {

        log.info("..: Adicionando um novo habit");

        var account = repository.findById(accountId);

        if (account.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        var updateAccount = account.get();
        updateAccount.addHabit(addHabitPresentation.getId());

        repository.save(updateAccount);

        return ResponseEntity.ok().build();
    }
}
