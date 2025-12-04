package wallet.model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;




public class Wallet {

    private final String holderName;

    private double balance;

    private final List<Transaction> transactions;

    private int transactionCount;

    private FixedDeposit fixedDeposit;

    private final LocalDateTime createdAt;

    public Wallet(String holderName, double balance, LocalDateTime createdAt) {
        this.holderName = holderName;
        this.balance = balance;
        this.transactions = new ArrayList<>();
        this.transactionCount = 0;
        this.fixedDeposit = null;
        this.createdAt = createdAt;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }


    

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public void incTransactionCount() {
        this.transactionCount++;
    }

    public FixedDeposit getFixedDeposit() {
        return fixedDeposit;
    }

    public void setFixedDeposit(FixedDeposit fixedDeposit) {




        this.fixedDeposit = fixedDeposit;
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
}




