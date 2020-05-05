package ru.mycity.ui.service.rest.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FullOrderDto {

    @JsonProperty("start")
    private int start;
    @JsonProperty("size")
    private int size;
    @JsonProperty("total")
    private int total;
    @JsonProperty("orders")
    private List<OrderRequestDto> orders;

    public FullOrderDto(int start, int size, int total) {
        this.start = start;
        this.size = size;
        this.total = total;
    }

    public FullOrderDto() {
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<OrderRequestDto> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderRequestDto> orders) {
        this.orders = orders;
    }
}
