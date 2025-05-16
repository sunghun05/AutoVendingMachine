import javax.swing.*;
import java.awt.*;

public class AutoVendingMachine extends JFrame {
    public AutoVendingMachine() {
        setTitle("Auto Vending Machine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();

        c.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 40));


        setSize(900, 600);
        setVisible(true);
    }
    public static void main(String[] args){
        new AutoVendingMachine();
    }
}
