package com.i4rt.demo.interfaces;

import com.i4rt.demo.model.Mode;
import com.i4rt.demo.model.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeRepo extends JpaRepository<Mode, Long> {
    @Query("SELECT m FROM Mode m WHERE m.name = :name")
    public Mode getModeByName(@Param("name") String name);

}
