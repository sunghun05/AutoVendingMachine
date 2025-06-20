import admin.AdminInfo;
import inventory.DrinkOut;
import inventory.OutputQueue;
import moneyBox.MoneyBox;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Stack;

class MoneyInput implements ActionListener {
    int MAX_MONEY = 7000;
    OutputQueue outputQueue;
    MoneyBox moneyBox;
    JLabel display;
    MoneyInput(OutputQueue outputQueue, MoneyBox moneyBox, JLabel display){
        this.outputQueue = outputQueue;
        this.moneyBox = moneyBox;
        this.display = display;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton)e.getSource(); // 이벤트 발생한 버튼 얻기
        if(btn.getText().equals("10")
                && this.outputQueue.getTotalMoneyInput() + 10 < MAX_MONEY){
            this.outputQueue.sumTotalMoneyInput(10);
        } else if(btn.getText().equals("50")
                && this.outputQueue.getTotalMoneyInput() + 50 < MAX_MONEY){
            this.outputQueue.sumTotalMoneyInput(50);
        }else if(btn.getText().equals("100")
                && this.outputQueue.getTotalMoneyInput() + 100 < MAX_MONEY){
            this.outputQueue.sumTotalMoneyInput(100);
        }else if(btn.getText().equals("500")
                && this.outputQueue.getTotalMoneyInput() + 500 < MAX_MONEY){
            this.outputQueue.sumTotalMoneyInput(500);
        }else if(btn.getText().equals("1000")
                && this.outputQueue.getTotalMoneyInput() + 1000 < MAX_MONEY){
            this.outputQueue.sumTotalMoneyInput(1000);
        }
//		System.out.println(this.outputQueue.getTotalMoneyInput());
        display.setText("NOW MONEY: "+outputQueue.getTotalMoneyInput());
    }

}

class Select implements ActionListener {

    private boolean isSelected;

    OutputQueue outputQueue;

    Integer drinkId;
    JButton btn;

    Select(OutputQueue outputQueue, JButton btn){
        this.outputQueue = outputQueue;
        this.isSelected = false;
        this.btn = btn;
    }

    void setSelected(boolean l){
        if(l){btn.setBackground(Color.GREEN);}
        else{btn.setBackground(Color.WHITE);}
        this.isSelected = l;
    }
    boolean getSelected(){
        return isSelected;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        System.out.println(btn.getText());
//		btn = (JButton)e.getSource(); // 이벤트 발생한 버튼 얻기
        if(!isSelected) {
            if (btn.getText().equals("믹스커피")) {
                this.drinkId = 1;
            } else if (btn.getText().equals("고급 믹스커피")) {
                this.drinkId = 3;
            } else if (btn.getText().equals("물")) {
                this.drinkId = 5;
            } else if (btn.getText().equals("캔커피")) {
                this.drinkId = 7;
            } else if (btn.getText().equals("이온음료")) {
                this.drinkId = 9;
            } else if (btn.getText().equals("고급 캔커피")) {
                this.drinkId = 11;
            } else if (btn.getText().equals("탄산음료")) {
                this.drinkId = 13;
            } else if (btn.getText().equals("특화음료")) {
                this.drinkId = 15;
            }
            this.isSelected = true;
            outputQueue.reFill(this.drinkId);
            btn.setBackground(Color.GREEN);
        }else{
            outputQueue.del(this.drinkId);
            setSelected(false);
        }
    }
}

class Purchase implements ActionListener {
    OutputQueue outputQueue;
    MoneyBox moneyBox;
    Select [] buttons;
    JLabel display, noMoney;
    Purchase(OutputQueue outputQueue, Select[] buttons, MoneyBox moneyBox, JLabel display, JLabel noMoney){
        this.outputQueue = outputQueue;
        this.buttons = buttons;
        this.moneyBox = moneyBox;
        this.display = display;
        this.noMoney = noMoney;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//		outputQueue.takeOut_(1);
        Stack<Integer> disabledDrinks = new Stack<Integer>();

        if(!outputQueue.isEmpty()){
            outputQueue.alert();
        }
        while(!outputQueue.isEmpty()){
            int nowMoney = outputQueue.getTotalMoneyInput();
            int purchasePrice = outputQueue.front.tray.price;
            if(nowMoney - purchasePrice >= 0) {

                int isAble = outputQueue.takeOut_(1);
                if (isAble != 0) {
                    disabledDrinks.push(isAble);
                    continue;
                }
                outputQueue.subTotalMoneyInput(purchasePrice);
                moneyBox.changeNstore(purchasePrice);
            }else{
                outputQueue.takeOut(0);
                System.out.println("no money");
                noMoney.setVisible(true);

                javax.swing.Timer timer = new javax.swing.Timer(3000, ex -> {
                    noMoney.setVisible(false);
                    ((javax.swing.Timer)ex.getSource()).stop();
                });
                timer.start();
                break;
            }

        }
        DrinkOut.baseX = 300;
        DrinkOut.baseY = 300;

        while(!disabledDrinks.empty()){
            try {
                switch (disabledDrinks.pop()) {
                    case 1:
                        buttons[0].btn.setEnabled(false); break;
                    case 3:
                        buttons[1].btn.setEnabled(false); break;
                    case 5:
                        buttons[2].btn.setEnabled(false); break;
                    case 7:
                        buttons[3].btn.setEnabled(false); break;
                    case 9:
                        buttons[4].btn.setEnabled(false); break;
                    case 11:
                        buttons[5].btn.setEnabled(false); break;
                    case 13:
                        buttons[6].btn.setEnabled(false); break;
                    case 15:
                        buttons[7].btn.setEnabled(false); break;
                }
            }catch(Exception exception){
                break;
            }
        }

        for (Select button : buttons) {
            button.setSelected(false);
        }
        display.setText("NOW MONEY: "+outputQueue.getTotalMoneyInput());
    }
}

class ReceiveMoney implements ActionListener {

    MoneyBox moneyBox;
    OutputQueue outputQueue;
    JLabel display;

    ReceiveMoney(MoneyBox moneyBox, OutputQueue outputQueue, JLabel display){
        this.moneyBox = moneyBox;
        this.outputQueue = outputQueue;
        this.display = display;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        moneyBox.changeNstore(outputQueue.getTotalMoneyInput());
        moneyBox.changeNreceive(outputQueue.getTotalMoneyInput());
        outputQueue.subTotalMoneyInput(outputQueue.getTotalMoneyInput());
        display.setText("NOW MONEY: "+outputQueue.getTotalMoneyInput());
    }
}


public class AutoVendingMachine extends JFrame {
    private JPanel contentPane;
    private JLabel moneyLabel;
//    private int currentMoney = 0;

    OutputQueue outputQueue = new OutputQueue();
    MoneyBox moneyBox = new MoneyBox();

    JButton[] allbtns = new JButton[16];

    public AutoVendingMachine() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1050, 600);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(30, 30, 30, 30));
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        // 음료 정보 배열: 가격, 이름, 이미지 경로
        Object[][] drinks = {
                {"200원", "믹스커피", "/sources/java.png"},
                {"300원", "고급 믹스커피", "/sources/java.png"},
                {"450원", "물", "/sources/java.png"},
                {"500원", "캔커피", "/sources/java.png"},
                {"550원", "이온음료", "/sources/java.png"},
                {"700원", "고급 캔커피", "/sources/java.png"},
                {"750원", "탄산음료", "/sources/java.png"},
                {"800원", "특화음료", "/sources/java.png"}
        };

        JPanel inventoryPanel = new JPanel(new GridLayout(2, 4, 20, 20));
        Select[] selects = new Select[8];
        for (int i = 0; i<8; i++) {
            Object[] drink = drinks[i];
            JPanel tray = new JPanel(new BorderLayout(5, 5));
            tray.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

            JLabel priceLabel = new JLabel((String) drink[0], JLabel.CENTER);
            priceLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
            tray.add(priceLabel, BorderLayout.NORTH);

            JLabel imgLabel = new JLabel();
            java.net.URL imgURL = getClass().getResource((String) drink[2]);
            if (imgURL != null) {
                imgLabel.setIcon(new ImageIcon(imgURL));
            }
            imgLabel.setHorizontalAlignment(JLabel.CENTER);
            tray.add(imgLabel, BorderLayout.CENTER);

            JButton selectBtn = new JButton((String) drink[1]);
            selectBtn.setBackground(Color.WHITE);
            allbtns[i] = selectBtn;
            selectBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 14));

            selects[i] = new Select(outputQueue, selectBtn);
            selectBtn.addActionListener(selects[i]);

            selectBtn.setOpaque(true);
            selectBtn.setBorderPainted(false);

            tray.add(selectBtn, BorderLayout.SOUTH);

            inventoryPanel.add(tray);
        }

        contentPane.add(inventoryPanel, BorderLayout.CENTER);

        // 하단 패널 (동전 버튼 + 기능 버튼)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout(10, 10));

        JButton[] btns = new JButton[8];
        // 돈 투입 버튼 패널
        JPanel moneyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        // 현재 금액 표시 라벨
        moneyLabel = new JLabel("NOW MONEY: "+outputQueue.getTotalMoneyInput());
        moneyLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        moneyPanel.add(moneyLabel);
        int[] moneys = {10, 50, 100, 500, 1000};
        for (int i = 0; i<5; i++) {
            int money = moneys[i];
            JButton btn = new JButton(((Integer)money).toString());
            allbtns[8+i] = btn;
            btn.setFont(new Font("맑은 고딕", Font.BOLD, 14));
            btn.addActionListener(new MoneyInput(outputQueue, moneyBox, moneyLabel));
            moneyPanel.add(btn);
            btns[i] = btn;
        }


        // 기능 버튼 패널
        JPanel funcPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));


        JLabel noMoney = new JLabel("no money");
        noMoney.setVisible(false);

        JButton purchaseBtn = new JButton("purchase");
        allbtns[13] = purchaseBtn;

        purchaseBtn.addActionListener(new Purchase(outputQueue, selects, moneyBox,moneyLabel, noMoney));

        JButton receiveMoneyBtn = new JButton("receiveMoney");
        allbtns[14] = receiveMoneyBtn;

        receiveMoneyBtn.addActionListener(new ReceiveMoney(moneyBox, outputQueue, moneyLabel));

        JButton adminBtn = new JButton("Admin");
        allbtns[15] = adminBtn;

        adminBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminInfo admin = new AdminInfo(outputQueue, moneyBox, allbtns);
                admin.setVisible(true);
                admin.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        for(int i = 0; i<16; i++){
                            allbtns[i].setEnabled(true);

                        }
                    }
                });
                for(int i = 0; i<16; i++){
                    allbtns[i].setEnabled(false);
                }
            }
        });

        purchaseBtn.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        receiveMoneyBtn.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        adminBtn.setFont(new Font("맑은 고딕", Font.BOLD, 14));

        funcPanel.add(purchaseBtn);
        funcPanel.add(receiveMoneyBtn);
        funcPanel.add(adminBtn);
        funcPanel.add(noMoney);

        // 하단 패널에 두 개의 패널을 좌우로 배치
        bottomPanel.add(moneyPanel, BorderLayout.WEST);
        bottomPanel.add(funcPanel, BorderLayout.EAST);

        contentPane.add(bottomPanel, BorderLayout.SOUTH);


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AutoVendingMachine frame = new AutoVendingMachine();
            frame.setTitle("Auto Vending Machine");
            frame.setVisible(true);
        });
    }
}
