package models;

import java.math.BigDecimal;

public class OrderItem {
    private int id;
    private int beverageQuantity;
    private BigDecimal beveragePrice;
    private int beverageId;
    private String beverageName;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBeverageQuantity() {
        return beverageQuantity;
    }

    public void setBeverageQuantity(int beverageQuantity) {
        this.beverageQuantity = beverageQuantity;
    }

    public BigDecimal getBeveragePrice() {
        return beveragePrice;
    }

    public void setBeveragePrice(BigDecimal beveragePrice) {
        this.beveragePrice = beveragePrice;
    }

    public int getBeverageId() {
        return beverageId;
    }

    public void setBeverageId(int beverageId) {
        this.beverageId = beverageId;
    }

    public String getBeverageName() {
        return beverageName;
    }

    public void setBeverageName(String beverageName) {
        this.beverageName = beverageName;
    }
}
