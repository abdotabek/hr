package org.example.repository;

import org.example.entity.redis.TokenStore;
import org.springframework.data.repository.CrudRepository;

public interface TokenStoreRepository extends CrudRepository<TokenStore, String> {


    TokenStore findByEmployeeId(Long employeeId);

    void deleteByEmployeeId(Long employeeId);
}
