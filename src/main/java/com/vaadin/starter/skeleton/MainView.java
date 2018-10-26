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
    private CustomerForm form = new CustomerForm(this);

    public MainView() {
        configureFilterTextField();
        configureClearFilterButton();
        HorizontalLayout filtering = new HorizontalLayout(filterText, clearFilterTextBtn);

        configureCustomerGrid();
        updateList();
        add(form);

        setHeight("100vh");

        grid.asSingleSelect().addValueChangeListener(event -> {
            form.setCustomer(event.getValue());
        });

        Button addCustomerBtn = new Button("Add new customer");
        addCustomerBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setCustomer(new Customer());
        });
        HorizontalLayout toolbar = new HorizontalLayout(filtering, addCustomerBtn);
        add(toolbar, grid);
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

    public void updateList() {
        grid.setItems(CustomerService.getInstance().findAll(filterText.getValue()));
    }
}
