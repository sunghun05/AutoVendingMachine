package linkedList;

public class Node<T> {
    public int index;
    public int value;
    public T next;
    public Node(int index, int value) {
        this.index = index;
        this.value = value;
        this.next = null;
    }
}
