package moneyBox;

import linkedList.LinkedList;
import moneyBox.Money;

public class MoneyBox implements LinkedList {
    private int[] moneyUnits = {10, 50, 100, 500, 1000};
    private Money head;

    private int MAX_MONEY_COUNT = 15;

    @Override
    public void init(int count){
        this.head = new Money();
        for(int i= 0; i<moneyUnits.length; i++){
            Money moneyUnit = new Money(i, moneyUnits[i], 10, 0);
            if (head.next == null) {
                head.next = moneyUnit;
            } else {
                Money tmp = this.head;
                while (tmp.next != null) {
                    tmp = tmp.next;
                }
                tmp.next = moneyUnit;
            }
        }
    }
    @Override
    public Money search(int index){
        Money tmp = this.head;
        for (int i = 0; i<index; i++){
            tmp = tmp.next;
        }
        return tmp;
    }


    //
    int[] calculateChanges(int money){

    }
    int[] getMoneyLog(){

    }

}
