package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class HbmCrudRepository {
    private final SessionFactory sf;

    public void run(Consumer<Session> con) {
        tx(session -> {
            con.accept(session);
            return null;
        });
    }

    public <T> List<T> list(String query, Class<T> cl) {
        return tx(session -> session.createQuery(query, cl).list());
    }

    public <T> Optional<T> optional(String query, Class<T> cl, Map<String, Object> map) {
        Function<Session, Optional<T>> fun = session -> {
            var sq = session.createQuery(query, cl);
            for (Map.Entry<String, Object> arg : map.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.uniqueResultOptional();
        };
        return tx(fun);
    }

    public <T> Set<T> set(String query, Class<T> cl, Map<String, Object> map) {
        Function<Session, Set<T>> fun = session -> {
            var sq = session.createQuery(query, cl);
            for (Map.Entry<String, Object> arg : map.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.stream().collect(Collectors.toSet());
        };
        return tx(fun);
    }

    public boolean total(Function<Session, Boolean> fun) {
        return tx(fun);
    }

    public <T> T tx(Function<Session, T> command) {
        Session session = sf.openSession();
        try (session) {
            Transaction tx = session.beginTransaction();
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (Exception e) {
            Transaction tx = session.getTransaction();
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }
}
