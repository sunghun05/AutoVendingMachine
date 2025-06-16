package inventory;

import linkedList.LinkedList;
import inventory.Inventory;
import linkedList.Node;

class product{
    int index;
    DrinksTray tray;
    product next;
    product(){
        this.tray = null;
        this.next = null;
    }
}

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
    Inventory inventory = new Inventory();

    product front;
    product rear;

    @Override
    public void init(){
        this.front = null;
        this.rear = null;
    }
    @Override
    public void reFill(int val){
        //enqueue, val : tray number
        product newProd = new product();
        newProd.tray = this.inventory.search(val);

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
    public void takeOut(int val){
        //dequeue
        if(!isEmpty()){
            //del drink in drinks tray
            this.front.tray.takeOut(1);
        }else {
            return ;
        }
    }
    public void del(int index){

    }
    private boolean isEmpty(){
        if(rear == front && front == null){
            return true;
        }else return false;
    }
    //select drink trays
    //output queue
}
