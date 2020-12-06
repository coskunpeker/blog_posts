package main.java.com.training.lists.copyonwritearraylist;

import java.lang.reflect.Field;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

public class CopyOnWriteArrayListTraining {

    public static void main(String[] args) {
        observeCapacity();
    }

    private static void observeCapacity() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();

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

    private static int getCapacity(CopyOnWriteArrayList<?> l) throws Exception {
        Field dataField = CopyOnWriteArrayList.class.getDeclaredField("array");
        dataField.setAccessible(true);
        return ((Object[]) dataField.get(l)).length;
    }

}
