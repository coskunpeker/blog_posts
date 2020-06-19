package main.java.com.training.arraylist;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class CapacityTraining {

    public static void main(String[] args) throws Exception {
        increaseCapacity();
        decreaseCapacity();
        observeCapacity();

    }

    private static void increaseCapacity() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        System.out.println("before the increasing: " + getCapacity(list));
        list.ensureCapacity(15);
        System.out.println("after the increasing: " + getCapacity(list));
    }

    private static void decreaseCapacity() throws Exception {
        ArrayList<Integer> list = new ArrayList<>();
        list.ensureCapacity(15);
        System.out.println("before the decreasing: " + getCapacity(list));

        // add some elements to list.
        list.add(1);
        list.add(2);

        // trimToSize will decrease capacity from 15 to current capacity, so it will be 2
        list.trimToSize();

        System.out.println("after the decreasing: " + getCapacity(list));
    }

    private static void observeCapacity() {
        ArrayList<Integer> list = new ArrayList<>();

        // observe the add operation
        IntStream.range(0, 20)
                 .forEach(element -> {
                     list.add(element);
                     try {
                         System.out.print("added element: " + element);
                         System.out.print(" ## list size: " + list.size());
                         System.out.println(" ## capacity after add operation: " + getCapacity(list));
                     } catch (Exception e) {
                         throw new RuntimeException(e);
                     }
                 });

    }

    /**
     * We can easily get array 'elementData' that is used by ArrayList to store elements.
     * The length of elementData gives the capacity of ArrayList
     */
    private static int getCapacity(ArrayList<?> l) throws Exception {
        Field dataField = ArrayList.class.getDeclaredField("elementData");
        dataField.setAccessible(true);
        return ((Object[]) dataField.get(l)).length;
    }

}
