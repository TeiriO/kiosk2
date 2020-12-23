package dao.CLI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import dao.DAO;
import lombok.Getter;
import products.Book;
import products.Journal;
import products.Newspaper;
import products.Product;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConsoleDAO implements DAO {
    private final Path PATH = Paths.get("src/main/resources/db.json");
    RuntimeTypeAdapterFactory<Product> adapter = RuntimeTypeAdapterFactory.of(Product.class, "type")
        .registerSubtype(Book.class, Book.class.getName())
        .registerSubtype(Journal.class, Journal.class.getName())
        .registerSubtype(Newspaper.class, Newspaper.class.getName());

    private final Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapterFactory(adapter)
        .create();

    @Getter
    private final List<Product> products = new ArrayList<>();

    public ConsoleDAO() {
        products.addAll(selectAll());
    }

    @Override
    public List<Product> selectAll() {
        var data = "";
        try {
            Stream<String> lines = Files.lines(PATH);
            data = lines.collect(Collectors.joining("\n"));
            lines.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        var temp = gson.fromJson(data, Product[].class);
        Arrays.stream(temp).forEach(x -> x.setType(x.getClass().getName()));
        return Arrays.stream(temp).collect(Collectors.toList());
    }

    @Override
    public Optional<Product> getProductByID(int productID) {
        return products.stream()
            .filter(x -> x.getProductID() == productID)
            .findAny();
    }

    @Override
    public void update(Product product) {
        products.add(product);
        try (BufferedWriter writer = Files.newBufferedWriter(PATH, StandardCharsets.UTF_8)) {
//            var json = new StringBuilder();
//            for (Product elem : products)
//                json.append(gson.toJson(elem));
            writer.write(gson.toJson(products));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void remove(int productID) {
        products.stream()
            .filter(x -> x.getProductID() == productID)
            .findFirst()
            .ifPresent(products::remove);
    }

    @Override
    public void insert(Product product) {

    }
}