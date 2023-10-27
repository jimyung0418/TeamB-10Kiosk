import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuContext {

    // 변수 선언
    private Map<String, List<Menu>> menus;
    private Map<String, List<McdonaldsMenu>> mcdonaldsMenus;
    private List<McdonaldsMenu> cart;
    private int totalPrice;
    private int orderNumber;

    // 변수 초기화
    public MenuContext() {
        menus = new HashMap<>();
        mcdonaldsMenus = new HashMap<>();
        cart = new ArrayList<>();
        totalPrice = 0;
        orderNumber = 0;

        initializeMenus();
    }

    private void initializeMenus() { // 메뉴들의 리스트 생성 및 초기화
        List<Menu> mainMenus = new ArrayList<>();
        mainMenus.add(new Menu("Burgers","주문 즉시 바로 조리해 더욱 맛있는, 맥도날드의 다양한 버거를 소개합니다."));
        mainMenus.add(new Menu("Sides","가볍게 즐겨도, 버거와 함께 푸짐하게 즐겨도, 언제나 맛있는 사이드 메뉴!"));
        mainMenus.add(new Menu("Drinks","언제나 즐겁게, 다양한 음료를 부담없이 즐기세요!"));

        List<Menu> orderMenus = new ArrayList<>();
        orderMenus.add(new Menu("Order", "장바구니를 확인 후 주문합니다."));
        orderMenus.add(new Menu("Cancel", "진행중인 주문을 취소합니다."));

        menus.put("Main", mainMenus); // Map menus에 KEY: "Main" / VALUE: List mainMenus를 저장
        menus.put("Order", orderMenus); // Map menus에 KEY: "Order" / VALUE: List orderMenus를 저장

        List<McdonaldsMenu> burgersMenus = new ArrayList<>();
        burgersMenus.add(new McdonaldsMenu("Big Mac", 6000, "패티 두 장에 빅맥만의 특별한 소스. 버거의 대명사."));
        burgersMenus.add(new McdonaldsMenu("Shanghai Burger", 6000, "닭가슴 통살 위에 양상추와 토마토. 입맛도 기분도 화끈하게!"));
        burgersMenus.add(new McdonaldsMenu("Quarter Pounder Cheese", 6300, "순 쇠고기 패티와 부드러운 치즈 두 장의 환상궁합!"));

        List<McdonaldsMenu> sidesMenus = new ArrayList<>();
        sidesMenus.add(new McdonaldsMenu("French Fries", 2800, "맥도날드의 역사가 담긴 월드 클래스 후렌치 후라이."));
        sidesMenus.add(new McdonaldsMenu("McNuggets", 3300, "바삭하고 촉촉한 치킨이 한 입에 쏙"));
        sidesMenus.add(new McdonaldsMenu("Cheese Sticks", 3300, "모짜렐라 치즈로 빈틈 없이 고소한 맥도날드 치즈스틱."));

        List<McdonaldsMenu> drinksMenus = new ArrayList<>();
        drinksMenus.add(new McdonaldsMenu("Coke", 2400, "코카콜라"));
        drinksMenus.add(new McdonaldsMenu("Sprite", 2400, "스프라이트"));
        drinksMenus.add(new McdonaldsMenu("Fanta", 2400, "환타 오렌지맛"));

        mcdonaldsMenus.put("Burgers", burgersMenus); // Map mcdonaldsMenus에 KEY: "Burgers" / VALUE: List burgersMenus를 저장
        mcdonaldsMenus.put("Sides", sidesMenus); // Map mcdonaldsMenus에 KEY: "Sides" / VALUE: List sidesMenus를 저장
        mcdonaldsMenus.put("Drinks", drinksMenus); // Map mcdonaldsMenus에 KEY: "Drinks" / VALUE: List drinksMenus를 저장
    }

    public List<Menu> getMenus(String key) {

        return menus.get(key);
    }

    public List<McdonaldsMenu> getMcdonaldsMenu(String key) { // 키 값으로 상품 리스트 불러오기
        return mcdonaldsMenus.get(key);
    }

    public Map<String,List<McdonaldsMenu>> getMcdonaldsMenusMap() {

        return mcdonaldsMenus;
    }

    public List<McdonaldsMenu> getCart() {
        return cart;
    }

    public void addMenu(String key, String description) {
        menus.get("Main").add(new Menu(key, description));
        mcdonaldsMenus.put(key, new ArrayList<>());
    }

    public void addMcdonaldsMenu(String key, McdonaldsMenu newMcdonaldsMenu) {
        mcdonaldsMenus.get(key).add(newMcdonaldsMenu);
    }

    public String getMainMenuName(int id) {
        List<Menu> mainMenus = menus.get("Main");
        for (Menu mainMenu : mainMenus) {
            if (mainMenu.id == id) {
                return mainMenu.name;
            }
        }
        return "";
    }

    public void addToCart(McdonaldsMenu mcdonaldsMenu) { // 장바구니에 추가
        cart.add(mcdonaldsMenu);
        totalPrice += mcdonaldsMenu.price;
    }

    public void displayAllMcdonaldsMenu() { // 모든 상품 메뉴 출력 (메뉴 삭제 시)
        System.out.println("[ 전체 상품 목록 ]");
        mcdonaldsMenus.forEach((key, value) -> {
            System.out.println(" [ " + key + " Menu ]");
            for (McdonaldsMenu mcdonaldsMenu: value) {
                System.out.println(mcdonaldsMenu.id + ". " + mcdonaldsMenu.name + "   | " + mcdonaldsMenu.price + " | " + mcdonaldsMenu.description);
            }
        });
    }

    public void displayCart() { // 장바구니 화면
        for (McdonaldsMenu mcdonaldsMenu: cart) {
            System.out.println(mcdonaldsMenu.name + "   | " + mcdonaldsMenu.price + " | " + mcdonaldsMenu.description);
        }
    }

    public int getTotalPrice() { // 장바구니 총액
        return totalPrice;
    }

    public int generateOrderNumber() { // 주문번호 생성
        orderNumber++;
        return orderNumber;
    }

    public void resetCart() { // 장바구니 초기화
        cart.clear();
        totalPrice = 0;
    }
}
