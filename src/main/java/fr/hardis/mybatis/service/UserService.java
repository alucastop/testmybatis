package fr.hardis.mybatis.service;

import fr.hardis.mybatis.model.User;
import fr.hardis.mybatis.repository.CarRepository;
import fr.hardis.mybatis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;


    public List<User> fetchAll() {
        return userRepository.findAll();
    }

    public void delete(Long id) {

        User user = new User();
        user.setId(id);
        userRepository.delete(user);
    }

    public User findOne(Long id){

        User  user = userRepository.getById(id);
        user.setCars(carRepository.findAllByIdUser(user.getId()));
        return user;
    }

    public User save(User user){
        return userRepository.save(user);
    }
}
