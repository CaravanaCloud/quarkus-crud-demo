package qcd.pizza;

import java.util.Map;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Pizza extends PanacheEntity {
    String description;
    String address;

    public static Pizza of(String description, String address) {
        var p = new Pizza();
        p.description = description;
        p.address = address;
        return p;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return Map.of(
            "id", id,
            "description", description, 
            "address", address).toString();
    }
}
