package org.jsp.onlinestore.controller;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.jsp.onlinestore.dao.OnlineStoreDao;
import org.jsp.onlinestore.dto.Category;
import org.jsp.onlinestore.dto.Customer;
import org.jsp.onlinestore.dto.CustomerProfile;
import org.jsp.onlinestore.dto.Orders;
import org.jsp.onlinestore.dto.Product;

public class OnlineStoreController {

    private static Scanner scanner = new Scanner(System.in);
    private static Map<Product, Integer> inMemoryCart = new HashMap<>();
    private static Customer currentCustomer = null;
    
    private static OnlineStoreDao dao = new OnlineStoreDao(); 

    public static void main(String[] args) {
        System.out.println("======================================");
        System.out.println("  SYSTEM BOOTED. WELCOME TO THE STORE!  ");
        System.out.println("======================================");

        boolean running = true;
        while (running) {
            System.out.println("\n--- MAIN STOREFRONT ---");
            System.out.println("[1] View All Products");
            System.out.println("[2] Search by Category");
            System.out.println("[3] View Profile & Cart");
            System.out.println("[0] Exit");
            System.out.print("Choose an option: ");

            int choice = -1;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); 
                continue;
            }

            switch (choice) {
                case 1:
                    showProductsAndAddToCart(null); 
                    break;
                case 2:
                    searchByCategory();
                    break;
                case 3:
                    viewProfileAndCart();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        dao.close(); 
        scanner.close();
        System.out.println("Thankyou!");
    }
//=============================================Search By Category=====================================
    private static void searchByCategory() {
        List<Category> categories = dao.getAllCategories(); 

        if (categories.isEmpty()) {
            System.out.println("No categories found in the database.");
            return;
        }

        System.out.println("\n--- CATEGORIES ---");
        for (Category cat : categories) {
            System.out.println("[" + cat.getId() + "] " + cat.getName());
        }

        System.out.print("Enter Category ID: ");
        int catId = scanner.nextInt();
        scanner.nextLine();
        
        showProductsAndAddToCart(catId);
    }
//========================Products and add to cart===============================
    private static void showProductsAndAddToCart(Integer categoryId) {
        List<Product> products = dao.getProducts(categoryId); 

        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }

        System.out.println("\nID | Name | Price | Stock");
        System.out.println("-----------------------------------");
        for (Product p : products) {
            System.out.println(p.getId() + " | " + p.getName() + " | $" + p.getPrice() + " | " + p.getStockQuantity());
        }

        System.out.print("\nEnter Product ID to add to cart (or 0 to go back): ");
        int productId = scanner.nextInt();
        if (productId == 0) {
            scanner.nextLine();
            return;
        }

        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        Product selectedProduct = dao.getProductById(productId); 

        if (selectedProduct == null) {
            System.out.println("Error: Invalid Product ID.");
        } else if (selectedProduct.getStockQuantity() < quantity) {
            System.out.println("Error: Not enough stock! Only " + selectedProduct.getStockQuantity() + " left.");
        } else {
            int existingQty = inMemoryCart.getOrDefault(selectedProduct, 0);
            inMemoryCart.put(selectedProduct, existingQty + quantity);
            System.out.println("Success! Added " + quantity + " x " + selectedProduct.getName() + " to your cart.");
        }
    }
//====================================User Profile and cart====================
    private static void viewProfileAndCart() {
        if (currentCustomer == null) {
            System.out.println("\nYou must be logged in to view your cart.");
            loginSignup();
            if (currentCustomer == null) return; 
        }

        System.out.println("\n--- YOUR PROFILE & CART ---");
        System.out.println("Customer: " + currentCustomer.getUsername());

        if (inMemoryCart.isEmpty()) {
            System.out.println("Your cart is currently empty.");
            return;
        }

        double total = 0;
        System.out.println("\nCart Contents:");
        for (Map.Entry<Product, Integer> entry : inMemoryCart.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            double subtotal = p.getPrice() * qty;
            total += subtotal;
            System.out.println("- " + p.getName() + " (Qty: " + qty + ") | Subtotal: $" + subtotal);
        }
        System.out.println("-----------------------------------");
        System.out.println("TOTAL BILL: $" + total);

        System.out.println("\n[1] Confirm Order (Checkout)  [2] Clear Cart  [0] Back");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            try {
                Orders completedOrder = dao.processCheckout(currentCustomer, inMemoryCart);
                
                inMemoryCart.clear();
                System.out.println("\n======================================");
                System.out.println("CHECKOUT SUCCESSFUL!");
                System.out.println("Order ID: " + completedOrder.getId());
                System.out.println("Total Billed: $" + completedOrder.getTotalAmount());
                System.out.println("======================================");
            } catch (Exception e) {
                System.out.println("\nCHECKOUT FAILED: " + e.getMessage());
                System.out.println("No charges were made. Please adjust your cart.");
            }
        } else if (choice == 2) {
            inMemoryCart.clear();
            System.out.println("Cart cleared.");
        }
    }
//====================================AUTHENTICATION=============================
    private static void loginSignup() {
        System.out.println("\n--- Login ---");
        System.out.println("[1] Login");
        System.out.println("[2] Sign Up");
        System.out.println("[0] Cancel");
        System.out.print("Choose option: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); 

        if (choice == 1) {
            System.out.print("Enter Username: ");
            String username = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            currentCustomer = dao.login(username, password); 

            if (currentCustomer != null) {
                System.out.println("Login successful! Welcome back.");
            } else {
                System.out.println("Error: Invalid credentials.");
            }

        } else if (choice == 2) {
            System.out.print("Choose a Username: ");
            String username = scanner.nextLine();
            System.out.print("Choose a Password: ");
            String password = scanner.nextLine();
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();
            
            System.out.print("Enter Shipping Address: ");
            String address = scanner.nextLine();
            System.out.print("Enter Phone Number: ");
            String phone = scanner.nextLine();

            Customer newCustomer = new Customer();
            newCustomer.setUsername(username);
            newCustomer.setPassword(password);
            newCustomer.setEmail(email);

            CustomerProfile profile = new CustomerProfile();
            profile.setAddress(address);
            profile.setPhone(phone);
            
            
            profile.setCustomer(newCustomer);
            newCustomer.setProfile(profile);

            try {
                currentCustomer = dao.register(newCustomer); 
                System.out.println("Account created successfully! You are now logged in.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
 }
