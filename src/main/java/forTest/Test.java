package forTest;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        var dog1 = new Dog("dog1", 20, new Date());
        var dog2 = new Dog("dog2", 40, new Date());
//        var dogs = List.of(dog1, dog2);
        var dogs = new Dog[]{dog1, dog2};
        RuntimeTypeAdapterFactory<Kek> adapter = RuntimeTypeAdapterFactory.of(Kek.class, "type").
            registerSubtype(Dog.class, Dog.class.getName());
        var gson2 = new GsonBuilder().setPrettyPrinting().registerTypeAdapterFactory(adapter).create();
        var gson = new GsonBuilder().setPrettyPrinting().create();

        System.out.println(dog1.getClass().getName());
        var json = gson2.toJson(dogs);
        System.out.println(json + "\n\n");

        Type type = new TypeToken<Lol>() {
        }.getType();

        var dogFromJson = gson2.fromJson(json, Kek[].class);
        System.out.println(Arrays.toString(dogFromJson));
    }
}

interface Lol {

}

abstract class Kek {
    protected String type = getClass().getName();
    int age;

    public Kek(int age) {
        this.age = age;
    }
}

class Dog extends Kek implements Lol {
    String name;
    Date date;

    public Dog(String name, int age, Date date) {
        super(age);
        this.name = name;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Dog{" +
            "name='" + name + '\'' +
            '}' +
            "{age=" + age +
            "}";
    }
}
