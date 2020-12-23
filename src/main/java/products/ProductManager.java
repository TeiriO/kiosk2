package products;

import dao.DAO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ProductManager {
    @Getter@Setter
    private DAO dao;
    @Getter
    private double income;

    public ProductManager(DAO dao) {
        this.dao = dao;
        income = 0.0;
    }

    public void saleProduct(int productID) {
        var oldProduct = dao.getProductByID(productID).orElseThrow();
        if (oldProduct.getStockBalance() == 0) {
            dao.remove(productID);
        } else {
            var newProduct = oldProduct
                .editSales(oldProduct.getSales() + 1)
                .editStockBalance(oldProduct.getStockBalance() - 1);
            dao.remove(oldProduct.getProductID());
            dao.update(newProduct);
            income += newProduct.getPrice();
        }
    }

    public void saleProduct(int productID, int count) {
        var oldProduct = dao.getProductByID(productID).orElseThrow();
        if (oldProduct.getStockBalance() - count >= 0) {
            var newProduct = oldProduct
                .editSales(oldProduct.getSales() + count)
                .editStockBalance(oldProduct.getStockBalance() - count);
            dao.remove(oldProduct.getProductID());
            dao.update(newProduct);
            income += newProduct.getPrice() * count;
        }
    }

    public void stockReplenishment(Product product, int count) {
        if (dao.getProductByID(product.getProductID()).isPresent()) {
            var updatedProduct = product.editStockBalance(count);
            dao.remove(product.getProductID());
            dao.update(updatedProduct);
        }
    }

    public String getInfo(int productID) {
        var product = dao.getProductByID(productID).orElseThrow(() -> new RuntimeException("Product with " + productID + " doesn't exist!"));
        return product.toString();
    }

    public List<Product> getAllProducts() {
        return dao.selectAll();
    }

    public void update(Product product) {
        dao.update(product);
    }

    public void addProduct(Product product) {
        dao.insert(product);
    }

    public Product getProductByID(int id) {
        return dao.getProductByID(id).get();
    }
}