import java.util.*;

// ================= MAIN CLASS =================
public class StockTradingPlatform {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        TradingPlatform platform = new TradingPlatform();
        User user = new User("Namokar", 10000);

        int choice;

        do {

            System.out.println("\n===== Stock Trading Platform =====");

            System.out.println("1. Show Market Stocks");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. Show Portfolio");
            System.out.println("5. Show Transaction History");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    platform.showMarketStocks();
                    break;

                case 2:

                    System.out.print("Enter stock name: ");
                    String buyName = sc.next();

                    System.out.print("Enter quantity: ");
                    int buyQty = sc.nextInt();

                    Stock buyStock = platform.findStock(buyName);

                    if (buyStock != null) {

                        double total = buyStock.getPrice() * buyQty;

                        if (user.getBalance() >= total) {

                            user.deductBalance(total);
                            user.buyStock(buyName, buyQty);

                            System.out.println("Stock bought successfully.");

                        } else {
                            System.out.println("Not enough balance.");
                        }

                    } else {
                        System.out.println("Stock not found.");
                    }

                    break;

                case 3:

                    System.out.print("Enter stock name: ");
                    String sellName = sc.next();

                    System.out.print("Enter quantity: ");
                    int sellQty = sc.nextInt();

                    Stock sellStock = platform.findStock(sellName);

                    if (sellStock != null) {

                        double total = sellStock.getPrice() * sellQty;

                        user.sellStock(sellName, sellQty);
                        user.addBalance(total);

                        System.out.println("Stock sold successfully.");

                    } else {
                        System.out.println("Stock not found.");
                    }

                    break;

                case 4:
                    user.showPortfolio();
                    break;

                case 5:
                    user.showHistory();
                    break;

                case 6:
                    System.out.println("Thank you for using platform.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 6);

        sc.close();
    }
}


// ================= STOCK CLASS =================
class Stock {

    private String stockName;
    private double price;

    public Stock(String stockName, double price) {
        this.stockName = stockName;
        this.price = price;
    }

    public String getStockName() {
        return stockName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}


// ================= TRANSACTION CLASS =================
class Transaction {

    private String stockName;
    private int quantity;
    private String type;

    public Transaction(String stockName, int quantity, String type) {
        this.stockName = stockName;
        this.quantity = quantity;
        this.type = type;
    }

    public void showTransaction() {
        System.out.println(type + " - " + stockName + " - Qty: " + quantity);
    }
}


// ================= TRADING PLATFORM CLASS =================
class TradingPlatform {

    private ArrayList<Stock> marketStocks;

    public TradingPlatform() {

        marketStocks = new ArrayList<>();

        marketStocks.add(new Stock("TCS", 3500));
        marketStocks.add(new Stock("Infosys", 1500));
        marketStocks.add(new Stock("Reliance", 2800));
    }

    public void showMarketStocks() {

        System.out.println("\nMarket Stocks:");

        for (Stock s : marketStocks) {
            System.out.println(s.getStockName() + " : ₹" + s.getPrice());
        }
    }

    public Stock findStock(String name) {

        for (Stock s : marketStocks) {

            if (s.getStockName().equalsIgnoreCase(name)) {
                return s;
            }
        }

        return null;
    }
}


// ================= USER CLASS =================
class User {

    private String userName;
    private double balance;

    private HashMap<String, Integer> portfolio;
    private ArrayList<Transaction> history;

    public User(String userName, double balance) {

        this.userName = userName;
        this.balance = balance;

        portfolio = new HashMap<>();
        history = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public void addBalance(double amount) {
        balance += amount;
    }

    public void deductBalance(double amount) {
        balance -= amount;
    }

    public void buyStock(String stockName, int qty) {

        portfolio.put(stockName,
                portfolio.getOrDefault(stockName, 0) + qty);

        history.add(new Transaction(stockName, qty, "BUY"));
    }

    public void sellStock(String stockName, int qty) {

        if (portfolio.containsKey(stockName)) {

            int currentQty = portfolio.get(stockName);

            if (currentQty >= qty) {

                portfolio.put(stockName, currentQty - qty);
                history.add(new Transaction(stockName, qty, "SELL"));

            } else {
                System.out.println("Not enough stock to sell.");
            }

        } else {
            System.out.println("You don't own this stock.");
        }
    }

    public void showPortfolio() {

        System.out.println("\nYour Portfolio:");

        if (portfolio.isEmpty()) {
            System.out.println("No stocks owned.");
        } else {

            for (String stock : portfolio.keySet()) {
                System.out.println(stock + " : " + portfolio.get(stock));
            }
        }

        System.out.println("Balance: ₹" + balance);
    }

    public void showHistory() {

        System.out.println("\nTransaction History:");

        if (history.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {

            for (Transaction t : history) {
                t.showTransaction();
            }
        }
    }
}
