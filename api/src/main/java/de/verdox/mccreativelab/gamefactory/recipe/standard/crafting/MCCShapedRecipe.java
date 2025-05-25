package de.verdox.mccreativelab.gamefactory.recipe.standard.crafting;

import com.google.common.base.Preconditions;
import de.verdox.mccreativelab.gamefactory.recipe.MCCIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public interface MCCShapedRecipe extends MCCCraftingRecipe {
    RecipePattern getPattern();

    boolean showsNotification();

    static RecipePattern.Builder createPattern() {
        return new RecipePattern.Builder();
    }

    record RecipePattern(Map<Character, MCCIngredient> mapping, List<String> pattern, int height, int width) {
        public static class Builder {
            private int rowSize = 0;
            private final List<String> pattern = new ArrayList<>();
            private final Map<Character, MCCIngredient> mapping = new HashMap<>();
            private final Set<Character> uniqueChars = new HashSet<>();
            private String[] rows;

            /**
             * Set the shape of this recipe to the specified rows. Each character
             * represents a different ingredient; excluding space characters, which
             * must be empty, exactly what each character represents is set separately.
             * The first row supplied corresponds with the upper most part of the recipe
             * on the workbench e.g. if all three rows are supplies the first string
             * represents the top row on the workbench.
             *
             * @param shape The rows of the recipe (up to 3 rows).
             * @return The changed recipe, so you can chain calls.
             */
            @NotNull
            public Builder shape(@NotNull final String... shape) {
                Preconditions.checkArgument(shape != null, "Must provide a shape");
                Preconditions.checkArgument(shape.length > 0 && shape.length < 4, "Crafting recipes should be 1, 2 or 3 rows, not ", shape.length);

                int lastLen = -1;
                for (String row : shape) {
                    Preconditions.checkArgument(row != null, "Shape cannot have null rows");
                    Preconditions.checkArgument(row.length() > 0 && row.length() < 4, "Crafting rows should be 1, 2, or 3 characters, not ", row.length());

                    Preconditions.checkArgument(lastLen == -1 || lastLen == row.length(), "Crafting recipes must be rectangular");
                    lastLen = row.length();
                    rowSize = lastLen;
                }
                this.rows = new String[shape.length];
                for (int i = 0; i < shape.length; i++) {
                    this.rows[i] = shape[i];
                }

                // Remove character mappings for characters that no longer exist in the shape
                HashMap<Character, MCCIngredient> newIngredients = new HashMap<>();
                for (String row : shape) {
                    for (char c : row.toCharArray()) {
                        // SPIGOT-7770: Space in recipe shape must represent no ingredient
                        if (c == ' ') {
                            continue;
                        }

                        newIngredients.put(c, mapping.get(c));
                    }
                }
                this.mapping.clear();
                this.mapping.putAll(newIngredients);
                return this;
            }

            public Builder ingredient(char character, MCCIngredient ingredient) {
                Preconditions.checkArgument(character != ' ', "Space in recipe shape must represent no ingredient");
                Preconditions.checkArgument(mapping.containsKey(character), "Symbol does not appear in the shape:", character);
                mapping.put(character, ingredient);
                uniqueChars.add(character);
                return this;
            }

            public RecipePattern build() {
                pattern.addAll(Arrays.asList(rows));
                return new RecipePattern(mapping, pattern, rows.length, rowSize);
            }
        }
    }
}
