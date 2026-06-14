package org.jsp.onlinestore.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Column(name = "total_amount")
    private double totalAmount;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItems> orderItems;

    public int getId() {
    	return id;
    }
    public void setId(int id) {
    	this.id = id; 
    }
    public Date getOrderDate() {
    	return orderDate; 
    }
    public void setOrderDate(Date orderDate) { 
    	this.orderDate = orderDate; 
    }
    public double getTotalAmount() {
    	return totalAmount; 
    }
    public void setTotalAmount(double totalAmount) {
    	this.totalAmount = totalAmount; 
    }
    public Customer getCustomer() {
    	return customer; 
    }
    public void setCustomer(Customer customer) {
    	this.customer = customer; 
    }
    public List<OrderItems> getOrderItems() {
    	return orderItems; 
    }
    public void setOrderItems(List<OrderItems> orderItems) {
    	this.orderItems = orderItems; 
    }
}
