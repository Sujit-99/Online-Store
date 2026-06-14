package org.jsp.onlinestore.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import org.jsp.onlinestore.dto.Category;
import org.jsp.onlinestore.dto.Customer;
import org.jsp.onlinestore.dto.OrderItems;
import org.jsp.onlinestore.dto.Orders;
import org.jsp.onlinestore.dto.Product;

public class OnlineStoreDao {

    private EntityManagerFactory emf;

    public OnlineStoreDao() {
        this.emf = Persistence.createEntityManagerFactory("dev");
    }

    public void close() {
        if (emf != null) {
            emf.close();
        }
    }

    
//==================================Categories List==========================
    public List<Category> getAllCategories() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("FROM Category", Category.class).getResultList();
        } finally {
            em.close();
        }
    }
//=============================Products List=================================
    public List<Product> getProducts(Integer categoryId) {
        EntityManager em = emf.createEntityManager();
        try {
            if (categoryId != null) {
                return em.createQuery("FROM Product p WHERE p.category.id = :catId", Product.class)
                         .setParameter("catId", categoryId)
                         .getResultList();
            } else {
                return em.createQuery("FROM Product", Product.class).getResultList();
            }
        } finally {
            em.close();
        }
    }

    public Product getProductById(int productId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Product.class, productId);
        } finally {
            em.close();
        }
    }
//================================Authentication==============================
    public Customer login(String username, String password) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("FROM Customer c WHERE c.username = :uname AND c.password = :pass", Customer.class)
                     .setParameter("uname", username)
                     .setParameter("pass", password)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null; 
        } finally {
            em.close();
        }
    }
//=====================================Register========================================
    public Customer register(Customer customer) throws Exception {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(customer);
            em.getTransaction().commit();
            return customer;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception("Registration failed. Username may already exist.");
        } finally {
            em.close();
        }
    }

    //====================================CHECKOUT TRANSACTION=============================
    public Orders processCheckout(Customer customer, Map<Product, Integer> cart) throws Exception {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
            Orders newOrder = new Orders();
            newOrder.setOrderDate(new Date());

            Customer managedCustomer = em.merge(customer);
            newOrder.setCustomer(managedCustomer);

            double totalAmount = 0;
            List<OrderItems> orderItemsList = new ArrayList<>();

            for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
                Product cartProduct = entry.getKey();
                int quantity = entry.getValue();

                // Fetch fresh product data to verify stock
                Product dbProduct = em.find(Product.class, cartProduct.getId());

                if (dbProduct == null) {
                    throw new Exception("Product ID " + cartProduct.getId() + " no longer exists.");
                }

                if (dbProduct.getStockQuantity() < quantity) {
                    throw new Exception(dbProduct.getName() + " only has " + dbProduct.getStockQuantity() + " left in stock.");
                }

                // ------------stock Calculation----------------
                dbProduct.setStockQuantity(dbProduct.getStockQuantity() - quantity);

                OrderItems item = new OrderItems();
                item.setOrder(newOrder);
                item.setProduct(dbProduct);
                item.setQuantity(quantity);
                item.setPurchasePrice(dbProduct.getPrice());

                orderItemsList.add(item);
                totalAmount += (dbProduct.getPrice() * quantity);
            }

            newOrder.setTotalAmount(totalAmount);
            newOrder.setOrderItems(orderItemsList);

            em.persist(newOrder);
            em.getTransaction().commit();
            
            return newOrder; 

        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e; 
        } finally {
            em.close();
        }
    }
}