package com.sm.backend.repository;


import com.sm.backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
@Query("select c from Category c where c.name=:name")
Optional<Category> findCategoryByName(String name);
}
