package dao.CLI;

import com.google.gson.*;
import products.Book;
import products.Product;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductSerializerDeserializer implements JsonSerializer<Product>, JsonDeserializer<Product> {

    @Override
    public Product deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        var object = jsonElement.getAsJsonObject();
        var ID = object.get("ID").getAsInt();
        var name = object.get("name").getAsString();
        var releaseDate = new Date();
        var price = object.get("price").getAsDouble();
        var sales = object.get("sales").getAsInt();
        var stockBalance = object.get("stockBalance").getAsInt();
        try {
            var format = new SimpleDateFormat("dd/MM/yyyy");
            releaseDate = format.parse(object.get("releaseDate").getAsString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return switch (object.get("type").getAsString()) {
            case "Book" -> new Book.BookBuilder()
                .setProductID(ID)
                .setName(name)
                .setReleaseDate(releaseDate)
                .setAuthor(object.get("author").getAsString())
                .setPrice(price)
                .setSales(sales)
                .setStockBalance(stockBalance)
                .setPages(object.get("pages").getAsInt())
                .buildBook();
            default -> throw new IllegalStateException("Unexpected value: " + object.get("type").getAsString());
        };
    }

    @Override
    public JsonElement serialize(Product product, Type type, JsonSerializationContext jsonSerializationContext) {
        var object = new JsonObject();
        object.addProperty("ID", product.getProductID());
        object.addProperty("name", product.getName());
        object.addProperty("releaseDate", product.getReleaseDate().toString());
        object.addProperty("stockBalance", product.getStockBalance());
        object.addProperty("sales", product.getSales());
        object.addProperty("price", product.getPrice());
        switch (product.getClass().getName()) {
            case "products.Book" -> {
//                product = (Book) product;
                object.addProperty("author", ((Book) product).getAuthor());
                object.addProperty("pages", ((Book) product).getPages());
                object.addProperty("type", "Book");
            }
            case "products.Journal" -> System.out.println("jskjdskjdskjdskdjksdjksjjkdsskjjsdskjd");
//            case "products.Newspaper" -> (Book) product;
            default -> throw new IllegalStateException("Unexpected value: " + product.getClass().getName());
        }
        return object;
    }
}
