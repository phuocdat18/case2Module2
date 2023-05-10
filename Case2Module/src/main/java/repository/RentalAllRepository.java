package repository;


import model.Rental;

public class RentalAllRepository extends FileContext<Rental> {
    public RentalAllRepository() {
        filePath = "./Case2Module/src/main/data/rentalAll.csv";
        tClass = Rental.class;
    }
}
