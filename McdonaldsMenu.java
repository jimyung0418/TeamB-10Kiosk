public class McdonaldsMenu extends Menu{ // 상품 클래스

    int price;

    McdonaldsMenu(String name, int price, String description) {
        super(name, description);
        this.price = price;
    }
}
