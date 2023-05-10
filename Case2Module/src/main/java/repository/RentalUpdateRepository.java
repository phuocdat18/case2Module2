package repository;


import model.Rental;

public class RentalUpdateRepository extends FileContext<Rental> {
    public RentalUpdateRepository() {
        filePath = "./Case2Module/src/main/data/rentalNow.csv";
        tClass = Rental.class;
    }
}
