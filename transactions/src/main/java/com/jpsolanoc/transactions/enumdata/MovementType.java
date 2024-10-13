package com.jpsolanoc.transactions.enumdata;

public enum MovementType {
    AHORROS(1, "Ahorros"),
    CORRIENTE(2, "Corriente");

    private final int id;
    private final String description;

    MovementType(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return description;
    }
}