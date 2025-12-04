package wallet.model;
import java.time.LocalDateTime;





public class Transaction {

    
    private final String otherParty;
    private final double amount;
    private final boolean isCredit;
    private final String type;
    private final LocalDateTime time;

    public Transaction(String otherParty, double amount, boolean isCredit, String type) {

        this.otherParty = otherParty;
        this.amount = amount;
        this.isCredit = isCredit;
        this.type = type;

        this.time = LocalDateTime.now();
    }

    public String getOtherParty() {
        return otherParty;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isCredit() {
        return isCredit;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public String toString() {

        String direction = isCredit ? "credit" : "debit";

        return otherParty + " " + direction + " " + amount + (type != null ? (" ["+type+"]") : "");
    }
}