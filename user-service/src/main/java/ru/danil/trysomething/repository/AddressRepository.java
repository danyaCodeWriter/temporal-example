package ru.danil.trysomething.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.danil.trysomething.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
}
