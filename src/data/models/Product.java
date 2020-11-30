package data.models;

import java.util.UUID;

public class Product {
    UUID id;
    String name;
    int value;
    String unit;
    float cost;

    public Product(String name, int value, String unit, float cost) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.value = value;
        this.unit = unit;
        this.cost = cost;
    }

    public Product(String uuid, String name, int value, String unit, float cost) {
        this.id = UUID.fromString(uuid);
        this.name = name;
        this.value = value;
        this.unit = unit;
        this.cost = cost;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }
}
