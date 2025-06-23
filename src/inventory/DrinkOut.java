package inventory;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DrinkOut extends JFrame {
    // GUI 위치 조정을 위한 기준 좌표 (정적 변수)
    public static int baseX = 300;  // 창의 초기 X 위치
    public static int baseY = 300;  // 창의 초기 Y 위치
    private final int OFFSET = 50;  // 새 창 생성 시 위치 이동량

    JPanel container;  // 컴포넌트를 담을 메인 패널

    void fileOut(String drink) {
        // 현재 시간 포맷팅
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = now.format(formatter);

        // 파일에 시간과 음료명 기록 (append 모드)
        try (FileWriter writer = new FileWriter("drinkLog.txt", true)) {
            writer.write(formattedNow + "\t" + drink + "\n");
        } catch (IOException e) {
            e.printStackTrace();  // 파일 입출력 오류 시 스택 트레이스 출력
        }
    }

    public void alert(int drinkId) {
        // drinkId에 해당하는 음료명 매핑
        String drink = "";
        switch (drinkId) {
            case 1:  drink = "믹스커피"; break;
            case 3:  drink = "고급 믹스커피"; break;
            case 5:  drink = "물"; break;
            case 7:  drink = "캔커피"; break;
            case 9:  drink = "이온음료"; break;
            case 11: drink = "고급 캔커피"; break;
            case 13: drink = "탄산음료"; break;
            case 15: drink = "특화음료"; break;
        }

        fileOut(drink);  // 파일 로깅

        // GUI에 음료명 표시
        JLabel drinkName = new JLabel();
        drinkName.setText(drink + "\t");
        container.add(drinkName);
        add(container);
    }

    /** 기본 생성자: 창 초기화 및 위치 설정 */
    public DrinkOut() {
        setSize(250, 100);  // 창 크기 설정
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // 닫기 시 현재 창만 종료

        container = new JPanel();  // 메인 패널 초기화

        // 창 위치 설정 (이전 창 대비 OFFSET 이동)
        setLocation(baseX, baseY);
        baseX += OFFSET;  // 다음 창 위치 업데이트

        setVisible(true);  // 창 표시
    }
}
