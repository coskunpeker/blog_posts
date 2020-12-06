package main.java.com.training.lists.arraylist;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class FailFastTraining {

    public static void main(String[] args) {
        failFast();
    }


    private static void failFast() {
        try {
            List<Integer> list = new ArrayList<>();

            list.add(1);
            list.add(2);
            list.add(3);
            list.add(4);
            list.add(5);

            for (Integer value : list) {
                System.out.println("List Value:" + value);
                if (value.equals(3)) {
                    list.remove(value);
                }
            }
        } catch (ConcurrentModificationException e) {
            // do not apply a logic here.
            // There is no guarantee that this exception will be thrown
            // just do logging etc.
        }
    }

}
