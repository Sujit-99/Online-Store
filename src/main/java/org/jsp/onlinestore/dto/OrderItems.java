package org.jsp.onlinestore.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_items")
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantity;
    
    @Column(name = "purchase_price")
    private double purchasePrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public int getId() {
    	return id; 
    }
    public void setId(int id) {
    	this.id = id; 
    }
    public int getQuantity() {
    	return quantity; 
    }
    public void setQuantity(int quantity) {
    	this.quantity = quantity; 
    }
    public double getPurchasePrice() {
    	return purchasePrice; 
    }
    public void setPurchasePrice(double purchasePrice) {
    	this.purchasePrice = purchasePrice; 
    }
    public Orders getOrder() {
    	return order; 
    }
    public void setOrder(Orders order) {
    	this.order = order; 
    }
    public Product getProduct() {
    	return product; 
    }
    public void setProduct(Product product) {
    	this.product = product; 
    }
}
