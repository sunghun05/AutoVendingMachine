import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

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

	/**
	 * Create the frame.
	 */
	public GUI() {
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
		
		JLabel drink1 = new JLabel("믹스커피");
		sl_Tray1.putConstraint(SpringLayout.WEST, drink1, 35, SpringLayout.WEST, Tray1);
		Tray1.add(drink1);
		
		JButton select1 = new JButton("New button");
		sl_Tray1.putConstraint(SpringLayout.NORTH, select1, 6, SpringLayout.SOUTH, drink1);
		Tray1.add(select1);
		
		JPanel panel = new JPanel();
		sl_Tray1.putConstraint(SpringLayout.WEST, select1, 0, SpringLayout.WEST, panel);
		sl_Tray1.putConstraint(SpringLayout.NORTH, panel, 10, SpringLayout.NORTH, Tray1);
		sl_Tray1.putConstraint(SpringLayout.SOUTH, panel, -61, SpringLayout.SOUTH, Tray1);
		sl_Tray1.putConstraint(SpringLayout.NORTH, drink1, 6, SpringLayout.SOUTH, panel);
		sl_Tray1.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, Tray1);
		sl_Tray1.putConstraint(SpringLayout.EAST, panel, 108, SpringLayout.WEST, Tray1);
		
		JLabel drinkImg1 = new JLabel(" ", JLabel.CENTER); //가운데로 수평정렬
		ImageIcon icon1 = new ImageIcon("sources/java.png");
		drinkImg1.setIcon(icon1);
		panel.add(drinkImg1);
		
		Tray1.add(panel);

		JPanel Tray2 = new JPanel();
		Inventory.add(Tray2);
		SpringLayout sl_Tray2 = new SpringLayout();
		Tray2.setLayout(sl_Tray2);
		
		JLabel drink2 = new JLabel("고급 믹스커피");
		Tray2.add(drink2);
		
		JPanel panel_1 = new JPanel();
		sl_Tray2.putConstraint(SpringLayout.NORTH, panel_1, 10, SpringLayout.NORTH, Tray2);
		sl_Tray2.putConstraint(SpringLayout.SOUTH, panel_1, -61, SpringLayout.SOUTH, Tray2);
		sl_Tray2.putConstraint(SpringLayout.NORTH, drink2, 6, SpringLayout.SOUTH, panel_1);
		sl_Tray2.putConstraint(SpringLayout.EAST, drink2, -10, SpringLayout.EAST, panel_1);
		sl_Tray2.putConstraint(SpringLayout.WEST, panel_1, 10, SpringLayout.WEST, Tray2);
		sl_Tray2.putConstraint(SpringLayout.EAST, panel_1, -11, SpringLayout.EAST, Tray2);
		
		JLabel drinkImg2 = new JLabel(" ", JLabel.CENTER); //가운데로 수평정렬
		ImageIcon icon2 = new ImageIcon("sources/java.png");
		drinkImg2.setIcon(icon2);
		panel_1.add(drinkImg2);
		
		Tray2.add(panel_1);
		
		JButton select2 = new JButton("New button");
		sl_Tray2.putConstraint(SpringLayout.NORTH, select2, 6, SpringLayout.SOUTH, drink2);
		sl_Tray2.putConstraint(SpringLayout.WEST, select2, 0, SpringLayout.WEST, panel_1);
		Tray2.add(select2);
		
		JPanel Tray3 = new JPanel();
		Inventory.add(Tray3);
		SpringLayout sl_Tray3 = new SpringLayout();
		Tray3.setLayout(sl_Tray3);
		
		JLabel drink3 = new JLabel("물");
		sl_Tray3.putConstraint(SpringLayout.EAST, drink3, -51, SpringLayout.EAST, Tray3);
		Tray3.add(drink3);
		
		JPanel panel_1_1 = new JPanel();
		sl_Tray3.putConstraint(SpringLayout.NORTH, panel_1_1, 10, SpringLayout.NORTH, Tray3);
		sl_Tray3.putConstraint(SpringLayout.SOUTH, panel_1_1, -61, SpringLayout.SOUTH, Tray3);
		sl_Tray3.putConstraint(SpringLayout.NORTH, drink3, 6, SpringLayout.SOUTH, panel_1_1);
		sl_Tray3.putConstraint(SpringLayout.WEST, panel_1_1, 10, SpringLayout.WEST, Tray3);
		sl_Tray3.putConstraint(SpringLayout.EAST, panel_1_1, -10, SpringLayout.EAST, Tray3);
		
		JLabel drinkImg3 = new JLabel(" ", JLabel.CENTER); //가운데로 수평정렬
		ImageIcon icon3 = new ImageIcon("sources/java.png");
		drinkImg3.setIcon(icon3);
		panel_1_1.add(drinkImg3);
		
		Tray3.add(panel_1_1);
		
		JButton select3 = new JButton("New button");
		sl_Tray3.putConstraint(SpringLayout.NORTH, select3, 6, SpringLayout.SOUTH, drink3);
		sl_Tray3.putConstraint(SpringLayout.EAST, select3, 0, SpringLayout.EAST, panel_1_1);
		Tray3.add(select3);
		
		JPanel Tray4 = new JPanel();
		Inventory.add(Tray4);
		SpringLayout sl_Tray4 = new SpringLayout();
		Tray4.setLayout(sl_Tray4);
		
		JLabel drink4 = new JLabel("캔커피");
		sl_Tray4.putConstraint(SpringLayout.EAST, drink4, -40, SpringLayout.EAST, Tray4);
		Tray4.add(drink4);
		
		JPanel panel_1_1_1 = new JPanel();
		sl_Tray4.putConstraint(SpringLayout.NORTH, drink4, 6, SpringLayout.SOUTH, panel_1_1_1);
		sl_Tray4.putConstraint(SpringLayout.NORTH, panel_1_1_1, 10, SpringLayout.NORTH, Tray4);
		sl_Tray4.putConstraint(SpringLayout.WEST, panel_1_1_1, 10, SpringLayout.WEST, Tray4);
		sl_Tray4.putConstraint(SpringLayout.SOUTH, panel_1_1_1, -61, SpringLayout.SOUTH, Tray4);
		sl_Tray4.putConstraint(SpringLayout.EAST, panel_1_1_1, -11, SpringLayout.EAST, Tray4);
		
		JLabel drinkImg4 = new JLabel(" ", JLabel.CENTER); //가운데로 수평정렬
		ImageIcon icon4 = new ImageIcon("sources/java.png");
		drinkImg4.setIcon(icon4);
		panel_1_1_1.add(drinkImg4);
		
		Tray4.add(panel_1_1_1);
		
		JButton select4 = new JButton("New button");
		sl_Tray4.putConstraint(SpringLayout.NORTH, select4, 6, SpringLayout.SOUTH, drink4);
		sl_Tray4.putConstraint(SpringLayout.WEST, select4, 0, SpringLayout.WEST, panel_1_1_1);
		Tray4.add(select4);
		
		JPanel Tray5 = new JPanel();
		Inventory.add(Tray5);
		SpringLayout sl_Tray5 = new SpringLayout();
		Tray5.setLayout(sl_Tray5);
		
		JLabel drink5 = new JLabel("이온음료");
		sl_Tray5.putConstraint(SpringLayout.WEST, drink5, 35, SpringLayout.WEST, Tray5);
		Tray5.add(drink5);
		
		JPanel panel_1_1_1_1 = new JPanel();
		sl_Tray5.putConstraint(SpringLayout.NORTH, panel_1_1_1_1, 10, SpringLayout.NORTH, Tray5);
		sl_Tray5.putConstraint(SpringLayout.SOUTH, panel_1_1_1_1, -61, SpringLayout.SOUTH, Tray5);
		sl_Tray5.putConstraint(SpringLayout.NORTH, drink5, 6, SpringLayout.SOUTH, panel_1_1_1_1);
		sl_Tray5.putConstraint(SpringLayout.WEST, panel_1_1_1_1, 10, SpringLayout.WEST, Tray5);
		sl_Tray5.putConstraint(SpringLayout.EAST, panel_1_1_1_1, -11, SpringLayout.EAST, Tray5);
		
		JLabel drinkImg5 = new JLabel(" ", JLabel.CENTER); //가운데로 수평정렬
		ImageIcon icon5 = new ImageIcon("sources/java.png");
		drinkImg5.setIcon(icon5);
		panel_1_1_1_1.add(drinkImg5);
		
		Tray5.add(panel_1_1_1_1);
		
		JButton select5 = new JButton("New button");
		sl_Tray5.putConstraint(SpringLayout.NORTH, select5, 6, SpringLayout.SOUTH, drink5);
		sl_Tray5.putConstraint(SpringLayout.EAST, select5, 0, SpringLayout.EAST, panel_1_1_1_1);
		Tray5.add(select5);
		
		JPanel Tray6 = new JPanel();
		Inventory.add(Tray6);
		SpringLayout sl_Tray6 = new SpringLayout();
		Tray6.setLayout(sl_Tray6);
		
		JLabel drink6 = new JLabel("고급 캔커피");
		sl_Tray6.putConstraint(SpringLayout.EAST, drink6, -24, SpringLayout.EAST, Tray6);
		Tray6.add(drink6);
		
		JPanel panel_1_1_1_1_1 = new JPanel();
		sl_Tray6.putConstraint(SpringLayout.NORTH, drink6, 6, SpringLayout.SOUTH, panel_1_1_1_1_1);
		sl_Tray6.putConstraint(SpringLayout.SOUTH, panel_1_1_1_1_1, -61, SpringLayout.SOUTH, Tray6);
		sl_Tray6.putConstraint(SpringLayout.NORTH, panel_1_1_1_1_1, 10, SpringLayout.NORTH, Tray6);
		sl_Tray6.putConstraint(SpringLayout.WEST, panel_1_1_1_1_1, 10, SpringLayout.WEST, Tray6);
		sl_Tray6.putConstraint(SpringLayout.EAST, panel_1_1_1_1_1, -11, SpringLayout.EAST, Tray6);
		
		JLabel drinkImg6 = new JLabel(" ", JLabel.CENTER); //가운데로 수평정렬
		ImageIcon icon6 = new ImageIcon("sources/java.png");
		drinkImg6.setIcon(icon6);
		panel_1_1_1_1_1.add(drinkImg6);
		
		Tray6.add(panel_1_1_1_1_1);
		
		JButton select6 = new JButton("New button");
		sl_Tray6.putConstraint(SpringLayout.NORTH, select6, 6, SpringLayout.SOUTH, drink6);
		sl_Tray6.putConstraint(SpringLayout.WEST, select6, 0, SpringLayout.WEST, panel_1_1_1_1_1);
		Tray6.add(select6);
		
		JPanel Tray7 = new JPanel();
		Inventory.add(Tray7);
		SpringLayout sl_Tray7 = new SpringLayout();
		Tray7.setLayout(sl_Tray7);
		
		JLabel drink7 = new JLabel("탄산음료");
		sl_Tray7.putConstraint(SpringLayout.WEST, drink7, 35, SpringLayout.WEST, Tray7);
		Tray7.add(drink7);
		
		JPanel panel_1_1_1_1_1_1 = new JPanel();
		sl_Tray7.putConstraint(SpringLayout.NORTH, panel_1_1_1_1_1_1, 10, SpringLayout.NORTH, Tray7);
		sl_Tray7.putConstraint(SpringLayout.SOUTH, panel_1_1_1_1_1_1, -61, SpringLayout.SOUTH, Tray7);
		sl_Tray7.putConstraint(SpringLayout.NORTH, drink7, 6, SpringLayout.SOUTH, panel_1_1_1_1_1_1);
		sl_Tray7.putConstraint(SpringLayout.WEST, panel_1_1_1_1_1_1, 10, SpringLayout.WEST, Tray7);
		sl_Tray7.putConstraint(SpringLayout.EAST, panel_1_1_1_1_1_1, -11, SpringLayout.EAST, Tray7);
		
		JLabel drinkImg7 = new JLabel(" ", JLabel.CENTER); //가운데로 수평정렬
		ImageIcon icon7 = new ImageIcon("sources/java.png");
		drinkImg7.setIcon(icon7);
		panel_1_1_1_1_1_1.add(drinkImg7);
		
		Tray7.add(panel_1_1_1_1_1_1);
		
		JButton select7 = new JButton("New button");
		sl_Tray7.putConstraint(SpringLayout.NORTH, select7, 6, SpringLayout.SOUTH, drink7);
		sl_Tray7.putConstraint(SpringLayout.WEST, select7, 0, SpringLayout.WEST, panel_1_1_1_1_1_1);
		Tray7.add(select7);
		
		JPanel Tray8 = new JPanel();
		Inventory.add(Tray8);
		SpringLayout sl_Tray8 = new SpringLayout();
		Tray8.setLayout(sl_Tray8);
		
		JLabel drink8 = new JLabel("특화음료");
		sl_Tray8.putConstraint(SpringLayout.WEST, drink8, 35, SpringLayout.WEST, Tray8);
		Tray8.add(drink8);
		
		JPanel panel_1_1_1_1_1_1_1 = new JPanel();
		sl_Tray8.putConstraint(SpringLayout.NORTH, drink8, 6, SpringLayout.SOUTH, panel_1_1_1_1_1_1_1);
		sl_Tray8.putConstraint(SpringLayout.NORTH, panel_1_1_1_1_1_1_1, 10, SpringLayout.NORTH, Tray8);
		sl_Tray8.putConstraint(SpringLayout.WEST, panel_1_1_1_1_1_1_1, 10, SpringLayout.WEST, Tray8);
		sl_Tray8.putConstraint(SpringLayout.SOUTH, panel_1_1_1_1_1_1_1, -61, SpringLayout.SOUTH, Tray8);
		sl_Tray8.putConstraint(SpringLayout.EAST, panel_1_1_1_1_1_1_1, -11, SpringLayout.EAST, Tray8);
		
		JLabel drinkImg8 = new JLabel(" ", JLabel.CENTER); //가운데로 수평정렬
		ImageIcon icon8 = new ImageIcon("sources/java.png");
		drinkImg8.setIcon(icon8);
		panel_1_1_1_1_1_1_1.add(drinkImg8);
		
		Tray8.add(panel_1_1_1_1_1_1_1);
		
		JButton select8 = new JButton("New button");
		sl_Tray8.putConstraint(SpringLayout.NORTH, select8, 6, SpringLayout.SOUTH, drink8);
		sl_Tray8.putConstraint(SpringLayout.WEST, select8, 0, SpringLayout.WEST, panel_1_1_1_1_1_1_1);
		Tray8.add(select8);
		
		JPanel PaymentMachine = new JPanel();
		Front_Pannel.add(PaymentMachine);
		SpringLayout sl_PaymentMachine = new SpringLayout();
		PaymentMachine.setLayout(sl_PaymentMachine);
		
		JPanel panel_8 = new JPanel();
		sl_PaymentMachine.putConstraint(SpringLayout.NORTH, panel_8, 45, SpringLayout.NORTH, PaymentMachine);
		sl_PaymentMachine.putConstraint(SpringLayout.WEST, panel_8, -93, SpringLayout.EAST, PaymentMachine);
		sl_PaymentMachine.putConstraint(SpringLayout.SOUTH, panel_8, 260, SpringLayout.NORTH, PaymentMachine);
		sl_PaymentMachine.putConstraint(SpringLayout.EAST, panel_8, -28, SpringLayout.EAST, PaymentMachine);
		PaymentMachine.add(panel_8);
		panel_8.setLayout(new GridLayout(5, 2, 0, 0));
		
		JButton btnNewButton = new JButton("10");
		panel_8.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("50");
		panel_8.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("100");
		panel_8.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("500");
		panel_8.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("1000");
		panel_8.add(btnNewButton_4);
	}
}
