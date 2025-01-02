package org.example.repository;

import org.example.entity.redis.TokenStore;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TokenStoreRepository extends CrudRepository<TokenStore, String> {

    List<TokenStore> findAllByEmployeeId(Long employeeId);

    TokenStore findByEmployeeId(Long employeeId);

    void deleteAllByEmployeeId(Long employeeId);
    void deleteByEmployeeId(Long employeeId);
}
