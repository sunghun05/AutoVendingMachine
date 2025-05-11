package moneyBox;

import linkedList.Node;
import linkedList.LinkedList;

class Money extends Node {
    Money next;
    Money(int index, int value){
        // value == index
        super(index, value);
        this.next = null;
    }
    Money(int value){
        super(value);
        this.next = null;
    }
}

public class MoneyTray implements LinkedList {
    Money top;
    int moneyUnit;
    final int sizeOfMoneyTray = 10;

    MoneyTray(int moneyUnit){
        //moneyUnit is price of the currency
        this.top = new Money(-1, moneyUnit);
        this.moneyUnit = moneyUnit;
        init();
    }
    //head
    @Override
    public void init() {
        for(int i = 0; i < this.sizeOfMoneyTray; i++){
            Money money = new Money(i);
            if (top.next == null) {
                top.next = money;
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
