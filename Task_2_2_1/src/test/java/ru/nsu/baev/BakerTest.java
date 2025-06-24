package ru.nsu.baev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.mockito.Mockito.*;

public class BakerTest {

    private Storage storage;
    private Order order;

    @BeforeEach
    public void setUp() {
        storage = mock(Storage.class);
        order = mock(Order.class);
    }

    @Test
    public void testBakerProcessesOrderSuccessfully() throws InterruptedException {
        // Setup
        when(storage.getOrder())
                .thenReturn(order)     // First call: got an order
                .thenReturn(null);     // Second call: stop trying

        when(storage.putPizza(order)).thenReturn(0);  // First attempt succeeds

        long workTime = 100; // 100 ms to "bake"
        long endTime = System.currentTimeMillis() + 500; // run for 500 ms

        Baker baker = new Baker(workTime, storage, endTime);
        baker.start();
        baker.join(); // wait for thread to finish

        // Verify interactions
        verify(storage, atLeastOnce()).getOrder();
        verify(storage, atLeastOnce()).putPizza(order);
    }

    @Test
    public void testBakerSkipsNullOrdersUntilTimeRunsOut() throws InterruptedException {
        // getOrder returns null until time is up
        when(storage.getOrder()).thenAnswer(inv -> {
            Thread.sleep(50);
            return null;
        });

        long workTime = 100;
        long endTime = System.currentTimeMillis() + 200;

        Baker baker = new Baker(workTime, storage, endTime);
        baker.start();
        baker.join();

        // Should have tried to getOrder a few times, but never putPizza
        verify(storage, atLeast(1)).getOrder();
        verify(storage, never()).putPizza(any());
    }

    @Test
    public void testBakerRequeuesOrderIfInterruptedDuringBaking() throws InterruptedException {
        AtomicBoolean baked = new AtomicBoolean(false);

        when(storage.getOrder()).thenReturn(order);
        when(storage.putPizza(order)).thenAnswer(invocation -> {
            if (!baked.get()) {
                Thread.sleep(500); // simulate long baking
                baked.set(true);
            }
            return 0;
        });

        long workTime = 100; // less than baking delay
        long endTime = System.currentTimeMillis() + 150; // Baker will timeout during baking

        Baker baker = new Baker(workTime, storage, endTime);
        baker.start();
        baker.join();

        // Он должен был попытаться снова поставить заказ в очередь
        verify(storage).putPizza(order);
    }

}
