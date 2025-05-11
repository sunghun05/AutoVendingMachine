package moneyBox;

import linkedList.LinkedList;
import moneyBox.PaymentMachine;
import moneyBox.MoneyTray;

/**
 * class name: MoneyBox
 * latest modify date: 2025.05.11
 * run environment: MacOS 15.4.1(24E263)
 *
 * Feature: manages each money trays with linked list method
 *
 * @author Sunghun Wang
 * @version 1.0, implemented class
 * @see
 */

public class MoneyBox extends PaymentMachine implements LinkedList {
    MoneyTray head;
    MoneyBox(){
        head.next = null;
        init();
    }
    @Override
    public void init(){
        reFill(10);
        reFill(50);
        reFill(100);
        reFill(500);
        reFill(1000);
    }
    @Override
    public void reFill(int moneyUnit){
        MoneyTray newMoneyTray = new MoneyTray(moneyUnit);
        if(head.next==null){
            head.next = newMoneyTray;
        }else{
            MoneyTray tmp = head;
            while(tmp.next != null){
                tmp = tmp.next;
            }
            tmp.next = newMoneyTray;
        }
    }
    @Override
    public void takeOut(int moneyUnit){

    }
    //use linked list
//    int[] calculateChanges(int money){
//
//    }
//    int[] getMoneyLog(){
//
//    }

}
