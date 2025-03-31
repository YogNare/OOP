package ru.nsu.baev;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Pizzeria {
    public static void main(String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode config = objectMapper.readTree(new File("config.json"));

        int storageCapacity = config.get("storageCapacity").asInt();
        int workingTime = config.get("workingTime").asInt();

        Storage storage = new Storage(storageCapacity);
        List<Baker> bakers = new ArrayList<>();
        List<Courier> couriers = new ArrayList<>();

        for (JsonNode bakerNode : config.get("bakers")) {
            bakers.add(new Baker(bakerNode.get("id").asInt(), bakerNode.get("speed").asInt(), storage));
        }
        for (JsonNode courierNode : config.get("couriers")) {
            couriers.add(new Courier(courierNode.get("id").asInt(), courierNode.get("capacity").asInt(), storage));
        }

        // Запуск потоков
        bakers.forEach(Thread::start);
        couriers.forEach(Thread::start);

        // Работаем заданное время
        Thread.sleep(workingTime);

        // Остановка пекарей
        bakers.forEach(Baker::stopBaking);
        couriers.forEach(Courier::stopDelivering);

        System.out.println("Пиццерия закрывается!");
    }
}
