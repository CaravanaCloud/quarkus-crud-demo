package qcd;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import qcd.pizza.Pizza;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


/**
 * The main view contains a button and a click listener.
 */
@Route("")
public class MainView extends VerticalLayout {


    @PostConstruct
    void init(){
        add(new H1("Pizza CRUD"));
        GridCrud<Pizza> crud = new GridCrud<>(Pizza.class);
        crud.setFindAllOperation(this::findAllPizzas);
        crud.setAddOperation(this::persist);
        crud.setUpdateOperation(this::persist);
        crud.setDeleteOperation(this::deletePizza);
        
        Grid<Pizza> grid = crud.getGrid();
        grid.removeColumnByKey("id");
        grid.removeColumnByKey("persistent");

        add(crud);
    }

    @Transactional
    List<Pizza> findAllPizzas() {
        return Pizza.listAll();
    }


    @Transactional
    Pizza persist(Pizza pizza) {
        if (pizza.isPersistent()) {
            pizza = Pizza.getEntityManager().merge(pizza);
        } else {
            pizza.persist();
        }
        return pizza;
    }


    @Transactional
    void deletePizza(Pizza pizza) {
        pizza.delete();
    }
}
