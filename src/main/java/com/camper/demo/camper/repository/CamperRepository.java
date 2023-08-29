package com.camper.demo.camper.repository;

import com.camper.demo.camper.entity.Camper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CamperRepository extends CrudRepository<Camper, Long> {

}