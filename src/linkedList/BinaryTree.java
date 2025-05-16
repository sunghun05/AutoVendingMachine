package linkedList;

public interface BinaryTree<Type> {

    public void reFill(Type element);
    public Type takeOut(int id, int count);
    public void insert(Type element);
    public Type search(int id);
    public void del(int id);

}
