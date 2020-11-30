package data.models;

import java.util.UUID;

public class Jobs {
    UUID uuid;
    UUID client;
    UUID worker;

//    public Jobs(UUID client, UUID worker) {
//        this.uuid = UUID.randomUUID();
//        this.client = client;
//        this.worker = worker;
//    }
    public Jobs(UUID job, UUID client, UUID worker) {
        this.uuid = job;
        this.client = client;
        this.worker = worker;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getClient() {
        return client;
    }

    public void setClient(UUID client) {
        this.client = client;
    }

    public UUID getWorker() {
        return worker;
    }

    public void setWorker(UUID worker) {
        this.worker = worker;
    }
}
