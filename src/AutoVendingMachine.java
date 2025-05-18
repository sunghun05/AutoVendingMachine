import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * class name: AutoVendingMachine
 * latest modify date: 2025.05.18
 * run environment: MacOS 15.4.1(24E263)
 *
 * Feature:
 *
 * @author Sunghun Wang
 * @version 0.8, implemented class
 * @see
 */
import inventory.*;
import moneyBox.*;

public class AutoVendingMachine extends JFrame {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private JRadioButton radioButton5;
    private JButton a10Button;
    private JButton a50Button;
    private JButton a100Button;
    private JButton a500Button;
    private JButton a1000Button;

    Inventory inventory = new Inventory();
    MoneyBox moneyBox = new MoneyBox();

    public AutoVendingMachine() {

        setTitle("Auto Vending Machine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();

        c.setLayout(new GridLayout(3, 3, 5, 5));

        setSize(900, 300);
        setVisible(true);

        // 라디오 버튼 생성
        radioButton1 = new JRadioButton("음료1");
        radioButton2 = new JRadioButton("음료2");
        radioButton3 = new JRadioButton("음료3");
        radioButton4 = new JRadioButton("음료4");
        radioButton5 = new JRadioButton("음료5");

//        // 버튼 그룹으로 묶기
//        ButtonGroup group = new ButtonGroup();
//        group.add(radioButton1);
//        group.add(radioButton2);
//        group.add(radioButton3);
//        group.add(radioButton4);
//        group.add(radioButton5);

        // 컨테이너에 추가
        c.add(radioButton1);
        c.add(radioButton2);
        c.add(radioButton3);
        c.add(radioButton4);
        c.add(radioButton5);

        setSize(900, 600);
        setVisible(true);

        radioButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrinksTray tray = inventory.search(1);
            }
        });
        radioButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrinksTray tray = inventory.search(3);
            }
        });
        radioButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrinksTray tray = inventory.search(5);
            }
        });
        radioButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrinksTray tray = inventory.search(7);
            }
        });
        radioButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DrinksTray tray = inventory.search(9);
            }
        });



    }
    public static void main(String[] args){
        new AutoVendingMachine();



    }
}
