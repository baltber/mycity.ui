package ru.mycity.ui.view.order;

import ru.mycity.ui.service.rest.dto.order.OrderRequestDto;

import java.io.Serializable;

public class OrderViewLogic implements Serializable {

    private final OrderView view;

    public OrderViewLogic(OrderView view) {
        this.view = view;
    }

    public void cancelForm() {
//        setFragmentParameter("");
        view.clearSelection();
    }

    public void editProduct(OrderRequestDto dto, String state) {
        view.editProduct(dto, state);
    }

}
