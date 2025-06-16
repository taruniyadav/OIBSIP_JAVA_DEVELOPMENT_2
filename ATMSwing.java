import javax.swing.*;
import java.awt.*;
import java.util.Map;

/* ------------- Swing wrapper that re‑uses your Bank / Account logic ------------- */
public class ATMSwing {

    private static final Bank BANK = Bank.defaultBank();   // uses your existing demo data
    private static Account current;                        // logged‑in user

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ATMSwing::showLoginWindow);
    }

    /* ---------------------- LOGIN WINDOW ---------------------- */
    private static void showLoginWindow() {
        JFrame f = new JFrame("ATM Login");
        JTextField idField = new JTextField(10);
        JPasswordField pinField = new JPasswordField(10);
        JButton loginBtn = new JButton("Login");

        loginBtn.addActionListener(e -> {
            String id  = idField.getText().trim();
            String pin = new String(pinField.getPassword()).trim();
            current = BANK.authenticate(id, pin);
            if (current != null) {
                f.dispose();
                showDashboard();
            } else {
                JOptionPane.showMessageDialog(f, "Invalid ID or PIN", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    panel.add(new JLabel("User ID:"));
    panel.add(idField);
    panel.add(new JLabel("PIN:"));
    panel.add(pinField);
    panel.add(new JLabel());
    panel.add(loginBtn);

    f.setContentPane(panel);
    f.setSize(600, 200);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setLocationRelativeTo(null);
    f.setVisible(true);
    }

    /* ---------------------- DASHBOARD ---------------------- */
    private static void showDashboard() {
        JFrame f = new JFrame("ATM – User " + current.getUserId());
        JButton histBtn   = new JButton("Transaction History");
        JButton depBtn    = new JButton("Deposit");
        JButton withBtn   = new JButton("Withdraw");
        JButton transBtn  = new JButton("Transfer");
        JButton logoutBtn = new JButton("Logout");

        histBtn.addActionListener(e ->
            JOptionPane.showMessageDialog(f, current.buildHistoryString(),
                    "History", JOptionPane.INFORMATION_MESSAGE));

        depBtn.addActionListener(e -> {
            String amt = JOptionPane.showInputDialog(f, "Amount to deposit:");
            try { current.deposit(Double.parseDouble(amt)); JOptionPane.showMessageDialog(f, "Deposited " + amt); }
            catch (Exception ex) { JOptionPane.showMessageDialog(f, "Invalid amount."); }
        });

        withBtn.addActionListener(e -> {
            String amt = JOptionPane.showInputDialog(f, "Amount to withdraw:");
            try {
                boolean ok = current.withdraw(Double.parseDouble(amt));
                JOptionPane.showMessageDialog(f, ok ? "Withdrawn " + amt : "Insufficient balance.");
            } catch (Exception ex) { JOptionPane.showMessageDialog(f, "Invalid amount."); }
        });

        transBtn.addActionListener(e -> {
            String toId = JOptionPane.showInputDialog(f, "Transfer to user ID:");
            String amt  = JOptionPane.showInputDialog(f, "Amount:");
            try {
                Account to = BANK.getAccount(toId.trim());
                boolean ok = to != null && current.transferTo(to, Double.parseDouble(amt));
                JOptionPane.showMessageDialog(f, ok ? "Transferred " + amt : "Transfer failed.");
            } catch (Exception ex) { JOptionPane.showMessageDialog(f, "Invalid input."); }
        });

        logoutBtn.addActionListener(e -> { current = null; f.dispose(); showLoginWindow(); });

      JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
    panel.add(histBtn);
    panel.add(depBtn);
    panel.add(withBtn);
    panel.add(transBtn);
    panel.add(logoutBtn);

    f.setContentPane(panel);
    f.setSize(600, 700);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setLocationRelativeTo(null);
    f.setVisible(true);
    }
}

