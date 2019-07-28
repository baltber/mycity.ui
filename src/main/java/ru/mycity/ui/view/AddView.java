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
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mycity.ui.service.rest.CoreService;
import ru.mycity.ui.service.rest.dto.AddComplaintDto;
import ru.mycity.ui.service.rest.dto.InsertComplaintResultDto;

@Route(value = "add")
public class AddView extends VerticalLayout {

    @Autowired
    private CoreService coreService;

    private TextField textFieldInc;
    private TextField textFieldAddr;
    private ComboBox<String> comboBoxCategory;
    private AddComplaintDto dto;
    private TextArea textArea;

    public AddView() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        dto = new AddComplaintDto();

        VerticalLayout textLayout = new VerticalLayout();
        H2 label = new H2("Подать обращение");
        textLayout.add(label);
        textLayout.setHorizontalComponentAlignment(Alignment.CENTER, label);
        add(textLayout);

        buildInc();
        buildCategory();
        buildAddress();
        buildFullInc();

        HorizontalLayout hl = new HorizontalLayout();
        hl.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        hl.setHeight("100%");

        VerticalLayout verticalLayout = new VerticalLayout(textFieldInc, comboBoxCategory, textFieldAddr, textArea);
        verticalLayout.setSizeFull();
        verticalLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        hl.add(verticalLayout);
        hl.setSizeFull();
        add(hl);

        Button send = new Button("Отправить");
        send.addClickListener(event -> {
            InsertComplaintResultDto resultDto = coreService.addComplaint(dto);
            if(resultDto!= null){
                Notification.show(String.valueOf(resultDto.getComplaintId()));
            }
        });

        add(send);
    }

    private void buildInc(){
        textFieldInc = new TextField();
        textFieldInc.setWidth("30%");
        textFieldInc.setLabel("Проблема");
        textFieldInc.setPlaceholder("Проблема");
        //TODO Добавить в БД поле
    }

    private void buildAddress(){
        textFieldAddr = new TextField();
        textFieldAddr.setWidth("30%");
        textFieldAddr.setLabel("Адрес");
        textFieldAddr.setPlaceholder("Адрес");
        textFieldAddr.addValueChangeListener(e -> {
            dto.setAddress(e.getValue());
        });
    }

    private void buildFullInc(){
        textArea = new TextArea();
        textArea.setWidth("30%");
        textArea.setLabel("Опишите подробно проблему");
        textArea.setRequiredIndicatorVisible(true);
        textArea.addValueChangeListener(e -> {
            dto.setMessage(e.getValue());
        });
    }

    private void buildCategory(){
        comboBoxCategory = new ComboBox<>("Категория");
        comboBoxCategory.setWidth("30%");
        comboBoxCategory.setItems("Сантехника", "Электричество");

        comboBoxCategory.addValueChangeListener(event -> {
            if (event.getSource().isEmpty()) {
                Notification.show("No browser selected");
            } else {
                Notification.show("Выбрана категория: " + event.getValue());
                dto.setCategory(event.getValue());
            }
        });
    }
}
