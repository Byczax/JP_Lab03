package data.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Client extends Person {
    private List<Product> basket;
    private List<Order> orders;

    public Client(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.basket = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public Client(String uuid, String name, List<Product> products) {
        this.id = UUID.fromString(uuid);
        this.name = name;
        this.basket = products;
        this.orders = new ArrayList<>();
    }


    public UUID getUUID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getBasket() {
        return basket;
    }

    public void setBasket(List<Product> orders) {
        this.basket = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
