package ru.mycity.ui.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import ru.mycity.ui.model.Complaint;
import ru.mycity.ui.service.rest.CoreService;
import ru.mycity.ui.service.rest.dto.ComplaintDto;
import ru.mycity.ui.service.rest.dto.auth.UserDto;
import ru.mycity.ui.view.mode.MainViewParameters;
import ru.mycity.ui.view.mode.UserViewParameters;

import javax.jws.soap.SOAPBinding;
import java.util.List;
import java.util.stream.Collectors;

@Route(value = "user_list", layout = AppView.class)
@RouteAlias(value = "", layout = AppView.class)
public class UserListView extends VerticalLayout {

    public static final String VIEW_NAME="Список Пользователей";

    private final Button button;
    private Grid<UserDto> grid;
    private UserViewParameters parameters    ;

    public UserListView() {
        parameters = new UserViewParameters();

        VerticalLayout textLayout = new VerticalLayout();
        H2 label = new H2("Список пользователей");
        textLayout.add(label);
        textLayout.setHorizontalComponentAlignment(Alignment.CENTER, label);
        add(textLayout);
        setHorizontalComponentAlignment(Alignment.CENTER, textLayout);

        //List Select
        ComboBox<String> comboBoxRole = new ComboBox<>("Роль");
        comboBoxRole.setItems("admin", "user");

        comboBoxRole.addValueChangeListener(event -> {
            if (event.getSource().isEmpty()) {
                Notification.show("No browser selected");
                parameters.setRole(null);
            } else {
                Notification.show("Выбрана категория: " + event.getValue());
                parameters.setRole(event.getValue());
            }
        });


        button = new Button("Показать");
        this.grid = new Grid<>(UserDto.class);

        grid.setHeight("200px");
        grid.setColumns("userName", "password", "role");
        grid.getColumnByKey("userName").setHeader("userName");
        grid.getColumnByKey("password").setHeader("password");
        grid.getColumnByKey("role").setHeader("role");

        HorizontalLayout horizontalLayout = new HorizontalLayout(button);

        HorizontalLayout horizontalLayout2 = new HorizontalLayout(comboBoxRole);
        setHorizontalComponentAlignment(Alignment.CENTER, horizontalLayout);
        setHorizontalComponentAlignment(Alignment.CENTER, horizontalLayout2);
        add(horizontalLayout);
        add(horizontalLayout2);
        add(grid);


        button.addClickListener(l -> listUser(parameters));

        Button logout = new Button("LOGOUT");
        logout.addClickListener(l -> {

            new SecurityContextLogoutHandler()
                    .logout(((VaadinServletRequest) VaadinService.getCurrentRequest())
                            .getHttpServletRequest(), null, null);
            VaadinService.getCurrentRequest().getWrappedSession().invalidate();
            UI.getCurrent().navigate(LoginView.class);
        });
        add(logout);


    }

    private void listUser(UserViewParameters parameters){
        CoreService coreService = new CoreService();
        List<UserDto> list = null;
        try {
            list = coreService.getUserList(parameters.toDto());
        } catch (Exception e) {
            e.printStackTrace();
        }
        grid.setItems(list);
        grid.getDataProvider().refreshAll();
    }

}
