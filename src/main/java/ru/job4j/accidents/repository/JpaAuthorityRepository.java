package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Authority;

public interface JpaAuthorityRepository extends CrudRepository<Authority, Integer> {
    Authority findByAuthority(String roleUser);
}
