package de.verdox.mccreativelab.impl.vanilla.inventory.types.menu;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.impl.vanilla.inventory.NMSContainerMenu;
import de.verdox.mccreativelab.wrapper.annotations.MCCReflective;
import de.verdox.mccreativelab.wrapper.inventory.MCCContainer;
import de.verdox.mccreativelab.wrapper.inventory.MCCMenuType;
import de.verdox.mccreativelab.wrapper.inventory.source.MCCBlockContainerSource;
import de.verdox.mccreativelab.wrapper.inventory.types.menu.MCCFurnaceContainerMenu;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.BlastFurnaceMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.inventory.SmokerMenu;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.jetbrains.annotations.Nullable;

public abstract class NMSFurnaceContainerMenu<T extends AbstractFurnaceMenu> extends NMSContainerMenu<MCCBlockContainerSource, T, MCCContainer> implements MCCFurnaceContainerMenu {
    public NMSFurnaceContainerMenu(T abstractContainerMenu) {
        super(abstractContainerMenu);
    }

    @Override
    @MCCReflective
    public boolean isFuel(@Nullable MCCItemStack stack) {
        ServerLevel level = readFieldFromHandle("level", new TypeToken<>() {});
        return stack != null && !stack.getType().isEmpty() && level.fuelValues().isFuel(MCCPlatform.getInstance().getConversionService().unwrap(stack, new TypeToken<>() {}));
    }

    @Override
    public boolean canSmelt(@Nullable MCCItemStack stack) {
        ServerLevel mainWorld = MCCPlatform.getInstance().getConversionService().unwrap(MCCPlatform.getInstance().getWorlds().get(0), new TypeToken<ServerLevel>() {});
        Container container = conversionService.unwrap(getContainer());
        return stack != null && !stack.getType().isEmpty() && mainWorld.recipeAccess().getRecipeFor(getRecipeType(), new net.minecraft.world.item.crafting.SingleRecipeInput(MCCPlatform.getInstance().getConversionService().unwrap(stack, new TypeToken<>() {})), mainWorld).isPresent();
    }

    @Override
    public float getCookTime() {
        return this.getHandle().getBurnProgress();
    }

    @Override
    public float getBurnTime() {
        return this.getHandle().getLitProgress();
    }

    @Override
    public boolean isBurning() {
        return this.getHandle().isLit();
    }

    @Override
    public void setCookTime(int cookProgress, int cookDuration) {
        this.getHandle().setData(AbstractFurnaceBlockEntity.DATA_COOKING_PROGRESS, cookProgress);
        this.getHandle().setData(AbstractFurnaceBlockEntity.DATA_COOKING_TOTAL_TIME, cookDuration);
    }

    @Override
    public void setBurnTime(int burnProgress, int burnDuration) {
        this.getHandle().setData(AbstractFurnaceBlockEntity.DATA_LIT_TIME, burnProgress);
        this.getHandle().setData(AbstractFurnaceBlockEntity.DATA_LIT_DURATION, burnDuration);
    }

    @Override
    public MCCMenuType getType() {
        return MCCPlatform.getInstance().getConversionService().wrap(getHandle().getType(), new TypeToken<>() {});
    }

    @Override
    public MCCContainer getContainer() {
        return conversionService.wrap(readContainerFromField("container"));
    }

    protected abstract RecipeType<? extends AbstractCookingRecipe> getRecipeType();

    public static class Furnace extends NMSFurnaceContainerMenu<FurnaceMenu> {
        public static final MCCConverter<FurnaceMenu, Furnace> CONVERTER = converter(Furnace.class, FurnaceMenu.class, Furnace::new, MCCHandle::getHandle);

        public Furnace(FurnaceMenu furnaceMenu) {
            super(furnaceMenu);
        }

        @Override
        protected RecipeType<? extends AbstractCookingRecipe> getRecipeType() {
            return RecipeType.SMELTING;
        }
    }

    public static class Smoker extends NMSFurnaceContainerMenu<SmokerMenu> {
        public static final MCCConverter<SmokerMenu, Smoker> CONVERTER = converter(Smoker.class, SmokerMenu.class, Smoker::new, MCCHandle::getHandle);

        public Smoker(SmokerMenu furnaceMenu) {
            super(furnaceMenu);
        }

        @Override
        protected RecipeType<? extends AbstractCookingRecipe> getRecipeType() {
            return RecipeType.SMOKING;
        }
    }

    public static class BlastFurnace extends NMSFurnaceContainerMenu<BlastFurnaceMenu> {
        public static final MCCConverter<BlastFurnaceMenu, BlastFurnace> CONVERTER = converter(BlastFurnace.class, BlastFurnaceMenu.class, BlastFurnace::new, MCCHandle::getHandle);

        public BlastFurnace(BlastFurnaceMenu furnaceMenu) {
            super(furnaceMenu);
        }

        @Override
        protected RecipeType<? extends AbstractCookingRecipe> getRecipeType() {
            return RecipeType.BLASTING;
        }
    }
}
