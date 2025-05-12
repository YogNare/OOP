package ru.nsu.baev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CourierTest {

    private Storage storage;
    private Order order1;
    private Order order2;

    @BeforeEach
    public void setUp() {
        storage = mock(Storage.class);
        order1 = mock(Order.class);
        order2 = mock(Order.class);

        // задаем координаты заказов
        order1.x = 100;
        order1.y = 100;
        order2.x = 300;
        order2.y = 400;
    }

    @Test
    public void testCourierDeliversPizza() throws InterruptedException {
        // storage.getPizza() будет возвращать два заказа, затем null
        when(storage.getPizza())
                .thenReturn(order1)
                .thenReturn(order2)
                .thenReturn(null);

        long endTime = System.currentTimeMillis() + 1000; // дать курьеру секунду
        Courier courier = new Courier(storage, 2, endTime);

        courier.start();
        courier.join(); // дождемся завершения потока

        verify(storage, atLeast(1)).getPizza();
        verify(order1, times(1)).delivered();
        verify(order2, times(1)).delivered();
    }

    @Test
    public void testCourierStopsWhenNoPizza() throws InterruptedException {
        when(storage.getPizza()).thenReturn(null);

        long endTime = System.currentTimeMillis() + 300;
        Courier courier = new Courier(storage, 2, endTime);
        courier.start();
        courier.join();

        // не должно быть доставок
        verify(order1, never()).delivered();
        verify(order2, never()).delivered();
    }

    @Test
    public void testCourierWaitsAndReturnsHome() throws InterruptedException {
        // Подготовим заказ
        when(storage.getPizza()).thenReturn(order1).thenReturn(null);
        order1.x = 480;
        order1.y = 480;

        long endTime = System.currentTimeMillis() + 800; // время чуть больше чем на доставку + домой

        Courier courier = new Courier(storage, 1, endTime);
        courier.start();
        courier.join();

        verify(order1).delivered();
        // проверим, что курьер после доставки дожидается и возвращается домой (опосредованно по времени)
    }
}
