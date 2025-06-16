import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record Transaction(String type,
                           double amount,
                           String ownerId,
                           String counterpartyId,
                           LocalDateTime timestamp) {

    // convenience constructor without timestamp
    public Transaction(String type, double amount, String ownerId, String counterpartyId) {
        this(type, amount, ownerId, counterpartyId, LocalDateTime.now());
    }

    @Override
    public String toString() {
        String time = timestamp.format(DateTimeFormatter.ofPattern("dd‑MMM‑uuuu HH:mm"));
        String cp = (counterpartyId == null ? "" : " (↔ " + counterpartyId + ")");
        return "%s | %-12s | ₹%-8.2f%s".formatted(time, type, amount, cp);
    }
}
