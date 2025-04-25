package dataStructs;

class Node {

    int val;
    String strData;
    Node next;

    public Node(){
        this.next = null;
    }
}
public class LinkedList {
    private Node head = new Node();
    public LinkedList() {
    }
    public void create(int data) {
        Node newNode = new Node();
        if(head.next == null){
            newNode.val = data;
            head.next = newNode;
        }
        else {
            Node tmp = this.head;
            while(tmp.next!=null){
                tmp = tmp.next;
            }
            newNode.val = data;
            tmp.next = newNode;
        }
    }
    public int search(int goal) {
        int index = 0;
        Node tmp = this.head.next;
        while(tmp.next!=null){
            if(tmp.val == goal){
                System.out.printf("the data '%d'locates at the index %d", goal, index);
                return index;
            }
            tmp = tmp.next;
            index++;
        }
        return -1;
    }
    public void insert(int index, int val){
        Node tmp = this.head.next;
        Node preTmp = this.head;
        int cnt = 0;
        while(tmp.next!=null){
            if(cnt == index){
                Node newNode = new Node();
                newNode.val = val;
                newNode.next = tmp;
                preTmp.next = newNode;
            }
            cnt++;
            preTmp = tmp;
            tmp = tmp.next;
        }
    }
    public void show() {
        Node tmp = this.head;
        while(tmp.next!=null){
            tmp = tmp.next;
            System.out.printf("%4d", tmp.val);
        }
        System.out.println();
    }
    public static void main(String[] args){
        LinkedList list = new LinkedList();
        for (int i = 1; i<11; i++){
            list.create(i);
        }
        list.show();
        list.insert(2, 10);
        list.show();
        list.search(4);
    }
}
