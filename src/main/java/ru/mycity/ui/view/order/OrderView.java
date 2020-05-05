package ru.mycity.ui.view.order;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.vaadin.klaudeta.PaginatedGrid;
import ru.mycity.ui.service.rest.dto.auth.UserDto;
import ru.mycity.ui.view.AppView;


@Route(value = "orders", layout = AppView.class)
public class OrderView extends HorizontalLayout {

    public static final String VIEW_NAME="Список заказов";

    private OrderViewLogic orderViewLogic = new OrderViewLogic(this);
    private OrderForm form;
    public OrderView() {
        setSizeFull();
        form=new OrderForm();
        form.setVisible(false);
        form.setEnabled(false);

        VerticalLayout tabAndGridLayout = new VerticalLayout();
        PaginatedGrid<UserDto> grid = new PaginatedGrid<>();
        grid.addColumn(UserDto::getUserName).setHeader("userName");
        grid.addColumn(UserDto::getPassword).setHeader("password");
        grid.addColumn(UserDto::getRole).setHeader("role");
        grid.setItems(new UserDto("sas", "asas", "asas"));
        grid.asSingleSelect().addValueChangeListener(
                event -> editProduct(event.getValue()));

        HorizontalLayout topBar = createTopBar();
        tabAndGridLayout.add(topBar);
        tabAndGridLayout.add(grid);
        tabAndGridLayout.setFlexGrow(1, grid);
        tabAndGridLayout.setFlexGrow(0, topBar);
        tabAndGridLayout.setSizeFull();
        tabAndGridLayout.expand(grid);
        add(tabAndGridLayout);
        add(form);
    }

    public HorizontalLayout createTopBar() {
        Tabs tabs = initTabs();
        final HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.add(tabs);
        topLayout.setVerticalComponentAlignment(Alignment.START, tabs);
        topLayout.expand(tabs);
        return topLayout;
    }

    private Tabs initTabs() {
        Tabs tabs = new Tabs();
        tabs.add(new Tab("Новые"));
        tabs.add(new Tab("В процессе"));
        tabs.add(new Tab("В доставке"));
        tabs.setSizeFull();
       return tabs;
    }


    public void editProduct(UserDto userDto) {
        showForm(userDto != null);
        form.bind(userDto);
    }

    /**
     * Shows and hides the new product form
     *
     * @param show
     */
    public void showForm(boolean show) {
        form.setVisible(show);
        form.setEnabled(show);
    }
}
