package com.czarnowski.recipes;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe findRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String addNewRecipe(Recipe recipe){
        recipeRepository.save(recipe);
        return "{\"id\": " + recipe.getId() + "}";
    }

    public void deleteRecipe(long id) {
        if (!recipeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        recipeRepository.deleteById(id);
    }

    @Transactional
    public void update(long id, Recipe recipe) {
        Recipe getRecipe = findRecipeById(id);
        getRecipe.setName(recipe.getName());
        getRecipe.setDescription(recipe.getDescription());
        getRecipe.setCategory(recipe.getCategory());
        getRecipe.setIngredients(recipe.getIngredients());
        getRecipe.setDirections(recipe.getDirections());
    }

    public List<Recipe> searchByCategory(String category) {
        return recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> searchByName(String name) {
        return recipeRepository.findAllByNameIgnoreCaseContainsOrderByDateDesc(name);
    }

    public List<Recipe> searchByNameAndCategory(String name, String category) {
        return recipeRepository.findAllByNameContainingIgnoreCaseAndCategoryIgnoreCaseOrderByDateDesc(name, category);
    }


}