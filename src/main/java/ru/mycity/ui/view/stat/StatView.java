package ru.mycity.ui.view.stat;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import ru.mycity.ui.service.rest.dto.order.OrderRequestDto;
import ru.mycity.ui.view.AppView;

@Route(value = "stat", layout = AppView.class)
public class StatView extends HorizontalLayout {
    public static final String VIEW_NAME="Статистика";

    private Grid<String> grid1;

    Label label;
    VerticalLayout tabAndGridLayout;
    public StatView() {
        tabAndGridLayout = new VerticalLayout();

        HorizontalLayout topBar = createTopBar();
        tabAndGridLayout.add(topBar);
        initLabel1();

        add(tabAndGridLayout);
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
        Tab newTab =new Tab(new HorizontalLayout(VaadinIcon.DOLLAR.create(), new Label("Стоимость")));
        Tab procTab =new Tab(new HorizontalLayout(VaadinIcon.CHECK.create(), new Label("Блюда")));
        newTab.setId("price");
        procTab.setId("dish");
        tabs.add(newTab);
        tabs.add(procTab);

        tabs.setSelectedTab(newTab);
        tabs.addSelectedChangeListener(e->{
        initGrid(e.getSelectedTab().getId().get());

        });
        tabs.setSizeFull();
        return tabs;
    }

    private void initGrid(String s){
        if("price".equals(s)){
            initLabel1();
        } else {
            initLabel2();
        }
    }

    private void initGrid1(){
        grid1 = new Grid<String>();
        grid1.addColumn("name").setHeader("name").setHeader("Имя клиента").setSortable(true);
        grid1.setItems("sasa");
        add(grid1);
    }

    private void initGrid2(){
        grid1 = new Grid();
        grid1.addColumn("name").setHeader("name").setHeader("Имя клиента").setSortable(true);
        grid1.setItems("aaaaaa");
        add(grid1);
    }

    private void initLabel1(){
        if(label!=null){
            tabAndGridLayout.remove(label);
        }
        label = new Label("1");
        tabAndGridLayout.add(label);
    }

    private void initLabel2(){
        if(label!=null){
            tabAndGridLayout.remove(label);
        }
        label = new Label("2");
        tabAndGridLayout.add(label);
    }
}
