package com.ecomerence.onlineshopping.model.repository;


import com.ecomerence.onlineshopping.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
    List<Category> findByActiveTrueOrderByNameAsc();
    List<Category> findAllByOrderByActiveDescNameAsc();
    Optional<Category> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
    @Query("SELECT COUNT(p) FROM Product p WHERE p.category.id = :id AND p.active = true")
    long countActiveProductsByCategoryId(@Param("id") Long id);

}
