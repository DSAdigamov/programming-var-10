import java.util.List;

public class Shop {

    private String name;

    private List<Item> items;

    public Shop() {
    }

    public Shop(String name, List<Item> items) {
        this.name = name;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "name='" + name + '\'' +
                ", items=" + items +
                '}';
    }
}
