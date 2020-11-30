package data.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {
    UUID id;
    LocalDate date;
    UUID client;
    OrderStatus status;
    int cost;
    boolean paid;
    List<Product> products;

    public Order(List<Product> products, int cost, UUID client) {
        this.id = UUID.randomUUID();
        this.date = java.time.LocalDate.now();
        this.client = client;
        this.status = OrderStatus.PENDING;
        this.cost = cost;
        this.paid = false;
        this.products = products;
    }
    public Order(UUID uuid,LocalDate date,int cost, UUID client, OrderStatus status, boolean paid, List<Product> products) {
        this.id = uuid;
        this.date = date;
        this.client = client;
        this.status = status;
        this.cost = cost;
        this.paid = paid;
        this.products = products;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public UUID getClient() {
        return client;
    }

    public void setClient(UUID client) {
        this.client = client;
    }

    public boolean isPaid() {
  return paid;
 }

 public void setPaid(boolean paid) {
  this.paid = paid;
 }

 public UUID getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
