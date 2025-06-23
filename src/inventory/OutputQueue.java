package inventory;

import linkedList.LinkedList;
import moneyBox.*;
import inventory.Inventory;
import linkedList.Node;

import javax.swing.*;

/**
 * class name:
 * latest modify date:
 * run environment: MacOS 15.4.1(24E263)
 *
 * Feature:
 *
 * @author Sunghun Wang
 * @version 1.0, implemented class
 * @see
 */

public class OutputQueue implements LinkedList {
    public Inventory inventory = new Inventory();

    public product front;
    public product rear;

    final private PaymentMachine payBox = new PaymentMachine();;

    private DrinkOut alert;

    public OutputQueue(){
        this.init();
        payBox.totalMoneyInput = 0;
    }

    public int getTotalMoneyInput(){
        return this.payBox.totalMoneyInput;
    }
    public void sumTotalMoneyInput(int add){
        this.payBox.totalMoneyInput += add;
    }
    public void subTotalMoneyInput(int sub){
        this.payBox.totalMoneyInput -= sub;
    }



    @Override
    public void init(){
        this.front = null;
        this.rear = null;
    }
    @Override
    public void reFill(int id){
        //enqueue, val : tray number
        product newProd = new product();
        newProd.tray = this.inventory.search(id);

        if(isEmpty()){
            newProd.index = 0;
            this.front = newProd;
            this.rear = newProd;
            return ;
        }
        newProd.index = rear.index + 1;
        rear.next = newProd;
        rear = newProd;
    }
    @Override
    //initialize
    public void takeOut(int val){
        while(front != null){
            product tmp = front;
            front = front.next;
            tmp = null;
        }
    }
    public void alert(){
        try{
            this.alert = new DrinkOut();
            alert.setVisible(true);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    @Override
    public int takeOut_(int count){
        //dequeue
        if(isEmpty()){
            return -1;
        }
        //del drink in drinks tray
        if(!front.tray.isEmpty()){
            //정상출력
            System.out.println(this.front.tray.trayNumber);
            this.front.tray.takeOut_(1);
            alert.alert(this.front.tray.trayNumber);
            this.front = front.next;
            return 0;
        }else{
            //Empty
            System.out.println(this.front.tray.trayNumber+"is Empty");

            int tmp = this.front.tray.trayNumber;
            this.front = front.next;
            return tmp;
        }
    }
    public void del(int id){
        product tmp = front;
        product pre = null;
        while(front != null){
            if(front.tray.trayNumber == id){
                if(pre == null){
                    pre = front;
                    front = front.next;
                    pre = null;
                    return ;
                }else{
                    pre.next = front.next;
                    front = front.next;
                }
            }
            pre = front;
            front = front.next;
        }
        this.front = tmp;
    }
    public boolean isEmpty(){
        return front == null;
    }
    //select drink trays
    //output queue
}
