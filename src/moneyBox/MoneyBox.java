package moneyBox;

import inventory.DrinkOut;
import inventory.DrinksTray;
import linkedList.LinkedList;
//import moneyBox.PaymentMachine;
import moneyBox.*;

/**
 * class name: MoneyBox
 * latest modify date: 2025.05.15
 * run environment: MacOS 15.4.1(24E263)
 *
 * Feature: manages each money trays with linked list method
 *
 * @author Sunghun Wang
 * @version 1.1.0, implemented class
 * @see
 */

class MoneyTmp {
    int moneyUnit;
    MoneyTmp next;
    MoneyTmp(int moneyUnit){
        this.moneyUnit = moneyUnit;
    }
}

public class MoneyBox extends PaymentMachine implements LinkedList {
    MoneyTray head = null;
    PaymentMachine paymentMachine;

    MoneyTmp front;
    MoneyTmp rear;

    public Integer totalMoneyInMachine;

    public MoneyBox(){
        init();
        front = null;
        rear = null;
        this.paymentMachine = paymentMachine;
        this.totalMoneyInMachine = 16600;
    }
    @Override
    public void init(){
        reFill(1000);
        reFill(500);
        reFill(100);
        reFill(50);
        reFill(10);
    }
    @Override
    public void reFill(int moneyUnit){
        MoneyTray newMoneyTray = new MoneyTray(moneyUnit);
        if(head==null){
            head = newMoneyTray;
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
        System.out.println("take out");
    }
    public void search(){
        MoneyTray tmp = head;
        while(tmp.next != null){
            tmp = tmp.next;
            System.out.println(tmp.moneyUnit);
        }
    }

    @Override
    public int takeOut_(int count){
        return 0;
    }
    public MoneyTray search(int moneyUnit){
        MoneyTray tmp = head;
        while(tmp != null){
            if(tmp.moneyUnit == moneyUnit){return tmp;}
            tmp = tmp.next;
        }
        return null;
    }
    public void changeNstore(int price) {
        MoneyTray tmp = head;

        Integer[] units = {1000, 500, 100, 50, 10};
        Integer[] counts = new Integer[5];

        for(int i = 0; i<5; i++){
            counts[i] = price / units[i];
            price -= units[i] * counts[i];

            tmp.reFill(counts[i]);
            this.totalMoneyInMachine += counts[i]*units[i];
            tmp = tmp.next;
        }
    }
    public void changeNreceive(int price) {
        MoneyTray tmp = head;

        Integer[] units = {1000, 500, 100, 50, 10};
        Integer[] counts = new Integer[5];
        MoneyOut out = new MoneyOut();
        for(int i = 0; i<5; i++){
            counts[i] = price / units[i];
            int res = tmp.takeOut_(counts[i]);
            price -= units[i] * counts[i];
            this.totalMoneyInMachine -= counts[i]*units[i];
            if(res == 0){

                if(counts[i] != 0) {System.out.println(units[i]+": "+counts[i]+"개");}
                for(int j = 0; j<counts[i]; j++){
                    out.alert(units[i]);
                }

            }else{
                for(int j = 0; j<res + counts[i]; j++){
                    out.alert(units[i]);
                }
                out.alert(-1);
                price -= units[i] * res;
                this.totalMoneyInMachine -= res*units[i];
            }

            tmp = tmp.next;
        }
    }
//    public void storeMoney(int price){
//
//    }
    boolean isEmptyTmp(){
        return front == null;
    }
}
class DebugMoneyBox {
    public static void main(String[] args){
        MoneyBox newMoneyBox = new MoneyBox();
        newMoneyBox.search();
    }
}
