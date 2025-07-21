package com.sm.backend.repository;

import com.sm.backend.model.ReturnOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnOrderItemRepository extends JpaRepository<ReturnOrderItem, Long>{

}
