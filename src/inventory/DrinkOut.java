package inventory;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DrinkOut extends JFrame {
    // 기준 좌표 및 오프셋(이동량)
    public static int baseX = 300;
    public static int baseY = 300;
    private final int OFFSET = 50;

    void fileOut(String drink){
        LocalDateTime now = LocalDateTime.now();

        // 원하는 포맷 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 포맷 적용
        String formattedNow = now.format(formatter);

        // 파일에 저장
        try (FileWriter writer = new FileWriter("drinkLog.txt", true)) {
            writer.write(formattedNow + "\t" + drink + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DrinkOut(int drinkId) {
        setSize(100, 50);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel container = new JPanel();
        String drink = "";
        switch (drinkId){
            case 1:  drink = "믹스커피"; break;
            case 3:  drink = "고급 믹스커피"; break;
            case 5:  drink = "물"; break;
            case 7:  drink = "캔커피"; break;
            case 9:  drink = "이온음료"; break;
            case 11: drink = "고급 캔커피"; break;
            case 13: drink = "탄산음료"; break;
            case 15: drink = "특화음료"; break;
        }

        fileOut(drink);

        JLabel drinkName = new JLabel(drink);
        container.add(drinkName);
        add(container);

        // 창 위치를 지정 (이전 창보다 OFFSET만큼 아래로)
        setLocation(baseX, baseY);

        // 다음 창을 위해 좌표를 OFFSET만큼 이동
        baseX += OFFSET;

        setVisible(true);
    }
}
