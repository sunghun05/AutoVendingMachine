package linkedList;

public interface LinkedList {
    void init();
    void reFill(String name, int count);
    void takeOut(String name, int count);
    Node search(int index);
}
