package products;

//import lombok.BookBuilder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

//@BookBuilder(toBuilder = true)
public class Newspaper extends Product {
    @Getter@Setter
    private int issue; // номер газеты

    public Newspaper(int productID, String name, Date releaseDate, int stockBalance, int sales, double price, int issue) {
        super(productID, name, releaseDate, stockBalance, sales, price);
        this.issue = issue;
    }

    @Override
    public Product editID(int newID) {
        return new Journal.JournalBuilder()
            .setProductID(newID)
            .setIssue(issue)
            .setName(getName())
            .setPrice(getPrice())
            .setReleaseDate(getReleaseDate())
            .setSales(getSales())
            .setStockBalance(getStockBalance())
            .buildJournal();
    }

    @Override
    public Product editName(String newName) {
        return new Journal.JournalBuilder()
            .setProductID(getProductID())
            .setIssue(issue)
            .setName(newName)
            .setPrice(getPrice())
            .setReleaseDate(getReleaseDate())
            .setSales(getSales())
            .setStockBalance(getStockBalance())
            .buildJournal();
    }

    @Override
    public Product editDate(Date newReleaseDate) {
        return new Journal.JournalBuilder()
            .setProductID(getProductID())
            .setIssue(issue)
            .setName(getName())
            .setPrice(getPrice())
            .setReleaseDate(newReleaseDate)
            .setSales(getSales())
            .setStockBalance(getStockBalance())
            .buildJournal();
    }

    @Override
    public Product editPrice(double price) {
        return new Journal.JournalBuilder()
            .setProductID(getProductID())
            .setIssue(issue)
            .setName(getName())
            .setPrice(price)
            .setReleaseDate(getReleaseDate())
            .setSales(getSales())
            .setStockBalance(getStockBalance())
            .buildJournal();
    }

    @Override
    public Product editStockBalance(int stockBalance) {
        return new Journal.JournalBuilder()
            .setProductID(getProductID())
            .setIssue(issue)
            .setName(getName())
            .setPrice(getPrice())
            .setReleaseDate(getReleaseDate())
            .setSales(getSales())
            .setStockBalance(getStockBalance() + stockBalance)
            .buildJournal();
    }

    @Override
    public Product editSales(int sales) {
        return new NewspaperBuilder()
            .setProductID(getProductID())
            .setIssue(issue)
            .setName(getName())
            .setPrice(getPrice())
            .setReleaseDate(getReleaseDate())
            .setSales(getSales() + sales)
            .setStockBalance(getStockBalance())
            .buildNewspaper();
    }

    public Newspaper editIssue(int issue) {
        return new NewspaperBuilder()
            .setProductID(getProductID())
            .setIssue(issue)
            .setName(getName())
            .setPrice(getPrice())
            .setReleaseDate(getReleaseDate())
            .setSales(getSales())
            .setStockBalance(getStockBalance())
            .buildNewspaper();
    }

    public static class NewspaperBuilder {
        private int productID;
        private String name;
        private Date releaseDate;
        private int stockBalance;
        private int sales;
        private double price;
        private int issue;

        public NewspaperBuilder setProductID(int productID) {
            this.productID = productID;
            return this;
        }

        public NewspaperBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public NewspaperBuilder setReleaseDate(Date date) {
            this.releaseDate = date;
            return this;
        }

        public NewspaperBuilder setStockBalance(int balance) {
            this.stockBalance = balance;
            return this;
        }

        public NewspaperBuilder setPrice(double price) {
            this.price = price;
            return this;
        }

        public NewspaperBuilder setIssue(int issue) {
            this.issue = issue;
            return this;
        }

        public NewspaperBuilder setSales(int sales) {
            this.sales = sales;
            return this;
        }

        public Newspaper buildNewspaper() {
            return new Newspaper(productID, name, releaseDate, stockBalance, sales, price, issue);
        }
    }

    @Override
    public String toString() {
        return "Journal[" +
            "{ID=" + getProductID() +
            "}, {name='" + getName() + '\'' +
            "}, {releaseDate=" + getReleaseDate().toString() +
            "}, {stockBalance=" + getStockBalance() +
            "}, {sales=" + getSales() +
            "}, {price=" + getPrice() +
            "}, {issue=" + issue +
            "}" +
            ']';
    }
}
