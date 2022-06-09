package com.i4rt.demo.interfaces;

import com.i4rt.demo.model.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegisterRepo extends JpaRepository<Register, Long> {
    @Query("SELECT r FROM Register r WHERE r.mode = :mode")
    public List<Register> getRegistersByMode(@Param("mode") String mode);
}
