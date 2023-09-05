
import atm.dao.AccountDao;
import atm.service.impl.AccountServiceImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AccountDao accountDao = new AccountDao();
        AccountServiceImpl service = new AccountServiceImpl();
        Scanner scanner = new Scanner(System.in);
        boolean checkError = true;
        System.out.print("Атнызды жазыныз: ");
        String name = scanner.nextLine();
        System.out.print("Фамилианызд жазыныз: ");
        String lastName = scanner.nextLine();
        if (name.length() > 1 && lastName.length() > 1) {
            if (name.length() < 12 && lastName.length() < 12) {
                do {
                    service.singUp(name, lastName);
                    break;
                }
                while (checkError);
            } else {
                System.out.println("Туура эмес ат же фамилия! Дагы сынап көрүңүз.");
                checkError = false;
            }
            if (checkError) {
                System.out.print("Картанын номерин киргизиңиз: ");
                String cardNumber = scanner.nextLine();
                System.out.print("Картанын пин кодун киргизиңиз: ");
                String pinCode = scanner.nextLine();
                service.singIn(cardNumber, pinCode);
            }
        }
    }
}