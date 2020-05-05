package ru.mycity.ui.view.order;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import org.vaadin.klaudeta.PaginatedGrid;
import ru.mycity.ui.service.rest.dto.auth.UserDto;
import ru.mycity.ui.view.AppView;

import java.io.Serializable;

public class OrderViewLogic implements Serializable {

    private final OrderView view;

    public OrderViewLogic(OrderView view) {
        this.view = view;
    }

    public void init() {

    }


}
