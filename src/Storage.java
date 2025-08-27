import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Storage {

    private static final String FILE = "vault.txt";

    public void save(Map<String, Account> accounts) {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(FILE))) {
            for (Account account : accounts.values()) {
                printWriter.println(account.getName() + ";" + account.getWebsite() + ";" + account.getUsername() + ";" + account.getPassword());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Account> load() {
        Map<String, Account> accounts = new HashMap<>();
        File vault = new File(FILE);
        try (Scanner scanner = new Scanner(vault)) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(";");
                accounts.put(parts[0], new Account(parts[0], parts[1], parts[2], parts[3]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public static void main(String[] args) {
        Account disney = new Account("Disney", "disney.com", "Tester", "dis123");
        Account spotify = new Account("Spotify", "spotify.com", "Tester", "spot123");
        Account valheim = new Account("Valheim", "valheim.com", "Tester", "val123");

        Storage storage = new Storage();
        Map<String, Account> accountMap = new HashMap<>();
        accountMap.put(disney.getName(), disney);
        accountMap.put(spotify.getName(), spotify);
        accountMap.put(valheim.getName(), valheim);

        storage.save(accountMap);

        Map<String, Account> accountMap2 = storage.load();
        System.out.println(accountMap2.toString());
    }

}
