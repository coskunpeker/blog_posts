package main.java.com.training.arraylist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArraysUtilityTraining {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("one", "two", "three"));
        List<String> unmodifiableList = Collections.unmodifiableList(list);
        unmodifiableList.add("four");

    }

    private static void cast() {
        ArrayList<String> list = (ArrayList<String>) Arrays.asList("a", "b", "c");
        list.forEach(System.out::println);
    }

}
