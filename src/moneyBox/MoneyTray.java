package moneyBox;

import linkedList.Node;
import linkedList.LinkedList;

class Money extends Node {
    Money next;
    Money(int value){
        super(value);
        this.next = null;
    }
}

public class MoneyTray implements LinkedList {
    Money head;
    Money tail;
    int moneyUnit;

    MoneyTray(int moneyUnit){
        this.head = new Money(moneyUnit);
        this.moneyUnit = moneyUnit;
        init(10);
    }
    //head
    @Override
    public void init(int count) {
        for(int index = 0; index < count; index++){
            Money money = new Money(index);
            if (head.next == null) {
                head.next = money;
            } else {
                Money tmp = this.head;
                while (tmp.next != null) {
                    tmp = tmp.next;
                }
                tmp.next = money;
            }
        }
    }
    @Override
    void reFill(int index, int count){
        Money tmp = this.head;
        while (tmp.next != null) {
            tmp = tmp.next;
        }
    }
    @Override
    void takeOut(int index, int count);
    public int search(int goal) {
        int index = 0;
        dataStructs.Node tmp = this.head.next;
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
        dataStructs.Node tmp = this.head.next;
        dataStructs.Node preTmp = this.head;
        int cnt = 0;
        while(tmp.next!=null){
            if(cnt == index){
                dataStructs.Node newNode = new dataStructs.Node();
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
        dataStructs.Node tmp = this.head;
        while(tmp.next!=null){
            tmp = tmp.next;
            System.out.printf("%4d", tmp.val);
        }
        System.out.println();
    }
}
