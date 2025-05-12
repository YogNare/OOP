package ru.nsu.baev;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.util.Queue;

public class Pizzeria {
    public static void main(String[] args) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        File file = new File("src/main/resources/config.json");

        Config config = mapper.readValue(file, Config.class);

        Storage storage = new Storage(config.storageCapacity);
        long duration = config.duration;
        long endTime = System.currentTimeMillis() + duration;

        List<Baker> bakers = new ArrayList<>();
        for (BakersSetting baker : config.bakers) {
            bakers.add(new Baker(baker.speed, storage, endTime));
        }

        List<Courier> couriers = new ArrayList<>();
        for (CouriersSetting courier : config.couriers) {
            couriers.add(new Courier(storage, courier.capacity, endTime));
        }

        List<Client> clients = new ArrayList<>();
        for (ClientsSetting client : config.clients) {
            clients.add(new Client(client.time, storage, client.id, client.coords.getFirst(), client.coords.getLast(), endTime));
        }

        bakers.forEach(Thread::start);
        couriers.forEach(Thread::start);
        clients.forEach(Thread::start);

        for (Baker baker : bakers) {
            baker.join();
        }
        for (Courier courier : couriers) {
            courier.join();
        }
        for (Client client : clients) {
            client.join();
        }

        System.out.println("Pizzeria finished!");
    }
}
