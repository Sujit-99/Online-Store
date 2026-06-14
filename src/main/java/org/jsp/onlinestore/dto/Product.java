package org.jsp.onlinestore.dto;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double price;
    
    @Column(name = "stock_quantity")
    private int stockQuantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    
    public int getId() {
    	return id;
    }
    public void setId(int id) {
    	this.id = id; 
    }
    public String getName() {
    	return name; 
    }
    public void setName(String name) {
    	this.name = name; 
    }
    public double getPrice() {
    	return price; 
    }
    public void setPrice(double price) {
    	this.price = price; 
    }
    public int getStockQuantity() {
    	return stockQuantity; 
    }
    public void setStockQuantity(int stockQuantity) {
    	this.stockQuantity = stockQuantity; 
    }
    public Category getCategory() {
    	return category; 
    }
    public void setCategory(Category category) {
    	this.category = category; 
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}