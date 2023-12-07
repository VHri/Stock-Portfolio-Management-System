public class Tester {
    public static void testCustomerBasic(){
        
        int totalCount = 100;
        int purchaseCount = 10;
        double price = 25.5;
        double balance = 9999999.0;


        StockMarket stmkt = new StockMarket();
        Stock s = new Stock("Test", "TT", price, totalCount);
        stmkt.addStock(s);
        Customer c = new Customer("TestUsr", "password", stmkt);
        c.setBalance(balance);
        assert c.getBalance() == balance;
        System.out.println("User balance is " + c.getBalance());
        System.out.println("Total value is " + c.computeTotalValue());


        c.buyStock(s, purchaseCount);
        assert c.getBalance() == balance - (double)purchaseCount*price;
        assert c.computeTotalValue() == (double)purchaseCount*price;
        System.out.println("User purchased stock ");
        System.out.println("User balance is " + c.getBalance());
        System.out.println("Total value is " + c.computeTotalValue());
    }
}
