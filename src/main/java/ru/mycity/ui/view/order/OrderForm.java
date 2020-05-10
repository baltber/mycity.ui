package ru.mycity.ui.view.order;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializableFunction;
import ru.mycity.ui.service.rest.dto.order.OrderDto;
import ru.mycity.ui.service.rest.dto.order.OrderList;
import ru.mycity.ui.service.rest.dto.order.OrderRequestDto;
import ru.mycity.ui.view.common.InfoLayout;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * A form for editing a single product.
 */
public class OrderForm extends Div {

    private final VerticalLayout content;

    private final TextField name;
    private final TextField address;
    private final TextField phone;
    private final Grid<OrderDto> order;
    private Button save;
    private Button discard;
    private Button cancel;
    private final Button delete;
    private Binder<OrderRequestDto> binder;
    private Binder<OrderList> listBinder;
    private OrderViewLogic viewLogic;
    private final InfoLayout delivery;
    private final InfoLayout deliveryPrice;
    private final InfoLayout totalPrice;
    private VerticalLayout items;
    private HorizontalLayout comboLayout;
    private OrderRequestDto orderRequestDto;
    private String currentState;

    private ComboBox<String> comboBoxState;


    public OrderForm(OrderViewLogic viewLogic) {
        this.viewLogic = viewLogic;
        setClassName("product-form");
        setWidth("40%");

        content = new VerticalLayout();
        comboLayout = new HorizontalLayout();
        content.setSizeUndefined();
        content.addClassName("product-form-content");
        add(content);


        name = new TextField("Имя");
        name.setWidth("100%");
        name.setRequired(true);
        name.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(name);

        phone = new TextField("Телефон");
        phone.setWidth("100%");
        phone.setRequired(true);
        phone.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(phone);

        final HorizontalLayout horizontalLayout = new HorizontalLayout(name,
                phone);
        horizontalLayout.setWidth("100%");
        horizontalLayout.setFlexGrow(1, name, phone);
        content.add(horizontalLayout);

        address = new TextField("Адрес");
        address.setWidth("100%");
        address.setRequired(true);
        address.setValueChangeMode(ValueChangeMode.EAGER);
        content.add(address);

        buildState("new");
        comboLayout.add(comboBoxState);
        content.add(comboLayout);

        order = new Grid<>(OrderDto.class);
        order.setWidth("100%");
        order.setHeightByRows(true);
        order.getColumnByKey("name").setHeader("Наименование блюда").setSortable(true).setAutoWidth(true);
        order.getColumnByKey("price").setHeader("Стоимость").setSortable(true).setAutoWidth(true);
        order.getColumnByKey("quantity").setHeader("Колличество").setSortable(true).setAutoWidth(true);
        order.getColumnByKey("amount").setHeader("Цена").setSortable(true).setAutoWidth(true);
        order.setColumnOrder(order.getColumnByKey("name"),
                order.getColumnByKey("price"),
                order.getColumnByKey("quantity"),
                order.getColumnByKey("amount"));
        content.add(order);

        delivery = new InfoLayout("Доставка", "");
        delivery.setWidth("100%");

        deliveryPrice = new InfoLayout("Стоимость доставки", "");
        deliveryPrice.setWidth("100%");

        totalPrice = new InfoLayout("Итого", "");
        totalPrice.setWidth("100%");
        items=new VerticalLayout(delivery, deliveryPrice, totalPrice);
        items.setWidth("100%");
        items.setSizeFull();
        content.add(items);



        save = new Button("Save");
        save.setWidth("100%");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        save.addClickShortcut(Key.KEY_S, KeyModifier.CONTROL);

        discard = new Button("Discard changes");
        discard.setWidth("100%");
        discard.addClickListener(
                event -> viewLogic.editProduct(orderRequestDto, currentState));

        cancel = new Button("Cancel (ESC)");
        cancel.setWidth("100%");

        cancel.addClickShortcut(Key.ESCAPE);
        cancel.addClickListener(event -> viewLogic.cancelForm());

        delete = new Button("Delete");
        delete.setWidth("100%");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_PRIMARY);
        delete.addClickListener(event -> {

        });


        content.add(save, discard, delete, cancel);
    }

    public void bind(OrderRequestDto dto, String state){
        this.orderRequestDto=dto;
        this.currentState=state;
        binder = new Binder<>(OrderRequestDto.class);
        binder.bind(name, OrderRequestDto::getName,
                OrderRequestDto::setName);
        binder.bind(address, OrderRequestDto::getAddress,
                OrderRequestDto::setAddress);
        binder.bind(phone, OrderRequestDto::getPhone,
                OrderRequestDto::setPhone);

        delivery.setSecondary(dto.getOrderList().getDelivery());
        deliveryPrice.setSecondary(String.valueOf(dto.getOrderList().getDeliveryPrice()));
        totalPrice.setSecondary(String.valueOf(dto.getOrderList().getTotalPrice()));

        order.setDataProvider(createDataProvider(dto));



        binder.readBean(dto);
    }


    private DataProvider<OrderDto, Void> createDataProvider(OrderRequestDto requestDto){

        return DataProvider.fromCallbacks(
                // First callback fetches items based on a query
                query -> {
                    // The index of the first item to load
                    int offset = query.getOffset();

                    // The number of items to load
                    int limit = query.getLimit();
                    List<OrderDto> orders = requestDto.getOrderList().getOrderDtoList();

                    return orders.stream();
                },
                // Second callback fetches the number of items
                // for a query
                query -> requestDto != null? requestDto.getOrderList().getOrderDtoList().size() : 0);
    }


    public void buildState(String currentState){
        comboLayout.setWidth("100%");
        if(comboBoxState != null){
            comboLayout.remove(comboBoxState);
        }
        comboBoxState = new ComboBox<>("Статус");
        comboBoxState.setWidth("30%");
        comboBoxState.setDataProvider(createListDataProvider(currentState));
        comboBoxState.setValue(currentState);

        comboBoxState.addValueChangeListener(event -> {
            if (event.getSource().isEmpty()) {
                Notification.show("No browser selected");
            } else {
                Notification.show("Выбрана роль: " + event.getValue());
            }
        });

        comboLayout.add(comboBoxState);
    }

    private ListDataProvider<String> createListDataProvider(String state){
        return DataProvider.fromStream(createStateListStream(state));
    }

    private Stream<String> createStateListStream(String currentState){
        switch (currentState){
            case "new":{
                return Stream.of("new", "process");
            }
            case "process":{
                return Stream.of("process", "delivery");
            }
            case "delivery":{
                return Stream.of("delivery", "done");
            }
            default: return Stream.of("delivery", "done");
        }
    }

}
