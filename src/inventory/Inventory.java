package inventory;

import inventory.*;
import linkedList.BinaryTree;

/**
 * class name: InventoryTree
 * latest modify date: 2025.05.16
 * run environment: MacOS 15.4.1(24E263)
 *
 * Feature:
 *
 * @author Sunghun Wang
 * @version 1.0, implemented class
 * @see
 */

class InventoryTree implements BinaryTree<DrinksTray> {

    DrinksTray head;

    InventoryTree(){
        head = new DrinksTray(6);
        reFill(new DrinksTray(1));
        reFill(new DrinksTray(3));
        reFill(new DrinksTray(5));
        reFill(new DrinksTray(7));
        reFill(new DrinksTray(9));
    }
    public void reFill(DrinksTray element){
        DrinksTray tmp = head;
        while(true){
            if (element.trayNumber < tmp.trayNumber) {
                if(tmp.left == null){
                    tmp.left = element;
                    System.out.println("left");
                    break;
                }else{
                    tmp = tmp.left;
                    System.out.println("left");
                    break;
                }
            } else if (element.trayNumber > tmp.trayNumber) {
                if(tmp.right == null){
                    tmp.right = element;
                    System.out.println("right");
                    break;
                }else{
                    tmp = tmp.right;
                    System.out.println("right");
                    break;
                }
            }
        }
    }
    public DrinksTray takeOut(int id, int count){
        DrinksTray tmp = new DrinksTray(1);
        return tmp;
    }
    public void insert(DrinksTray element){

    }
    public DrinksTray search(int id){
        DrinksTray tmp = new DrinksTray(1);
        return tmp;
    }
    public void del(int id){

    }
}

class debugInventoryTree {
    public static void main(String[] args){
        InventoryTree box = new InventoryTree();
    }
}