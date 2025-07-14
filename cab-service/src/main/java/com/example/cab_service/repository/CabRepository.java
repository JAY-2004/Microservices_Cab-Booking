package com.example.cab_service.repository;

import com.example.cab_service.entity.Cab;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CabRepository extends JpaRepository<Cab, Long> {
}

