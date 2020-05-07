package ru.mycity.ui.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.mycity.ui.security.CustomRequestCache;
import ru.mycity.ui.view.order.OrderView;

@Route(value = LoginView.ROUTE)
@CssImport("./styles/shared-styles.css")
public class LoginView extends FlexLayout implements RouterLayout {

    public static final String ROUTE = "login";

    @Autowired
    public LoginView(AuthenticationManager authenticationManager, CustomRequestCache requestCache){
        setHeight("100%");

        LoginForm login = new LoginForm();
        login.addLoginListener(e -> {

            try{
                final Authentication authentication = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(e.getUsername(), e.getPassword())); //

                // if authentication was successful we will update the security context and redirect to the page requested first
                SecurityContextHolder.getContext().setAuthentication(authentication);

                UI.getCurrent().navigate(OrderView.class);
            } catch (AuthenticationException ex){
                login.setError(true);
            }
        });

        FlexLayout centeringLayout = new FlexLayout();
        centeringLayout.setSizeFull();
        centeringLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        centeringLayout.setAlignItems(Alignment.CENTER);
        centeringLayout.add(login);


        add(buildLoginInformation());
        add(centeringLayout);
    }

    private Component buildLoginInformation() {
        VerticalLayout loginInformation = new VerticalLayout();
        loginInformation.setClassName("login-information");

        H1 loginInfoHeader = new H1("Login");
        loginInfoHeader.setWidth("100%");
        Span loginInfoText = new Span(
                "Войдите или зарегистрируйте " +
                        "нового пользователя ");
        loginInfoText.setWidth("100%");
        loginInformation.add(loginInfoHeader);
        loginInformation.add(loginInfoText);

        loginInformation.add(createRegisterButton());

        return loginInformation;
    }

    private Button createRegisterButton(){
        Button button = new Button("Регистрация");
        button.addClickListener(e -> UI.getCurrent().navigate(AddUserView.class));
        return button;
    }

}
