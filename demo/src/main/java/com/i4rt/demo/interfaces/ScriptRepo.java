package com.i4rt.demo.interfaces;

import com.i4rt.demo.model.Mode;
import com.i4rt.demo.model.Script;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ScriptRepo extends JpaRepository<Script, Long> {
    @Query("SELECT s FROM Script s WHERE s.name = :name")
    public Script getScriptByName(@Param("name") String name);

    @Modifying
    @Query("update Script s set s.name=:name, s.modes=:modeSet WHERE s.name = :name")
    public Script updateScriptNameModes(@Param("name") String name, @Param("modeSet") Set<Mode> modeSet);

}
