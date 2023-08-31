package com.camper.demo.signup.repository;

import com.camper.demo.activity.entity.Activity;
import com.camper.demo.signup.entity.Signup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SignupRepository extends CrudRepository<Signup, Long> {
    List<Signup> findByActivity(Activity activity);
}
