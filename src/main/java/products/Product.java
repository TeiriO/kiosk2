package products;

//import lombok.BookBuilder;

import lombok.Data;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.util.Date;

@Data
//@Value
//@BookBuilder(toBuilder = true)
@FieldDefaults(makeFinal = true)
public abstract class Product {
    private int productID;
    private String name;
    private Date releaseDate;
    private int stockBalance;
    private int sales;
    private double price;
    @Setter @NonFinal
    protected String type = getClass().getName();

    public Product(int productID, String name, Date releaseDate, int stockBalance, int sales, double price) {
        this.productID = productID;
        this.name = name;
        this.releaseDate = releaseDate;
        this.stockBalance = stockBalance;
        this.sales = sales;
        this.price = price;
//        System.out.println("I am in the product");
    }

    public Product(int productID, int sales) {
        this.productID = productID;
        this.sales = sales;
        name = null;
        releaseDate = null;
        stockBalance = 0;
        price = 0;
    }

    public abstract Product editID(int newID);

    public abstract Product editName(String newName);

    public abstract Product editDate(Date newReleaseDate);

    public abstract Product editPrice(double price);

    public abstract Product editStockBalance(int stockBalance);

    public abstract Product editSales(int sales);

    @Override
    public String toString() {
        return "Product[" +
            "{productID=" + productID +
            "}, {name='" + name + '\'' +
            "}, {releaseDate=" + releaseDate +
            "}, {stockBalance=" + stockBalance +
            "}, {sales=" + sales +
            "}, {price=" + price +
            ']';
    }
}