package inventory;

import inventory.*;
import linkedList.BinaryTree;

/**
 * class name: InventoryTree
 * latest modify date: 2025.05.18
 * run environment: MacOS 15.4.1(24E263)
 *
 * Feature: Implemented class that resembles Drinks Tray storing drinks in reality
 *
 * @author Sunghun Wang
 * @version 0.9, implemented class
 * @see
 */

public class Inventory implements BinaryTree<DrinksTray> {

    DrinksTray head;

    public Inventory(){
        head = new DrinksTray(8, 0);
        reFill(new DrinksTray(1, 200));
        reFill(new DrinksTray(3, 300));
        reFill(new DrinksTray(5, 450));
        reFill(new DrinksTray(7, 500));
        reFill(new DrinksTray(9, 550));
        reFill(new DrinksTray(11, 700));
        reFill(new DrinksTray(13, 750));
        reFill(new DrinksTray(15, 800));
    }
    public void reFill(DrinksTray element){
        DrinksTray tmp = head;
        while(true){
            if (element.trayNumber < tmp.trayNumber) {
                if(tmp.left == null){
                    tmp.left = element;
//                    System.out.println("left");
                    break;
                }else{
                    tmp = tmp.left;
//                    System.out.println("left");
                }
            } else if (element.trayNumber > tmp.trayNumber) {
                if(tmp.right == null){
                    tmp.right = element;
//                    System.out.println("right");
                    break;
                }else{
                    tmp = tmp.right;
//                    System.out.println("right");
                }
            }
        }
    }
    public DrinksTray takeOut(int id, int count){
        DrinksTray tmp = new DrinksTray(1, 0);
        return tmp;
    }
    public void insert(DrinksTray element){

    }
    public DrinksTray search(int id){
        DrinksTray tmp = this.head;

        while(tmp.trayNumber != id){
            if(id < tmp.trayNumber && tmp.left != null){
                tmp = tmp.left;
            }else if(id > tmp.trayNumber && tmp.right != null) {
                tmp = tmp.right;
            }else{
                break;
            }
        }
        return tmp;
    }
    public void del(int id){

    }
}

class debugInventoryTree {
    public static void main(String[] args){
        Inventory box = new Inventory();
        DrinksTray tmp = box.search(7);
        System.out.println(tmp.trayNumber);

    }
}