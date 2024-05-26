import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final ShopService shopService = new ShopService();

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        boolean isReadyToExit = false;
        while (!isReadyToExit) {
            printKeyboard();

            var selectedVar = scanner.nextByte();
            switch (selectedVar) {
                case 1 -> {
                    System.out.println("Введите название товара:");
                    var itemName = scanner.next();

                    shopService.printInfoAboutShopsByItemName(itemName);
                }
                case 2 -> {
                    System.out.println("Введите название магазина:");
                    var shopName = scanner.next();

                    shopService.printInfoAboutItemsByShopName(shopName);
                }
                case 3 -> {
                    isReadyToExit = true;
                }
                default -> System.out.println("Я вас не понял, попробуйте снова");
            }
        }
    }

    public static void printKeyboard() {
        System.out.println("""
                \n=========================================================
                1. Выбрать товар
                2. Выбрать магазин
                3. Выход
                =========================================================""");
    }
}
