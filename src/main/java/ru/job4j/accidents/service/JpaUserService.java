package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.JpaUserRepository;

@Service
@AllArgsConstructor
@Slf4j
public class JpaUserService {
    private final JpaUserRepository userRepository;
    public boolean save(User user) {
        boolean rsl = false;
        try {
            userRepository.save(user);
            rsl = true;
        } catch (Exception e) {
            log.error("Ошибка при регистрации: " + e.getMessage(), e);
        }
        return rsl;
    }
}
