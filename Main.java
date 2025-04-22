import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.DateTimeException;
import java.util.InputMismatchException;

public class Main {
    private static final int maxZapysiv = 50;

    private static LocalDateTime[] daty = new LocalDateTime[maxZapysiv];
    private static String[] teksty = new String[maxZapysiv];
    private static int kilkistZapysiv = 0;

    public static void main(String[] args) {
        Scanner skan = new Scanner(System.in);
        boolean pratsyuye = true;

        System.out.println("( ͡°͜ʖ͡°) Мій щоденник запущено ( ͡°͜ʖ͡°)");

        while (pratsyuye) {
            pokazatyHolovneMenu();
            int vybir = 0;

            try {
                System.out.print("Ваш вибір: ");
                vybir = skan.nextInt();
                skan.nextLine(); // Очищення буфера
            } catch (InputMismatchException e) {
                System.out.println("\nНевірний вибір (╬▔皿▔)╯! Спробуйте ще раз, але вже число.");
                skan.nextLine(); // Очищення буфера
                continue;
            }

            switch (vybir) {
                case 1:
                    dodatyZapys(skan);
                    break;

                case 2:
                    vydalytyZapys(skan);
                    break;

                case 3:
                    pokazatyVsiZapysy();
                    break;

                case 4:
                    System.out.println("\n✓ До побачення! Щоденник закрито.");
                    pratsyuye = false;
                    break;

                default:
                    System.out.println("\nНевірний вибір (╬▔皿▔)╯! Спробуйте ще раз.");
            }
        }
        skan.close();
    }

    private static void pokazatyHolovneMenu() {
        System.out.println("\n┌───────────────────────────────────────┐");
        System.out.println("│ МІЙ ЩОДЕННИК   つ◕_◕ ༽つ               │");
        System.out.println("├───────────────────────────────────────┤");
        System.out.println("│ 1. Додати запис                       │");
        System.out.println("│ 2. Видалити запис                     │");
        System.out.println("│ 3. Переглянути всі записи             │");
        System.out.println("│ 4. Вийти з програми                   │");
        System.out.println("└───────────────────────────────────────┘");
    }

    private static void dodatyZapys(Scanner skan) {
        if (kilkistZapysiv >= maxZapysiv) {
            System.out.println("\nЩоденник повний (╬▔皿▔)╯! Максимальна кількість записів: " + maxZapysiv);
            return;
        }

        System.out.println("\n┌────────────────────────────────────┐");
        System.out.println("│ДОДАВАННЯ НОВОГО ЗАПИСУ              │");
        System.out.println("└────────────────────────────────────┘");

        LocalDateTime data = null;

        while (data == null) {
            try {
                System.out.println("Введіть дату для запису (рік, місяць, день):");
                System.out.print("Рік: ");
                int rik = skan.nextInt();
                System.out.print("Місяць: ");
                int misyats = skan.nextInt();
                System.out.print("День: ");
                int den = skan.nextInt();
                skan.nextLine();

                data = LocalDateTime.of(rik, misyats, den, 0, 0);
            } catch (InputMismatchException e) {
                System.out.println("Помилка введення! Введіть ціле число.");
                skan.nextLine();
            } catch (DateTimeException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }

        for (int i = 0; i < kilkistZapysiv; i++) {
            if (daty[i] != null) {
                if (daty[i].equals(data)) {
                    System.out.println("Запис з такою датою вже існує (╬▔皿▔)╯! Спробуйте іншу дату.");
                    return;
                }
            }
        }

        System.out.println("\nВведіть текст запису. Для завершення введіть '~'.");
        System.out.println("Можна вводити декілька рядків, кожен з нових рядків.");

        String tekst = "";
        String ryadok;

        while (true) {
            ryadok = skan.nextLine();
            if (ryadok.equals("~")) {
                break;
            }

            if (!tekst.isEmpty()) {
                tekst = tekst + "\n" + ryadok;
            } else {
                tekst = ryadok;
            }
        }

        if (tekst.isEmpty()) {
            System.out.println("Запис не може бути порожнім (╬▔皿▔)╯!");
            return;
        }

        daty[kilkistZapysiv] = data;
        teksty[kilkistZapysiv] = tekst;
        kilkistZapysiv++;

        System.out.println("\n✓ Запис успішно додано до щоденника!");
    }

    private static void vydalytyZapys(Scanner skan) {
        if (kilkistZapysiv == 0) {
            System.out.println("\nЩоденник порожній (╬▔皿▔)╯!");
            return;
        }

        System.out.println("\n┌────────────────────────────────────┐");
        System.out.println("│ВИДАЛЕННЯ ЗАПИСУ                     │");
        System.out.println("└────────────────────────────────────┘");

        LocalDateTime data = null;

        while (data == null) {
            try {
                System.out.println("Введіть дату запису для видалення (рік, місяць, день):");
                System.out.print("Рік: ");
                int rik = skan.nextInt();
                System.out.print("Місяць: ");
                int misyats = skan.nextInt();
                System.out.print("День: ");
                int den = skan.nextInt();
                skan.nextLine();

                data = LocalDateTime.of(rik, misyats, den, 0, 0);
            } catch (InputMismatchException e) {
                System.out.println("Помилка введення! Введіть ціле число.");
                skan.nextLine();
            } catch (DateTimeException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }

        int indexZapysu = -1;

        for (int i = 0; i < kilkistZapysiv; i++) {
            if (daty[i] != null) {
                if (daty[i].equals(data)) {
                    indexZapysu = i;
                    break;
                }
            }
        }

        if (indexZapysu == -1) {
            System.out.println("Запис з такою датою не знайдено (╬▔皿▔)╯!");
            return;
        }

        for (int i = indexZapysu; i < kilkistZapysiv - 1; i++) {
            daty[i] = daty[i + 1];
            teksty[i] = teksty[i + 1];
        }

        daty[kilkistZapysiv - 1] = null;
        teksty[kilkistZapysiv - 1] = null;
        kilkistZapysiv--;

        System.out.println("\n✓ Запис успішно видалено!");
    }

    private static void pokazatyVsiZapysy() {
        if (kilkistZapysiv == 0) {
            System.out.println("\nЩоденник порожній (╬▔皿▔)╯!");
            return;
        }

        System.out.println("\n┌────────────────────────────────────┐");
        System.out.println("│ПЕРЕЛІК ВСІХ ЗАПИСІВ                 │");
        System.out.println("└────────────────────────────────────┘");

        for (int i = 0; i < kilkistZapysiv; i++) {
            System.out.println("\n[Запис #" + (i + 1) + ", Дата: " + daty[i].toLocalDate() + "]");
            System.out.println("────────────────────────────────────");
            System.out.println(teksty[i]);
            System.out.println("────────────────────────────────────");
        }

        System.out.println("\n✓ Загальна кількість записів: " + kilkistZapysiv);
    }
}