package com.example.hellofx;

import javafx.beans.property.SimpleStringProperty;

public class Customer {
    private final SimpleStringProperty name;
    private final SimpleStringProperty province;

    public Customer(String name, String province) {
        this.name = new SimpleStringProperty(name);
        this.province = new SimpleStringProperty(province);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getProvince() {
        return province.get();
    }

    public SimpleStringProperty provinceProperty() {
        return province;
    }
}