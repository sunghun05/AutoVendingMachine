package admin;

import inventory.DrinksTray;
import inventory.OutputQueue;
import moneyBox.MoneyBox;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

class reFillBtns implements ActionListener{
    OutputQueue outputQueue;
    Integer drinkId;
    reFillBtns(Integer drinkId, OutputQueue outputQueue){
        this.outputQueue = outputQueue;
        this.drinkId = drinkId;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        DrinksTray tray = outputQueue.inventory.search(this.drinkId);
        tray.reFill(1);
    }
}

class reFillWindow extends JFrame {
    public static int baseX = 300;
    public static int baseY = 300;
    private final int OFFSET = 50;

    OutputQueue outputQueue;
    JPanel container;

    public reFillWindow(OutputQueue outputQueue) {
        this.outputQueue = outputQueue;
        setSize(1000, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        container = new JPanel();
        container.setLayout(new BorderLayout()); // 레이아웃 명확히 지정

        String[] drinks = {"믹스커피", "고급 믹스커피", "물", "캔커피", "이온음료", "고급 캔커피", "탄산음료", "특화음료"};
        Integer[] id = {1, 3, 5, 7, 9, 11, 13, 15};

        JPanel inventoryPanel = new JPanel(new GridLayout(2, 4, 10, 10)); // 2행 4열로 보기 좋게

        for (int i = 0; i < 8; i++) {
            JButton selectBtn = new JButton(drinks[i]);
            selectBtn.setBackground(Color.WHITE);
            selectBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
            selectBtn.setOpaque(true);
            selectBtn.setBorderPainted(false);
            selectBtn.addActionListener(new reFillBtns(id[i], outputQueue));
            inventoryPanel.add(selectBtn);
        }
        container.add(inventoryPanel, BorderLayout.CENTER);

        setContentPane(container); // JFrame의 contentPane으로 지정!

        setLocation(baseX, baseY);
        baseX += OFFSET;

        setVisible(true);
    }
}


/**
 * class name: AdminInfo
 * latest modify date: 2025.06.19
 * run environment: MacOS 15.4.1(24E263)
 *
 * Feature: Generates Admin Window
 *
 * @author Sunghun Wang
 * @version 1.0, implemented class
 * @see
 */

public class AdminInfo extends JFrame implements ActionListener {
    private static final String CREDENTIALS_FILE = "admin_credentials.txt";
    private JButton accessAdminBtn;
    private JButton changePasswordBtn;
    private static String currentId;
    private static String currentPassword;

    private int currentIncome = 0; // 수입 저장 변수
    private JLabel incomeLabel; // 수입 라벨 참조 변수
    private int totalMoneyInMachine;
    private JLabel totalMoney;
    JButton[] allbtns;

    OutputQueue outputQueue;
    MoneyBox moneyBox;

    public AdminInfo(OutputQueue outputQueue,
                     MoneyBox moneyBox, JButton[] allbtns) {
        super("관리자 정보 시스템");

        this.outputQueue = outputQueue;
        this.moneyBox = moneyBox;

        loadCredentials(); // 파일에서 아이디와 비밀번호 불러오기

        // 버튼 설정
        accessAdminBtn = new JButton("관리자 메뉴 접근");
        changePasswordBtn = new JButton("비밀번호 변경");

        this.allbtns = allbtns;

        accessAdminBtn.addActionListener(this);
        changePasswordBtn.addActionListener(this);

        this.totalMoneyInMachine = moneyBox.totalMoneyInMachine;

        // 레이아웃 설정
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(accessAdminBtn);
        panel.add(changePasswordBtn);

        add(panel);
        setSize(350, 200);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == accessAdminBtn) {
            authenticateAdmin();
        } else if (e.getSource() == changePasswordBtn) {
            changePassword();
        }
    }

    // 인증 처리 (아이디, 비밀번호 모두 입력)
    private void authenticateAdmin() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        JTextField idField = new JTextField(15);
        JPasswordField pwField = new JPasswordField(15);

        panel.add(new JLabel("아이디:"));
        panel.add(idField);
        panel.add(new JLabel("비밀번호:"));
        panel.add(pwField);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "관리자 인증",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            String inputId = idField.getText();
            String inputPw = new String(pwField.getPassword());
            if (inputId.equals(currentId) && inputPw.equals(currentPassword)) {
                openAdminMenu();
            } else {
                JOptionPane.showMessageDialog(this,
                        "아이디 또는 비밀번호가 일치하지 않습니다.", "인증 실패",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // 비밀번호 변경 (관리자 페이지 내에서 변경 가능)
    private void changePassword() {
        // 1. 현재 아이디/비밀번호 확인
        JPanel authPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        JTextField idField = new JTextField(15);
        JPasswordField pwField = new JPasswordField(15);

        authPanel.add(new JLabel("아이디:"));
        authPanel.add(idField);
        authPanel.add(new JLabel("비밀번호:"));
        authPanel.add(pwField);

        int authResult = JOptionPane.showConfirmDialog(
                this, authPanel, "관리자 인증",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (authResult != JOptionPane.OK_OPTION) return;

        String inputId = idField.getText();
        String inputPw = new String(pwField.getPassword());
        if (!inputId.equals(currentId) || !inputPw.equals(currentPassword)) {
            JOptionPane.showMessageDialog(this,
                    "아이디 또는 비밀번호가 일치하지 않습니다.", "오류",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. 새 비밀번호 입력 및 유효성 검사
        JPanel changePanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JPasswordField newPwField = new JPasswordField(15);
        JPasswordField confirmPwField = new JPasswordField(15);

        changePanel.add(new JLabel("새 비밀번호:"));
        changePanel.add(newPwField);
        changePanel.add(new JLabel("비밀번호 확인:"));
        changePanel.add(confirmPwField);

        int changeResult = JOptionPane.showConfirmDialog(
                this, changePanel, "비밀번호 변경",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (changeResult == JOptionPane.OK_OPTION) {
            String newPw = new String(newPwField.getPassword());
            String confirmPw = new String(confirmPwField.getPassword());

            // 새 비밀번호 유효성 검사
            if (!isValidPassword(newPw)) {
                JOptionPane.showMessageDialog(this,
                        "비밀번호는 8자리 이상이며, 숫자와 특수문자를 각각 1개 이상 포함해야 합니다.",
                        "유효성 검사 실패", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 비밀번호 확인
            if (!newPw.equals(confirmPw)) {
                JOptionPane.showMessageDialog(this,
                        "새 비밀번호가 일치하지 않습니다.", "오류",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 비밀번호 업데이트
            currentPassword = newPw;
            saveCredentials();
            JOptionPane.showMessageDialog(this,
                    "비밀번호가 성공적으로 변경되었습니다.", "완료",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // 비밀번호 유효성 검사 (숫자+특수문자 포함 8자리 이상)
    private boolean isValidPassword(String password) {
        if (password.length() < 8) return false;

        boolean hasDigit = false;
        boolean hasSpecial = false;
        String specialChars = "!@#$%^&*()-_=+[]{}|;:'\",.<>/?`~";

        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) hasDigit = true;
            if (specialChars.indexOf(c) >= 0) hasSpecial = true;
        }
        return hasDigit && hasSpecial;
    }

    private void openAdminMenu() {
        JFrame adminFrame = new JFrame("관리자 정보 메뉴");
        adminFrame.setSize(600, 520);
        adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        adminFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for(int i = 0; i<16; i++){
                   allbtns[i].setEnabled(true);

                }
            }
        });

        // 환영 메시지
        JLabel welcomeLabel = new JLabel(currentId + "님, 관리자 메뉴에 접근하셨습니다.", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("맑은 고딕", Font.BOLD, 22));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        // 음료 종류별 개수 표
        String[] columns = {"음료명", "개수"};
        String[] beverageTypes = {"믹스커피", "고급 믹스커피", "물", "캔커피", "이온음료", "고급 캔커피", "탄산음료", "특화음료"};
        Map<String, Integer> beverageCounts = getBeverageCounts();
        Object[][] data = new Object[beverageTypes.length][2];
        for (int i = 0; i < beverageTypes.length; i++) {
            data[i][0] = beverageTypes[i];
            data[i][1] = beverageCounts.getOrDefault(beverageTypes[i], 0) + "개";
        }
        JTable table = new JTable(data, columns);
        table.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        table.setRowHeight(28);
        table.setEnabled(false);
        table.getTableHeader().setFont(new Font("맑은 고딕", Font.BOLD, 15));
        table.setShowGrid(false);

        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setBorder(BorderFactory.createTitledBorder("음료별 판매 현황"));
        tablePane.setPreferredSize(new Dimension(320, 250));

        // 현재 수입 라벨
        incomeLabel = new JLabel("현재 수입: " + moneyBox.totalMoneyInMachine + "원", SwingConstants.CENTER);
        incomeLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        incomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // 수금 입력 패널
        JPanel collectPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JLabel amountLabel = new JLabel("수금 금액:");
        JTextField amountField = new JTextField(10);
        JButton collectBtn = new JButton("수금");

        collectBtn.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        collectBtn.setPreferredSize(new Dimension(80, 30));

        // 수금 버튼 액션
        collectBtn.addActionListener(e -> {
            try {
                int amount = Integer.parseInt(amountField.getText().trim());
                currentIncome += amount;
                moneyBox.changeNreceive(amount);
                incomeLabel.setText("현재 수입: " + moneyBox.totalMoneyInMachine + "원");
                saveIncomeData();
                amountField.setText("");
                JOptionPane.showMessageDialog(adminFrame, amount + "원이 수금되었습니다.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(adminFrame, "유효한 숫자를 입력하세요!", "입력 오류", JOptionPane.ERROR_MESSAGE);
            }
        });

        collectPanel.add(amountLabel);
        collectPanel.add(amountField);
        collectPanel.add(collectBtn);

        // 버튼 패널
        JButton logViewBtn = new JButton("일별/월별 매출");
        JButton logViewBtn2 = new JButton("음료별 일별/월별 매출");
        JButton refillBtn = new JButton("재고 보충");

        logViewBtn.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        logViewBtn2.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        refillBtn.setFont(new Font("맑은 고딕", Font.BOLD, 16));

        refillBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new reFillWindow(outputQueue);
            }
        });

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));
        btnPanel.add(logViewBtn);
        btnPanel.add(logViewBtn2);
        btnPanel.add(refillBtn);

        // 로그 보기 기능 연결
        logViewBtn.addActionListener(e -> showSalesSummary());
        logViewBtn2.addActionListener(e -> showBeverageSales());

        // 전체 레이아웃 (BoxLayout)
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(welcomeLabel);
        content.add(Box.createVerticalStrut(10));
        content.add(tablePane);
        content.add(incomeLabel);      // 수입 라벨 (수금 버튼 위)
        content.add(collectPanel);     // 수금 입력 패널
        content.add(Box.createVerticalStrut(10));
        content.add(btnPanel);

        adminFrame.setContentPane(content);
        adminFrame.setLocationRelativeTo(null);
        adminFrame.setVisible(true);
    }

    // 수입 데이터 저장
    private void saveIncomeData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("income.txt"))) {
            writer.write(String.valueOf(currentIncome));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "수입 데이터 저장 실패: " + e.getMessage());
        }
    }

    // 프로그램 시작 시 수입 데이터 로드
    private void loadIncomeData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("income.txt"))) {
            currentIncome = Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            currentIncome = 0;
        }
    }
    // 음료 종류별 개수 계산
    private Map<String, Integer> getBeverageCounts() {
        Map<String, Integer> counts = new HashMap<>();
        // 음료 종류 초기화
        String[] beverages = {"믹스커피", "고급 믹스커피", "물", "캔커피", "이온음료", "고급 캔커피", "탄산음료", "특화음료"};
        for (String beverage : beverages) {
            counts.put(beverage, 0);
        }

        // 파일에서 데이터 읽기
        try (BufferedReader reader = new BufferedReader(new FileReader("drinkLog.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length < 2) continue;

                String beverage = parts[1].trim();
                if (counts.containsKey(beverage)) {
                    counts.put(beverage, counts.get(beverage) + 1);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "음료 데이터를 읽을 수 없습니다: " + e.getMessage());
        }
        return counts;
    }

    // 음료 개수 표시용 라벨 생성 (HTML 형식)
    private JLabel createBeverageLabel(Map<String, Integer> counts) {
        StringBuilder html = new StringBuilder("<html><div style='text-align:center;'>음료 개수:<br>");

        // 음료 종류별로 개수 표시
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            html.append(entry.getKey()).append(": ").append(entry.getValue()).append("개<br>");
        }
        html.append("</div></html>");

        JLabel label = new JLabel(html.toString());
        label.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        return label;
    }

    // 일별/월별 매출 요약 보기
    private void showSalesSummary() {
        try {
            Map<String, Integer> dailySales = new TreeMap<>();
            Map<String, Integer> monthlySales = new TreeMap<>();

            processSalesData(dailySales, monthlySales);

            // 테이블 데이터 생성
            String[] columns = {"기간", "매출 건수"};
            DefaultTableModel dailyModel = new DefaultTableModel(columns, 0);
            for (Map.Entry<String, Integer> entry : dailySales.entrySet()) {
                dailyModel.addRow(new Object[]{entry.getKey(), entry.getValue()});
            }

            DefaultTableModel monthlyModel = new DefaultTableModel(columns, 0);
            for (Map.Entry<String, Integer> entry : monthlySales.entrySet()) {
                monthlyModel.addRow(new Object[]{entry.getKey(), entry.getValue()});
            }

            // 탭 패널 생성
            JTabbedPane tabbedPane = new JTabbedPane();

            JTable dailyTable = new JTable(dailyModel);
            tabbedPane.addTab("일별 매출", new JScrollPane(dailyTable));

            JTable monthlyTable = new JTable(monthlyModel);
            tabbedPane.addTab("월별 매출", new JScrollPane(monthlyTable));

            JFrame frame = new JFrame("매출 요약");
            frame.add(tabbedPane);
            frame.setSize(500, 400);
            frame.setVisible(true);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "데이터 파일을 읽을 수 없습니다: " + e.getMessage());
        }
    }

    // 음료 종류별 매출 보기
    private void showBeverageSales() {
        try {
            Map<String, Map<String, Integer>> dailyBeverageSales = new TreeMap<>();
            Map<String, Map<String, Integer>> monthlyBeverageSales = new TreeMap<>();

            processBeverageData(dailyBeverageSales, monthlyBeverageSales);

            // 테이블 데이터 생성
            String[] columns = {"날짜", "음료", "판매량"};
            DefaultTableModel dailyModel = new DefaultTableModel(columns, 0);
            for (String date : dailyBeverageSales.keySet()) {
                for (Map.Entry<String, Integer> entry : dailyBeverageSales.get(date).entrySet()) {
                    dailyModel.addRow(new Object[]{date, entry.getKey(), entry.getValue()});
                }
            }

            String[] monthlyColumns = {"월", "음료", "판매량"};
            DefaultTableModel monthlyModel = new DefaultTableModel(monthlyColumns, 0);
            for (String month : monthlyBeverageSales.keySet()) {
                for (Map.Entry<String, Integer> entry : monthlyBeverageSales.get(month).entrySet()) {
                    monthlyModel.addRow(new Object[]{month, entry.getKey(), entry.getValue()});
                }
            }

            // 탭 패널 생성
            JTabbedPane tabbedPane = new JTabbedPane();

            JTable dailyTable = new JTable(dailyModel);
            tabbedPane.addTab("음료별 일별 판매량", new JScrollPane(dailyTable));

            JTable monthlyTable = new JTable(monthlyModel);
            tabbedPane.addTab("음료별 월별 판매량", new JScrollPane(monthlyTable));

            JFrame frame = new JFrame("음료별 판매 현황");
            frame.add(tabbedPane);
            frame.setSize(600, 400);
            frame.setVisible(true);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "데이터 파일을 읽을 수 없습니다: " + e.getMessage());
        }
    }

    // 매출 데이터 처리
    private void processSalesData(Map<String, Integer> dailySales,
                                  Map<String, Integer> monthlySales) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("drinkLog.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length < 2) continue;

                try {
                    LocalDateTime dateTime = LocalDateTime.parse(parts[0],
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                    String date = dateTime.toLocalDate().toString();
                    String month = dateTime.getYear() + "-" + String.format("%02d", dateTime.getMonthValue());

                    dailySales.put(date, dailySales.getOrDefault(date, 0) + 1);
                    monthlySales.put(month, monthlySales.getOrDefault(month, 0) + 1);

                } catch (DateTimeParseException e) {
                    // 날짜 형식 오류 무시
                }
            }
        }
    }

    // 음료별 매출 데이터 처리
    private void processBeverageData(Map<String, Map<String, Integer>> dailyBeverageSales,
                                     Map<String, Map<String, Integer>> monthlyBeverageSales) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("drinkLog.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length < 2) continue;

                try {
                    LocalDateTime dateTime = LocalDateTime.parse(parts[0],
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    String beverage = parts[1];

                    String date = dateTime.toLocalDate().toString();
                    String month = dateTime.getYear() + "-" + String.format("%02d", dateTime.getMonthValue());

                    // 일별 데이터 처리
                    dailyBeverageSales
                            .computeIfAbsent(date, k -> new HashMap<>())
                            .merge(beverage, 1, Integer::sum);

                    // 월별 데이터 처리
                    monthlyBeverageSales
                            .computeIfAbsent(month, k -> new HashMap<>())
                            .merge(beverage, 1, Integer::sum);

                } catch (DateTimeParseException e) {
                    // 날짜 형식 오류 무시
                }
            }
        }
    }


    // 파일에서 아이디와 비밀번호 로드
    private void loadCredentials() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CREDENTIALS_FILE))) {
            currentId = reader.readLine();
            currentPassword = reader.readLine();
        } catch (IOException e) {
            // 파일이 없으면 기본값 사용
            currentId = "admin";
            currentPassword = "Admin123!";
            saveCredentials();
        }
    }

    // 아이디와 비밀번호 파일에 저장
    private void saveCredentials() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CREDENTIALS_FILE))) {
            writer.write(currentId + "\n" + currentPassword);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "정보 저장 실패: " + e.getMessage(), "오류",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AdminInfo(new OutputQueue(), new MoneyBox(), new JButton[1]).setVisible(true);
        });
    }
}
