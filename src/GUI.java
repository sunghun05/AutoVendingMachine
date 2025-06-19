import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

import admin.AdminInfo;
import inventory.OutputQueue;

import inventory.DrinkOut;
import moneyBox.MoneyBox;




/**
 * class name: moneyInput
 * latest modify date: 2025.06.15
 * run environment: windows 11
 *
 * Feature: input money
 *
 * @author Sunghun Wang
 * @version 0.5, implemented action listener
 * @see
 */

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
	Select [] buttons = new Select[8];
	JLabel display, noMoney;
	Purchase(OutputQueue outputQueue, Select d1, Select d2, Select d3,
			 Select d4, Select d5, Select d6, Select d7, Select d8, MoneyBox moneyBox, JLabel display, JLabel noMoney){
		this.outputQueue = outputQueue;
		this.buttons[0] = d1; this.buttons[1] = d2; this.buttons[2] = d3; this.buttons[3] = d4;
		this.buttons[4] = d5; this.buttons[5] = d6; this.buttons[6] = d7; this.buttons[7] = d8;
		this.moneyBox = moneyBox;
		this.display = display;
		this.noMoney = noMoney;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		outputQueue.takeOut_(1);
		Stack<Integer> disabledDrinks = new Stack<Integer>();

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
		moneyBox.changeNreceive(outputQueue.getTotalMoneyInput());
		outputQueue.subTotalMoneyInput(outputQueue.getTotalMoneyInput());
		display.setText("NOW MONEY: "+outputQueue.getTotalMoneyInput());
	}
}

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public GUI() {

		OutputQueue outputQueue = new OutputQueue();
		MoneyBox moneyBox = new MoneyBox();

		JLabel currentMoney;
		JLabel noMoney;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 619, 880);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(35, 35, 35, 35));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel Front_Pannel = new JPanel();
		contentPane.add(Front_Pannel);
		Front_Pannel.setLayout(new GridLayout(2, 2, 0, 0));

		JPanel Inventory = new JPanel();
		Front_Pannel.add(Inventory);
		Inventory.setLayout(new GridLayout(0, 4, 20, 2));

		JPanel Tray1 = new JPanel();
		Inventory.add(Tray1);
		SpringLayout sl_Tray1 = new SpringLayout();
		Tray1.setLayout(sl_Tray1);

		JLabel drink1 = new JLabel("200원");
		sl_Tray1.putConstraint(SpringLayout.WEST, drink1, 35, SpringLayout.WEST, Tray1);
		Tray1.add(drink1);

		JButton select1 = new JButton("믹스커피");
		select1.setOpaque(true);
		select1.setBorderPainted(false);
		sl_Tray1.putConstraint(SpringLayout.NORTH, select1, 6, SpringLayout.SOUTH, drink1);

		Select selection1 = new Select(outputQueue, select1);

		select1.addActionListener(selection1);
		Tray1.add(select1);

		JPanel panel = new JPanel();
		sl_Tray1.putConstraint(SpringLayout.WEST, select1, 5, SpringLayout.WEST, panel);
//		sl_Tray1.putConstraint(SpringLayout.NORTH, panel, 20, SpringLayout.NORTH, Tray1);
		sl_Tray1.putConstraint(SpringLayout.SOUTH, panel, -61, SpringLayout.SOUTH, Tray1);
		sl_Tray1.putConstraint(SpringLayout.NORTH, drink1, 6, SpringLayout.SOUTH, panel);
		sl_Tray1.putConstraint(SpringLayout.WEST, panel, 15, SpringLayout.WEST, Tray1);
//		sl_Tray1.putConstraint(SpringLayout.EAST, panel, 108, SpringLayout.WEST, Tray1);

		JLabel drinkImg1 = new JLabel(" ", JLabel.CENTER); //가운데로 수평정렬
		ImageIcon icon1 = new ImageIcon("./src/sources/java.png");
		drinkImg1.setIcon(icon1);
		panel.add(drinkImg1);

		Tray1.add(panel);

		JPanel Tray2 = new JPanel();
		Inventory.add(Tray2);
		SpringLayout sl_Tray2 = new SpringLayout();
		Tray2.setLayout(sl_Tray2);

		JLabel drink2 = new JLabel("300원");
		sl_Tray2.putConstraint(SpringLayout.WEST, drink2, 35, SpringLayout.WEST, Tray2);
		Tray2.add(drink2);
		JButton select2 = new JButton("고급 믹스커피");
		select2.setOpaque(true);
		select2.setBorderPainted(false);
		sl_Tray2.putConstraint(SpringLayout.NORTH, select2, 6, SpringLayout.SOUTH, drink2);

		Select selection2 = new Select(outputQueue, select2);

		select2.addActionListener(selection2);
		Tray2.add(select2);

		JPanel panel_1 = new JPanel();
		sl_Tray2.putConstraint(SpringLayout.WEST, select2, 0, SpringLayout.WEST, panel_1);
		sl_Tray2.putConstraint(SpringLayout.NORTH, drink2, 6, SpringLayout.SOUTH, panel_1);
		sl_Tray2.putConstraint(SpringLayout.SOUTH, panel_1, -61, SpringLayout.SOUTH, Tray2);
		sl_Tray2.putConstraint(SpringLayout.WEST, panel_1, 0, SpringLayout.WEST, Tray2);

		JLabel drinkImg2 = new JLabel(" ", JLabel.CENTER); //가운데로 수평정렬
		ImageIcon icon2 = new ImageIcon("./src/sources/java.png");
		drinkImg2.setIcon(icon2);
		panel_1.add(drinkImg2);

		Tray2.add(panel_1);

		sl_Tray2.putConstraint(SpringLayout.WEST, select2, 0, SpringLayout.WEST, panel_1);

		JPanel Tray3 = new JPanel();
		Inventory.add(Tray3);
		SpringLayout sl_Tray3 = new SpringLayout();
		Tray3.setLayout(sl_Tray3);

		JPanel panel_1_1 = new JPanel();

		JLabel drink3 = new JLabel("450원");
		sl_Tray3.putConstraint(SpringLayout.EAST, drink3, -51, SpringLayout.EAST, Tray3);
		Tray3.add(drink3);

		JButton select3 = new JButton("물");
		select3.setOpaque(true);
		select3.setBorderPainted(false);
		sl_Tray3.putConstraint(SpringLayout.NORTH, select3, 6, SpringLayout.SOUTH, drink3);
		sl_Tray3.putConstraint(SpringLayout.EAST, select3, 0, SpringLayout.EAST, panel_1_1);

		Select selection3 = new Select(outputQueue, select3);

		select3.addActionListener(selection3);

		Tray3.add(select3);
		sl_Tray3.putConstraint(SpringLayout.WEST, select3, 0, SpringLayout.WEST, panel_1_1);
		sl_Tray3.putConstraint(SpringLayout.NORTH, drink3, 6, SpringLayout.SOUTH, panel_1_1);
		sl_Tray3.putConstraint(SpringLayout.SOUTH, panel_1_1, -61, SpringLayout.SOUTH, Tray3);
		sl_Tray3.putConstraint(SpringLayout.WEST, panel_1_1, 0, SpringLayout.WEST, Tray3);

		JLabel drinkImg3 = new JLabel(" ", JLabel.CENTER); //가운데로 수평정렬
		ImageIcon icon3 = new ImageIcon("./src/sources/java.png");
		drinkImg3.setIcon(icon3);
		panel_1_1.add(drinkImg3);

		Tray3.add(panel_1_1);



		JPanel Tray4 = new JPanel();
		Inventory.add(Tray4);
		SpringLayout sl_Tray4 = new SpringLayout();
		Tray4.setLayout(sl_Tray4);

		JLabel drink4 = new JLabel("500원");
		sl_Tray4.putConstraint(SpringLayout.WEST, drink4, 35, SpringLayout.WEST, Tray4);
		Tray4.add(drink4);

		JPanel panel_1_1_1 = new JPanel();

		JButton select4 = new JButton("캔커피");
		select4.setOpaque(true);
		select4.setBorderPainted(false);
		sl_Tray4.putConstraint(SpringLayout.NORTH, select4, 6, SpringLayout.SOUTH, drink4);
//		sl_Tray4.putConstraint(SpringLayout.WEST, select4, 0, SpringLayout.WEST, panel_1_1_1);

		sl_Tray4.putConstraint(SpringLayout.WEST, select4, 14, SpringLayout.WEST, panel_1_1_1);
		sl_Tray4.putConstraint(SpringLayout.NORTH, drink4, 6, SpringLayout.SOUTH, panel_1_1_1);
		sl_Tray4.putConstraint(SpringLayout.SOUTH, panel_1_1_1, -61, SpringLayout.SOUTH, Tray4);
		sl_Tray4.putConstraint(SpringLayout.WEST, panel_1_1_1, 0, SpringLayout.WEST, Tray4);

		JLabel drinkImg4 = new JLabel(" ", JLabel.CENTER); //가운데로 수평정렬
		ImageIcon icon4 = new ImageIcon("./src/sources/java.png");
		drinkImg4.setIcon(icon4);
		panel_1_1_1.add(drinkImg4);

		Tray4.add(panel_1_1_1);


		Select selection4 = new Select(outputQueue, select4);

		select4.addActionListener(selection4);

		Tray4.add(select4);

		JPanel Tray5 = new JPanel();
		Inventory.add(Tray5);
		SpringLayout sl_Tray5 = new SpringLayout();
		Tray5.setLayout(sl_Tray5);

		JLabel drink5 = new JLabel("550원");
		sl_Tray5.putConstraint(SpringLayout.WEST, drink5, 35, SpringLayout.WEST, Tray5);
		Tray5.add(drink5);

		JPanel panel_1_1_1_1 = new JPanel();
		sl_Tray5.putConstraint(SpringLayout.NORTH, panel_1_1_1_1, 10, SpringLayout.NORTH, Tray5);
		sl_Tray5.putConstraint(SpringLayout.SOUTH, panel_1_1_1_1, -61, SpringLayout.SOUTH, Tray5);
		sl_Tray5.putConstraint(SpringLayout.NORTH, drink5, 6, SpringLayout.SOUTH, panel_1_1_1_1);
		sl_Tray5.putConstraint(SpringLayout.WEST, panel_1_1_1_1, 10, SpringLayout.WEST, Tray5);
		sl_Tray5.putConstraint(SpringLayout.EAST, panel_1_1_1_1, -11, SpringLayout.EAST, Tray5);

		JLabel drinkImg5 = new JLabel(" ", JLabel.CENTER); //가운데로 수평정렬
		ImageIcon icon5 = new ImageIcon("./src/sources/java.png");
		drinkImg5.setIcon(icon5);
		panel_1_1_1_1.add(drinkImg5);

		Tray5.add(panel_1_1_1_1);

		JButton select5 = new JButton("이온음료");
		select5.setOpaque(true);
		select5.setBorderPainted(false);
		sl_Tray5.putConstraint(SpringLayout.NORTH, select5, 6, SpringLayout.SOUTH, drink5);
		sl_Tray5.putConstraint(SpringLayout.EAST, select5, 0, SpringLayout.EAST, panel_1_1_1_1);

		Select selection5 = new Select(outputQueue, select5);

		select5.addActionListener(selection5);
		Tray5.add(select5);

		JPanel Tray6 = new JPanel();
		Inventory.add(Tray6);
		SpringLayout sl_Tray6 = new SpringLayout();
		Tray6.setLayout(sl_Tray6);

		JLabel drink6 = new JLabel("700원");
		sl_Tray6.putConstraint(SpringLayout.EAST, drink6, -24, SpringLayout.EAST, Tray6);
		Tray6.add(drink6);

		JPanel panel_1_1_1_1_1 = new JPanel();
		sl_Tray6.putConstraint(SpringLayout.NORTH, drink6, 6, SpringLayout.SOUTH, panel_1_1_1_1_1);
		sl_Tray6.putConstraint(SpringLayout.SOUTH, panel_1_1_1_1_1, -61, SpringLayout.SOUTH, Tray6);
		sl_Tray6.putConstraint(SpringLayout.NORTH, panel_1_1_1_1_1, 10, SpringLayout.NORTH, Tray6);
		sl_Tray6.putConstraint(SpringLayout.WEST, panel_1_1_1_1_1, 10, SpringLayout.WEST, Tray6);
		sl_Tray6.putConstraint(SpringLayout.EAST, panel_1_1_1_1_1, -11, SpringLayout.EAST, Tray6);

		JLabel drinkImg6 = new JLabel(" ", JLabel.CENTER); //가운데로 수평정렬
		ImageIcon icon6 = new ImageIcon("./src/sources/java.png");
		drinkImg6.setIcon(icon6);
		panel_1_1_1_1_1.add(drinkImg6);

		Tray6.add(panel_1_1_1_1_1);

		JButton select6 = new JButton("고급 캔커피");
		select6.setOpaque(true);
		select6.setBorderPainted(false);
		sl_Tray6.putConstraint(SpringLayout.NORTH, select6, 6, SpringLayout.SOUTH, drink6);
		sl_Tray6.putConstraint(SpringLayout.WEST, select6, 0, SpringLayout.WEST, panel_1_1_1_1_1);

		Select selection6 = new Select(outputQueue, select6);

		select6.addActionListener(selection6);
		Tray6.add(select6);

		JPanel Tray7 = new JPanel();
		Inventory.add(Tray7);
		SpringLayout sl_Tray7 = new SpringLayout();
		Tray7.setLayout(sl_Tray7);

		JLabel drink7 = new JLabel("750원");
		sl_Tray7.putConstraint(SpringLayout.WEST, drink7, 35, SpringLayout.WEST, Tray7);
		Tray7.add(drink7);

		JPanel panel_1_1_1_1_1_1 = new JPanel();
		sl_Tray7.putConstraint(SpringLayout.NORTH, panel_1_1_1_1_1_1, 10, SpringLayout.NORTH, Tray7);
		sl_Tray7.putConstraint(SpringLayout.SOUTH, panel_1_1_1_1_1_1, -61, SpringLayout.SOUTH, Tray7);
		sl_Tray7.putConstraint(SpringLayout.NORTH, drink7, 6, SpringLayout.SOUTH, panel_1_1_1_1_1_1);
		sl_Tray7.putConstraint(SpringLayout.WEST, panel_1_1_1_1_1_1, 10, SpringLayout.WEST, Tray7);
		sl_Tray7.putConstraint(SpringLayout.EAST, panel_1_1_1_1_1_1, -11, SpringLayout.EAST, Tray7);

		JLabel drinkImg7 = new JLabel(" ", JLabel.CENTER); //가운데로 수평정렬
		ImageIcon icon7 = new ImageIcon("./src/sources/java.png");
		drinkImg7.setIcon(icon7);
		panel_1_1_1_1_1_1.add(drinkImg7);

		Tray7.add(panel_1_1_1_1_1_1);

		JButton select7 = new JButton("탄산음료");
		select7.setOpaque(true);
		select7.setBorderPainted(false);
		sl_Tray7.putConstraint(SpringLayout.NORTH, select7, 6, SpringLayout.SOUTH, drink7);
		sl_Tray7.putConstraint(SpringLayout.WEST, select7, 0, SpringLayout.WEST, panel_1_1_1_1_1_1);

		Select selection7 = new Select(outputQueue, select7);

		select7.addActionListener(selection7);
		Tray7.add(select7);

		JPanel Tray8 = new JPanel();
		Inventory.add(Tray8);
		SpringLayout sl_Tray8 = new SpringLayout();
		Tray8.setLayout(sl_Tray8);

		JLabel drink8 = new JLabel("800원");
		sl_Tray8.putConstraint(SpringLayout.WEST, drink8, 35, SpringLayout.WEST, Tray8);
		Tray8.add(drink8);

		JPanel panel_1_1_1_1_1_1_1 = new JPanel();
		sl_Tray8.putConstraint(SpringLayout.NORTH, drink8, 6, SpringLayout.SOUTH, panel_1_1_1_1_1_1_1);
		sl_Tray8.putConstraint(SpringLayout.NORTH, panel_1_1_1_1_1_1_1, 10, SpringLayout.NORTH, Tray8);
		sl_Tray8.putConstraint(SpringLayout.WEST, panel_1_1_1_1_1_1_1, 10, SpringLayout.WEST, Tray8);
		sl_Tray8.putConstraint(SpringLayout.SOUTH, panel_1_1_1_1_1_1_1, -61, SpringLayout.SOUTH, Tray8);
		sl_Tray8.putConstraint(SpringLayout.EAST, panel_1_1_1_1_1_1_1, -11, SpringLayout.EAST, Tray8);

		JLabel drinkImg8 = new JLabel(" ", JLabel.CENTER); //가운데로 수평정렬
		ImageIcon icon8 = new ImageIcon("./src/sources/java.png");
		drinkImg8.setIcon(icon8);
		panel_1_1_1_1_1_1_1.add(drinkImg8);

		Tray8.add(panel_1_1_1_1_1_1_1);

		JButton select8 = new JButton("특화음료");
		select8.setOpaque(true);
		select8.setBorderPainted(false);
		sl_Tray8.putConstraint(SpringLayout.NORTH, select8, 6, SpringLayout.SOUTH, drink8);
		sl_Tray8.putConstraint(SpringLayout.WEST, select8, 0, SpringLayout.WEST, panel_1_1_1_1_1_1_1);

		Select selection8 = new Select(outputQueue, select8);

		select8.addActionListener(selection8);
		Tray8.add(select8);

		JPanel PaymentMachine = new JPanel();
		Front_Pannel.add(PaymentMachine);
		SpringLayout sl_PaymentMachine = new SpringLayout();
		PaymentMachine.setLayout(sl_PaymentMachine);

		Integer nowMoney = outputQueue.getTotalMoneyInput();
		currentMoney = new JLabel("NOW MONEY: " + nowMoney.toString());
		sl_PaymentMachine.putConstraint(SpringLayout.NORTH, currentMoney, 20, SpringLayout.NORTH, PaymentMachine);
		sl_PaymentMachine.putConstraint(SpringLayout.EAST, currentMoney, -250, SpringLayout.EAST, PaymentMachine);
		PaymentMachine.add(currentMoney);

		JPanel panel_8 = new JPanel();
		sl_PaymentMachine.putConstraint(SpringLayout.NORTH, panel_8, 55, SpringLayout.NORTH, PaymentMachine);
		sl_PaymentMachine.putConstraint(SpringLayout.WEST, panel_8, -93, SpringLayout.EAST, PaymentMachine);
		sl_PaymentMachine.putConstraint(SpringLayout.SOUTH, panel_8, 260, SpringLayout.NORTH, PaymentMachine);
		sl_PaymentMachine.putConstraint(SpringLayout.EAST, panel_8, -28, SpringLayout.EAST, PaymentMachine);
		PaymentMachine.add(panel_8);
		panel_8.setLayout(new GridLayout(5, 2, 0, 0));

		JButton btnNewButton = new JButton("10");
		btnNewButton.addActionListener(new MoneyInput(outputQueue, moneyBox, currentMoney));
		panel_8.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("50");
		btnNewButton_1.addActionListener(new MoneyInput(outputQueue, moneyBox, currentMoney));
		panel_8.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("100");
		btnNewButton_2.addActionListener(new MoneyInput(outputQueue, moneyBox, currentMoney));
		panel_8.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("500");
		btnNewButton_3.addActionListener(new MoneyInput(outputQueue, moneyBox, currentMoney));
		panel_8.add(btnNewButton_3);

		JButton btnNewButton_4 = new JButton("1000");
		btnNewButton_4.addActionListener(new MoneyInput(outputQueue, moneyBox, currentMoney));
		panel_8.add(btnNewButton_4);

		noMoney = new JLabel("NO MONEY");
		sl_PaymentMachine.putConstraint(SpringLayout.NORTH, noMoney, 50, SpringLayout.NORTH, PaymentMachine);
		sl_PaymentMachine.putConstraint(SpringLayout.EAST, noMoney, -250, SpringLayout.EAST, PaymentMachine);
		noMoney.setVisible(false);
		PaymentMachine.add(noMoney);

		JButton outPutDrinks = new JButton("purchase");
		outPutDrinks.addActionListener(new Purchase(outputQueue, selection1, selection2, selection3, selection4,
													selection5, selection6, selection7, selection8, moneyBox,
													currentMoney, noMoney));
		sl_PaymentMachine.putConstraint(SpringLayout.NORTH, outPutDrinks, 0, SpringLayout.NORTH, PaymentMachine);
		sl_PaymentMachine.putConstraint(SpringLayout.EAST, outPutDrinks, -28, SpringLayout.EAST, PaymentMachine);
		PaymentMachine.add(outPutDrinks);

		JButton receiveMoney = new JButton("receive Money");
		receiveMoney.addActionListener(new ReceiveMoney(moneyBox, outputQueue, currentMoney));

//		JPanel panel_9 = new JPanel();
		sl_PaymentMachine.putConstraint(SpringLayout.NORTH, receiveMoney, 25, SpringLayout.NORTH, PaymentMachine);
		sl_PaymentMachine.putConstraint(SpringLayout.EAST, receiveMoney, -28, SpringLayout.EAST, PaymentMachine);
		PaymentMachine.add(receiveMoney);

		JButton adminButton = new JButton("ADMIN");
		adminButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AdminInfo().setVisible(true);
			}
		});
		sl_PaymentMachine.putConstraint(SpringLayout.SOUTH, adminButton, -20, SpringLayout.SOUTH, PaymentMachine);
		sl_PaymentMachine.putConstraint(SpringLayout.WEST, adminButton, 30, SpringLayout.WEST, PaymentMachine);
		PaymentMachine.add(adminButton);

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
