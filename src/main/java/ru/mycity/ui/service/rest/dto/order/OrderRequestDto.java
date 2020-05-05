package ru.mycity.ui.service.rest.dto.order;

public class OrderRequestDto {

    private String summary;
    private String name;
    private String address;
    private String flat;
    private String phone;
    private String email;
    private String comment;
    private OrderList orderList;
    private String organisationGuid;

    public OrderRequestDto() {
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public OrderList getOrderList() {
        return orderList;
    }

    public void setOrderList(OrderList orderList) {
        this.orderList = orderList;
    }

    public String getOrganisationGuid() {
        return organisationGuid;
    }

    public void setOrganisationGuid(String organisationGuid) {
        this.organisationGuid = organisationGuid;
    }
}
