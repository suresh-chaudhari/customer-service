package com.microservice.customer.client;

import com.microservice.customer.model.Account;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "account-service", url = "${feign.accountServiceUrl}")
public interface AccountClient {

    @RequestMapping(method = RequestMethod.GET, value = "/accounts/customer/{customerId}")
    List<Account> getAccounts(@PathVariable("customerId") Integer customerId);
    
}
