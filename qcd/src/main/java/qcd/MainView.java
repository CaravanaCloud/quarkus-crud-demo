package qcd;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import qcd.pizza.Pizza;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


/**
 * The main view contains a button and a click listener.
 */
@Route("")
public class MainView extends VerticalLayout {

    @Inject
    GreetService greetService;

    @Inject
    DataSource ds;

    public Map<String, String> getHealthMap() {
        try (var conn = ds.getConnection();
            var stmt = conn.createStatement();
            var rs = stmt.executeQuery("SELECT 1+1")){
                var ok = rs.next() && 2 == rs.getInt(1);
                if (ok) 
                    return Map.of("status", "ok");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }   
        return Map.of("status", "error");
    }

    @PostConstruct
    public void init(){
        var hcButton = new Button("Check Health", e -> {
            var status = getHealthMap().get("status");
            add(new Paragraph("Health status: " + status));
        });
        add(hcButton);

        var listButton = new Button("List Pizzas", e -> {
            Pizza.listAll()
                .stream()
                .map(p -> new Paragraph(p.toString()))
                .forEach(this::add);
        });
        add(listButton);
    }

    public MainView() {
        // Use TextField for standard text input
        TextField textField = new TextField("Your name");
        textField.addThemeName("bordered");

        // Button click listeners can be defined as lambda expressions
        Button button = new Button("Say hello", e -> {
            add(new Paragraph(greetService.greet(textField.getValue())));
        });

        // Theme variants give you predefined extra styles for components.
        // Example: Primary button is more prominent look.
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // You can specify keyboard shortcuts for buttons.
        // Example: Pressing enter in this view clicks the Button.
        button.addClickShortcut(Key.ENTER);

        // Use custom CSS classes to apply styling. This is defined in shared-styles.css.
        addClassName("centered-content");

        add(textField, button);
    }
}
