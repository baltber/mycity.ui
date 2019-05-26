package ru.mycity.ui.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "login")
public class LoginView extends VerticalLayout {

    public LoginView(){
        HorizontalLayout layout = new HorizontalLayout();
        LoginForm component = new LoginForm();
        component.addLoginListener(e -> {
            boolean isAuthenticated = true;
            if (isAuthenticated) {
                System.out.println("LOGIN");
                UI.getCurrent().navigate(MainView.class);
            } else {
                component.setError(true);
            }
        });

        setHorizontalComponentAlignment(Alignment.CENTER, layout);
        layout.add(component);

        add(layout);
    }

//    private LoginOverlay login = new LoginOverlay(); //
//
//    public LoginView() {
//        login.setAction("login"); //
//        login.setOpened(true); //
//        login.setTitle("Spring Secured Vaadin");
//        login.setDescription("Login Overlay Example");
//        getElement().appendChild(login.getElement()); //
//        login.addLoginListener(e -> {
//            boolean isAuthenticated = false;
//            if (isAuthenticated) {
//                System.out.println("LOGIN");
//                UI.getCurrent().navigate(MainView.class);
//            } else {
//                login.setError(true);
//            }
//        });
//    }
}
