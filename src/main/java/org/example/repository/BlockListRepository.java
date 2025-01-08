package org.example.repository;

import org.example.entity.redis.BlockList;
import org.springframework.data.repository.CrudRepository;

public interface BlockListRepository extends CrudRepository<BlockList, Long> {

}
