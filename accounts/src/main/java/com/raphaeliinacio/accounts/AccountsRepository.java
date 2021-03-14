package com.raphaeliinacio.accounts;

import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends DatastoreRepository<Account, Long> {
}
