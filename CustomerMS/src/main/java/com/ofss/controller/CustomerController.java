package com.ofss.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ofss.model.Customer;

@RestController
public class CustomerController {

	@Autowired
	RestTemplate restTemplate;
	List<Customer> allCustomers=new ArrayList<>();
	
	public CustomerController() {
		Customer c1=new Customer(1, "Prakash", Arrays.asList(101,102));
		Customer c2=new Customer(2, "Jayanth", Arrays.asList(102,104));
		Customer c3=new Customer(3, "Kusuma", Arrays.asList(105,101));
		Customer c4=new Customer(4, "Mehul", null);
		Customer c5=new Customer(5, "Sathyasri", Arrays.asList(101,102,103,104,105));
		allCustomers.add(c1);
		allCustomers.add(c2);
		allCustomers.add(c3);
		allCustomers.add(c4);
		allCustomers.add(c5);
		
		
	}
	
	// Get all customers
	// GET /customers
	@RequestMapping(value="/customers",method=RequestMethod.GET)
	public List<Customer> fetchAllCustomers() {
		return allCustomers;
	}
	
	// Get customer by id
	// GET /customers/id/{id}
	@RequestMapping(value="/customers/id/{id}",method=RequestMethod.GET)
	public Customer fetchCustomerById(@PathVariable("id") int custId) {
		for (Customer c:allCustomers) {
			if (c.getCustomerId()==custId)
				return c;
		}
		return null;
	}
	
	
	// 3rd api, which is going to take part in other ms communication
	// GET /customers/id/{id}/stocks
	// This 3rd api should not return only the stock ids
	// but it must return the detaisl of all the stocks held by this customer id
	// From this API, it has to call the 2nd MS (STOCK MS) API to get stock object for a given stock id
	// The method call is not going to be a local method call, but it is a remote method call
	// In Spring based project, if we want to make a remote method call, we must use this RestTemplate class
	@RequestMapping(value="/customers/id/{id}/stocks",method=RequestMethod.GET)
	public List<Object> fetchAllStocksByCustomerId(@PathVariable("id") int custId) {
		List<Object> allStocksDetails=new ArrayList<>();
		// verify if the customer id exists or not
		for (Customer c:allCustomers) {
			if (c.getCustomerId()==custId) {
				// Get all his stocks ids and display them
				List<Integer> allStocksByCustomer=c.getStockIds();
				if (allStocksByCustomer != null) {
					for (int stockId:allStocksByCustomer) {
						System.out.println("Stock id is "+stockId);
						// I am going to call the API from the 2nd microservice
						// Provide the stock id to the 2nd microservice (StocksMS) and get the whole Stock object
						Object stockObject=getAsStockObject(stockId);
						allStocksDetails.add(stockObject);
						
						
					}
				}
				else 
				{
					// customer is present, but no stocks bought by this customer
					return null;
				}
			}
			
		} // end of for loop
		return allStocksDetails;
		
	}
	
	public Object getAsStockObject(int stockId) {
		// We are going to make a remote method call to the 2nd microservice
		// using RestTemplate class
		
		String stockServiceURL="http://STOCKMS/stocks/id/{stockId}";
		// We don't have the Stock class definition
		// Class Object is the root of the class hierarchy.
		// Every class has Object as a superclass. All objects,including arrays, implement the methods of this class.
		return restTemplate.getForObject(stockServiceURL, Object.class,stockId);
		
	}
}
