package de.verdox.mccreativelab.wrapper.platform.data.patcher;

import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.platform.data.MCCDataPackInterceptor;
import de.verdox.vserializer.util.gson.JsonObjectBuilder;
import net.kyori.adventure.key.Key;

import java.util.function.Consumer;

public class RecipePatcher {
    public static final MCCDataPackInterceptor interceptor = MCCPlatform.getInstance().getDataPackInterceptor();

    public static void patchRecipe(String name, Consumer<JsonObjectBuilder> consumer) {
        interceptor.modify(MCCDataPackInterceptor.PackAssetType.RECIPE, Key.key("minecraft", name), dataPackAsset -> {
            consumer.accept(JsonObjectBuilder.create(dataPackAsset.jsonObject()));
            return true;
        });
    }

    public static void removeRecipe(String name) {
        interceptor.exclude(MCCDataPackInterceptor.PackAssetType.RECIPE, Key.key("minecraft", name));
    }

    private static void patchResult(JsonObjectBuilder jsonObjectBuilder, String name, int result) {
        jsonObjectBuilder.add("result",
                JsonObjectBuilder
                        .create()
                        .add("id", "minecraft:" + name)
                        .add("count", result)
        );
    }

    private static void patchResult(JsonObjectBuilder jsonObjectBuilder, String name) {
        jsonObjectBuilder.add("result",
                JsonObjectBuilder
                        .create()
                        .add("id", "minecraft:" + name)
        );
    }

    public static void patchShapelessRecipeWithSameName(String name, int result) {
        patchRecipe(name, jsonObjectBuilder -> patchResult(jsonObjectBuilder, name, result));
    }

    public static void patchShapedRecipeWithSameName(String name, int result) {
        patchRecipe(name, jsonObjectBuilder -> patchResult(jsonObjectBuilder, name, result));
    }

    private static void patchFoodRecipe(String name, int smokerSeconds, int campfireSeconds, boolean delete) {
        if (delete)
            removeRecipe(name);
        patchSmeltingTime(name + "_from_smoking", smokerSeconds);
        patchSmeltingTime(name + "_from_campfire_cooking", campfireSeconds);
    }

    private static void patchSmeltingType(String name, String smeltingType) {
        patchRecipe(name, jsonObjectBuilder -> jsonObjectBuilder.add("type", smeltingType));
    }

    private static void patchSmeltingTime(String name, int timeInSeconds) {
        patchRecipe(name, jsonObjectBuilder -> jsonObjectBuilder.add("cookingtime", timeInSeconds * 20));
    }

    private static void patchSmeltingResult(String name, String result) {
        patchRecipe(name, jsonObjectBuilder -> patchResult(jsonObjectBuilder, result));
    }


    private static void patchOreRecipe(String name, boolean isIngot, boolean isLapis, int blastingSeconds, String newResult) {
        var exactName = name;
        if (isIngot)
            exactName = name + "_ingot";
        if (isLapis)
            exactName = name + "_lazuli";
        removeRecipe(exactName + "_from_smelting_" + name + "_ore");
        removeRecipe(exactName + "_from_smelting_deepslate_" + name + "_ore");
        patchSmeltingTime(exactName + "_from_blasting_" + name + "_ore", blastingSeconds);
        patchSmeltingTime(exactName + "_from_blasting_deepslate_" + name + "_ore", blastingSeconds);
        if (newResult != null && !newResult.isEmpty()) {
            patchSmeltingResult(exactName + "_from_blasting_" + name + "_ore", newResult);
            patchSmeltingResult(exactName + "_from_blasting_deepslate_" + name + "_ore", newResult);
        }
    }

    private static void patchOreRawMaterial(String name, int blastingSeconds, boolean delete, String newResult) {
        if (delete) {
            removeRecipe(name + "_ingot_from_smelting_raw_" + name);
        }
        patchSmeltingTime(name + "_ingot_from_blasting_raw_" + name, blastingSeconds);
        if (newResult != null && !newResult.isEmpty())
            patchSmeltingResult(name + "_ingot_from_blasting_raw_" + name, newResult);
    }

    private static void patchSmeltingRecipe(String name, int smeltingSeconds, boolean smeltingPrefix, boolean deleteSmoker, boolean deleteBlasting) {
        if (deleteSmoker)
            removeRecipe(name + "_from_smoking");
        if (deleteBlasting)
            removeRecipe(name + "_from_blasting");
        var exactName = smeltingPrefix ? name + "_from_smelting" : name;
        patchSmeltingTime(exactName, smeltingSeconds);
    }
}