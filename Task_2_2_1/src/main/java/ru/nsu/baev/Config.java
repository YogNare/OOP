package ru.nsu.baev;

import java.util.List;

public class Config {
    public List<BakersSetting> bakers;
    public List<CouriersSetting> couriers;
    public List<ClientsSetting> clients;

    public int storageCapacity;
    public long duration;
}

class BakersSetting {
    public int id;
    public int speed;
}

class CouriersSetting {
    public int id;
    public int capacity;
}

class ClientsSetting {
    public int id;
    public List<Integer> coords;
    public int time;
}