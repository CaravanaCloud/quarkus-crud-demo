package qcd.pizza;

import java.util.stream.Stream;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;

public class Pizzas {
    @Transactional
    void init(@Observes StartupEvent ev) {
        var p1 = Pizza.of("Pizza Margherita", "Via Margherita, 42");
        var p2 = Pizza.of("Pizza Mozzarella", "Via Mozzarella, 333");
        var p3 = Pizza.of("Pizza Napoli", "Via Napoli, 1");
        Stream.of(p1, p2, p3).forEach(p -> p.persist());
    }
}
