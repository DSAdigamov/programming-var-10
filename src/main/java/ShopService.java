import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShopService {

    private Map<String, List<Item>> SHOP_NAME_TO_ITEMS_MAP;

    private Map<String, List<Shop>> ITEM_NAME_TO_SHOPS_MAP = new HashMap<>();

    public ShopService() {
        try {
            setUpMaps();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void printInfoAboutShopsByItemName(String itemName) {
        var shops = ITEM_NAME_TO_SHOPS_MAP.get(itemName);
        if (shops == null) {
            System.out.println("Такого товара не существует!");
            return;
        }

        System.out.println(
                "Товар " + itemName + " продается в магазинах: " + shops.stream().map(Shop::getName)
                        .toList());
    }

    public void printInfoAboutItemsByShopName(String shopName) {
        var items = SHOP_NAME_TO_ITEMS_MAP.get(shopName);
        if (items == null) {
            System.out.println("Такого магазина не существует!");
            return;
        }

        System.out.println("В магазине " + shopName + " продаются:");
        items.forEach(System.out::println);
    }

    private void setUpMaps() throws IOException {
        List<Shop> shops = parseShopData();
        shops.forEach(shop -> shellSortByPrice(shop.getItems()));

        SHOP_NAME_TO_ITEMS_MAP = shops.stream()
                .collect(Collectors.toMap(Shop::getName, Shop::getItems));

        shops.forEach(shop -> {
            shop.getItems().forEach(item -> {
                if (!ITEM_NAME_TO_SHOPS_MAP.containsKey(item.getName())) {
                    var shopList = new ArrayList<Shop>();
                    shopList.add(shop);

                    ITEM_NAME_TO_SHOPS_MAP.put(item.getName(), shopList);
                    return;
                }

                ITEM_NAME_TO_SHOPS_MAP.get(item.getName()).add(shop);
            });
        });
    }

    private List<Shop> parseShopData() throws IOException {
        ObjectMapper om = new ObjectMapper();

        return om.readValue(new File("Data.json"),
                om.getTypeFactory().constructCollectionType(List.class, Shop.class));
    }

    public void shellSortByPrice(List<Item> listToSort) {
        int n = listToSort.size();

        for (int step = n / 2; step > 0; step /= 2) {
            for (int i = step; i < n; i++) {
                Item key = listToSort.get(i);
                int j = i;
                while (j >= step && listToSort.get(j - step).getPrice() > key.getPrice()) {
                    listToSort.set(j, listToSort.get(j - step));
                    j -= step;
                }
                listToSort.set(j, key);
            }
        }
    }
}
