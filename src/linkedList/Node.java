package linkedList;

abstract public class Node {
    int index;
    int price;
    int count;
    int loss;
    Node next;
    Node(int index, int price, int count){
        
    }
    boolean isEmpty(){
        if (this.count == 0){
            return true;
        }
        else{
            return false;
        }
    }
}
