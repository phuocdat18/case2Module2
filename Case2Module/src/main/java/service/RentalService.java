package service;


import model.Rental;
import repository.RentalAllRepository;
import repository.RentalRepository;
import repository.RentalUpdateRepository;

import java.io.IOException;
import java.util.List;

public class RentalService
{
    private RentalRepository rentalRepository;
    private RentalUpdateRepository rentalUpdateRepository;
    private RentalAllRepository rentalAllRepository;
    public RentalService() {
        rentalRepository = new RentalRepository();
        rentalUpdateRepository = new RentalUpdateRepository();
        rentalAllRepository = new RentalAllRepository();
    }
    public List<Rental> getAllRental() throws IOException {
        return rentalRepository.getAll();
    }
    public int checkIdRentalAll(int id) throws IOException {
        return rentalAllRepository.checkID(id);
    }
    public int checkIdOrder(int id) throws IOException {
        return rentalRepository.checkID(id);
    }
    public void deleteModelOutRentalAllById(int id) throws IOException {
        rentalAllRepository.deleteById(id);
    }
    public void deleteModelOutOrderById(int id) throws IOException {
        rentalRepository.deleteById(id);

    }

    public List<Rental> getAllRentalAll() throws IOException {
        return rentalAllRepository.getAll();
    }
    public List<Rental> getAllRentalUpdate() throws IOException {
        return rentalUpdateRepository.getAll();
    }

    public Rental searchIdRental(int idModelRental) throws IOException {
        List<Rental> allRental = getAllRental();
        for (int i = 0; i < allRental.size(); i++) {
            if(allRental.get(i).getIdRental() == idModelRental) {
                return allRental.get(i);
            }
        }
        return null;
    }

}