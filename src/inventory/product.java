package inventory;

public class product{
    int index;
    public DrinksTray tray;
    product next;
    product(){
        this.tray = null;
        this.next = null;
    }
}