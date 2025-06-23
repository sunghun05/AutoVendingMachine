package inventory;

import linkedList.LinkedList;
import linkedList.Node;

/**
 * class name: Drinks
 * latest modify date: 2025.05.18
 * run environment: MacOS 15.4.1(24E263)
 *
 * Feature:
 *
 * @author Sunghun Wang
 * @version 1.0, implemented class
 * @see
 */

//Node 클래스를 상속받아 Drinks 클래스 구성
class Drinks extends Node<Drinks> {
    Drinks prev;
    Drinks(int index, int value){
        super(index, value);
        this.prev = null;
    }
}

/**
 * class name: DrinksTray
 * latest modify date: 2025.05.16
 * run environment: MacOS 15.4.1(24E263)
 *
 * Feature:
 *
 * @author Sunghun Wang
 * @version 1.0, implemented class
 * @see
 */
// Queue
//node of inventory which is binary tree
public class DrinksTray implements LinkedList {

    Drinks head;
    Drinks tail;

    DrinksTray left;
    DrinksTray right;

    public int trayNumber;
    public int count;
    public Integer price;

    DrinksTray(int trayNumber, int price){  //
        this.trayNumber = trayNumber;
        this.price = price;
        count = 0;
        init();
    }

    //initialize
    @Override
    public void init(){
        //head.index: length, head.value: trayNumber
        this.head = new Drinks(0, this.trayNumber);
        this.tail = head;
        reFill(10);
    }

    //refill drinks with linked list data structure
    @Override
    public void reFill(int count){
        Drinks newDrinks;
        int endPoint = this.head.index + count;
        for(int i = this.head.index; i<endPoint; i++) {
            newDrinks = new Drinks(i, this.trayNumber); // generate new node
            if (isEmpty()) {                            // if tray is empty
                newDrinks.prev = head;
                head.next = newDrinks;
            } else {                                    // 재고가 이미 있을 때
                Drinks tmp = head;
                while (tmp.next != null) {
                    tmp = tmp.next;
                }
                tmp.next = newDrinks;
                newDrinks.prev = tmp;
            }
            head.index++;
            tail = tail.next;
            this.count++;
        }
    }

    //get drinks
    @Override
    public void takeOut(int count){
        return ;
    }
    @Override
    public int takeOut_(int count){
        for(int i = 0; i<count; i++){
            try{
                this.tail = this.tail.prev;
                this.tail.next = null;
            }catch(NullPointerException e){
                return -1;
            }
            this.count--;
        }
        return 0;
    }
    //순차 탐색
    void search(){
        Drinks tmp = head;
        while(tmp.next != null){
            tmp = tmp.next;
            System.out.println(tmp.index);
        }
    }
    boolean isEmpty() {
        return (head == tail);
    }
}

class debugDrinksTray {
    public static void main(String[] args){
        DrinksTray tray1 = new DrinksTray(1, 0);
        tray1.takeOut(2);
        tray1.takeOut_(6);
        tray1.reFill(10);
        tray1.search();
        System.out.println(tray1.count);
    }
}
