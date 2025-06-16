import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private final String userId;
    private final String pin;
    private double balance;
    private final List<Transaction> history = new ArrayList<>();

    public Account(String userId, String pin, double openingBalance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = openingBalance;
        history.add(new Transaction("OPEN", openingBalance, userId, null));
    }

    /* ---- getters ---- */
    public String getUserId() { return userId; }
    public boolean checkPin(String p) { return pin.equals(p); }

    /* ---- operations ---- */
    public void deposit(double amount) {
        balance += amount;
        history.add(new Transaction("DEPOSIT", amount, userId, null));
    }

    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) return false;
        balance -= amount;
        history.add(new Transaction("WITHDRAW", amount, userId, null));
        return true;
    }

    public boolean transferTo(Account target, double amount) {
        if (target == null || target == this || amount <= 0 || amount > balance) return false;
        balance -= amount;
        target.balance += amount;

        history.add(new Transaction("TRANSFER‑OUT", amount, userId, target.userId));
        target.history.add(new Transaction("TRANSFER‑IN", amount, target.userId, userId));
        return true;
    }

    public void printHistory() {
        System.out.println("\n Transaction History for " + userId + " -");
        for (Transaction t : history) {
            System.out.println(t);
        }
        System.out.printf("Current Balance: ₹%.2f%n%n", balance);
    }
/* ---- NEW: build history for GUI ---- */
public String buildHistoryString() {
    StringBuilder sb = new StringBuilder();
    for (Transaction t : history) sb.append(t).append("\n");
    sb.append(String.format("Current Balance: ₹%.2f", balance));
    return sb.toString();
}

}
