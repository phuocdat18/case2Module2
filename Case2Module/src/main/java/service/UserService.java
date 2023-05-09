package service;

import model.Model;
import model.Role;
import model.User;
import repository.UserRepository;
import repository.UserUseRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private UserRepository userRepository;
    private UserUseRepository userUseRepository;
    public UserService() {
        userRepository = new UserRepository();
        userUseRepository = new UserUseRepository();
    }
    public List<User> getAllUser() throws IOException {
        return userRepository.getAll();
    }
    public boolean checkLoginAdmin(String username, String pass) throws IOException {
        List<User> allUsers = getAllUser();
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getUsername().equals(username) && allUsers.get(i).getPassword().equals(pass) && allUsers.get(i).getRole().equals(Role.admin)) {
                return true;
            }
        }
        return false;
    }
    public boolean checkLoginCustomer(String username, String pass) throws IOException {
        List<User> allUsers = getAllUser();
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getUsername().equals(username) && allUsers.get(i).getPassword().equals(pass) && allUsers.get(i).getRole().equals(Role.customer)) {
                return true;
            }
        }
        return false;
    }
    public List<User> getCustomerList() throws IOException {
        List<User> allUsers = userRepository.getAll();
        List<User> customerList = new ArrayList<>();
        for(User user: allUsers){
            if(user.getRole() == Role.customer){
                customerList.add(user);
            }
        }
        return customerList;
    }
    public User loginCustomer(String username, String password) throws IOException {
        List<User> allUsers = getAllUser();
        for (int i = 0; i < allUsers.size(); i++) {
            if(allUsers.get(i).getUsername().equals(username) && allUsers.get(i).getPassword().equals(password) && allUsers.get(i).getRole().equals(Role.customer)) {
                return allUsers.get(i);
            }
        }
        return null;
    }
    public boolean checkUserName(String username) throws IOException {
        List<User> allUsers = getAllUser();
        for (int i = 0; i < allUsers.size(); i++) {
            if(allUsers.get(i).getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
    public boolean checkPhoneNumber(String phoneNumber) throws IOException {
        List<User> allUsers = getAllUser();
        for (int i = 0; i < allUsers.size(); i++) {
            if(allUsers.get(i).getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

//    public boolean checkCCCD(String cccd) throws IOException {
//        List<User> allUsers = getAllUser();
//        for (int i = 0; i < allUsers.size(); i++) {
//            if(allUsers.get(i).getCccd().equals(cccd)) {
//                return true;
//            }
//        }
//        return false;
//    }

    public boolean checkEmail(String email) throws IOException {
        List<User> allUsers = getAllUser();
        for (int i = 0; i < allUsers.size(); i++) {
            if(allUsers.get(i).getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
    public boolean checkPassword(String password) throws IOException {
        List<User> allUsers = getAllUserUse();
        for (int i = 0; i < allUsers.size(); i++) {
            if(allUsers.get(i).getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }



    public List<User> getAllUserUse() throws IOException {
        return userUseRepository.getAll();
    }

//    public User searchIdUser(int idUser) throws IOException {
//        List<User> allUser = getAllUser();
//        for (int i = 0; i < allUser.size(); i++) {
//            if(allUser.get(i).getId() == idUser) {
//                return allUser.get(i);
//            }
//        }
//        return null;
//    }
}
