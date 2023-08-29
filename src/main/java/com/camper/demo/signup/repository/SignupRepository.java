package com.camper.demo.signup.repository;

import com.camper.demo.signup.entity.Signup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignupRepository extends CrudRepository<Signup, Long> {

}
