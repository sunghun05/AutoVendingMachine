package inventory;

import linkedList.LinkedList;
import linkedList.Node;
import moneyBox.MoneyTray;

/**
 * class name: Drinks
 * latest modify date: 2025.05.
 * run environment: MacOS 15.4.1(24E263)
 *
 * Feature:
 *
 * @author Sunghun Wang
 * @version 1.0, implemented class
 * @see
 */

class Drinks extends Node<Drinks> {
    Drinks(int index, int value){
        super(index, value);
    }
}

/**
 * class name: DrinksTray
 * latest modify date: 2025.05.
 * run environment: MacOS 15.4.1(24E263)
 *
 * Feature:
 *
 * @author Sunghun Wang
 * @version 1.0, implemented class
 * @see
 */
public class DrinksTray implements LinkedList {

    Drinks head;

    int trayNumber;

    DrinksTray(int trayNumber){
        this.trayNumber = trayNumber;
        init();
    }
    @Override
    public void init(){
        //head.index: length, head.value: trayNumber
        this.head = new Drinks(0, this.trayNumber);
        reFill(10);
    }
    @Override
    public void reFill(int count){
        Drinks newDrinks;
        for(int i = this.head.index; i<count+this.head.index; i++) {
            newDrinks = new Drinks(i, this.trayNumber);
            if (head.next == null) {
                head.next = newDrinks;
                head.index++;
            } else {
                Drinks tmp = head;
                while (tmp.next != null) {
                    tmp = tmp.next;
                }
                tmp.next = newDrinks;
                head.index++;
            }
        }
    }
    @Override
    public void takeOut(int moneyUnit){
        return ;
    }
    void search(){
        Drinks tmp = head;
        while(tmp.next != null){
            tmp = tmp.next;
            System.out.println(tmp.index);
        }
    }

}

class debugDrinksTray {
    public static void main(String[] args){
        DrinksTray tray1 = new DrinksTray(1);
        tray1.search();
    }
}
