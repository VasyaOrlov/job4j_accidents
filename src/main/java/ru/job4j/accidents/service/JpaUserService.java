package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.JpaUserRepository;

@Service
@AllArgsConstructor
public class JpaUserService {
    private final JpaUserRepository userRepository;
    public void save(User user) {
        userRepository.save(user);
    }
}
