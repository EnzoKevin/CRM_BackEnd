package com.crm.MVP.modules.User.Service;

import com.crm.MVP.modules.User.Entity.User;
import com.crm.MVP.modules.User.Repository.UserRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    public User update(Long id, User updatedUser) {
        User user = findById(id);
        updatedUser.setId(user.getId());
        return userRepository.save(updatedUser);
    }

    public void delete(Long id) {
        findById(id);
        userRepository.deleteById(id);
    }
}
