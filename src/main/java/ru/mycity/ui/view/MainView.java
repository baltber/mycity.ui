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
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import ru.mycity.ui.model.Complaint;
import ru.mycity.ui.service.rest.CoreService;
import ru.mycity.ui.service.rest.dto.ComplaintDto;

import java.util.List;
import java.util.stream.Collectors;

@Route(value = "main")
public class MainView extends VerticalLayout {

    private final Button button;
    private Grid<Complaint> grid;
    private MainViewParameters parameters;

    public MainView() {
        parameters = new MainViewParameters();

        VerticalLayout textLayout = new VerticalLayout();
        H2 label = new H2("Обращения граждан");
        textLayout.add(label);
        textLayout.setHorizontalComponentAlignment(Alignment.CENTER, label);
        add(textLayout);
        setHorizontalComponentAlignment(Alignment.CENTER, textLayout);

        //List Select
        ComboBox<String> comboBoxCategory = new ComboBox<>("Категория");
        comboBoxCategory.setItems("Сантехника", "Электричество");

        comboBoxCategory.addValueChangeListener(event -> {
            if (event.getSource().isEmpty()) {
                Notification.show("No browser selected");
                parameters.setCategory(null);
            } else {
                Notification.show("Выбрана категория: " + event.getValue());
                parameters.setCategory(event.getValue());
            }
        });

        String sessionID = ((VaadinServletRequest) VaadinService.getCurrentRequest()).getHttpServletRequest().getSession().getId();

        System.out.println(sessionID);

        ComboBox<String> comboBoxStatus = new ComboBox<>("Статус");
        comboBoxStatus.setItems("NEW", "DONE");

        comboBoxStatus.addValueChangeListener(event -> {
            if (event.getSource().isEmpty()) {
                Notification.show("No browser selected");
                parameters.setCategory(null);
            } else {
                Notification.show("Выбран статус: " + event.getValue());
                parameters.setCategory(event.getValue());
            }
        });

        button = new Button("Получить все обращения");
        this.grid = new Grid<>(Complaint.class);

        grid.setHeight("200px");
        grid.setColumns("category", "message", "address", "status");
        grid.getColumnByKey("category").setHeader("Категория");
        grid.getColumnByKey("message").setHeader("Сообщение");
        grid.getColumnByKey("address").setHeader("Адрес");
        grid.getColumnByKey("status").setHeader("Статус");

        HorizontalLayout horizontalLayout = new HorizontalLayout(button);

        HorizontalLayout horizontalLayout2 = new HorizontalLayout(comboBoxCategory, comboBoxStatus);
        setHorizontalComponentAlignment(Alignment.CENTER, horizontalLayout);
        setHorizontalComponentAlignment(Alignment.CENTER, horizontalLayout2);
        add(horizontalLayout);
        add(horizontalLayout2);
        add(grid);


        button.addClickListener(l -> listComplaint(parameters));

        Button logout = new Button("LOGOUT");
        logout.addClickListener(l -> {
            VaadinService.getCurrentRequest().getWrappedSession().invalidate();
            new SecurityContextLogoutHandler()
                    .logout(((VaadinServletRequest) VaadinService.getCurrentRequest())
                            .getHttpServletRequest(), null, null);
            UI.getCurrent().navigate(LoginView.ROUTE);
        });
        add(logout);


    }

    private void listComplaint(MainViewParameters parameters){
        CoreService coreService = new CoreService();
        List<Complaint> list = null;
        try {
            list = toModel(coreService. getComplaints(parameters));
        } catch (Exception e) {
            e.printStackTrace();
        }
        grid.setItems(list);
        grid.getDataProvider().refreshAll();
    }

    private List<Complaint> toModel(List<ComplaintDto> complaintDto){
    return complaintDto.stream().map(this::toModel).collect(Collectors.toList());
    }

    private Complaint toModel(ComplaintDto dto){
        Complaint complaint = new Complaint();
        complaint.setAddress(dto.getAddress());
        complaint.setMessage(dto.getMessage());
        complaint.setCategory(dto.getCategory());
        complaint.setStatus(dto.getStatus());
        return complaint;
    }
}
