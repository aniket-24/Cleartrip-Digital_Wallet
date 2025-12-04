package wallet.model;





public class FixedDeposit {
    
    private final double amount;
    private int remainingTransactions;
    private boolean isActive;

    public FixedDeposit(double amount) {

        this.amount = amount;
        this.remainingTransactions = 5;
        this.isActive = true;
    }

    public double getAmount() {
        return amount;
    }

    public int getRemainingTransactions() {
        return remainingTransactions;
    }

    public boolean isActive() {
        return isActive;
    }

    public void decrementTransaction() {
        if(remainingTransactions > 0) {
            remainingTransactions--;
        }
        if(remainingTransactions == 0) {
            isActive = false;
        }
    }

    public void dissolve() {
        isActive = false;
    }

    public void reset() {
        remainingTransactions = 5;
        isActive = true;
    }
}