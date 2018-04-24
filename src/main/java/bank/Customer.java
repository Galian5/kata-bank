package bank;

import java.util.Objects;
import java.util.UUID;

public class Customer {
    private final UUID uuid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(uuid, customer.uuid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid);
    }

    public Customer() {

        this.uuid = UUID.randomUUID();
    }
}
