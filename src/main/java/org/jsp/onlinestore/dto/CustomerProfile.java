package org.jsp.onlinestore.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "customer_profiles")
public class CustomerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String address;
    private String phone;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public int getId() {
    	return id; 
    }
    public void setId(int id) {
    	this.id = id; 
    }
    public String getAddress() {
    	return address; 
    }
    public void setAddress(String address) {
    	this.address = address; 
    }
    public String getPhone() {
    	return phone; 
    }
    public void setPhone(String phone) {
    	this.phone = phone; 
    }
    public Customer getCustomer() {
    	return customer; 
    }
    public void setCustomer(Customer customer) {
    	this.customer = customer; 
    }
}
