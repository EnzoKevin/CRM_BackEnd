package com.crm.MVP.modules.User.Controller;

import com.crm.MVP.modules.User.DTO.UserRequestDTO;
import com.crm.MVP.modules.User.DTO.UserResponseDTO;
import com.crm.MVP.modules.User.Entity.User;
import com.crm.MVP.modules.User.Mapper.UserMapper;
import com.crm.MVP.modules.User.Service.UserService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserRequestDTO dto) {
        User user = userService.create(userMapper.toEntity(dto));
        return ResponseEntity.created(URI.create("/users/" + user.getId()))
                .body(userMapper.toResponseDTO(user));
    }

    @GetMapping
    public List<UserResponseDTO> findAll() {
        return userService.findAll().stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public UserResponseDTO findById(@PathVariable Long id) {
        return userMapper.toResponseDTO(userService.findById(id));
    }

    @PutMapping("/{id}")
    public UserResponseDTO update(@PathVariable Long id, @Valid @RequestBody UserRequestDTO dto) {
        User user = userMapper.toEntity(dto);
        return userMapper.toResponseDTO(userService.update(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
