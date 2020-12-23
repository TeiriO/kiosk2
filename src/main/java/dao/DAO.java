package dao;

import products.Product;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DAO {
    List<Product> selectAll();

    Optional<Product> getProductByID(int productID);

    void update(Product product);

    void remove(int productID);

    void insert(Product product);

    default Date parseDate(String release_date) throws ParseException {
        SimpleDateFormat format1 = new SimpleDateFormat("MMM dd HH:mm:ss yyyy");
        try {
            return format1.parse(release_date);
        } catch (ParseException e) {
            // если дата записана в дефолтном формате java.util.Date
            var temp = release_date.split(" ");
            release_date = temp[1] + " " + temp[2] + " " + temp[3] + " " + temp[5];
            return format1.parse(release_date);
        }
    }
}
