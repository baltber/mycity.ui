package ru.mycity.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.mycity.ui.service.rest.CoreService;
import ru.mycity.ui.service.rest.dto.AddComplaintDto;
import ru.mycity.ui.service.rest.dto.InsertComplaintResultDto;
import ru.mycity.ui.service.rest.dto.auth.AddUserRequestDto;
import ru.mycity.ui.service.rest.dto.auth.AddUserResponseDto;
import ru.mycity.ui.service.rest.dto.auth.UserDto;

@Route(value = "adduser")
public class AddUserView extends VerticalLayout {

    @Autowired
    private CoreService coreService;

    private TextField textFieldName;
    private TextField textFieldPass;
    private ComboBox<String> comboBoxRole;
    private UserDto dto;

    public AddUserView() {
        AddUserRequestDto requestDto = new AddUserRequestDto();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        dto = new UserDto();

        VerticalLayout textLayout = new VerticalLayout();
        H2 label = new H2("Добавить нового пользователя");
        textLayout.add(label);
        textLayout.setHorizontalComponentAlignment(Alignment.CENTER, label);
        add(textLayout);

        buildUserName();
        buildPassword();
        buildRole();

        HorizontalLayout hl = new HorizontalLayout();
        hl.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        hl.setHeight("100%");

        VerticalLayout verticalLayout = new VerticalLayout(textFieldName, textFieldPass, comboBoxRole);
        verticalLayout.setSizeFull();
        verticalLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        hl.add(verticalLayout);
        hl.setSizeFull();
        add(hl);

        requestDto.setUserDto(dto);
        Button send = new Button("Отправить");
        send.addClickListener(event -> {
            AddUserResponseDto resultDto = coreService.addUser(requestDto);
            if(resultDto!= null){
                Notification.show(String.valueOf(resultDto.getResultDto().getMessage()));
            }
        });

        add(send);
    }

    private void buildUserName(){
        textFieldName = new TextField();
        textFieldName.setWidth("30%");
        textFieldName.setLabel("Имя пользователя");
        textFieldName.setPlaceholder("Имя пользователя");
        textFieldName.addValueChangeListener(e -> {
            dto.setUserName(e.getValue());
        });
    }

    private void buildPassword(){
        textFieldPass = new TextField();
        textFieldPass.setWidth("30%");
        textFieldPass.setLabel("Пароль");
        textFieldPass.setPlaceholder("Пароль");
        textFieldPass.addValueChangeListener(e -> {
            dto.setPassword(new BCryptPasswordEncoder().encode(e.getValue()));
        });
    }

    private void buildRole(){
        comboBoxRole = new ComboBox<>("Роль");
        comboBoxRole.setWidth("30%");
        comboBoxRole.setItems("admin", "user");

        comboBoxRole.addValueChangeListener(event -> {
            if (event.getSource().isEmpty()) {
                Notification.show("No browser selected");
            } else {
                Notification.show("Выбрана роль: " + event.getValue());
                dto.setRole(event.getValue());
            }
        });
    }
}
