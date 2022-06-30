package com.i4rt.demo.interfaces;

import com.i4rt.demo.model.Mode;
import com.i4rt.demo.model.Script;
import com.i4rt.demo.model.ScriptVersion2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public interface ScriptVersion2Repo extends JpaRepository<ScriptVersion2, Long> {
    @Query("SELECT s FROM ScriptVersion2 s WHERE s.name = :name")
    public ScriptVersion2 getScriptByName(@Param("name") String name);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update ScriptVersion2 s set s.rowCommandsJsonRow=:rowCommandsJsonRow WHERE s.name = :name")
    public void updateScript(@Param("name") String name, @Param("rowCommandsJsonRow") String rowCommandsJsonRow);

}
