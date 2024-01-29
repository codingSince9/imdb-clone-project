package imdb.controllers;

import imdb.model.User;
import imdb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable(value = "id") String userId) {
        return userRepository.findById(Integer.valueOf(userId))
                .orElseThrow(() -> new ResourceNotFoundException("User " + "id " + userId));
    }

    @GetMapping(value = "/myprofile")
    public User getMyProfileData() {
        return userRepository.findAll().get(0);
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable(value = "email") String email) {
        return userRepository.findByEmail(email);
    }

    @PostMapping("/add")
    public User createUser(@RequestBody User user) {
        User userByEmail = userRepository.findByEmail(user.getEmail());
        if (userByEmail == null) {
            return userRepository.save(user);
        } else {
            return null;
        }
    }

    @PostMapping(value="/saveUser")
    public User saveUser(@RequestBody User user) {
        User userById = userRepository.findById(user.getId());
        if(userById == null) {
            return userRepository.save(user);
        } else {
            return null;
        }
    }

    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable(value = "id") Integer userId, @RequestBody User userDetails) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User " + "id " + userId));

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());

        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User " + "id " + userId));

        userRepository.delete(user);

        return ResponseEntity.ok().build();
    }
}
