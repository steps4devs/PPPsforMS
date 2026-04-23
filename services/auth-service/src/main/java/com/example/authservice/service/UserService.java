package com.example.authservice.service;

import com.example.authservice.entity.User;
import com.example.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User update(Long id, User userDetails) {
        User user = findById(id);
        if (user != null) {
            user.setNombre(userDetails.getNombre());
            user.setApellido(userDetails.getApellido());
            user.setEmail(userDetails.getEmail());
            user.setUsername(userDetails.getUsername());
            user.setActivo(userDetails.getActivo());
            // No actualizamos el password aquí por simplicidad
            return userRepository.save(user);
        }
        return null;
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
