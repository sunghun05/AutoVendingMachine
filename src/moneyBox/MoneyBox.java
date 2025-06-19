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
    public MoneyBox(){
        init();
        front = null;
        rear = null;
        this.paymentMachine = paymentMachine;
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
    //use linked list
//    int[] getMoneyLog(){
//
//    }
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
    //enqueue
    public void receiveMoney(int moneyUnit){
        MoneyTmp newMoney = new MoneyTmp(moneyUnit);
        newMoney.next = null;
        if(isEmptyTmp()){
            front = newMoney;
            rear = front;
        }else{
            rear.next = newMoney;
            rear = rear.next;
        }
    }
    //dequeue all (store money)
    public void pushMoney(){
        while(!isEmptyTmp()){
            MoneyTray tmp = head;
            while(tmp != null){
                if(tmp.moneyUnit == front.moneyUnit){
                    tmp.reFill(1);
                }
                tmp = tmp.next;
            }
            front = front.next;
        }
    }
    public void changeNstore(int price) {
        MoneyTray tmp = head;

        Integer[] units = {1000, 500, 100, 50, 10};
        Integer[] counts = new Integer[5];

        for(int i = 0; i<5; i++){
            counts[i] = price / units[i];
            price -= units[i] * counts[i];

            tmp.reFill(counts[i]);
            tmp = tmp.next;
        }
    }
    public void changeNreceive(int price) {
        MoneyTray tmp = head;

        Integer[] units = {1000, 500, 100, 50, 10};
        Integer[] counts = new Integer[5];

        for(int i = 0; i<5; i++){
            counts[i] = price / units[i];
            price -= units[i] * counts[i];
            if(counts[i] != 0) {System.out.println(units[i]+": "+counts[i]+"개");}
            int res = tmp.takeOut_(counts[i]);
            if(res != -1){
                for(int j = 0; j<counts[i]; j++){
                    new MoneyOut(units[i]);
                }
            }else{
                new MoneyOut(-1);
            }


//            if(res == -1
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
