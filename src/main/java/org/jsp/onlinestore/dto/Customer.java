package org.jsp.onlinestore.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String email;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CustomerProfile profile;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Orders> orders;

    public int getId() {
    	return id; 
    }
    public void setId(int id) {
    	this.id = id; 
    }
    public String getUsername() {
    	return username; 
    }
    public void setUsername(String username) {
    	this.username = username; 
    }
    public String getPassword() {
    	return password; 
    }
    public void setPassword(String password) {
    	this.password = password; 
    }
    public String getEmail() {
    	return email; 
    }
    public void setEmail(String email) {
    	this.email = email; 
    }
    public CustomerProfile getProfile() {
    	return profile; 
    }
    public void setProfile(CustomerProfile profile) {
    	this.profile = profile; 
    }
    public List<Orders> getOrders() {
    	return orders; 
    }
    public void setOrders(List<Orders> orders) { 
    	this.orders = orders; 
    }
}
