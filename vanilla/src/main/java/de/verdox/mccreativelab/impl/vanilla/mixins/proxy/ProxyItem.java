package de.verdox.mccreativelab.impl.vanilla.mixins.proxy;

import de.verdox.mccreativelab.gamefactory.item.MCCCustomItemType;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.player.MCCInteractionHand;
import de.verdox.mccreativelab.wrapper.entity.types.MCCItemEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCLivingEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.misc.MCCUseOnContext;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class ProxyItem extends Item implements GameProxy {
    private final Item delegate;
    private final MCCCustomItemType customItemType;

    public ProxyItem(Item delegate, MCCCustomItemType customItemType) {
        super(new Properties());
        this.delegate = delegate;
        this.customItemType = customItemType;
    }

    /**
     * Called when this item is used when targeting a Block
     */
    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        return proxy(
                delegate::useOn,
                param(context, MCCUseOnContext.class),
                (c) -> {
                    return getProxy().useOn(c);
                }
        );
    }

    @Override
    public void postHurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        proxy(
                delegate::postHurtEnemy,
                param(stack, MCCItemStack.class),
                param(target, MCCLivingEntity.class),
                param(attacker, MCCLivingEntity.class),
                (item, targetEntity, attackerEntity) -> getProxy().postHurtEnemy(item, targetEntity, attackerEntity)
        );
    }

    @Override
    public boolean isCorrectToolForDrops(@NotNull ItemStack stack, @NotNull BlockState state) {
        var result = delegate.isCorrectToolForDrops(stack, state);
        if (result) {
            return true;
        }
        return getProxy().isCorrectToolForDrops(conversionService().wrap(stack, MCCItemStack.class), conversionService().wrap(state, MCCBlockState.class));
    }

    @Override
    public InteractionResult interactLivingEntity(@NotNull ItemStack stack, @NotNull Player player, @NotNull LivingEntity interactionTarget, @NotNull InteractionHand usedHand) {
        return proxy(
                delegate::interactLivingEntity,
                param(stack, MCCItemStack.class),
                param(player, MCCPlayer.class),
                param(interactionTarget, MCCLivingEntity.class),
                param(usedHand, MCCInteractionHand.class),
                (item, mccPlayer, targetEntity, interactionHand) -> {
                    return getProxy().interactLivingEntity(item, mccPlayer, targetEntity, interactionHand);
                }
        );
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        proxy(
                delegate::inventoryTick,
                param(stack, MCCItemStack.class),
                param(level, MCCWorld.class),
                param(entity, MCCEntity.class),
                param(slotId),
                param(isSelected),
                (item, world, holdingEntity, slot, selected) -> getProxy().inventoryTick(item, world, holdingEntity, slot, selected)
        );
    }

    @Override
    public void onCraftedBy(@NotNull ItemStack stack, @NotNull Level level, @NotNull Player player) {
        proxy(
                delegate::onCraftedBy,
                param(stack, MCCItemStack.class),
                param(level, MCCWorld.class),
                param(player, MCCPlayer.class),
                (item, world, mccPlayer) -> getProxy().onCraftedBy(item, world, mccPlayer)
        );
    }

    @Override
    public void onCraftedPostProcess(@NotNull ItemStack stack, @NotNull Level level) {
        proxy(
                delegate::onCraftedPostProcess,
                param(stack, MCCItemStack.class),
                param(level, MCCWorld.class),
                (item, world) -> getProxy().onCraftedBySystem(item, world)
        );
    }


    @Override
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity livingEntity, @NotNull ItemStack stack, int remainingUseDuration) {
        proxy(
                delegate::onUseTick,
                param(level, MCCWorld.class),
                param(livingEntity, MCCLivingEntity.class),
                param(stack, MCCItemStack.class),
                param(remainingUseDuration),
                (world, entity, item, useDurationRemaining) -> getProxy().onUseTick(item, world, entity, useDurationRemaining)
        );
    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using the Item before the action is complete.
     */
    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity livingEntity) {
        try {
            return delegate.finishUsingItem(stack, level, livingEntity);
        } finally {
            if (isProxy()) {
                getProxy().finishUsingItem(getConversionService().wrap(stack), getConversionService().wrap(level), getConversionService().wrap(livingEntity));
            }
        }
    }

    @Override
    public void onDestroyed(@NotNull ItemEntity itemEntity) {
        proxy(
                delegate::onDestroyed,
                param(itemEntity, MCCItemEntity.class),
                (item) -> getProxy().onDestroyed(item)
        );
    }

    /**
     * Checks if a proxy exists
     * @return true if a proxy exists
     */
    @Override
    public boolean isProxy() {
        return customItemType != null;
    }

    /**
     * Returns the proxy for the specific call. Please check if it has a proxy using isProxy method
     */
    @NotNull
    private MCCCustomItemType getProxy() {
        return Objects.requireNonNull(customItemType);
    }
}
