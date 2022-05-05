package com.i4rt.demo.interfaces;

import com.i4rt.demo.model.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepo extends JpaRepository<Data, Long> {

}
