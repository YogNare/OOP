package ru.nsu.baev;

import java.util.ArrayList;

public class WTF {
    final ArrayList<Integer> num;
    int counter = 0;
    public WTF(ArrayList<Integer> num) {
        this.num = num;
    }

    public void addOne() {
        num.add(counter ++);
    }
}
