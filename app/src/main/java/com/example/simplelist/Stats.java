package com.example.simplelist;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Stats {
    DONE,TODO,DOING;


    private static final List<Stats> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Stats randomState()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
