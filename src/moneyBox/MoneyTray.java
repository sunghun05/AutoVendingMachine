package moneyBox;

import linkedList.LinkedList;
import linkedList.Node;

/**
 * class name: Money
 * latest modify date: 2025.05.11
 * run environment: mac os 15.4.1(24E263)
 *
 * @author Sunghun Wang
 * @version 1.0, implemented class
 * @see linkedList.Node
 */

class Money extends Node<Money> {
    Money(int index, int value){
        super(index, value);
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
    final int sizeOfMoneyTray = 50;
    //sum total income
    static int totalIncome = 0;

    MoneyTray(int moneyUnit){
        this.next = null;
        //moneyUnit is price of the currency
        this.top = null;
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
        int start;
        try{
            start = this.top.index+1;
        }catch (Exception ex){
            start = 0;
        }
        for (int i = start; i<start+count; i++){
            Money newMoney = new Money(i, this.moneyUnit);

            if(isEmpty()){
                top = newMoney;
            }else if(isFull()) {System.out.println("Stack Overflow " + moneyUnit); break;}
            else {
                newMoney.next = top;
                top = newMoney;
            }
            System.out.println("top: "+this.top.index);
        }
    }
    @Override
    public void takeOut(int count){

    }
    boolean isFull() {
        return (this.top.index == this.sizeOfMoneyTray-1);
    }
    boolean isEmpty() {
        return (this.top == null);
    }

    @Override
    public int takeOut_(int count){
        System.out.println("out "+moneyUnit);
        for (int i = 0; i<count; i++){
            if(isEmpty()) {System.out.println("Stack Underflow(no money left): returns "+-1*(count-i) );return -1*(count-i);}
            else{
                System.out.println("top: "+ (this.top.index));
                Money tmp = top.next;
                top = null;
                top = tmp;

            }
        }
        System.out.println("end");
        return 0;
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
        Tray_10.takeOut_(10);
//        Tray_10.takeOut(2);
        System.out.println(Tray_10.top.index);
    }
}
