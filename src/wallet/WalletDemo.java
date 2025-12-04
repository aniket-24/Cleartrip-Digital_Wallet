package wallet;
import wallet.core.WalletSystem;


// 1. Compile all .java files
// javac -d out -sourcepath src src/wallet/WalletDemo.java

// 2. Run the WalletDemo class
// java -cp out wallet.WalletDemo


public class WalletDemo {
    public static void main(String[] args) {

        WalletSystem ws = new WalletSystem();

        ws.createWallet("harry", 100);

        ws.createWallet("ron", 95.7);

        ws.createWallet("hermione", 104);

        ws.createWallet("albus", 200);

        ws.createWallet("draco", 500);



        System.out.println("xxxxxx over-view xxxxx");

        ws.overview();
        ws.transferMoney("albus", "draco", 30);
        ws.transferMoney("hermione", "harry", 2);

        ws.transferMoney("albus", "ron", 5);

        System.out.println("xxx over-view xxx");
        ws.overview();




        System.out.println("===== stat harry ====");
        ws.statement("harry");

        System.out.println("==== stat albus =====");
        ws.statement("albus");

        System.out.println(">>>>> offer2 >>>>>");
        ws.offer2();
        ws.overview();


        // FD test
        ws.fixedDeposit("harry", 100);

        ws.transferMoney("ron", "harry", 2);
        ws.transferMoney("draco", "harry", 1);
        ws.transferMoney("albus", "harry", 1);


        ws.transferMoney("hermione", "harry", 1);
        ws.transferMoney("harry", "draco", 1);

        System.out.println("xxxx over-view xxx");
        ws.overview();
        ws.statement("harry");




        // Edge case
        ws.createWallet("harry", 50);
        ws.createWallet("ginny", -1);

        ws.transferMoney("hermione", "ginny", 10);
        ws.transferMoney("draco", "albus", 9999);
        ws.transferMoney("harry", "harry", 5);





        ws.fixedDeposit("nonexistent", 10);
        ws.fixedDeposit("harry", 10000);




    }
}


// @Aniket Kumar
// @aniketskr@gmail.com 
// @9315113398