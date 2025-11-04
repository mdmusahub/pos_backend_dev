package com.sm.backend.repository;

import com.sm.backend.model.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRepository extends JpaRepository <Tax,Long> {

}
