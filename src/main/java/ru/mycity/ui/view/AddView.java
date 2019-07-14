package ru.mycity.ui.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mycity.ui.service.rest.CoreService;
import ru.mycity.ui.service.rest.dto.AddComplaintDto;
import ru.mycity.ui.service.rest.dto.InsertComplaintResultDto;

@Route(value = "add")
public class AddView extends VerticalLayout {

@Autowired
private CoreService coreService;

    public AddView() {
        AddComplaintDto dto = new AddComplaintDto();
        VerticalLayout textLayout = new VerticalLayout();
        H2 label = new H2("Подать обращение");
        textLayout.add(label);
        textLayout.setHorizontalComponentAlignment(Alignment.CENTER, label);
        add(textLayout);
        setHorizontalComponentAlignment(Alignment.CENTER, textLayout);

        TextField textFieldInc = new TextField();
        textFieldInc.setLabel("Проблема");
        textFieldInc.setPlaceholder("Проблема");


        //List Select
        ComboBox<String> comboBoxCategory = new ComboBox<>("Категория");
        comboBoxCategory.setItems("Сантехника", "Электричество");

        comboBoxCategory.addValueChangeListener(event -> {
            if (event.getSource().isEmpty()) {
                Notification.show("No browser selected");
            } else {
                Notification.show("Выбрана категория: " + event.getValue());
                dto.setCategory(event.getValue());
            }
        });

        TextField textFieldAddr = new TextField();
        textFieldAddr.setLabel("Адрес");
        textFieldAddr.setPlaceholder("Адрес");
        textFieldAddr.addValueChangeListener(e -> {
            dto.setAddress(e.getValue());
        });

        TextArea textArea = new TextArea();
        textArea.setLabel("Опишите подробно проблему");
        textArea.isRequired();
        textArea.addValueChangeListener(e -> {
           dto.setMessage(e.getValue());
        });

        VerticalLayout verticalLayout = new VerticalLayout(textFieldInc, comboBoxCategory, textFieldAddr, textArea);

        add(verticalLayout);

        Button send = new Button("Отправить");
        send.addClickListener(event -> {
            InsertComplaintResultDto resultDto = coreService.addComplaint(dto);
            if(resultDto!= null){
                Notification.show(String.valueOf(resultDto.getComplaintId()));
            }
        });

        add(send);


    }
}
