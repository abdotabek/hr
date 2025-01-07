package org.example.repository;

import org.example.entity.redis.BlockList;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BlockListRepository extends CrudRepository<BlockList, String> {
    Optional<BlockList> findByEmployeeId(Long id);

}
