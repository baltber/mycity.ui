package ru.mycity.ui.service.rest.dto.order;

public class OrderDto {

    private String name;
    private String quantity;
    private String price;
    private String amount;

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public String getAmount() {
        return amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "name='" + name + '\'' +
                ", quantity='" + quantity + '\'' +
                ", price='" + price + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}