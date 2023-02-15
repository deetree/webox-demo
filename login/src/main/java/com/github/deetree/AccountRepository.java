package com.github.deetree;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Mariusz Bal
 */
@Repository
interface AccountRepository extends CrudRepository<Account, Integer> {

    Optional<Account> findByEmail(String email);

}
