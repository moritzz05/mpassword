import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Map;

public class UI extends JFrame {

    private final DefaultTableModel tableModel;
    private final JTable table;
    private final Manager manager;

    public UI(Manager manager) {
        super("mpassword");
        this.manager = manager;

        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        String[] columns = {"Account", "Website", "User", "Password"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        JButton add = new JButton("Add");
        JButton delete = new JButton("Delete");
        JButton save = new JButton("Save");

        add.addActionListener(this::onAdd);
        delete.addActionListener(this::onDelete);
        save.addActionListener(this::onSave);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(add);
        buttonPanel.add(delete);
        buttonPanel.add(save);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadAccounts();
    }

    private void loadAccounts() {
        tableModel.setRowCount(0);
        Map<String, Account> accounts = manager.getAccounts();
        for (Account account : accounts.values()) {
            tableModel.addRow(new Object[]{
                    account.getName(), account.getWebsite(), account.getUsername(), account.getPassword()
            });
        }
    }

    private void onAdd(ActionEvent e) {
        JTextField accountField = new JTextField();
        JTextField websiteField = new JTextField();
        JTextField userField = new JTextField();
        JTextField passwordField = new JTextField();

        Object[] message = {
                "Account:", accountField,
                "Website:", websiteField,
                "User:", userField,
                "Password", passwordField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add new entry",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            Account account = new Account(
                    accountField.getText(),
                    websiteField.getText(),
                    userField.getText(),
                    passwordField.getText()
            );
            manager.add(account);
            tableModel.addRow(new Object[]{
                    account.getName(),
                    account.getWebsite(),
                    account.getUsername(),
                    account.getPassword()
            });
            manager.getDb().save(manager.getAccounts(), manager.getMasterPassword());
        }
    }

    private void onDelete(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String name = (String) tableModel.getValueAt(row, 0);
            manager.delete(name);
            tableModel.removeRow(row);
            manager.getDb().save(manager.getAccounts(), manager.getMasterPassword());
        } else {
            JOptionPane.showMessageDialog(this, "Choose entry first!");
        }
    }

    private void onSave(ActionEvent e) {
        manager.getDb().save(manager.getAccounts(), manager.getMasterPassword());
        JOptionPane.showMessageDialog(this, "Saved!");
    }

}
