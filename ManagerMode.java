import java.util.*;

public class ManagerMode {

    private List<Order> orderList = new ArrayList<>(); // 주문 목록 리스트

    public void printManagerModeMenu() { // 관리자 모드 메인 메뉴
        System.out.println("0. 메인 메뉴");
        System.out.println("1. 대기 주문 목록");
        System.out.println("2. 완료 주문 목록");
        System.out.println("3. 메뉴 생성");
        System.out.println("4. 메뉴 삭제");
    }

    public void addCartToOrderList(int ordernumber, List<McdonaldsMenu> cart, Integer totalPrice, String message) {
        List<McdonaldsMenu> orderMcdonaldsMenuList = new ArrayList<>(cart);
        orderList.add(new Order(ordernumber, orderMcdonaldsMenuList, totalPrice, message));
    }

    public void WaitingOrdersProcessInput() { // 대기 주문 처리 관리자 입력
        displayWaitingOrders();

        System.out.println();
        System.out.println("대기중인 주문을 처리하시겠습니까?");
        System.out.println("1. 확인        2. 취소");
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        if (input == 1) {
            processWaitingOrder();
        }
    }

    public void displayWaitingOrders() { // 대기 주문 화면 출력
        System.out.println("대기 주문 목록: ");
        for (Order order : orderList) {
            if (!order.complete) {
                order.display();
            }
        }
    }

    public void printWaitingOrders() { // 대기 주문 목록
        for (Order order : orderList) {
            if (!order.complete) {
                order.printOrderStatus();
            }
        }
    }

    public void processWaitingOrder() { // 대기 주문 완료 처리
        if (orderList.isEmpty()) { // 주문 목록 리스트가 비어있다면
            System.out.println("대기 주문이 없습니다.");
            return;
        }

        System.out.println("완료할 주문 번호: ");
        Scanner scanner = new Scanner(System.in);
        int orderNumber = scanner.nextInt();
        scanner.nextLine();

        Order orderToComplete = null;
        for (Order order : orderList) {
            if (order.getOrderNumber() == orderNumber) {
                order.setComplete(true);
                orderToComplete = order;
                break;
            }
        }

        if (orderToComplete != null) {
            System.out.println("처리가 완료되었습니다.");
        } else {
            System.out.println("주문 번호를 찾을 수 없습니다.");
        }
        System.out.println();
    }

    public void printCompletedOrders() { // 완료 주문 목록
        for (Order order : orderList) {
            if (order.complete) {
                order.printOrderStatus();
            }
        }
    }

    public void displayCompletedOrders() { // 완료 주문 화면 출력
        System.out.println("완료 주문 목록: ");
        for (Order order : orderList) {
            if (order.complete) {
                order.display();
                System.out.println("\t완료시각: " + order.completeDate);
                System.out.println();
            }
        }
        System.out.println();
        System.out.println("(3초 후 메인 메뉴로 돌아갑니다.)");
        try {
            Thread.sleep(3000); // 3초 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public McdonaldsMenu createMenus() { // 메뉴 생성 관리자 입력
        Scanner scanner = new Scanner(System.in);
        System.out.println("이름: ");
        String name = scanner.nextLine();

        System.out.println("설명: ");
        String description = scanner.nextLine();

        System.out.println("가격: ");
        int price = scanner.nextInt();

        System.out.println("메뉴가 생성되었습니다.");
        System.out.println();

        return new McdonaldsMenu(name, price, description);
    }

    public void deleteMenus(Map<String, List<McdonaldsMenu>> mcdonaldsMenu, int itemId) { // 메뉴 삭제
        mcdonaldsMenu.forEach((key, value) -> {
            int removeIndex = -1;
            for(int i=0; i<value.size(); i++) {
                if (value.get(i).id == itemId) {
                    removeIndex = i;
                }
            }
            if (removeIndex > -1) {
                value.remove(removeIndex);
            }
        });
        System.out.println("상품이 삭제되었습니다.");
    }
}
