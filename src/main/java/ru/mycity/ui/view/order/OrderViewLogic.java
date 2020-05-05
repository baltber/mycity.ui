package ru.mycity.ui.view.order;

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

}
