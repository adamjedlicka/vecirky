package jeda00.container;

public class BadAdder implements Adder {

    @Override
    public int add(int a, int b) {
        return a - b;
    }

}
