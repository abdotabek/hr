package org.example.repository;

import org.example.entity.redis.BlackList;
import org.springframework.data.repository.CrudRepository;

public interface BlockListRepository extends CrudRepository<BlackList, Long> {

}
