package moneyBox;

import inventory.DrinkOut;
import inventory.DrinksTray;
import linkedList.LinkedList;

/**
 * 자판기 금고 관리 시스템
 * - 화폐 단위별 트레이를 연결 리스트로 관리
 * - 금액 저장/반환 기능 구현
 * - 자판기 내 총 금액 추적
 *
 * @author Sunghun Wang
 * @version 1.1.0
 */
public class MoneyBox extends PaymentMachine implements LinkedList {
    MoneyTray head = null;  // 화폐 트레이 연결 리스트 헤드
    PaymentMachine paymentMachine;  // 결제 시스템 참조
    public Integer totalMoneyInMachine;  // 자판기 내 총 금액

    // 임시 화폐 큐 (사용되지 않음)
    MoneyTmp front;
    MoneyTmp rear;

    public MoneyBox() {
        init();  // 화폐 트레이 초기화
        this.totalMoneyInMachine = 16600;  // 초기 자본금 설정
    }

    /**
     * 화폐 트레이 초기화 (1000, 500, 100, 50, 10원 단위)
     */
    @Override
    public void init() {
        reFill(1000);
        reFill(500);
        reFill(100);
        reFill(50);
        reFill(10);
    }

    /**
     * 특정 단위 화폐 트레이 추가
     * @param moneyUnit 추가할 화폐 단위
     */
    @Override
    public void reFill(int moneyUnit) {
        MoneyTray newTray = new MoneyTray(moneyUnit);
        if (head == null) {
            head = newTray;
        } else {
            MoneyTray current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newTray;
        }
    }

    // 미구현 메서드 (인터페이스 요구사항)
    @Override public void takeOut(int moneyUnit) {}
    @Override public int takeOut_(int count) { return 0; }

    /**
     * 특정 단위 화폐 트레이 검색
     * @param moneyUnit 찾을 화폐 단위
     * @return 해당 MoneyTray 객체, 없으면 null
     */
    public MoneyTray search(int moneyUnit) {
        MoneyTray current = head;
        while (current != null) {
            if (current.moneyUnit == moneyUnit) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * 수금 처리 (판매 금액 저장)
     * @param price 저장할 금액
     */
    public void changeNstore(int price) {
        MoneyTray current = head;
        int[] units = {1000, 500, 100, 50, 10};  // 화폐 단위 배열

        for (int unit : units) {
            int count = price / unit;  // 해당 단위 개수 계산
            price %= unit;             // 남은 금액 갱신

            // 해당 단위 트레이에 화폐 추가
            if (current != null) {
                current.reFill(count);
                totalMoneyInMachine += count * unit;  // 총액 업데이트
                current = current.next;
            }
        }
    }

    /**
     * 거스름돈 반환 처리
     * @param price 반환할 금액
     */
    public void changeNreceive(int price) {
        MoneyTray current = head;
        int[] units = {1000, 500, 100, 50, 10};
        MoneyOut out = new MoneyOut();  // 화폐 출력 핸들러

        for (int unit : units) {
            int count = price / unit;
            price %= unit;

            if (current != null) {
                // 트레이에서 화폐 인출 시도
                int shortfall = current.takeOut_(count);

                // 성공 시
                if (shortfall == 0) {
                    totalMoneyInMachine -= count * unit;
                    for (int j = 0; j < count; j++) {
                        out.alert(unit);  // 화폐 출력
                    }
                }
                // 실패 시 (재고 부족)
                else {
                    int available = count - shortfall;
                    // 가능한 만큼 인출
                    for (int j = 0; j < available; j++) {
                        out.alert(unit);
                    }
                    out.alert(-1);  // 오류 알림
                    totalMoneyInMachine -= available * unit;
                }
                current = current.next;
            }
        }
    }

    // 디버깅용 메서드 (트레이 구조 출력)
    public void search() {
        MoneyTray current = head;
        while (current != null) {
            System.out.println(current.moneyUnit);
            current = current.next;
        }
    }
}

/**
 * 임시 화폐 저장 클래스 (현재 미사용)
 */
class MoneyTmp {
    int moneyUnit;
    MoneyTmp next;
    MoneyTmp(int moneyUnit) {
        this.moneyUnit = moneyUnit;
    }
}

/**
 * 디버깅용 실행 클래스
 */
class DebugMoneyBox {
    public static void main(String[] args) {
        MoneyBox machine = new MoneyBox();
        machine.search();  // 트레이 구조 출력
    }
}
