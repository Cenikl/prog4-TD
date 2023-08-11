package com.example.prog3.Repository.last2;

import com.example.prog3.model.last2.Cnaps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CnapsRepository extends JpaRepository<Cnaps,Long> {
}
