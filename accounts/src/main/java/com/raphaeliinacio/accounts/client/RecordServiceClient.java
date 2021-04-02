package com.raphaeliinacio.accounts.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "recordService", url = "https://records-service-dot-macro-scion-300810.rj.r.appspot.com/v1/records")
public interface RecordServiceClient {
    @GetMapping("/{accountId}")
    List<RecordRepresentation> getRecordsByAccount(@PathVariable("accountId") Long accountId);
}
