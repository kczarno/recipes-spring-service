package com.czarnowski.recipes;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe,Long> {

    List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(String category);

    List<Recipe> findAllByNameIgnoreCaseContainsOrderByDateDesc(String name);

    List<Recipe> findAllByNameContainingIgnoreCaseAndCategoryIgnoreCaseOrderByDateDesc(String name, String category);
}