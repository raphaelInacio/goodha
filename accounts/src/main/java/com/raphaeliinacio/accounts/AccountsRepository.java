package com.raphaeliinacio.accounts;

import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
    public interface AccountsRepository extends DatastoreRepository<Account, Long> {
    List<Account> findAll();

    Optional<Account> findByEmail(String email);
}
