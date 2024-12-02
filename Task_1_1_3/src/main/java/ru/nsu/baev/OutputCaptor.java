package ru.nsu.baev;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class OutputCaptor {
    private final PrintStream originalOut;
    private final ByteArrayOutputStream outputStreamCaptor;

    public OutputCaptor() {
        // Сохраняем текущий поток вывода
        this.originalOut = System.out;
        this.outputStreamCaptor = new ByteArrayOutputStream();
    }

    // Начинаем перехват вывода
    public void start() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    // Останавливаем перехват и восстанавливаем поток
    public void stop() {
        System.setOut(originalOut);
    }

    // Получаем перехваченный вывод
    public String getCapturedOutput() {
        return outputStreamCaptor.toString();
    }
}