package inventory;

import linkedList.LinkedList;
import linkedList.Node;
import moneyBox.MoneyTray;

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
public class DrinksTray implements LinkedList {

    Drinks head;
    Drinks tail;

    DrinksTray left;
    DrinksTray right;

    int trayNumber;
    boolean isSelected;

    DrinksTray(int trayNumber){
        this.trayNumber = trayNumber;
        init();
    }
    @Override
    public void init(){
        //head.index: length, head.value: trayNumber
        this.head = new Drinks(0, this.trayNumber);
        this.tail = head;
        reFill(10);
    }
    @Override
    public void reFill(int count){
        Drinks newDrinks;
        int endPoint = this.head.index + count;
        for(int i = this.head.index; i<endPoint; i++) {
            newDrinks = new Drinks(i, this.trayNumber);
            if (isEmpty()) {
                newDrinks.prev = head;
                head.next = newDrinks;
            } else {
                Drinks tmp = head;
                while (tmp.next != null) {
                    tmp = tmp.next;
                }
                tmp.next = newDrinks;
                newDrinks.prev = tmp;
            }
            head.index++;
            tail = tail.next;
        }
    }
    @Override
    public void takeOut(int count){
        for(int i = 0; i<count; i++){
            try{
            this.tail = this.tail.prev;
            this.tail.next = null;
            }catch(NullPointerException e){
                System.out.printf("Tray %d is Empty", this.trayNumber);
            }
        }
    }
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
        DrinksTray tray1 = new DrinksTray(1);
        tray1.takeOut(8);
        tray1.search();
    }
}
