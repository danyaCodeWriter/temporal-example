package ru.danil.trysomething.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.danil.trysomething.entity.Check;
import ru.danil.trysomething.entity.CompositeKey;

@Repository
public interface CheckRepository extends JpaRepository<Check, Long> {

}
