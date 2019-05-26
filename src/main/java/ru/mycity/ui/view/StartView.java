package ru.mycity.ui.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "start")
public class StartView extends VerticalLayout {

    public StartView(){

        Button login = new Button("Войти в кабинет");
        login.addClickListener(e -> UI.getCurrent().navigate(LoginView.class));

        Button add = new Button("Подать обращение");

        VerticalLayout layout = new VerticalLayout(login, add);
        layout.setHorizontalComponentAlignment(Alignment.CENTER, login);
        layout.setHorizontalComponentAlignment(Alignment.CENTER, add);
        setHorizontalComponentAlignment(Alignment.CENTER, layout);
        add(layout);
    }

}
