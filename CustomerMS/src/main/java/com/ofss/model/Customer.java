package com.ofss.model;

import java.util.List;

public class Customer {
	int customerId;
	String customerName;
	List<Integer> stockIds;
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Customer(int customerId, String customerName, List<Integer> stockIds) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.stockIds = stockIds;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public List<Integer> getStockIds() {
		return stockIds;
	}
	public void setStockIds(List<Integer> stockIds) {
		this.stockIds = stockIds;
	}
	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", stockIds=" + stockIds + "]";
	}
	
	
}
