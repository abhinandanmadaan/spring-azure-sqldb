package com.example.demo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.model.Customer;

public class CustomerDBRowMapper implements RowMapper<Customer>{

	@Override
	public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Customer customer = new Customer();
		
		customer.setCustomerID(rs.getInt("CustomerID"));
		customer.setFirstName(rs.getString("FirstName"));
		customer.setLastName(rs.getString("LastName"));
		return customer;
	}

}
