package com.tuan.coffeemanager.model;

import java.io.Serializable;

public class Table implements Serializable {

    private String id;
    private int number;

    public Table() {
    }

    public Table(String id, int number) {
        this.id = id;
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
