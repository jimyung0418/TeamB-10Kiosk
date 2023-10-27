import java.util.List;
import java.util.Scanner;

public class Main {
    private static MenuContext menuContext;
    private static ManagerMode managerMode;

    public static void main(String[] args) {
        menuContext = new MenuContext();
        managerMode = new ManagerMode();


        displayMainMenu();
    }

    private static void displayMainMenu() { // 메인 메뉴 화면 출력 메서드
        mainOrderStatus();
        System.out.println();
        System.out.println("Mcdonald's 에 오신걸 환영합니다 !");
        System.out.println("아래 메뉴판을 보시고 메뉴를 골라 입력해주세요.\n");

        System.out.println("[ McDonald's MENU ]");
        List<Menu> mainMenus = menuContext.getMenus("Main");
        int num = printMainMenu(mainMenus, 1);

        System.out.println("[ Order MENU ]");
        List<Menu> orderMenus = menuContext.getMenus("Order");
        printMainMenu(orderMenus, num); // 메인 메뉴 리스트 출력

        mainMenuInput(); // 메인 메뉴 사용자 입력
    }

    private static int printMainMenu(List<Menu> menus, int num) { // 메인 메뉴 리스트 출력
        for (int i=0; i<menus.size(); i++) {
            System.out.println(num++ + ". " + menus.get(i).name + "   | " + menus.get(i).description);
        }
        return num;
    }

    private static void mainMenuInput() { // 메인 메뉴 사용자 입력
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        int mainMenuSize = menuContext.getMenus("Main").size();
        int orderMenuSize = menuContext.getMenus("Order").size();

        if (input == 0) {
            displayManagerModeMenu(); // 관리자 모드 이동
        } else if (input <= mainMenuSize) {
            displayMcdonaldsMenu(menuContext.getMenus("Main").get(input - 1));
        } else if (input <= mainMenuSize + orderMenuSize) {
            int orderInput = input - mainMenuSize;
            switch (orderInput) {
                case 1:
                    displayOrderMenu();
                    break;
                case 2:
                    displayCancelMenu();
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                    mainMenuInput(); // 재귀
            }
        } else {
            System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
            mainMenuInput(); // 재귀
        }
    }

    private static void displayMcdonaldsMenu(Menu menu) { // 상품 메뉴 화면 출력
        System.out.println("Mcdonald's 에 오신걸 환영합니다 !");
        System.out.println("아래 상품메뉴판을 보시고 상품을 골라 입력해주세요.\n");

        System.out.println("[ " + menu.name + " MENU ]");
        List<McdonaldsMenu> mcdonaldsMenus = menuContext.getMcdonaldsMenu(menu.name);
        printMcdonaldsMenus(mcdonaldsMenus); // 상품 메뉴 리스트 출력

        McdonaldMenuInput(mcdonaldsMenus); // 상품 메뉴 사용자 입력
    }

    private static void printMcdonaldsMenus(List<McdonaldsMenu> mcdonaldsMenus) { // 상품 메뉴 리스트 출력
        for (int i=0; i<mcdonaldsMenus.size(); i++) {
            int num = i + 1;
            System.out.println(num + ". " + mcdonaldsMenus.get(i).name + "   | " + mcdonaldsMenus.get(i).price + " | " + mcdonaldsMenus.get(i).description);
        }
    }

    private static void McdonaldMenuInput(List<McdonaldsMenu> mcdonaldsMenus) { // 상품 메뉴 사용자 입력
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        if (input >= 1 && input <= mcdonaldsMenus.size()) {
            McdonaldsMenu selectedMenu = mcdonaldsMenus.get(input-1);
            displayConfirmation(selectedMenu); // 장바구니 담기 컨펌 화면 출력
        } else {
            System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
            McdonaldMenuInput(mcdonaldsMenus); // 재귀
        }
    }

    private static void displayConfirmation(McdonaldsMenu mcdonaldsMenu) { // 장바구니 담기 컨펌 화면 출력
        System.out.println(mcdonaldsMenu.name + "   | " + mcdonaldsMenu.price + " | " + mcdonaldsMenu.description);
        System.out.println("위 메뉴를 장바구니에 추가하시겠습니까?");
        System.out.println("1. 확인        2.취소");

        ConfirmationInput(mcdonaldsMenu);
    }

    private static void ConfirmationInput(McdonaldsMenu mcdonaldsMenu) { // 장바구니 담기 컨펌 화면 사용자 입력
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        if (input == 1) {
            menuContext.addToCart(mcdonaldsMenu);
            System.out.println("장바구니에 추가되었습니다.");
            displayMainMenu();
        } else if (input == 2) {
            displayMainMenu();
        } else {
            System.out.println("잘못된 입력입니다. 다시 입력해주세요");
            ConfirmationInput(mcdonaldsMenu); // 재귀
        }
    }

    private static void displayOrderMenu() { // 장바구니 주문 확정 화면 출력
        System.out.println("아래와 같이 주문 하시겠습니까?\n");
        menuContext.displayCart();

        System.out.println("[ Total Price ]");
        System.out.println("W " + menuContext.getTotalPrice() + "\n");

        System.out.println("1. 주문      2. 메뉴판");

        orderMenuInput(); // 장바구니 주문 확정 사용자 입력
    }

    private static void orderMenuInput() { // 장바구니 주문 확정 사용자 입력
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        if (input == 1) {
            displayOrderComplete(); // 주문 완료 화면 및 요청사항 사용자 입력
        } else if (input == 2) {
            displayMainMenu(); // 메인 메뉴 화면 출력
        } else {
            System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
            orderMenuInput(); // 재귀
        }
    }

    private static void displayOrderComplete() { // 주문 완료 화면 및 요청사항 사용자 입력
        int orderNumber = menuContext.generateOrderNumber();
        List<McdonaldsMenu> cart = menuContext.getCart();
        Integer totalPrice = menuContext.getTotalPrice();
        System.out.println("주문 시 요청사항을 입력해주세요.");
        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();

        // 관리자 모드에 주문 정보 저장
        managerMode.addCartToOrderList(orderNumber, cart, totalPrice, message);

        System.out.println("주문이 완료되었습니다!");
        System.out.println("대기번호는 [ " + orderNumber + " ]  번 입니다.");

        resetCartAndDisplayMainMenu();
    }

    private static void resetCartAndDisplayMainMenu() { // 장바구니 초기화 및 메인메뉴 복귀
        menuContext.resetCart();
        System.out.println("(3초 후 메뉴판으로 돌아갑니다.)");
        try {
            Thread.sleep(3000); // 3초 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        displayMainMenu();
    }

    private static void displayCancelMenu() { // 주문 취소 화면 출력
        System.out.println("주문을 취소하시겠습니까?");
        System.out.println("1. 확인        2. 취소");

        cancelMenuInput();
    }

    private static void mainOrderStatus() { // 메인 메뉴 상단 주문 현황
        System.out.println("[ 완료 주문 ]");
        managerMode.printCompletedOrders();
        System.out.println();
        System.out.println("[ 대기 주문 ]");
        managerMode.printWaitingOrders();
    }

    private static void cancelMenuInput() { // 주문 취소 확정 사용자 입력
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        if (input == 1) {
            menuContext.resetCart();
            System.out.println("주문이 취소되었습니다.");
            displayMainMenu();
        } else if (input == 2) {
            displayMainMenu();
        } else {
            System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
            cancelMenuInput(); // 재귀
        }
    }

    private static void displayManagerModeMenu() { // 관리자 모드 화면 출력
        System.out.println("[ 관리자 모드 ]");
        System.out.println("아래 목록에서 원하는 명령을 골라 입력해주세요.\n");

        managerMode.printManagerModeMenu(); // 관리자 모드 메인 메뉴 출력

        managerModeInput();
    }

    private static void managerModeInput() { // 관리자 모드 관리자 입력
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        if(input == 0) {
            displayMainMenu();
        } else if (input >= 1 && input <= 4) {
            switch (input) {
                case 1:
                    managerMode.WaitingOrdersProcessInput(); // 대기 주문 목록 및 관리자 입력
                    break;
                case 2:
                    managerMode.displayCompletedOrders();
                    break;
                case 3:
                    String menuName = getMenuName();
                    McdonaldsMenu newMcdonaldsMenu = managerMode.createMenus();
                    menuContext.addMcdonaldsMenu(menuName, newMcdonaldsMenu);
                    break;
                case 4:
                    menuContext.displayAllMcdonaldsMenu();
                    System.out.println("삭제할 상품 ID: ");
                    int itemId = scanner.nextInt();
                    managerMode.deleteMenus(menuContext.getMcdonaldsMenusMap(), itemId);
                    break;
                default:
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
            }
        } else {
            System.out.println("잘못된 입력입니다. 다시 입력해주세요");
        }
        displayManagerModeMenu();
    }

    private static String getMenuName() {
        System.out.println("[ 메뉴 목록 ]");
        List<Menu> mainMenus = menuContext.getMenus("Main");
        printMainMenu(mainMenus, 1);
        System.out.println(mainMenus.size() + 1 + ". 신규 메뉴");
        System.out.println("메뉴 ID: ");
        Scanner scanner = new Scanner(System.in);
        int menuId = scanner.nextInt();
        if (menuId <= mainMenus.size()) {
            return menuContext.getMainMenuName(menuId);
        } else {
            System.out.println("신규 메뉴 이름: ");
            String newMenuName = scanner.next();
            System.out.println("신규 메뉴 설명: ");
            String newMenuDescription = scanner.next();
            menuContext.addMenu(newMenuName, newMenuDescription);
            return newMenuName;
        }
    }


}
