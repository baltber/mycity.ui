package ru.mycity.ui.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.*;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import ru.mycity.ui.view.order.OrderView;


@Theme(value = Lumo.class)
@Route(value = AppView.ROUTE)
@PWA(name = "Bookstore", shortName = "Bookstore", enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/menu-buttons.css", themeFor = "vaadin-button")
public class AppView extends AppLayout  implements RouterLayout{

    public static final String ROUTE = "app";

    public AppView() {

        // menu toggle
        final DrawerToggle drawerToggle = new DrawerToggle();
        drawerToggle.addClassName("menu-toggle");
        addToNavbar(drawerToggle);

        // image, logo
        final HorizontalLayout top = new HorizontalLayout();
        top.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        top.setClassName("menu-header");


        StreamResource res = new StreamResource(
                "table-logo.png", () -> this.getClass()
                .getClassLoader().getResourceAsStream("img/table-logo.png"));
        Image imageFromStream = new Image( res,"logo image");
        final Label title = new Label("Доставка");
        top.add(imageFromStream, title);
        top.add(title);
        addToNavbar(top);

        // Navigation items
        addToDrawer(createMenuLink(OrderView.class, OrderView.VIEW_NAME,
                VaadinIcon.SHOP.create()));

        Button logoutButton =createMenuButton("Logout", VaadinIcon.SIGN_OUT.create());
        logoutButton.addClickListener(e ->  {
            new SecurityContextLogoutHandler()
                .logout(((VaadinServletRequest) VaadinService.getCurrentRequest())
                        .getHttpServletRequest(), null, null);
        VaadinService.getCurrentRequest().getWrappedSession().invalidate();
        UI.getCurrent().navigate(LoginView.class);
        });
        addToDrawer(logoutButton);

    }




    private RouterLink createMenuLink(Class<? extends Component> viewClass,
                                      String caption, Icon icon) {
        final RouterLink routerLink = new RouterLink(null, viewClass);
        routerLink.setClassName("menu-link");
        routerLink.add(icon);
        routerLink.add(new Span(caption));
        icon.setSize("24px");
        return routerLink;
    }

    private Button createMenuButton(String caption, Icon icon) {
        final Button routerButton = new Button(caption);
        routerButton.setClassName("menu-button");
        routerButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        routerButton.setIcon(icon);
        icon.setSize("24px");
        return routerButton;
    }

}
