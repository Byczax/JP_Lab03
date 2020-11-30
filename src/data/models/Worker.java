package data.models;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Worker extends Person{

    public Worker(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }
    public Worker(String  uuid, String name) {
        this.id = UUID.fromString(uuid);
        this.name = name;
    }

    public UUID getUUID() {
        return id;
    }

    public void setUUID(UUID index) {
        this.id = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
