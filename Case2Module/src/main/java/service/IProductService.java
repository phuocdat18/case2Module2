package service;

import model.Model;

import java.util.List;

public interface IProductService {
    void add(Model newProduct);

    void update(int id, Model updateProduct);

    List<Model> getProducts();

    void updateQuantity(int id, int quantity);

}
