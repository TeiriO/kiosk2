package dao.db;

import dao.DAO;
import products.Journal;
import products.Product;

import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

public class JournalDAOImpl implements DAO {
    private final DBConnection connectionSupplier = DBConnection.getInstance();
    private final static String queries = "src/main/resources/journalQueries.properties";
    private final static Properties sql = new Properties();

    public JournalDAOImpl() {
        try {
            sql.load(new FileReader(queries));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> selectAll() {
        var journals = new ArrayList<Product>();
        try (var con = connectionSupplier.getConnection();
             var set = con.createStatement().executeQuery(sql.getProperty("select"))) {

            while (set.next())
                journals.add(parseJournal(set));
//            journals.add(parseJournal(set));
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
        return journals;
    }

    private Product parseJournal(ResultSet journalSet) throws SQLException, ParseException {
        return new Journal.JournalBuilder()
            .setProductID(journalSet.getInt("journal_id"))
            .setName(journalSet.getString("journal_name"))
            .setPrice(journalSet.getDouble("price"))
            .setSales(journalSet.getInt("sales"))
            .setReleaseDate(parseDate(journalSet.getString("release_date")))
            .setIssue(journalSet.getInt("issue"))
            .setStockBalance(journalSet.getInt("stock_balance"))
            .setPages(journalSet.getInt("pages"))
            .buildJournal();
    }

    @Override
    public Optional<Product> getProductByID(int productID) {
        Product journal = null;
        try (var con = connectionSupplier.getConnection();
             var stm = con.prepareStatement(sql.getProperty("select_by_id"))) {

            stm.setInt(1, productID);
            var journalSet = stm.executeQuery();
            journalSet.next();
            journal = parseJournal(journalSet);
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
        return journal == null ? Optional.empty() : Optional.of(journal);
    }

    @Override
    public void update(Product product) {
        var journal = (Journal) product;
        try (var connection = connectionSupplier.getConnection();
             var stm = connection.prepareStatement(sql.getProperty("update"))
        ) {
            stm.setString(1, product.getName());
            stm.setInt(2, journal.getIssue());
            stm.setString(3, String.valueOf(journal.getReleaseDate()));
            stm.setInt(4, journal.getPages());
            stm.setInt(5, journal.getStockBalance());
            stm.setInt(6, journal.getSales());
            stm.setDouble(7, journal.getPrice());
            stm.setInt(8, journal.getProductID());
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
        var journal = (Journal) product;
        try (var connection = connectionSupplier.getConnection();
             var stm = connection.prepareStatement(sql.getProperty("insert"))
        ) {
            stm.setString(1, product.getName());
            stm.setInt(2, journal.getIssue());
            stm.setString(3, String.valueOf(journal.getReleaseDate()));
            stm.setInt(4, journal.getPages());
            stm.setInt(5, journal.getStockBalance());
            stm.setInt(6, journal.getSales());
            stm.setDouble(7, journal.getPrice());
            stm.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
