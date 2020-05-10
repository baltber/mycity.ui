package ru.mycity.ui.view.order;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;
import org.vaadin.klaudeta.PaginatedGrid;
import ru.mycity.ui.service.OrderService;
import ru.mycity.ui.service.rest.dto.order.OrderRequestDto;
import ru.mycity.ui.view.AppView;

import java.util.List;


@Route(value = "orders", layout = AppView.class)
public class OrderView extends HorizontalLayout {

    private OrderService orderService;

    public static final String VIEW_NAME="Список заказов";

    private OrderViewLogic orderViewLogic = new OrderViewLogic(this);
    private PaginatedGrid<OrderRequestDto> grid;
    private OrderForm form;

    private String state;

    public OrderView() {
        state="new";
        orderService = new OrderService();
        setSizeFull();
        form=new OrderForm(orderViewLogic);
        form.setVisible(false);
        form.setEnabled(false);

        VerticalLayout tabAndGridLayout = new VerticalLayout();

        HorizontalLayout topBar = createTopBar();
        VerticalLayout lGrid = createGrid();
        tabAndGridLayout.add(topBar);
        tabAndGridLayout.add(lGrid);
        tabAndGridLayout.setFlexGrow(1, lGrid);
        tabAndGridLayout.setFlexGrow(0, topBar);
        tabAndGridLayout.expand(lGrid);
        tabAndGridLayout.setSizeFull();
        add(tabAndGridLayout);
        add(form);
    }

    public VerticalLayout createGrid() {
        final VerticalLayout gridLayout = new VerticalLayout();
        grid = new PaginatedGrid<>();
        grid.addColumn(OrderRequestDto::getName).setHeader("name").setHeader("Имя клиента").setSortable(true);
        grid.addColumn(OrderRequestDto::getAddress).setHeader("address").setHeader("Адрес").setSortable(true);
        grid.addColumn(OrderRequestDto::getEmail).setHeader("email").setHeader("E-Mail").setSortable(true);
        grid.addColumn(OrderRequestDto::getPhone).setHeader("phone").setHeader("Телефон").setSortable(true);
        grid.setDataProvider(createDataProvider("new"));

        grid.asSingleSelect().addValueChangeListener(
                event -> editProduct(event.getValue(), this.state));
        grid.setPageSize(15);
        grid.setPaginatorSize(5);
        gridLayout.setWidth("100%");
        gridLayout.add(grid);
        gridLayout.expand(grid);
        return gridLayout;
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
        Tab newTab =new Tab(new HorizontalLayout(VaadinIcon.EDIT.create(), new Label("Новые")));
        Tab procTab =new Tab(new HorizontalLayout(VaadinIcon.PROGRESSBAR.create(), new Label("В процессе")));
        Tab delTab =new Tab(new HorizontalLayout(VaadinIcon.TRUCK.create(), new Label("В доставке")));
        newTab.setId("new");
        procTab.setId("process");
        delTab.setId("delivery");
        tabs.add(newTab);
        tabs.add(procTab);
        tabs.add(delTab);

        tabs.setSelectedTab(newTab);
        tabs.addSelectedChangeListener(e->{
            this.state=e.getSelectedTab().getId().orElse("new");
            grid.setDataProvider(createDataProvider(this.state));

        });
        tabs.setSizeFull();
       return tabs;
    }




    public void editProduct(OrderRequestDto dto, String state) {
        showForm(dto != null);
        if(dto != null){
            form.bind(dto, state);
            form.buildState(state);
        }


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

    /**
     * Deselects the selected row in the grid.
     */
    public void clearSelection() {
        grid.getSelectionModel().deselectAll();
    }

    private DataProvider<OrderRequestDto, Void> createDataProvider(String state){

        return DataProvider.fromCallbacks(
                // First callback fetches items based on a query
                query -> {
                    // The index of the first item to load
                    int offset = query.getOffset();

                    // The number of items to load
                    int limit = query.getLimit();

                    List<OrderRequestDto> orders = orderService.getListOrder(state, limit, offset);

                    return orders.stream();
                },
                // Second callback fetches the number of items
                // for a query
                query -> orderService.getCount(state));
    }
}
