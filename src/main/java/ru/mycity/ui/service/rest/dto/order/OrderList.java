package ru.mycity.ui.service.rest.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OrderList {
    @JsonProperty("order")
    private List<OrderDto> orderDtoList;
    private String delivery;
    private int deliveryPrice;
    private int totalPrice;



    public OrderList() {
    }

    public OrderList(List<OrderDto> orderDtoList) {
        this.orderDtoList = orderDtoList;
    }

    public List<OrderDto> getOrderDtoList() {
        return orderDtoList;
    }

    public void setOrderDtoList(List<OrderDto> orderDtoList) {
        this.orderDtoList = orderDtoList;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public int getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(int deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }


    @Override
    public String toString() {
        return "OrderList{" +
                "orderDtoList=" + orderDtoList +
                ", delivery='" + delivery + '\'' +
                ", deliveryPrice=" + deliveryPrice +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
