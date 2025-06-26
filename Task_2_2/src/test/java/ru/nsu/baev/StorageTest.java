package ru.nsu.baev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StorageTest {
    private Storage storage;
    private final int CAPACITY = 2;

    @BeforeEach
    public void setUp() {
        storage = new Storage(CAPACITY);
    }

    @Test
    public void testPutPizzaWhenUnderCapacity() {
        Order order = mock(Order.class);
        int result = storage.putPizza(order);

        assertEquals(0, result);
        verify(order, times(1)).putToStorage();
    }

    @Test
    public void testPutPizzaWhenOverCapacity() {
        Order order1 = mock(Order.class);
        Order order2 = mock(Order.class);
        Order order3 = mock(Order.class);

        storage.putPizza(order1);
        storage.putPizza(order2);
        int result = storage.putPizza(order3);

        assertEquals(1, result);
        verify(order3, never()).putToStorage();
    }

    @Test
    public void testGetPizzaWhenNotEmpty() {
        Order order = mock(Order.class);
        storage.putPizza(order);

        Order result = storage.getPizza();

        assertEquals(order, result);
        verify(order, times(1)).takeCourier();
    }

    @Test
    public void testGetPizzaWhenEmpty() {
        Order result = storage.getPizza();
        assertNull(result);
    }

    @Test
    public void testPutOrder() {
        Order order = mock(Order.class);
        storage.putOrder(order);

        Order result = storage.getOrder();
        assertEquals(order, result);
        verify(order, times(1)).addToQueue();
        verify(order, times(1)).putToBaker();
    }

    @Test
    public void testGetOrderWhenEmpty() {
        Order result = storage.getOrder();
        assertNull(result);
    }
}
