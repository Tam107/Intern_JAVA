package intern.JAVACORE2.Ex8;

import java.util.HashMap;
import java.util.Map;

public class ProductManager {
    private HashMap<String, Product> products;

    public ProductManager(){
        products = new HashMap<>();
    }

    public void addProduct(String id, String name, double price, int quantity){
        if (products.containsKey(id)){
            System.out.println("Cannot add existing product");
        }
        else {
            products.put(id, new Product(name, price, quantity));
            System.out.println("Added product: "+name);
        }
    }

    public void displayAllProduct(){
        for (Map.Entry<String, Product> map : products.entrySet()){
            System.out.println("The id: "+map.getKey()+" the product: "+map.getValue().toString());
        }
    }

    public void findProduct(String id){
        for (Map.Entry<String, Product> map : products.entrySet()){
            if (map.getKey().equals(id)){
                System.out.println("The product with id: "+id+" is: "+map.getValue().toString());
            }
        }
    }

    public void removeProduct(String id){
        if (products.containsKey(id)){
            products.remove(id);
            System.out.println("Removed product with id: "+id);
        }
    }

    public void updateProduct(String id, String name, double price, int quantity){
        boolean check = true;
        for (Map.Entry<String, Product> map : products.entrySet()){
            if (map.getKey().equals(id)){
                map.setValue(new Product(name, price, quantity));
                System.out.println("updated product: "+map.getValue().toString());
            }
        }
        if (!check){
            System.out.println("cannot found product");
        }
    }
}
