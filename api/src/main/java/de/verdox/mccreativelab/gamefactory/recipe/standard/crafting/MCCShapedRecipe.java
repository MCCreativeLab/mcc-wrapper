package de.verdox.mccreativelab.gamefactory.recipe.standard.crafting;

import de.verdox.mccreativelab.gamefactory.recipe.MCCIngredient;

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
            private int amountRows = 0;
            private final List<String> pattern = new ArrayList<>();
            private final Map<Character, MCCIngredient> mapping = new HashMap<>();
            private final Set<Character> uniqueChars = new HashSet<>();

            public Builder withRow(char... characters) {
                if (amountRows >= 3) {
                    throw new IllegalArgumentException("A maximum of 3 rows are allowed");
                }
                if (characters.length > 3) {
                    throw new IllegalArgumentException("Rows must contain at most three characters");
                }
                if (rowSize > 0 && characters.length != rowSize) {
                    throw new IllegalArgumentException("The row characters must have the same length");
                }
                rowSize = characters.length;
                for (char character : characters) {
                    pattern.add(character + "");
                }
                amountRows++;
                return this;
            }

            public Builder ingredient(char character, MCCIngredient ingredient) {
                mapping.put(character, ingredient);
                uniqueChars.add(character);
                return this;
            }

            public RecipePattern build() {
                for (String s : pattern) {
                    s.chars().forEach(value -> {
                        char character = (char) value;
                        if (!uniqueChars.contains(character)) {
                            throw new IllegalArgumentException("Recipe character " + character + " not mapped to an ingredient");
                        }
                    });
                }
                return new RecipePattern(mapping, pattern, amountRows, rowSize);
            }
        }
    }
}
