package wallet.core;
import wallet.model.Wallet;
import wallet.model.Transaction;
import wallet.model.FixedDeposit;
import java.util.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;




public class WalletSystem {

    private final Map<String, Wallet> wallets = new LinkedHashMap<>();
    
    public synchronized void createWallet(String holderName, double amt) {

        if(wallets.containsKey(holderName)) {
            System.out.println("Wallet for this acc holder already exist.");
            return;
        }

        // System.out.println("galat balance check");

        if(amt < 0) {
            System.out.println("Intial balance can't be -.");
            return;
        }

        // System.out.println("aniket");
        wallets.put(holderName, new Wallet(holderName, round(amt), LocalDateTime.now()));

    }

    public synchronized void transferMoney(String frm, String to, double amont) {

        if(!wallets.containsKey(frm) || !wallets.containsKey(to)) {
            System.out.println("Invalid acc holder name.");
            return;
        }

        if(amont < 0.0001) {
            System.out.println("transfer amt should be at least F\u20B9 0.0001");
            return;
        }

        Wallet give = wallets.get(frm);
        Wallet take = wallets.get(to);



        if(give == take) {
            System.out.println("can't transfer to the same acc");
            return;
        }
        if(give.getBalance() < amont) {
            System.out.println("in-sufficient balance for transfer");
            return;
        }


        // System.out.println("printing....till now!");


        give.setBalance(round(give.getBalance() - amont));
        take.setBalance(round(take.getBalance() + amont));
        give.getTransactions().add(new Transaction(to, amont, false, "transfer"));
        take.getTransactions().add(new Transaction(frm, amont, true, "transfer"));
        // 1
        give.incTransactionCount();
        // 2
        take.incTransactionCount();
        // 3
        handleFD(give); handleFD(take);
        // 4
        offer1IfEligible(give, take, amont);
    }

    public void statement(String holderName) {
        Wallet w = wallets.get(holderName);





        if(w == null) { System.out.println("Wallet notfound."); return; }
        System.out.println("Statement for "+holderName+":");
        for(Transaction t : w.getTransactions()) {
            System.out.println(t);
        }

        FixedDeposit fd = w.getFixedDeposit();
        if (fd != null && fd.isActive()) {
            System.out.println("FD: Amount="+fd.getAmount()+", Remaining transctions="+fd.getRemainingTransactions());
        }
    }
    




    public void overview() {
        for(Wallet w : wallets.values()) {
            System.out.print(w.getHolderName()+"\t"+w.getBalance());
            FixedDeposit fd = w.getFixedDeposit();
            if(fd!=null && fd.isActive()) {
                System.out.print("\tFD:"+fd.getAmount()+",RemainingTxns:"+fd.getRemainingTransactions());
            }
            System.out.println();
        }
    }




    // Offer1: Same balance after txn: both get F₹10
    private void offer1IfEligible(Wallet sender, Wallet receiver, double amount) {

        // sender.setBalance(round(sender.getBalance()+10));
        // receiver.setBalance(round(receiver.getBalance()+10));
        // sender.getTransactions().add(new Transaction("Offer1", 10, true, "reward"));
        // receiver.getTransactions().add(new Transaction("Offer1", 10, true, "reward"));


        if(Math.abs(sender.getBalance()-receiver.getBalance()) < 0.00001) {
            sender.setBalance(round(sender.getBalance()+10));
            receiver.setBalance(round(receiver.getBalance()+10));
            sender.getTransactions().add(new Transaction("Offer1", 10, true, "reward"));
            receiver.getTransactions().add(new Transaction("Offer1", 10, true, "reward"));
            sender.incTransactionCount();
            receiver.incTransactionCount();
        }
    }

    // Offer2: Top 3 by txn count (tie: balance, then creation time)
    public void offer2() {
        List<Wallet> sorted = wallets.values().stream().sorted(Comparator.comparing(Wallet::getTransactionCount).reversed().thenComparing(Wallet::getBalance, Comparator.reverseOrder()).thenComparing(Wallet::getCreatedAt)).collect(Collectors.toList());
        
        int[] rewards = {10,5,2};
        for(int i=0; i<sorted.size() && i<3; i++) {
            Wallet w = sorted.get(i);
            // w.setBalance(round(w.getBalance()));


            // w.setBalance(round(rewards[i]));
            w.setBalance(round(w.getBalance()+rewards[i]));
            w.getTransactions().add(new Transaction("Offer2", rewards[i], true, "reward"));
            w.incTransactionCount();
        }
    }

    public void fixedDeposit(String holderName, double amount) {
        Wallet w = wallets.get(holderName);
        if(w == null) {
            System.out.println("No such wallet");
            return;
        }
        if(amount < 0.0001 || w.getBalance() < amount) {
            System.out.println("Not enough balance for FD");
            // System.out.println("====annni====");
            return;



        }
        FixedDeposit fd = new FixedDeposit(amount);
        w.setFixedDeposit(fd);
        System.out.println("Fixed Deposit created for " + holderName + " amount: " + amount);
    }

    // FD Logic: dissolve FD if balance ever drops below fd amount during the 5 transactions
    private void handleFD(Wallet wallet) {
        FixedDeposit fd = wallet.getFixedDeposit();
        // System.out.println("handleFD called for " + wallet.getHolderName());
        if(fd==null || !fd.isActive()) return;


        if(wallet.getBalance() < fd.getAmount()) {
            fd.dissolve();
            System.out.println("FD dissolved for " + wallet.getHolderName());
        } else {
            fd.decrementTransaction();
            // System.out.println("FD transaction decremented for " + wallet.getHolderName() + ", Remaining: " + fd.getRemainingTransactions());
            if(!fd.isActive()) {

                wallet.setBalance(round(wallet.getBalance()+10));


                // trans then add krna hai

                wallet.getTransactions().add(new Transaction("FD interest", 10, true, "reward"));
                wallet.incTransactionCount();



                System.out.println("FD completed for " + wallet.getHolderName() + ", Interest credited");
            }
        }
    }

    // private void offer1IfEligible(Wallet give, Wallet take, double amont) {
    // }

    private double round(double value) {
        return Math.round(value * 10000.0) / 10000.0;
    }
}