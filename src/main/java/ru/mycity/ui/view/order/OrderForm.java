package ru.mycity.ui.view.order;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToBigDecimalConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import ru.mycity.ui.service.rest.dto.auth.UserDto;
import ru.mycity.ui.service.rest.dto.order.OrderDto;
import ru.mycity.ui.service.rest.dto.order.OrderRequestDto;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

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
    OrderViewLogic viewLogic;

    private static class PriceConverter extends StringToBigDecimalConverter {

        public PriceConverter() {
            super(BigDecimal.ZERO, "Cannot convert value to a number.");
        }

        @Override
        protected NumberFormat getFormat(Locale locale) {
            // Always display currency with two decimals
            final NumberFormat format = super.getFormat(locale);
            if (format instanceof DecimalFormat) {
                format.setMaximumFractionDigits(2);
                format.setMinimumFractionDigits(2);
            }
            return format;
        }
    }

    private static class StockCountConverter extends StringToIntegerConverter {

        public StockCountConverter() {
            super(0, "Could not convert value to " + Integer.class.getName()
                    + ".");
        }

        @Override
        protected NumberFormat getFormat(Locale locale) {
            // Do not use a thousands separator, as HTML5 input type
            // number expects a fixed wire/DOM number format regardless
            // of how the browser presents it to the user (which could
            // depend on the browser locale).
            final DecimalFormat format = new DecimalFormat();
            format.setMaximumFractionDigits(0);
            format.setDecimalSeparatorAlwaysShown(false);
            format.setParseIntegerOnly(true);
            format.setGroupingUsed(false);
            return format;
        }
    }

    public OrderForm(OrderViewLogic viewLogic) {
        this.viewLogic = viewLogic;
        setClassName("product-form");
        setWidth("40%");

        content = new VerticalLayout();
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



        order = new Grid<>(OrderDto.class);
        order.setWidth("100%");
        order.getColumnByKey("name").setHeader("Наименование блюда").setSortable(true).setAutoWidth(true);
        order.getColumnByKey("price").setHeader("Стоимость").setSortable(true).setAutoWidth(true);
        order.getColumnByKey("quantity").setHeader("Колличество").setSortable(true).setAutoWidth(true);
        order.getColumnByKey("amount").setHeader("Цена").setSortable(true).setAutoWidth(true);
        order.setColumnOrder(order.getColumnByKey("name"),
                order.getColumnByKey("price"),
                order.getColumnByKey("quantity"),
                order.getColumnByKey("amount"));
        content.add(order);
        save = new Button("Save");
        save.setWidth("100%");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        save.addClickShortcut(Key.KEY_S, KeyModifier.CONTROL);

        discard = new Button("Discard changes");
        discard.setWidth("100%");

        cancel = new Button("Cancel");
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

    public void bind(OrderRequestDto dto){
        binder = new Binder<>(OrderRequestDto.class);
        binder.bind(name, OrderRequestDto::getName,
                OrderRequestDto::setName);
        binder.bind(address, OrderRequestDto::getAddress,
                OrderRequestDto::setAddress);
        binder.bind(phone, OrderRequestDto::getPhone,
                OrderRequestDto::setPhone);

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

}
