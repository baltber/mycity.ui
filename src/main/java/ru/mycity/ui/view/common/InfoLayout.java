package ru.mycity.ui.view.common;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;


public class InfoLayout extends HorizontalLayout {
    private Label primary;
    private Label secondary;
    private HorizontalLayout content;
    private HorizontalLayout primaryContainer;
    private HorizontalLayout secondaryContainer;

    public InfoLayout(String primary, String secondary) {

        setAlignItems(FlexComponent.Alignment.CENTER);
        setWidth("100%");

        this.primary = new Label(primary);
        this.secondary =new Label(secondary);

        this.primary.getStyle().set("fontWeight", "bold");
        primaryContainer = new HorizontalLayout();
        primaryContainer.setWidth("100%");
        primaryContainer.add(this.primary);
        primaryContainer.setAlignItems(Alignment.START);

        secondaryContainer = new HorizontalLayout();
        secondaryContainer.setWidth("100%");
        secondaryContainer.add(this.secondary);
        secondaryContainer.setAlignItems(Alignment.END);
        content = new HorizontalLayout(primaryContainer, secondaryContainer);
        content.setWidth("100%");
        add(content);
    }


    public Label getPrimary() {
        return primary;
    }

    public void setPrimary(Label primary) {
        this.primary = primary;

    }

    public Label getSecondary() {
        return secondary;
    }

    public void setSecondary(String secondary) {
        this.secondary.setText(secondary);
    }

}
