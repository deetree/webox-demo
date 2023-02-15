package com.github.deetree;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mariusz Bal
 */
@Repository
interface AccountRepository extends CrudRepository<Account, Integer> {

    boolean existsAccountByEmail(String email);
}



