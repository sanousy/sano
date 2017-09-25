package sano;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HowariS
 */
public class Stack<T> {

    List<T> stack = null;

    Stack() {
        stack = new ArrayList<>();
    }

    public int Top() {
        return stack.size();
    }

    public void push(T item) {
        stack.add(item);
    }

    public T pop() {
        //System.out.println("Stack size" + stack.size());
        T item = stack.get(stack.size() - 1);
        stack.remove(stack.size() - 1);
        return item;
    }

    public T peep() {
        T item = null;
        try {
            item = stack.get(stack.size() - 1);
        } catch (Exception e) {
        };
        return item;
    }

}
