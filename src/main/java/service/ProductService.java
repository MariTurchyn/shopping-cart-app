package service;

import model.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private final List<Product> products = new ArrayList<>();

    public ProductService() {
        products.add(new Product(1, "Laptop 14\" Pro", 1299.00));
        products.add(new Product(2, "Noise-Cancel Headphones", 179.00));
        products.add(new Product(3, "Smartphone X", 899.00));
        products.add(new Product(4, "Mechanical Keyboard", 89.00));
        products.add(new Product(5, "USB-C Dock", 129.00));
        products.add(new Product(6, "4K Monitor 27\"", 349.00));
    }

    public List<Product> getAll() { return products; }
}
