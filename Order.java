import java.time.LocalDateTime;
import java.util.List;

public class Order {

    // 변수 선언
    int orderNumber; // 주문 번호
    List<McdonaldsMenu> cart; // 장바구니
    Integer totalPrice; // 장바구니 총 금액
    String message; // 요청 사항
    LocalDateTime orderDate; // 주문 일시
    LocalDateTime completeDate; // 완료 일시
    Boolean complete = false; // 주문 완료 처리 여부

    public Order(int orderNumber, List<McdonaldsMenu> cart, int totalPrice, String message) {
        this.orderNumber = orderNumber;
        this.cart = cart;
        this.totalPrice = totalPrice;
        this.message = message;
        this.orderDate = LocalDateTime.now();
    }

    public void display() { // 주문 정보 출력
        System.out.println("\t주문번호: " + this.orderNumber);
        System.out.println("\t주문상품 목록: ");
        for (McdonaldsMenu mcdonaldsMenu: cart) {
            System.out.println("\t\t" + mcdonaldsMenu.name + "   | " + mcdonaldsMenu.price + " | " + mcdonaldsMenu.description);
        }
        System.out.println("\t총 가격: " + this.totalPrice);
        System.out.println("\t요청 사항: " + this.message);
        System.out.println("주문 시각 : " + this.orderDate);
    }

    public void printOrderStatus () { // 메인 메뉴 상단 주문 현황 (주문 번호만)
        System.out.println("\t 주문번호: " + this.orderNumber);
    }

    public int getOrderNumber() { // 주문 번호 호출
        return this.orderNumber;
    }

    public void setComplete(Boolean complete) { // 주문 완료 처리
        this.complete = complete;
        this.completeDate = LocalDateTime.now();
    }
}
