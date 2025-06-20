package moneyBox;

import javax.swing.*;

class MoneyOut extends JFrame{
    // 기준 좌표 및 오프셋(이동량)
    public static int baseX = 300;
    public static int baseY = 500;
    private final int OFFSET = 50;

    JPanel container;

    public void alert(int moneyUnit) {

        Integer money = moneyUnit;
        JLabel currencies;

        if(money == -1){
            currencies = new JLabel("거스름돈 부족");
        }else{
            currencies = new JLabel(money.toString());
        }
        container.add(currencies);
        add(container);
    }

    public MoneyOut() {
        setSize(250, 100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        container = new JPanel();

        // 창 위치를 지정 (이전 창보다 OFFSET만큼 아래로)
        setLocation(baseX, baseY);

        // 다음 창을 위해 좌표를 OFFSET만큼 이동
        baseX += OFFSET;

        setVisible(true);
    }
}

public class PaymentMachine {

    public Integer totalMoneyInput;

    public PaymentMachine() {
        this.totalMoneyInput = 0;
    }


}
