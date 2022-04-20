package com.czarnowski.recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
public class RecipeRestController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeRestController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/api/recipe/{id}")
    public Recipe getRecipe(@PathVariable Long id) {
        return recipeService.findRecipeById(id);
    }

    @PostMapping("/api/recipe/new")
    public String saveNewRecipe(@Valid @RequestBody Recipe recipe) {
        return recipeService.addNewRecipe(recipe);
    }

    @DeleteMapping("/api/recipe/{id}")
    @PreAuthorize("@recipeOwnershipCheck.isOwner(#id, principal)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable long id) {
        recipeService.deleteRecipe(id);
    }

    @GetMapping("/api/recipe/search")
    public ResponseEntity<List<Recipe>> search(@RequestParam(required = false) String name,
                                               @RequestParam(required = false) String category) {

        if (name == null && category == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Recipe> result;

        if (name == null) {
            result = recipeService.searchByCategory(category);
        } else if (category == null) {
            result = recipeService.searchByName(name);
        } else {
            result = recipeService.searchByNameAndCategory(name, category);
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/api/recipe/{id}")
    @PreAuthorize("@recipeOwnershipCheck.isOwner(#id, principal)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void putRecipe(@PathVariable long id,
                          @Valid @RequestBody Recipe recipe) {
        recipeService.update(id, recipe);
    }
}