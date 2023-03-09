package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Authority;
import ru.job4j.accidents.repository.JpaAuthorityRepository;

@Service
@AllArgsConstructor
public class JpaAuthorityService {

    private final JpaAuthorityRepository authorityRepository;
    public Authority findByAuthority(String roleUser) {
        return authorityRepository.findByAuthority(roleUser);
    }
}
