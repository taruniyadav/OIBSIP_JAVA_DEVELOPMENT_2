import java.util.HashMap;
import java.util.Map;

public class Bank {
    private final Map<String, Account> accounts = new HashMap<>();

    /* ------- factory method with demo data ------- */
    public static Bank defaultBank() {
        Bank b = new Bank();
        b.addAccount(new Account("1001", "1111", 5000));
        b.addAccount(new Account("1002", "2222", 3000));
        return b;
    }

    /* ------- methods ------- */
    public void addAccount(Account acc) { accounts.put(acc.getUserId(), acc); }

    public Account authenticate(String id, String pin) {
        Account acc = accounts.get(id);
        return (acc != null && acc.checkPin(pin)) ? acc : null;
    }

    public Account getAccount(String id) { return accounts.get(id); }
}
