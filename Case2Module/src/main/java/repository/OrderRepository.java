package repository;

import model.Order;

public class OrderRepository extends FileContext<Order> {
    public OrderRepository() {
        filePath = "Case2Module/src/main/data/order.csv";
        tClass = Order.class;
    }
}
