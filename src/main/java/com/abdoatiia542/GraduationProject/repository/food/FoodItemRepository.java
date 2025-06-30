package com.abdoatiia542.GraduationProject.repository.food;

import com.abdoatiia542.GraduationProject.model.food.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
}
