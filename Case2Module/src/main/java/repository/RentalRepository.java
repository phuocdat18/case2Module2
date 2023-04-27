package repository;


import model.Rental;

public class RentalRepository extends FileContext<Rental> {

    public RentalRepository() {
        filePath = "./Case2Module/src/main/data/rental.csv";
        tClass = Rental.class;
    }
}
