package org.example.repository;

import org.example.entity.District;
import org.example.repository.imp.DistrictRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long>, DistrictRepositoryCustom, JpaSpecificationExecutor<District> {
}
