package de.verdox.mccreativelab.impl.vanilla.mixins;

import de.verdox.mccreativelab.gamefactory.item.MCCCustomItemType;
import de.verdox.mccreativelab.impl.vanilla.mixins.proxy.ProxyItem;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Unique
    private MCCCustomItemType customItemType;

    @Unique
    private Item cachedItem;

    @Unique
    private ProxyItem proxyItem;

    @Shadow
    public abstract boolean isEmpty();

    @Shadow
    public abstract Item getItem();

    @Shadow
    public abstract DataComponentMap getComponents();

    @Shadow public abstract ItemStack copy();

    public ItemStack self() {
        return (ItemStack) (Object) this;
    }

    @Redirect(
            method = "finishUsingItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/Item;finishUsingItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private ItemStack replaceFinishUsingItemCall(Item item, ItemStack stack, Level level, LivingEntity entity) {
        return proxyItem().finishUsingItem(stack, level, entity);
    }

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    //TODO: Inject at InteractionResult interactionResult = item.useOn(context);
    public void useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {

    }

    @Redirect(
            method = "useOn",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/Item;useOn(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;"
            )
    )
    private InteractionResult redirectUseOn(Item item, UseOnContext context) {
        return proxyItem().useOn(context);
    }


    @Inject(method = "getDestroySpeed", at = @At("HEAD"))
    public void getDestroySpeed(BlockState state, CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(proxyItem().getDestroySpeed(self(), state));
    }

    @Inject(method = "postHurtEnemy", at = @At("HEAD"), cancellable = true)
    public void redirectPostHurtEnemy(LivingEntity enemy, LivingEntity attacker, CallbackInfo ci) {
        proxyItem().postHurtEnemy(self(), enemy, attacker);
        ci.cancel();
    }

    @Inject(method = "isCorrectToolForDrops", at = @At("HEAD"), cancellable = true)
    public void redirectIsCorrectTool(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(proxyItem().isCorrectToolForDrops(self(), state));
    }

    @Inject(method = "interactLivingEntity", at = @At("HEAD"), cancellable = true)
    public void redirectInteractLiving(Player player, LivingEntity entity, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        cir.setReturnValue(proxyItem().interactLivingEntity(self(), player, entity, hand));
    }

    @Inject(method = "inventoryTick", at = @At("TAIL"))
    public void redirectInventoryTick(Level level, Entity entity, int slot, boolean selected, CallbackInfo ci) {
        if (self().getPopTime() > 0) {
            self().setPopTime(self().getPopTime() - 1);
        }

        if (self().getItem() != null) {
            proxyItem().inventoryTick(self(), level, entity, slot, selected);
        }
    }

    @Inject(method = "onCraftedBy", at = @At("HEAD"), cancellable = true)
    public void redirectOnCraftedBy(Level level, Player player, int amount, CallbackInfo ci) {
        player.awardStat(Stats.ITEM_CRAFTED.get(getItem()), amount);
        proxyItem().onCraftedBy(self(), level, player);
        ci.cancel();
    }

    @Inject(method = "onCraftedBySystem", at = @At("HEAD"), cancellable = true)
    public void redirectOnCraftedBySystem(Level level, CallbackInfo ci) {
        proxyItem().onCraftedPostProcess(self(), level);
        ci.cancel();
    }

    @Inject(method = "onUseTick", at = @At("TAIL"))
    public void redirectOnUseTick(Level level, LivingEntity entity, int remainingUseDuration, CallbackInfo ci) {
        proxyItem().onUseTick(level, entity, self(), remainingUseDuration);
    }

    @Inject(method = "onDestroyed", at = @At("HEAD"), cancellable = true)
    public void redirectOnDestroyed(ItemEntity entity, CallbackInfo ci) {
        proxyItem().onDestroyed(entity);
        ci.cancel();
    }

    private Item proxyItem() {
        MCCCustomItemType customType = extractFromItem();
        if (customType == null) {
            return getItem();
        }

        if (getItem().equals(cachedItem)) {
            return proxyItem;
        }
        ProxyItem proxyItem = new ProxyItem(getItem(), customItemType);
        this.proxyItem = proxyItem;
        return proxyItem;
    }

    @Nullable
    private MCCCustomItemType extractFromItem() {
        if (getItem().equals(cachedItem)) {
            return customItemType;
        }

        cachedItem = getItem();
        Optional<MCCCustomItemType> optionalMCCCustomItemType = MCCPlatform.getInstance().getGameFactory().extract(MCCPlatform.getInstance().getConversionService().wrap(self()));
        customItemType = optionalMCCCustomItemType.orElse(null);
        return customItemType;
    }
}

