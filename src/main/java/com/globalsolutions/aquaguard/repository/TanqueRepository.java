package com.globalsolutions.aquaguard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.globalsolutions.aquaguard.model.Tanque;

public interface TanqueRepository extends JpaRepository<Tanque, Long>{
    
}
