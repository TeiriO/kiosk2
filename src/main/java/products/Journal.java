package products;

import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Date;

//@BookBuilder(toBuilder = true)
@FieldDefaults(makeFinal = true)
public class Journal extends Product {
    @Getter
    private int issue;
    @Getter
    private int pages;

    public Journal(int productID, String name, Date releaseDate, int stockBalance, int sales, double price, int issue, int pages) {
        super(productID, name, releaseDate, stockBalance, sales, price);
        this.issue = issue;
        this.pages = pages;
        type = getClass().getName();
    }

    @Override
    public Product editID(int newID) {
        return new Journal.JournalBuilder()
            .setProductID(newID)
            .setIssue(issue)
            .setName(getName())
            .setPages(pages)
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
            .setPages(pages)
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
            .setPages(pages)
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
            .setPages(pages)
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
            .setPages(pages)
            .setPrice(getPrice())
            .setReleaseDate(getReleaseDate())
            .setSales(getSales())
            .setStockBalance(getStockBalance() + stockBalance)
            .buildJournal();
    }

    @Override
    public Product editSales(int sales) {
        return new Journal.JournalBuilder()
            .setProductID(getProductID())
            .setIssue(issue)
            .setName(getName())
            .setPages(pages)
            .setPrice(getPrice())
            .setReleaseDate(getReleaseDate())
            .setSales(getSales() + sales)
            .setStockBalance(getStockBalance())
            .buildJournal();
    }

    public Journal editIssue(int issue) {
        return new Journal.JournalBuilder()
            .setProductID(getProductID())
            .setIssue(issue)
            .setName(getName())
            .setPages(pages)
            .setPrice(getPrice())
            .setReleaseDate(getReleaseDate())
            .setSales(getSales())
            .setStockBalance(getStockBalance())
            .buildJournal();
    }

    public Journal editPages(int pages) {
        return new Journal.JournalBuilder()
            .setProductID(getProductID())
            .setIssue(issue)
            .setName(getName())
            .setPages(pages)
            .setPrice(getPrice())
            .setReleaseDate(getReleaseDate())
            .setSales(getSales())
            .setStockBalance(getStockBalance())
            .buildJournal();
    }

    public static class JournalBuilder {
        private int productID;
        private String name;
        private Date releaseDate;
        private int stockBalance;
        private int sales;
        private double price;
        private int issue;
        private int pages;

        public JournalBuilder setProductID(int productID) {
            this.productID = productID;
            return this;
        }

        public JournalBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public JournalBuilder setReleaseDate(Date date) {
            this.releaseDate = date;
            return this;
        }

        public JournalBuilder setStockBalance(int balance) {
            this.stockBalance = balance;
            return this;
        }

        public JournalBuilder setPrice(double price) {
            this.price = price;
            return this;
        }

        public JournalBuilder setIssue(int issue) {
            this.issue = issue;
            return this;
        }

        public JournalBuilder setSales(int sales) {
            this.sales = sales;
            return this;
        }

        public JournalBuilder setPages(int pages) {
            this.pages = pages;
            return this;
        }

        public Journal buildJournal() {
            return new Journal(productID, name, releaseDate, stockBalance, sales, price, issue, pages);
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
            "}, {pages=" + pages +
            ']';
    }
}
