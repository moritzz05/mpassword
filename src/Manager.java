import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Manager {

    private Map<String, Account> accounts;
    private String masterPassword;
    private boolean unlocked;

    private Storage db = new Storage();

    public Manager(String masterPassword) {
        this.accounts = new HashMap<>();
        this.masterPassword = masterPassword;
        this.unlocked = false;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public void unlock(String attempt) {
        if (!attempt.equals(masterPassword)) {
            System.out.println("Wrong password!");
            return;
        } else {
            this.setUnlocked(true);
        }
    }

    public void lock() {
        this.setUnlocked(false);
    }

    public void list() {
        if (!unlocked) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        accounts.values().stream().sorted(Comparator.comparing(Account::getName)).forEach(stringBuilder::append);
        System.out.println(stringBuilder.toString());
    }

    public void add(Account account) {
        if (!unlocked) {
            return;
        }
        if (accounts.containsKey(account.getName())) {
            System.out.println("Already saved!");
            return;
        }
        accounts.put(account.getName(), account);
    }

    public void delete(String name) {
        if (!unlocked) {
            return;
        }
        if (!accounts.containsKey(name)) {
            System.out.println("Account cant be deleted!");
            return;
        }
        accounts.remove(name);
    }

    public Account get(String name) {
        if (!unlocked) {
            return null;
        }
        if (!accounts.containsKey(name)) {
            System.out.println("Account not found!");
            return null;
        } else {
            return accounts.get(name);
        }
    }

    public static void main(String[] args) {
//        Account disney = new Account("Disney", "disney.com", "Tester", "dis123");
//        Account spotify = new Account("Spotify", "spotify.com", "Tester", "spot123");
//        Account valheim = new Account("Valheim", "valheim.com", "Tester", "val123");

        Manager mpassword = new Manager("hehehe");
        Scanner scanner = new Scanner(System.in);

        while (!mpassword.unlocked) {
            System.out.println("Masterpassword please!");
            mpassword.unlock(scanner.nextLine());
        }

        boolean running = true;

        while (running) {
            mpassword.accounts = mpassword.db.load();

            System.out.println("Welcome to mpassword!");
            System.out.println("(1) See all accounts");
            System.out.println("(2) Add account");
            System.out.println("(3) Get specific account");
            System.out.println("(4) Delete specific account");
            System.out.println("(5) STOP");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    mpassword.list();
                    break;
                case "2":
                    String name = scanner.nextLine();
                    String website = scanner.nextLine();
                    String username = scanner.nextLine();
                    String password = scanner.nextLine();
                    Account newAccount = new Account(name, website, username, password);
                    mpassword.add(newAccount);
                    mpassword.db.save(mpassword.accounts);
                    break;
                case "3":
                    String toGet = scanner.nextLine();
                    System.out.println(mpassword.get(toGet));
                    break;
                case "4":
                    String toDel = scanner.nextLine();
                    mpassword.delete(toDel);
                    mpassword.db.save(mpassword.accounts);
                    break;
                case "5":
                    mpassword.db.save(mpassword.accounts);
                    return;
                default:
                    System.out.println("Invalid number");
                    mpassword.db.save(mpassword.accounts);
                    break;
            }
        }
    }
}
