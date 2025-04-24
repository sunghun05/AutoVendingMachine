package dataStructs;

class Space {
    int top = -1;
    int val;
}

abstract public class Stack {
    abstract public void push(Space s, int val);

    abstract public int pop(Space s);
    abstract public boolean isEmpty(Space s);
    abstract public boolean isFull(Space s);



}
