package repository;

import model.User;

public class UserRepository extends FileContext<User> {
    public UserRepository(){
        filePath = "./Case2Module/src/main/data/user.csv";
        tClass = User.class;
    }
}
