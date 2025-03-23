package de.verdox.mccreativelab.impl.vanilla.inventory.types.menu;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.impl.vanilla.inventory.NMSContainerMenu;
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
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.jetbrains.annotations.Nullable;

public class NMSFurnaceContainerMenu extends NMSContainerMenu<MCCBlockContainerSource, AbstractFurnaceMenu, MCCContainer> implements MCCFurnaceContainerMenu {
    public static final MCCConverter<AbstractFurnaceMenu, NMSFurnaceContainerMenu> CONVERTER = converter(NMSFurnaceContainerMenu.class, AbstractFurnaceMenu.class, NMSFurnaceContainerMenu::new, MCCHandle::getHandle);

    public NMSFurnaceContainerMenu(AbstractFurnaceMenu abstractContainerMenu) {
        super(abstractContainerMenu);
    }

    @Override
    public boolean isFuel(@Nullable MCCItemStack stack) {
        return stack != null && !stack.getType().isEmpty() && AbstractFurnaceBlockEntity.isFuel(MCCPlatform.getInstance().getConversionService().unwrap(stack, new TypeToken<>() {}));
    }

    @Override
    public boolean canSmelt(@Nullable MCCItemStack stack) {
        ServerLevel mainWorld = MCCPlatform.getInstance().getConversionService().unwrap(MCCPlatform.getInstance().getWorlds().get(0), new TypeToken<ServerLevel>() {});
        Container container = conversionService.unwrap(getContainer());
        return stack != null && !stack.getType().isEmpty() && mainWorld.getRecipeManager().getRecipeFor(((AbstractFurnaceBlockEntity) container).recipeType, new net.minecraft.world.item.crafting.SingleRecipeInput(MCCPlatform.getInstance().getConversionService().unwrap(stack, new TypeToken<>() {})), mainWorld).isPresent();
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
}
