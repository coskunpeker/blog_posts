package main.java.com.training.vector;

import java.util.List;
import java.util.Vector;

public class VectorTraining {

    public static void main(String[] args) {
        List<String> list = new Vector<>();

        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add(null);
        list.add(null);

        list.forEach(System.out::println);

        

    }
    
}