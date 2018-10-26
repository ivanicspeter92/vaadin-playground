package com.vaadin.starter.skeleton;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

/**
 * The main view contains a button and a click listener.
 */
@Route("")
@PWA(name = "Project Base for Vaadin Flow", shortName = "Project Base")
public class MainView extends VerticalLayout {
    private TextField filterText = new TextField();
    private Button clearFilterTextBtn = new Button(new Icon(VaadinIcon.CLOSE_CIRCLE));
    private Grid<Customer> grid = new Grid<>();

    public MainView() {
        configureFilterTextField();
        configureClearFilterButton();
        HorizontalLayout filtering = new HorizontalLayout(filterText, clearFilterTextBtn);

        configureCustomerGrid();
        updateList();
        add(filtering, grid);

        setHeight("100vh");
    }

    private void configureFilterTextField() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());
    }

    private void configureClearFilterButton() {
        clearFilterTextBtn.addClickListener(e -> filterText.clear());
    }

    private void configureCustomerGrid() {
        grid.setSizeFull();

        grid.addColumn(Customer::getId).setHeader("ID");
        grid.addColumn(Customer::getFirstName).setHeader("First name");
        grid.addColumn(Customer::getLastName).setHeader("Last name");
        grid.addColumn(Customer::getStatus).setHeader("Status");
    }

    private void updateList() {
        grid.setItems(CustomerService.getInstance().findAll(filterText.getValue()));
    }
}
