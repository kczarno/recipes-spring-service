package com.czarnowski.recipes;

import com.czarnowski.user.RecipesUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "recipeOwnershipCheck")
public class RecipeOwnershipCheck {

    private final RecipeService recipeService;

    @Autowired
    public RecipeOwnershipCheck(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    public boolean isOwner(long id, RecipesUserDetails userDetails) {
        Recipe toUpdate = recipeService.findRecipeById(id);
        return toUpdate.getUser().getEmail().equals(userDetails.getUsername());
    }
}
