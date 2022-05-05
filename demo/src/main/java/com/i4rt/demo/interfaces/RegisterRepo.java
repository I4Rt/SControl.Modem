package com.i4rt.demo.interfaces;

import com.i4rt.demo.model.Data;
import com.i4rt.demo.model.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterRepo extends JpaRepository<Register, Long> {

}
