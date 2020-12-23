package dao.db;

import dao.DAO;
import products.Newspaper;
import products.Product;

import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

public class NewspaperDAOImpl implements DAO {
    private final DBConnection connectionSupplier = DBConnection.getInstance();
    private final static String queries = "src/main/resources/newspaperQueries.properties";
    private final static Properties sql = new Properties();

    public NewspaperDAOImpl() {
        try {
            sql.load(new FileReader(queries));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> selectAll() {
        var newspapers = new ArrayList<Product>();
        try (var con = connectionSupplier.getConnection();
             var booksSet = con.createStatement().executeQuery(sql.getProperty("select"))) {

            while (booksSet.next())
                newspapers.add(parseNewspaper(booksSet));
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
        return newspapers;
    }

    private Product parseNewspaper(ResultSet newspaperSet) throws SQLException, ParseException {
        return new Newspaper.NewspaperBuilder()
            .setProductID(newspaperSet.getInt("newspaper_id"))
            .setName(newspaperSet.getString("newspaper_name"))
            .setPrice(newspaperSet.getDouble("price"))
            .setSales(newspaperSet.getInt("sales"))
            .setReleaseDate(parseDate(newspaperSet.getString("release_date")))
            .setIssue(newspaperSet.getInt("issue"))
            .setStockBalance(newspaperSet.getInt("stock_balance"))
            .buildNewspaper();
    }

    @Override
    public Optional<Product> getProductByID(int productID) {
        Product newspaper = null;
        try (var con = connectionSupplier.getConnection();
             var stm = con.prepareStatement(sql.getProperty("select_by_id"))) {

            stm.setInt(1, productID);
            var set = stm.executeQuery();
            set.next();
            newspaper = parseNewspaper(set);
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
        return newspaper == null ? Optional.empty() : Optional.of(newspaper);
    }

    @Override
    public void update(Product product) {
        var newspaper = (Newspaper) product;
        try (var connection = connectionSupplier.getConnection();
             var stm = connection.prepareStatement(sql.getProperty("update"))
        ) {
            stm.setString(1, product.getName());
            stm.setInt(2, newspaper.getIssue());
            stm.setString(3, String.valueOf(newspaper.getReleaseDate()));
            stm.setInt(4, newspaper.getStockBalance());
            stm.setInt(5, newspaper.getSales());
            stm.setDouble(6, newspaper.getPrice());
            stm.setInt(7, newspaper.getProductID());
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
        var newspaper = (Newspaper) product;
        try (var connection = connectionSupplier.getConnection();
             var stm = connection.prepareStatement(sql.getProperty("insert"))
        ) {
            stm.setInt(1, newspaper.getIssue());
            stm.setString(2, String.valueOf(newspaper.getReleaseDate()));
            stm.setInt(3, newspaper.getStockBalance());
            stm.setInt(4, newspaper.getSales());
            stm.setDouble(5, newspaper.getPrice());
            stm.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
