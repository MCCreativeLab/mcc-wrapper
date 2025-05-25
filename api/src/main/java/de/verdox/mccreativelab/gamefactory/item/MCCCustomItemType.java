package de.verdox.mccreativelab.gamefactory.item;

import de.verdox.mccreativelab.gamefactory.MCCGameFactory;
import de.verdox.mccreativelab.generator.resourcepack.types.ItemTextureData;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentType;
import de.verdox.mccreativelab.wrapper.misc.MCCUseOnContext;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.player.MCCInteractionHand;
import de.verdox.mccreativelab.wrapper.entity.types.MCCItemEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCLivingEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.item.MCCItemType;
import de.verdox.mccreativelab.wrapper.item.components.MCCDataComponentMap;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.typed.MCCDataComponentTypes;
import de.verdox.mccreativelab.wrapper.typed.MCCItems;
import de.verdox.mccreativelab.wrapper.world.MCCInteractionResult;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public abstract class MCCCustomItemType implements MCCItemType {
    private final MCCDataComponentMap standardComponents;
    private ItemVisuals itemVisuals;
    private ItemTextureData itemTextureData;

    public MCCCustomItemType(MCCDataComponentMap standardComponents) {
        this.standardComponents = standardComponents;
    }

    public MCCCustomItemType(Consumer<MCCDataComponentMap> standardComponentSetup) {
        this(MCCPlatform.getInstance().getElementFactory().createEmptyDataComponentMap());
        standardComponentSetup.accept(standardComponents);
    }

    public void linkItemVisuals(ItemVisuals itemVisuals) {
        if (this.itemVisuals != null) {
            throw new IllegalStateException("MCCCustomItemType is already linked");
        }
        this.itemVisuals = itemVisuals;
        this.itemVisuals.seal();
        this.itemTextureData = itemVisuals.asTextureData(key());
    }

    public MCCInteractionResult useOn(MCCUseOnContext context) {
        return MCCInteractionResult.PASS;
    }

    /**
     * Called after a player hurt an entity with this item as a weapon.
     * @param stack the item
     * @param enemy the enemy
     * @param attacker the attacker
     */
    public void postHurtEnemy(MCCItemStack stack, MCCLivingEntity enemy, MCCLivingEntity attacker) {

    }

    /**
     * Called to check if the item is the correct tool to drop loot from the block state
     * @param stack the item used as tool
     * @param state the block state
     * @return the result of the check
     */
    public boolean isCorrectToolForDrops(MCCItemStack stack, MCCBlockState state) {
        return false;
    }

    /**
     * Called when a player interacts (right click) with an entity while holding the item in any hand
     * Try interacting with given entity. Return {@code MCCInteractionResult.PASS} if nothing should happen.
     * <p>
     * @param item the item
     * @param player the player
     * @param target the entity
     * @param usedHand the hand in which the item is being held
     * @return the result of the interaction
     */
    public MCCInteractionResult interactLivingEntity(MCCItemStack item, MCCPlayer player, MCCLivingEntity target, MCCInteractionHand usedHand) {
        return MCCInteractionResult.PASS;
    }

    /**
     * Called each tick as long the {@code ItemStack} is in player's inventory. Used to progress the pickup animation and update maps.
     * @param item the item stack
     * @param level the world
     * @param entity the entity with the item in its inventory
     * @param inventorySlot the slot of the item in the inventory
     * @param isCurrentItem whether the item is currently being held by the entity
     */
    public void inventoryTick(MCCItemStack item, MCCWorld level, MCCEntity entity, int inventorySlot, boolean isCurrentItem) {

    }

    /**
     * Called when the item was crafted by a player
     * @param item the item crafted
     * @param level the world
     * @param player the player
     */
    public void onCraftedBy(MCCItemStack item, MCCWorld level, MCCPlayer player) {

    }

    /**
     * Called when an item was crafted by something else than a player. For now this is only used by a crafter
     * @param item the item
     * @param level the world
     */
    public void onCraftedBySystem(MCCItemStack item, MCCWorld level) {

    }

    /**
     * Called while an item is being used by a living entity. This is only applied when an item is usable and has some behavior defined.
     * @param item The item
     * @param level the world
     * @param livingEntity the entity using the item
     * @param remainingUseDuration the remaining use duration of the item in ticks
     */
    public void onUseTick(MCCItemStack item, MCCWorld level, MCCLivingEntity livingEntity, int remainingUseDuration) {

    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using the Item before the action is complete.
     * @param level the world
     * @param player the player
     * @param hand the hand that the player used the item with
     */
    public void finishUsingItem(MCCWorld level, MCCPlayer player, MCCInteractionHand hand) {

    }

    /**
     * Called when an item is destroyed by external forces in the world
     * @param itemEntity the item entity that was destroyed
     */
    public void onDestroyed(MCCItemEntity itemEntity) {

    }

    @Override
    public final @NotNull MCCItemStack createItem() {
        MCCItemStack stack = itemTextureData.createItem();
        var standardComponents = getItemStandardComponentMap();
        for (MCCDataComponentType<?> mccDataComponentType : getItemStandardComponentMap()) {
            stack.components().copyFrom(mccDataComponentType, standardComponents);
        }
        return stack;
    }

    @Override
    public MCCDataComponentMap getItemStandardComponentMap() {
        return standardComponents;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public final Key getRegistryKey() {
        return MCCGameFactory.ITEM_REGISTRY.key();
    }

    @Override
    @NotNull
    public final Key key() {
        return MCCGameFactory.ITEM_REGISTRY.get().getKey(this);
    }

    @Override
    public final boolean isVanilla() {
        return false;
    }
}
