package com.raphaeliinacio.accounts.client;

import feign.Body;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "habbitService", url = "https://habits-service-dot-macro-scion-300810.rj.r.appspot.com/v1/habits")
public interface HabitServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    HabitPresentation getHabitById(@PathVariable("id") Long id);

    @RequestMapping(method = RequestMethod.POST, value = "/all")
    HabitPresentation allHabitsByIds(@RequestBody List<Long> ids);
}
