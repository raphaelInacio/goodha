package com.raphaeliinacio.accounts.repository;

import com.raphaeliinacio.accounts.client.RecordRepresentation;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface RecordRepository {
    List<RecordRepresentation> getRecordsByAccount(@PathVariable("accountId") Long accountId);
}
