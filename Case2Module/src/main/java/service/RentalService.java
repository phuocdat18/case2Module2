package service;

import model.Order;
import model.Rental;
import repository.RentalRepository;

import java.io.IOException;
import java.util.List;

public class RentalService
{
    private RentalRepository rentalRepository;
    public RentalService() {
        rentalRepository = new RentalRepository();
    }
    public List<Rental> getAllOrder() throws IOException {
        return rentalRepository.getAll();
    }
//    public List<Rental> getAllOrderAll() throws IOException {
//        return orderAllRepository.getAll();
//    }
//    public int checkIdOrderAll(int id) throws IOException {
//        return orderAllRepository.checkID(id);
//    }
    public int checkIdOrder(int id) throws IOException {
        return rentalRepository.checkID(id);
    }
//    public void deleteModelOutOrderAllById(int id) throws IOException {
//        orderAllRepository.deleteById(id);
//    }
    public void deleteModelOutOrderById(int id) throws IOException {
        rentalRepository.deleteById(id);
    }

}