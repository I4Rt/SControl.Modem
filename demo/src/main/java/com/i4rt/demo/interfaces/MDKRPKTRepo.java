package com.i4rt.demo.interfaces;

import com.i4rt.demo.model.MDKRPKT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MDKRPKTRepo extends JpaRepository<MDKRPKT, Long> {
    @Query("SELECT m FROM Mode m WHERE m.name = :name")
    public MDKRPKT getByName(@Param("name") String name);
}
