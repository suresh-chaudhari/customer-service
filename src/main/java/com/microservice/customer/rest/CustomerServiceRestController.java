package com.microservice.customer.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.microservice.customer.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.customer.client.AccountClient;
import com.microservice.customer.model.Customer;
import com.microservice.customer.model.CustomerType;

@RestController
public class CustomerServiceRestController {

	protected Logger logger = Logger.getLogger(CustomerServiceRestController.class.getName());

	@Autowired
	private LoadBalancerClient loadBalancer;

	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	private AccountClient accountClient;


	private List<Customer> customers;

	public CustomerServiceRestController() {
		customers = new ArrayList<>();
		customers.add(new Customer(1, "12345", "Adam Kowalski", CustomerType.INDIVIDUAL));
		customers.add(new Customer(2, "12346", "Anna Malinowska", CustomerType.INDIVIDUAL));
		customers.add(new Customer(3, "12347", "Paweł Michalski", CustomerType.INDIVIDUAL));
		customers.add(new Customer(4, "12348", "Karolina Lewandowska", CustomerType.INDIVIDUAL));
	}

	@RequestMapping(value = "/customers", method = RequestMethod.GET)
	public List<Customer> findAll() {
		logger.info("Customer.findAll()");
		return customers;
	}
	
	@RequestMapping(value = "/customers/{id}", method = RequestMethod.GET)
	public Customer findById(@PathVariable("id") Integer id) {
		logger.info(String.format("Customer.findById(%s)", id));
		Customer customer = customers.stream().filter(it -> it.getId().intValue()==id.intValue()).findFirst().get();
		List<Account> accounts =  accountClient.getAccounts(id);
		customer.setAccounts(accounts);
		return customer;
	}
	
}
