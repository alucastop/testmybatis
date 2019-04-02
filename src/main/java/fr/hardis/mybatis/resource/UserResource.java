package fr.hardis.mybatis.resource;


import fr.hardis.mybatis.mapper.UserMapper;
import fr.hardis.mybatis.model.User;
import fr.hardis.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/user")
public class UserResource {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAllUser() {

       //return userMapper.findAll();
        return userService.fetchAll();

    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {

        //User user = userMapper.findOne(id);
        User user = userService.findOne(id);
        System.out.println(user.getCars());
       // return userService.findOne(id);
        return user;

    }


    @GetMapping(value = "/insert/{prenom}/{nom}")
    public ResponseEntity<Boolean> insertUser(@PathVariable String prenom, @PathVariable String nom){

        Boolean result = userMapper.insertUser(new User(nom, prenom, null));
        //Boolean result=userService.save(new User(nom, prenom, null))!=null;

        if(result) {
            return new ResponseEntity<Boolean>(result, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<Boolean>(result, HttpStatus.CONFLICT);
        }
    }

    @GetMapping(value = "/update/{prenom}/{nom}/{id}")
    public ResponseEntity<Boolean> insertUser(@PathVariable Long id, @PathVariable String prenom, @PathVariable String nom){

        //Boolean result = userMapper.updateUser(new User(nom, prenom,null));

        Boolean result=userService.save(new User(nom, prenom, null))!=null;

        if(result) {
            return new ResponseEntity<Boolean>(result, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<Boolean>(result, HttpStatus.CONFLICT);
        }
    }


    @GetMapping(value = "/delete/{id}")
    public List<User> deleteUser(@PathVariable Long id){

        //userService.delete(id);
        userMapper.deleteOne(id);
        //Boolean result = userMapper.updateUser(new User(nom, prenom,null));
       // return userService.fetchAll();
        return userMapper.findAll();

    }

}
