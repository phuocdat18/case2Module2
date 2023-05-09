package repository;

import model.User;

public class UserUseRepository extends FileContext<User>{
    public UserUseRepository(){
        filePath = "Case2Module/src/main/data/userUse.csv";
        tClass = User.class;
    }
}
