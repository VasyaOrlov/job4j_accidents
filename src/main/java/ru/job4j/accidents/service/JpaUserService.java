package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.JpaUserRepository;

@Service
@AllArgsConstructor
public class JpaUserService {
    private static final Logger LOG = LoggerFactory.getLogger(JpaUserService.class.getName());
    private final JpaUserRepository userRepository;
    public boolean save(User user) {
        boolean rsl = false;
        try {
            userRepository.save(user);
            rsl = true;
        } catch (Exception e) {
            LOG.error("Ошибка при регистрации");
        }
        return rsl;
    }
}
