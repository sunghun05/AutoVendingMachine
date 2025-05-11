package moneyBox;

import linkedList.*;

/**
 * class name: Money
 * latest modify date: 2025.05.11
 * run environment: mac os 15.4.1(24E263)
 *
 * @author Sunghun Wang
 * @version 1.0, implemented class
 * @see linkedList.Node
 */

class Money extends Node {
    Money next;
    Money(int index, int value){
        // value == index
        super(index, value);
        this.next = null;
    }
}


/**
 * class name: MoneyTray
 * latest modify date: 2025.05.11
 * run environment: MacOS 15.4.1(24E263)
 *
 * Feature: Money Tray with Stack algorithm by linked list data structure
 *
 * @author Sunghun Wang
 * @version 1.0, implemented class
 * @see linkedList.LinkedList
 */

public class MoneyTray implements LinkedList {
    //stack
    Money top;
    //money box linked list
    MoneyTray next;
    //10, 50, 100, 500, 1000
    int moneyUnit;
    //maximum capacity of money tray
    final int sizeOfMoneyTray = 10;
    //sum total income
    static int totalIncome = 0;

    MoneyTray(int moneyUnit){
        //moneyUnit is price of the currency
        this.top = new Money(-1, moneyUnit);
        this.moneyUnit = moneyUnit;
        init();
    }
    //head
    //initialize money tray (stack)
    @Override
    public void init() {
        //add 10
        this.reFill(10);
    }
    // == append
    @Override
    public void reFill(int count){
        int start = this.top.index+1;
        for (int i = start; i<start+count; i++){
            if(isFull()){System.out.println("Stack Overflow(money tray is full)");break;}
            else{
                Money newMoney = new Money(i, this.moneyUnit);
                if(isEmpty()){
                    top.next = newMoney;
                }else{
                    newMoney.next = top.next;
                    top.next = newMoney;
                }
                System.out.println("top: "+this.top.next.index);
            }
        }
    }
    @Override
    public void takeOut(int count){
        for (int i = 0; i<count; i++){
            if(isEmpty()) {System.out.println("Stack Underflow(no money left)");break;}
            else{
                System.out.println("top: "+ (--this.top.next.index));
                top.next = top.next.next;
            }
        }
    }
    boolean isFull() {
        return (this.top.index == this.sizeOfMoneyTray-1);
    }
    boolean isEmpty() {
        return (this.top.next == null);
    }
}
/**
 * class name: debugMoneyTray
 * latest modify date: 2025.05.11
 * run environment: mac os 15.4.1(24E263)
 *
 * @author Sunghun Wang
 * @version 1.0, implemented class
 *
 */
class debugMoneyTray{
    public static void main(String[] args){
        MoneyTray Tray_10 = new MoneyTray(10);
        Tray_10.takeOut(9);
//        Tray_10.takeOut(2);
        System.out.println(Tray_10.top.next.index);
    }
}
