package ru.mycity.ui.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.mycity.ui.security.CustomRequestCache;

@Route(value = LoginView.ROUTE)
public class LoginView extends VerticalLayout {

    public static final String ROUTE = "login";

    @Autowired
    public LoginView(AuthenticationManager authenticationManager, CustomRequestCache requestCache){
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setHeight("100%");

        LoginForm login = new LoginForm();
        HorizontalLayout hl = new HorizontalLayout();
        login.addLoginListener(e -> {

            try{
                final Authentication authentication = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(e.getUsername(), e.getPassword())); //

                // if authentication was successful we will update the security context and redirect to the page requested first
                SecurityContextHolder.getContext().setAuthentication(authentication);

                UI.getCurrent().navigate(MainView.class);
            } catch (AuthenticationException ex){
                login.setError(true);
            }
        });

        hl.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        hl.setHeight("100%");
        hl.add(login);
        add(hl);
    }
}
