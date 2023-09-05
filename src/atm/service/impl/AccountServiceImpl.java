package atm.service.impl;

import atm.dao.AccountDao;
import atm.model.UserAccount;
import atm.service.AccountService;

import java.util.*;

public class AccountServiceImpl extends Exception implements AccountService {
    private final AccountDao accountDao = new AccountDao();
    private final Random random = new Random();
    private final Scanner scanner = new Scanner(System.in);
    private final Scanner scannerInt = new Scanner(System.in);
    private boolean Code = true;

    public enum Color {
        ANSI_RESET("\u001B[0m"),
        ANSI_BLACK("\u001B[30m"),
        ANSI_RED("\u001B[31m"),
        ANSI_GREEN("\u001B[32m"),
        ANSI_YELLOW("\u001B[33m"),
        ANSI_BLUE("\u001B[34m"),
        ANSI_PURPLE("\u001B[35m"),
        ANSI_CYAN("\u001B[36m"),
        ANSI_WHITE("\u001B[37m");
        private final String color;

        Color(String color) {
            this.color = color;
        }

        @Override
        public String toString() {
            return color;
        }
    }

    @Override
    public void singUp(String name, String lastName) {
        ArrayList<UserAccount> users = new ArrayList<>();
        int num;
        int code;
        num = random.nextInt(10000000, 99999999);
        code = random.nextInt(1000, 9999);
        String cardNumber = Integer.toString(num);
        String cardPinCode = Integer.toString(code);
        System.out.println(Color.ANSI_YELLOW + "Сиздин картанын номери: " + cardNumber +
                "\nСиздин пин коду карта: " + cardPinCode + Color.ANSI_RESET);
        users.add(new UserAccount("Husein", "Obama", "00000000", "0000", 0));
        users.add(new UserAccount(name, lastName, cardNumber, cardPinCode, 0));
        accountDao.setUserAccounts(users);
    }

    @Override
    public void singIn(String cardNumber, String pinCode) {
        UserAccount[] userAccounts = new UserAccount[1];
        for (UserAccount userAccount : accountDao.getUserAccounts()) {
            try {
                if (userAccount.getCardNumber().equals(cardNumber) && userAccount.getPinCode().equals(pinCode)) {
                    userAccounts[0] = userAccount;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                userAccounts[0] = null;
                System.out.println(Color.ANSI_RED + "Неверный пин код или номер карты!!!");
                Code = false;
            }
        }
        do {
            if (userAccounts[0] != null) {
                System.out.println(Color.ANSI_YELLOW + "{1} Балансты көрүү" +
                        "\n{2} Депозитти алуу" +
                        "\n{3} Досуңузга акча жөнөтүңүз" +
                        "\n{4} Эсептен акча алуу" +
                        "\n{5} Балансты толуктоо");
                System.out.print(Color.ANSI_CYAN + "Аракет тандаңыз: ");
                int action = scannerInt.nextInt();
                switch (action) {
                    case 1 -> {
                        System.out.print(Color.ANSI_CYAN + "Картанын номерин киргизиңиз: ");
                        String cardNumber1 = scanner.nextLine();
                        System.out.print("Картанын пин кодун киргизиңиз: ");
                        String pinCode1 = scanner.nextLine();
                        balance(cardNumber1, pinCode1);
                    }
                    case 2 -> {
                        System.out.print(Color.ANSI_CYAN + "Картанын номерин киргизиңиз: ");
                        String cardNumber2 = scanner.nextLine();
                        System.out.print("Картанын пин кодун киргизиңиз: ");
                        String pinCode2 = scanner.nextLine();
                        deposit(cardNumber2, pinCode2);
                    }
                    case 3 -> {
                        System.out.print(Color.ANSI_CYAN + "Досуңуздун карта номерин киргизиңиз: ");
                        String friendCardNumber = scanner.nextLine();
                        sendToFriend(friendCardNumber);
                    }
                    case 4 -> {
                        System.out.print(Color.ANSI_CYAN + "Сиз каалаган сумманы киргизүү ");
                        int amount = scannerInt.nextInt();
                        withdrawMoney(amount);
                    }
                    case 5 -> {
                        System.out.print(Color.ANSI_CYAN + "Эсепке салгыңыз келген сумманы киргизиңиз: ");
                        int amount = scannerInt.nextInt();
                        Balance(amount);
                    }
                }
            } else {
                System.out.println(Color.ANSI_RED + "Пин коду же картанын номери туура эмес!!!");
                Code = false;
            }
        } while (Code);
    }

    @Override
    public void balance(String cardNumber, String pinCode) {
        UserAccount[] userAccounts = new UserAccount[1];
        for (UserAccount userAccount : accountDao.getUserAccounts()) {
            try {
                if (userAccount.getCardNumber().equals(cardNumber) && userAccount.getPinCode().equals(pinCode)) {
                    userAccounts[0] = userAccount;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                userAccounts[0] = null;
                System.out.println(Color.ANSI_RED + "Пин коду же картанын номери туура эмес!!!");
                Code = false;
            }
        }

        if (userAccounts[0] != null) {
            System.out.println(Color.ANSI_BLUE + "Сиздин баланс: " + userAccounts[0].getBalance());
        } else {
            System.out.println(Color.ANSI_RED + "Пин коду же картанын номери туура эмес!!!");
            Code = false;
        }
    }

    @Override
    public void deposit(String cardNumber, String pinCode) {
        UserAccount[] userAccounts = new UserAccount[1];
        for (UserAccount userAccount : accountDao.getUserAccounts()) {
            try {
                if (userAccount.getCardNumber().equals(cardNumber) && userAccount.getPinCode().equals(pinCode)) {
                    userAccounts[0] = userAccount;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                userAccounts[0] = null;
                System.out.println(Color.ANSI_RED + "Пин коду же картанын номери туура эмес!!!");
                Code = false;
            }
        }

        if (userAccounts[0] != null) {
            System.out.print("Толуктоо үчүн депозиттин суммасын киргизиңиз: ");
            int depositMoney = scannerInt.nextInt();
            userAccounts[0].setBalance(userAccounts[0].getBalance() + depositMoney);
            System.out.println(Color.ANSI_YELLOW + "Сиз Аманатты ийгиликтүү алдыңыз!" + Color.ANSI_BLUE +
                    "\nСиздин баланс : " + userAccounts[0].getBalance());
        } else {
            System.out.println(Color.ANSI_RED + "Пин коду же картанын номери туура эмес!!!");
            Code = false;
        }

    }

    @Override
    public void sendToFriend(String friendCardNumber) {
        UserAccount[] userAccounts = new UserAccount[1];
        UserAccount[] userAccounts1 = new UserAccount[1];
        System.out.print(Color.ANSI_CYAN + "Картанын номерин киргизиңиз:");
        String cardNumber = scanner.nextLine();
        System.out.print("Картанын пин кодун киргизиңиз: ");
        String pinCode = scanner.nextLine();
        for (UserAccount userAccount : accountDao.getUserAccounts()) {
            try {
                if (userAccount.getCardNumber().equals(friendCardNumber)) {
                    userAccounts1[0] = userAccount;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                userAccounts1[0] = null;
                System.out.println(Color.ANSI_RED + "Пин коду же картанын номери туура эмес!!!");
                Code = false;
            }
        }
        for (UserAccount userAccount : accountDao.getUserAccounts()) {
            try {
                if (userAccount.getCardNumber().equals(cardNumber) && userAccount.getPinCode().equals(pinCode)) {
                    userAccounts[0] = userAccount;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                userAccounts[0] = null;
                System.out.println(Color.ANSI_RED + "Пин коду же картанын номери туура эмес!!!");
                Code = false;
            }
        }
        if (userAccounts[0] != null) {
            System.out.print(Color.ANSI_CYAN + "Которуу үчүн сумманы киргизиңиз:");
            int amount = scannerInt.nextInt();
            if (userAccounts[0].getBalance() >= amount) {
                userAccounts[0].setBalance(userAccounts[0].getBalance() - amount);
                System.out.println(Color.ANSI_BLUE + "Сиздин баланс: " + userAccounts[0].getBalance());
            } else {
                System.out.println(Color.ANSI_RED + "Картада каражат жетишсиз!!!");
            }
            for (UserAccount userFriend : userAccounts1) {
                if (userFriend != null && userAccounts[0].getBalance() >= amount) {
                    userFriend.setBalance(userFriend.getBalance() + amount);
                    System.out.println(Color.ANSI_BLUE + "Досуңуздун балансы: " + userFriend.getBalance());
                }
            }
        }
    }

    @Override
    public void withdrawMoney(int amount) {
        UserAccount[] userAccounts = new UserAccount[1];
        System.out.print(Color.ANSI_CYAN + "Картанын номерин киргизиңиз:");
        String cardNumber = scanner.nextLine();
        System.out.print("Картанын пин кодун киргизиңиз: ");
        String pinCode = scanner.nextLine();
        for (UserAccount userAccount : accountDao.getUserAccounts()) {
            try {
                if (userAccount.getCardNumber().equals(cardNumber) && userAccount.getPinCode().equals(pinCode)) {
                    userAccounts[0] = userAccount;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                userAccounts[0] = null;
                System.out.println(Color.ANSI_RED + "Пин коду же картанын номери туура эмес!!!");
                Code = false;
            }
        }
        if (userAccounts[0] != null) {
            if (userAccounts[0].getBalance() >= amount) {
                if (amount % 1000 == 0) {
                    System.out.println(Color.ANSI_YELLOW + "Чыгаруу " + amount + "1000 рубль боюнча рубль - " + amount / 1000 + " (Штук) [1]");
                }
                if (amount % 500 == 0) {
                    System.out.println("Чыгаруу " + amount + "500 рубль боюнча рубль - " + amount / 500 + " (Штук) [2]");
                }
                if (amount % 200 == 0) {
                    System.out.println("Чыгаруу " + amount + "200 рубль боюнча рубль - " + amount / 200 + " (Штук) [3]");
                }
                if (amount % 100 == 0) {
                    System.out.println("Чыгаруу " + amount + "100 рубль боюнча рубль - " + amount / 100 + " (Штук) [4]");
                }
                if (amount % 50 == 0) {
                    System.out.println("Чыгаруу " + amount + "50 рубль боюнча рубль - " + amount / 50 + " (Штук) [5]");
                }
                if (amount % 10 == 0) {
                    System.out.println("Чыгаруу " + amount + "10 рубль боюнча рубль - " + amount / 10 + " (Штук) [6]");
                }
                System.out.print(Color.ANSI_CYAN + "Накталай акча алуу ыкмасын тандаңыз: ");
                int action = scannerInt.nextInt();
                switch (action) {
                    case 1 -> {
                        if (amount % 1000 == 0) {
                            userAccounts[0].setBalance(userAccounts[0].getBalance() - amount);
                            System.out.println(Color.ANSI_YELLOW + "Алынган " + action + " 1000 рубль боюнча рубль - " + amount / 1000 + " (Штук)");
                            System.out.println(Color.ANSI_BLUE + "Сиздин баланс: " + userAccounts[0].getBalance());
                        }
                    }
                    case 2 -> {
                        if (amount % 500 == 0) {
                            userAccounts[0].setBalance(userAccounts[0].getBalance() - amount);
                            System.out.println(Color.ANSI_YELLOW + "Алынган " + amount + " 500 рубль боюнча рубль - " + amount / 500 + " (Штук)");
                            System.out.println(Color.ANSI_BLUE + "Сиздин баланс: " + userAccounts[0].getBalance());
                        }
                    }
                    case 3 -> {
                        if (amount % 200 == 0) {
                            userAccounts[0].setBalance(userAccounts[0].getBalance() - amount);
                            System.out.println(Color.ANSI_YELLOW + "Алынган " + amount + " 200 рубль боюнча рубль - " + amount / 200 + " (Штук)");
                            System.out.println(Color.ANSI_BLUE + "Сиздин баланс: " + userAccounts[0].getBalance());
                        }
                    }
                    case 4 -> {
                        if (amount % 100 == 0) {
                            userAccounts[0].setBalance(userAccounts[0].getBalance() - amount);
                            System.out.println(Color.ANSI_YELLOW + "Алынган " + amount + " 100 рубль боюнча рубль - " + amount / 100 + " (Штук)");
                            System.out.println(Color.ANSI_BLUE + "Сиздин баланс: " + userAccounts[0].getBalance());
                        }
                    }
                    case 5 -> {
                        if (amount % 50 == 0) {
                            userAccounts[0].setBalance(userAccounts[0].getBalance() - amount);
                            System.out.println(Color.ANSI_YELLOW + "Алынган " + amount + " 50 рубль боюнча рубль - " + amount / 50 + " (Штук)");
                            System.out.println(Color.ANSI_BLUE + "Сиздин баланс: " + userAccounts[0].getBalance());
                        }
                    }
                    case 6 -> {
                        if (amount % 10 == 0) {
                            userAccounts[0].setBalance(userAccounts[0].getBalance() - amount);
                            System.out.println(Color.ANSI_YELLOW + "Алынган " + amount + " 10 рубль боюнча рубль - " + amount / 10 + " (Штук)");
                            System.out.println(Color.ANSI_BLUE + "Сиздин баланс: " + userAccounts[0].getBalance());
                        }
                    }
                }
            } else {
                System.out.println(Color.ANSI_RED + "Картада каражат жетишсиз!!!");
                Code = false;
            }
        } else {
            System.out.println(Color.ANSI_RED + "Пин коду же картанын номери туура эмес!!!");
            Code = false;
        }
    }

    @Override
    public void Balance(int amount) {
        UserAccount[] userAccounts = new UserAccount[1];
        System.out.print(Color.ANSI_CYAN + "Картанын номерин сактаңыз: ");
        String cardNumber = scanner.nextLine();
        System.out.print(Color.ANSI_CYAN + "Картанын пин кодун киргизиңиз: ");
        String pinCode = scanner.nextLine();
        for (UserAccount userAccount : accountDao.getUserAccounts()) {
            try {
                if (userAccount.getCardNumber().equals(cardNumber) && userAccount.getPinCode().equals(pinCode)) {
                    userAccounts[0] = userAccount;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                userAccounts[0] = null;
                System.out.println(Color.ANSI_RED + "Пин коду же картанын номери туура эмес!!!");
                Code = false;
            }
        }
        if (userAccounts[0] != null) {
            userAccounts[0].setBalance(userAccounts[0].getBalance() + amount);
            System.out.println(Color.ANSI_BLUE + "Сиздин баланс: " + userAccounts[0].getBalance());
        } else {
            System.out.println(Color.ANSI_RED + "Пин коду же картанын номери туура эмес!!!");
            Code = false;
        }
    }
}