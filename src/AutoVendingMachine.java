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

/**
 * 동전 투입 이벤트 처리 클래스
 * - 사용자가 투입한 동전(10,50,100,500,1000원) 처리
 * - 최대 투입 금액(7000원) 제한 적용
 */
class MoneyInput implements ActionListener {
    int MAX_MONEY = 7000;  // 최대 투입 가능 금액
    OutputQueue outputQueue;  // 출력 큐 참조
    MoneyBox moneyBox;  // 금고 참조
    JLabel display;  // 금액 표시 라벨

    MoneyInput(OutputQueue outputQueue, MoneyBox moneyBox, JLabel display) {
        this.outputQueue = outputQueue;
        this.moneyBox = moneyBox;
        this.display = display;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        int amount = Integer.parseInt(btn.getText());  // 버튼 텍스트에서 금액 파싱
        int currentMoney = outputQueue.getTotalMoneyInput();

        // 최대 금액 검증 후 투입 금액 추가
        if (currentMoney + amount <= MAX_MONEY) {
            outputQueue.sumTotalMoneyInput(amount);
            display.setText("NOW MONEY: " + outputQueue.getTotalMoneyInput());
        }
    }
}

/**
 * 음료 선택 이벤트 처리 클래스
 * - 음료 버튼 클릭 시 선택/해제 토글 기능
 * - 선택된 음료를 OutputQueue에 추가/제거
 */
class Select implements ActionListener {
    private boolean isSelected;  // 선택 상태 플래그
    OutputQueue outputQueue;  // 출력 큐 참조
    Integer drinkId;  // 음료 ID (1~15 홀수)
    JButton btn;  // 연결된 버튼

    Select(OutputQueue outputQueue, JButton btn) {
        this.outputQueue = outputQueue;
        this.isSelected = false;
        this.btn = btn;
    }

    /** 선택 상태 업데이트 및 UI 반영 */
    void setSelected(boolean l) {
        btn.setBackground(l ? Color.GREEN : Color.WHITE);
        this.isSelected = l;
    }

    boolean getSelected() {
        return isSelected;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isSelected) {
            // 버튼 텍스트 기반 음료 ID 매핑
            switch (btn.getText()) {
                case "믹스커피": drinkId = 1; break;
                case "고급 믹스커피": drinkId = 3; break;
                case "물": drinkId = 5; break;
                case "캔커피": drinkId = 7; break;
                case "이온음료": drinkId = 9; break;
                case "고급 캔커피": drinkId = 11; break;
                case "탄산음료": drinkId = 13; break;
                case "특화음료": drinkId = 15; break;
            }
            outputQueue.reFill(drinkId);  // 큐에 음료 추가
            setSelected(true);  // 선택 상태 활성화
        } else {
            outputQueue.del(drinkId);  // 큐에서 음료 제거
            setSelected(false);  // 선택 상태 비활성화
        }
    }
}

/**
 * 구매 처리 이벤트 클래스
 * - OutputQueue에 있는 음료 순차 처리
 * - 잔액 부족 시 경고 메시지 표시
 * - 품절 음료 버튼 비활성화
 */
class Purchase implements ActionListener {
    OutputQueue outputQueue;
    MoneyBox moneyBox;
    Select[] buttons;  // 음료 선택 버튼 배열
    JLabel display, noMoney;  // 금액 표시/잔액 부족 라벨

    Purchase(OutputQueue outputQueue, Select[] buttons, MoneyBox moneyBox, JLabel display, JLabel noMoney) {
        this.outputQueue = outputQueue;
        this.buttons = buttons;
        this.moneyBox = moneyBox;
        this.display = display;
        this.noMoney = noMoney;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Stack<Integer> disabledDrinks = new Stack<>();  // 품절 음료 저장

        // 큐가 비어있지 않으면 알림 시작
        if (!outputQueue.isEmpty()) outputQueue.alert();

        // 큐 처리 루프
        while (!outputQueue.isEmpty()) {
            int nowMoney = outputQueue.getTotalMoneyInput();
            int price = outputQueue.front.tray.price;

            // 잔액 검증
            if (nowMoney >= price) {
                int result = outputQueue.takeOut_(1);  // 음료 배출 시도

                // 품절 음료 처리 (0: 성공, 0!=: 품절 ID)
                if (result != 0) {
                    disabledDrinks.push(result);
                    continue;
                }

                // 금액 업데이트
                outputQueue.subTotalMoneyInput(price);
                moneyBox.changeNstore(price);  // 금고에 금액 저장
            } else {
                // 잔액 부족 처리
                outputQueue.takeOut(0);  // 음료 제거
                noMoney.setVisible(true);  // 경고 메시지 표시

                // 3초 후 메시지 숨김
                new javax.swing.Timer(3000, evt -> {
                    noMoney.setVisible(false);
                    ((javax.swing.Timer) evt.getSource()).stop();
                }).start();
                break;
            }
        }

        // 음료 배출 창 위치 초기화
        DrinkOut.baseX = 300;
        DrinkOut.baseY = 300;

        // 품절 음료 버튼 비활성화
        while (!disabledDrinks.empty()) {
            int id = disabledDrinks.pop();
            // ID에 해당하는 버튼 찾아 비활성화
            for (Select button : buttons) {
                if (button.drinkId != null && button.drinkId == id) {
                    button.btn.setEnabled(false);
                    break;
                }
            }
        }

        // 모든 버튼 선택 상태 초기화
        for (Select button : buttons) {
            button.setSelected(false);
        }
        display.setText("NOW MONEY: " + outputQueue.getTotalMoneyInput());
    }
}

/**
 * 동전 반환 이벤트 처리 클래스
 * - 현재 투입된 금액 전체 반환
 */
class ReceiveMoney implements ActionListener {
    MoneyBox moneyBox;
    OutputQueue outputQueue;
    JLabel display;  // 금액 표시 라벨

    ReceiveMoney(MoneyBox moneyBox, OutputQueue outputQueue, JLabel display) {
        this.moneyBox = moneyBox;
        this.outputQueue = outputQueue;
        this.display = display;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int amount = outputQueue.getTotalMoneyInput();
        moneyBox.changeNstore(amount);  // 금고에 저장
        moneyBox.changeNreceive(amount);  // 반환 처리
        outputQueue.subTotalMoneyInput(amount);  // 투입 금액 초기화
        display.setText("NOW MONEY: " + outputQueue.getTotalMoneyInput());
    }
}

/**
 * 자판기 메인 GUI 클래스
 * - 음료 목록 표시 및 상호작용 처리
 * - 금액 투입/반환/구매 기능 구현
 * - 관리자 모드 연결
 */
public class AutoVendingMachine extends JFrame {
    private JPanel contentPane;
    private JLabel moneyLabel;
    OutputQueue outputQueue = new OutputQueue();  // 음료 출력 큐
    MoneyBox moneyBox = new MoneyBox();  // 금고 시스템
    JButton[] allbtns = new JButton[16];  // 전체 버튼 참조

    public AutoVendingMachine() {
        // 프레임 기본 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1050, 600);
        setTitle("Auto Vending Machine");

        // 메인 컨테이너 설정
        contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(30, 30, 30, 30));
        setContentPane(contentPane);

        // 음료 데이터: [가격, 이름, 이미지 경로]
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

        // 음료 트레이 그리드 레이아웃 (2x4)
        JPanel inventoryPanel = new JPanel(new GridLayout(2, 4, 20, 20));
        Select[] selects = new Select[8];  // 음료 선택 핸들러 배열

        // 음료 트레이 생성
        for (int i = 0; i < 8; i++) {
            JPanel tray = new JPanel(new BorderLayout(5, 5));
            tray.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

            // 가격 라벨 (상단)
            JLabel priceLabel = new JLabel((String) drinks[i][0], JLabel.CENTER);
            priceLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
            tray.add(priceLabel, BorderLayout.NORTH);

            // 이미지 라벨 (중앙)
            JLabel imgLabel = new JLabel(new ImageIcon(getClass().getResource((String) drinks[i][2])));
            imgLabel.setHorizontalAlignment(JLabel.CENTER);
            tray.add(imgLabel, BorderLayout.CENTER);

            // 선택 버튼 (하단)
            JButton selectBtn = new JButton((String) drinks[i][1]);
            selectBtn.setBackground(Color.WHITE);
            selectBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
            selectBtn.setOpaque(true);
            selectBtn.setBorderPainted(false);

            // 핸들러 연결 및 저장
            selects[i] = new Select(outputQueue, selectBtn);
            selectBtn.addActionListener(selects[i]);
            allbtns[i] = selectBtn;  // 전역 참조 저장

            tray.add(selectBtn, BorderLayout.SOUTH);
            inventoryPanel.add(tray);
        }
        contentPane.add(inventoryPanel, BorderLayout.CENTER);

        // 하단 패널 (금액 입력 + 기능 버튼)
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));

        // 금액 입력 패널 (좌측)
        JPanel moneyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        moneyLabel = new JLabel("NOW MONEY: " + outputQueue.getTotalMoneyInput());
        moneyLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        moneyPanel.add(moneyLabel);

        // 동전 버튼 생성 (10,50,100,500,1000)
        int[] moneys = {10, 50, 100, 500, 1000};
        for (int i = 0; i < 5; i++) {
            JButton btn = new JButton(String.valueOf(moneys[i]));
            btn.setFont(new Font("맑은 고딕", Font.BOLD, 14));
            btn.addActionListener(new MoneyInput(outputQueue, moneyBox, moneyLabel));
            moneyPanel.add(btn);
            allbtns[8 + i] = btn;  // 전역 참조 저장
        }

        // 기능 버튼 패널 (우측)
        JPanel funcPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));

        // 잔액 부족 라벨 (초기 숨김)
        JLabel noMoney = new JLabel("no money");
        noMoney.setVisible(false);
        funcPanel.add(noMoney);

        // 구매 버튼
        JButton purchaseBtn = new JButton("purchase");
        purchaseBtn.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        purchaseBtn.addActionListener(new Purchase(outputQueue, selects, moneyBox, moneyLabel, noMoney));
        funcPanel.add(purchaseBtn);
        allbtns[13] = purchaseBtn;

        // 반환 버튼
        JButton receiveMoneyBtn = new JButton("receiveMoney");
        receiveMoneyBtn.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        receiveMoneyBtn.addActionListener(new ReceiveMoney(moneyBox, outputQueue, moneyLabel));
        funcPanel.add(receiveMoneyBtn);
        allbtns[14] = receiveMoneyBtn;

        // 관리자 버튼
        JButton adminBtn = new JButton("Admin");
        adminBtn.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        adminBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 관리자 창 생성 및 이벤트 처리
                AdminInfo admin = new AdminInfo(outputQueue, moneyBox, allbtns);
                admin.setVisible(true);

                // 관리자 창 닫힐 때 버튼 활성화 복구
                admin.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        for (JButton btn : allbtns) btn.setEnabled(true);
                    }
                });

                // 현재 버튼 비활성화
                for (JButton btn : allbtns) btn.setEnabled(false);
            }
        });
        funcPanel.add(adminBtn);
        allbtns[15] = adminBtn;

        // 하단 패널 조립
        bottomPanel.add(moneyPanel, BorderLayout.WEST);
        bottomPanel.add(funcPanel, BorderLayout.EAST);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AutoVendingMachine().setVisible(true));
    }
}
