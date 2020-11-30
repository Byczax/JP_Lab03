package data.models;

public enum OrderStatus {
    PENDING,
    AWAITING,
    REALIZING,
    COMPLETED;
    static final OrderStatus[] VALUES = values();

    public OrderStatus incrementSize() {
        return VALUES[ordinal() + 1];
    }

    public OrderStatus decrementSize() {
        return VALUES[ordinal() - 1];
    }
}

