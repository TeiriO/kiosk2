package dao.db;

import dao.DAO;
import products.Book;
import products.Product;

import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class BookDAOImpl implements DAO {
    private final static String queries = "src/main/resources/bookQueries.properties";
    private final static Properties sql = new Properties();

    public BookDAOImpl() {
        try {
            sql.load(new FileReader(queries));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final DBConnection connectionSupplier = DBConnection.getInstance();

    @Override
    public List<Product> selectAll() {
        var books = new ArrayList<Product>();
        try (var con = connectionSupplier.getConnection();
             var booksSet = con.createStatement().executeQuery(sql.getProperty("select_books"))) {

            while (booksSet.next())
                books.add(parseBook(booksSet));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return books;
    }

    @Override
    public Optional<Product> getProductByID(int productID) {
        Product book = null;
        try (var con = connectionSupplier.getConnection();
             var stm = con.prepareStatement(sql.getProperty("select_book_by_id"))) {

            stm.setInt(1, productID);
            var bookSet = stm.executeQuery();
            bookSet.next();
            book = parseBook(bookSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return book == null ? Optional.empty() : Optional.of(book);
    }

    private Product parseBook(ResultSet bookSet) throws SQLException {
        return new Book.BookBuilder()
            .setProductID(bookSet.getInt("book_id"))
            .setName(bookSet.getString("book_name"))
            .setAuthor(bookSet.getString("author"))
            .setPrice(bookSet.getDouble("price"))
            .setPublisher(bookSet.getString("publisher"))
            .setSales(bookSet.getInt("sales"))
            .setStockBalance(bookSet.getInt("stock_balance"))
            .setPages(bookSet.getInt("pages"))
            .buildBook();
    }

    @Override
    public void update(Product product) {
        var book = (Book) product;
        try (var connection = connectionSupplier.getConnection();
             var stm = connection.prepareStatement(sql.getProperty("update"))
        ) {
            stm.setString(1, product.getName());
            stm.setString(2, book.getAuthor());
            stm.setString(3, book.getPublisher());
            stm.setInt(4, book.getPages());
            stm.setInt(5, book.getStockBalance());
            stm.setInt(6, book.getSales());
            stm.setDouble(7, book.getPrice());
            stm.setInt(8, book.getProductID());
            stm.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void remove(int productID) {
        try (var connection = connectionSupplier.getConnection();
             var stm = connection.prepareStatement(sql.getProperty("delete"))
        ) {
            stm.setInt(1, productID);
            stm.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void insert(Product product) {
        var book = (Book) product;
        try (var connection = connectionSupplier.getConnection();
             var stm = connection.prepareStatement(sql.getProperty("insert"))
        ) {
            stm.setString(1, product.getName());
            stm.setString(2, book.getAuthor());
            stm.setString(3, book.getPublisher());
            stm.setInt(4, book.getPages());
            stm.setInt(5, book.getStockBalance());
            stm.setInt(6, book.getSales());
            stm.setDouble(7, book.getPrice());
            stm.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
