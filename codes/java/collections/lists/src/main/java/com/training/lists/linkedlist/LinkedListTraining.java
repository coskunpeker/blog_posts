package main.java.com.training.lists.linkedlist;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class LinkedListTraining {

    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);


        list.addFirst(0);

        list.addLast(6);

        list.forEach(System.out::println);

        list.pollFirst();
        System.out.println("#############");
        list.forEach(System.out::println);

        list.pollLast();
        System.out.println("#############");
        list.forEach(System.out::println);

        System.out.println("#############");

        IntStream.range(0, list.size())
                 .forEach(x-> {
                     Integer pop = list.pop();
                     System.out.println("popped: " + pop);
                 });

        System.out.println("#############");
        list.forEach(System.out::println);

        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);

        List<Integer> list2 = new LinkedList<>(set);

        list2.forEach(System.out::println);

    }

}
