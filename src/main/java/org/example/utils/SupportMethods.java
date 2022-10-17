package org.example.utils;

import java.util.Random;

public class SupportMethods {
    public  boolean isNotEmptyString(String string) {
        return !string.isEmpty();
    }

    public int getRandomNumberBetweenTwoNumbers(int start, int end){
        return new Random().nextInt(end) + start;
    }
}
