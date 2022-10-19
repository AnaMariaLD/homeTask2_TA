package org.example.utils;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SupportMethods {
    public boolean isNotEmptyString(String string) {

        return !string.isEmpty();
    }

    public int getRandomNumberBetweenTwoNumbers(int start, int end) {
        return new Random().nextInt(end) + start;
    }

    public static List<Double> getFullPrice(Iterator<WebElement> wholePrice, Iterator<WebElement> decimalPrice, int resultPerPage) {
        List<Double> productsList = new ArrayList<>();
        int i = 0;
        while (wholePrice.hasNext() && decimalPrice.hasNext() && i < resultPerPage) {
            i++;
            String combinedPrice = wholePrice.next().getText().replace(",", "") + "." + decimalPrice.next().getText();
            if (combinedPrice.equals(".")) {
                continue;
            }
            System.out.println(combinedPrice);
            double temp = Double.parseDouble(combinedPrice);
            productsList.add(temp);
        }
        return productsList;
    }

}
