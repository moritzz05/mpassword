import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Storage {

    private static final String FILE = "vault.txt";

    public void save(Map<String, Account> accounts, String mpw) {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(FILE))) {
            for (Account account : accounts.values()) {
                String line = account.getName() + ";" + account.getWebsite() + ";" + account.getUsername() + ";" + account.getPassword();
                String encrypted = Crypto.encrypt(line, mpw);
                printWriter.println(encrypted);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Account> load(String mpw) {
        Map<String, Account> accounts = new HashMap<>();
        File vault = new File(FILE);
        try (Scanner scanner = new Scanner(vault)) {
            while (scanner.hasNextLine()) {
                String encryptedLine = scanner.nextLine();
                String decrypted = Crypto.decrypt(encryptedLine, mpw);
                String[] parts = decrypted.split(";");
                accounts.put(parts[0], new Account(parts[0], parts[1], parts[2], parts[3]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

}
