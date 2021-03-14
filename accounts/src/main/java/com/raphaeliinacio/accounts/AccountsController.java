package com.raphaeliinacio.accounts;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/accounts")
@Slf4j
public class AccountsController {

    @Autowired
    private AccountsRepository repository;

    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<AccountPresentation> creteAccount(@RequestBody AccountPresentation presentation) {
        log.info("..: Criando uma nova account, {} ", presentation);
        Account newAccount = repository.save(mapper.map(presentation, Account.class));
        log.info("..: Account criada com sucesso :.. , {} ", newAccount);
        return new ResponseEntity(mapper.map(newAccount, AccountPresentation.class), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccount(@PathVariable Long id) {
        log.info("..: Buscando uma nova account, {} ", id);
        var account = repository.findById(id);

        if (account.isEmpty()) {
            log.info("..: Account não encontrada, {} :.. ", id);
            return ResponseEntity.noContent().build();
        }

        log.info("..: Account encontrada, {} :.. ", account);
        return ResponseEntity.ok(mapper.map(account.get(), AccountPresentation.class));
    }
}
