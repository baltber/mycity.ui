package ru.mycity.ui.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "start")
public class StartView extends VerticalLayout {

    public StartView(){
        setHeight("100%");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        HorizontalLayout hl = new HorizontalLayout();
        hl.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        hl.setHeight("100%");

        Button login = new Button("Войти в кабинет");
        login.addClickListener(e -> UI.getCurrent().navigate(LoginView.class));

        Button add = new Button("Подать обращение");
        add.addClickListener(e -> UI.getCurrent().navigate(AddView.class));

        Button addUser = new Button("Добавить нового пользователя");
        addUser.addClickListener(e -> UI.getCurrent().navigate(AddUserView.class));

        Button listUser = new Button("Список пользователей");
        listUser.addClickListener(e -> UI.getCurrent().navigate(UserListView.class));

        VerticalLayout layout = new VerticalLayout(login, add, addUser, listUser);
        layout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        hl.add(layout);
        add(hl);
    }

}
