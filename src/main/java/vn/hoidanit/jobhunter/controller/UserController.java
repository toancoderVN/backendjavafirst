package vn.hoidanit.jobhunter.controller;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.security.crypto.password.PasswordEncoder;


@RestController
public class UserController {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    
   
    /* 
    @PostMapping("/users/create")
    public String createNewUser(){
        User user = new User();
        user.setEmail("toansos2002@gmail.com");
        user.setName("Toan");
        user.setPassword("123456");

        this.userService.handleCreateUser(user);

        return "create user";
    }
        */

    @PostMapping("/users")
    public ResponseEntity<User> createNewUser(@RequestBody User postManUser) {
        //TODO: process POST request
        String hasPassword = this.passwordEncoder.encode(postManUser.getPassword());
        postManUser.setPassword(hasPassword);
        User toanUser = this.userService.handleCreateUser(postManUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(toanUser);
    }

    

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id) throws IdInvalidException {
        if(id >= 1500){
            throw new IdInvalidException("Id khong lon hon 1500");
        }
        
        //TODO: process POST request
        this.userService.handleDeleteUser(id);
        return ResponseEntity.ok("Delete User");
    }

   // fetch user by id
   @GetMapping("/users/{id}")
   public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
       return ResponseEntity.status(HttpStatus.OK).body(userService.fetchUserById(id));
   }

   // fetch all users
   @GetMapping("/users")
   public ResponseEntity<List<User>> getAllUser() {
       return ResponseEntity.status(HttpStatus.OK).body(userService.fetchAllUser());
   } 

   @PutMapping("/users")
   public ResponseEntity<User> updateNewUser(@RequestBody User postManUser) {
    //TODO: process POST request
    User toanUser = this.userService.handleUpdateUser(postManUser);
    return ResponseEntity.status(HttpStatus.OK).body(toanUser);
}
}
