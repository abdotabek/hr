package org.example.repository;

import org.example.entity.redis.TokenStore;
import org.springframework.data.repository.CrudRepository;

public interface TokenStoreRepository extends CrudRepository<TokenStore, String> {

    Iterable<TokenStore> findAll(); // Для получения всех записей
}
