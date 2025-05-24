package de.verdox.mccreativelab.impl.vanilla.mixins;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.gamefactory.item.MCCCustomItemType;
import de.verdox.mccreativelab.impl.vanilla.gamefactory.proxy.ProxyItem;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
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

import java.util.Objects;
import java.util.Optional;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Unique
    private MCCCustomItemType mcc_wrapper$customItemType;

    @Unique
    private Item mcc_wrapper$cachedItem;

    @Unique
    private ProxyItem mcc_wrapper$proxyItem;

    @Shadow
    public abstract boolean isEmpty();

    @Shadow
    public abstract Item getItem();

    @Shadow
    public abstract DataComponentMap getComponents();

    @Shadow
    public abstract ItemStack copy();

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
        if (mcc_wrapper$hasProxyItem()) {
            return mcc_wrapper$proxyItem().finishUsingItem(stack, level, entity);
        } else {
            return getItem().finishUsingItem(stack, level, entity);
        }
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
        if (mcc_wrapper$hasProxyItem()) {
            return mcc_wrapper$proxyItem().useOn(context);
        } else {
            return getItem().useOn(context);
        }
    }


/*    @Inject(method = "getDestroySpeed", at = @At("HEAD"), cancellable = true)
    public void getDestroySpeed(BlockState state, CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(mcc_wrapper$proxyItem().getDestroySpeed(self(), state));
    }*/

    @Inject(method = "postHurtEnemy", at = @At("HEAD"), cancellable = true)
    public void redirectPostHurtEnemy(LivingEntity enemy, LivingEntity attacker, CallbackInfo ci) {
        if (mcc_wrapper$hasProxyItem()) {
            mcc_wrapper$proxyItem().postHurtEnemy(self(), enemy, attacker);
        } else {
            getItem().postHurtEnemy(self(), enemy, attacker);
        }
        ci.cancel();
    }

    @Inject(method = "isCorrectToolForDrops", at = @At("HEAD"), cancellable = true)
    public void redirectIsCorrectTool(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (mcc_wrapper$hasProxyItem()) {
            cir.setReturnValue(mcc_wrapper$proxyItem().isCorrectToolForDrops(self(), state));
        }
        else {
            cir.setReturnValue(getItem().isCorrectToolForDrops(self(), state));
        }
    }

    @Inject(method = "interactLivingEntity", at = @At("HEAD"), cancellable = true)
    public void redirectInteractLiving(Player player, LivingEntity entity, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (mcc_wrapper$hasProxyItem()) {
            cir.setReturnValue(mcc_wrapper$proxyItem().interactLivingEntity(self(), player, entity, hand));
        }
        else {
            cir.setReturnValue(getItem().interactLivingEntity(self(), player, entity, hand));
        }
    }

    @Inject(method = "inventoryTick", at = @At("HEAD"), cancellable = true)
    public void redirectInventoryTick(Level level, Entity entity, int slot, boolean selected, CallbackInfo ci) {
        if (self().getPopTime() > 0) {
            self().setPopTime(self().getPopTime() - 1);
        }

        if (mcc_wrapper$hasProxyItem()) {
            mcc_wrapper$proxyItem().inventoryTick(self(), level, entity, slot, selected);
        }
        else {
            getItem().inventoryTick(self(), level, entity, slot, selected);
        }
        ci.cancel();
    }

    @Inject(method = "onCraftedBy", at = @At("HEAD"), cancellable = true)
    public void redirectOnCraftedBy(Level level, Player player, int amount, CallbackInfo ci) {
        player.awardStat(Stats.ITEM_CRAFTED.get(getItem()), amount);
        if (mcc_wrapper$hasProxyItem()) {
            mcc_wrapper$proxyItem().onCraftedBy(self(), level, player);
        }
        else {
            getItem().onCraftedBy(self(), level, player);
        }
        ci.cancel();
    }

    @Inject(method = "onCraftedBySystem", at = @At("HEAD"), cancellable = true)
    public void redirectOnCraftedBySystem(Level level, CallbackInfo ci) {
        if (mcc_wrapper$hasProxyItem()) {
            mcc_wrapper$proxyItem().onCraftedPostProcess(self(), level);
        }
        else {
            getItem().onCraftedPostProcess(self(), level);
        }
        ci.cancel();
    }

    @Inject(method = "onUseTick", at = @At("HEAD"), cancellable = true)
    public void redirectOnUseTick(Level level, LivingEntity entity, int remainingUseDuration, CallbackInfo ci) {
        Consumable consumable = self().get(DataComponents.CONSUMABLE);
        if (consumable != null && consumable.shouldEmitParticlesAndSounds(remainingUseDuration)) {
            consumable.emitParticlesAndSounds(entity.getRandom(), entity, self(), 5);
        }

        if(mcc_wrapper$proxyItem().isProxy()) {
            mcc_wrapper$proxyItem().onUseTick(level, entity, self(), remainingUseDuration);
        }
        else {
            this.getItem().onUseTick(level, entity, self(), remainingUseDuration);
        }
        ci.cancel();
    }

    @Inject(method = "onDestroyed", at = @At("HEAD"), cancellable = true)
    public void redirectOnDestroyed(ItemEntity entity, CallbackInfo ci) {
        if(mcc_wrapper$proxyItem().isProxy()) {
            mcc_wrapper$proxyItem().onDestroyed(entity);
        }
        else {
            getItem().onDestroyed(entity);
        }
        ci.cancel();
    }

    @Unique
    private boolean mcc_wrapper$hasProxyItem() {
        MCCCustomItemType customType = mcc_wrapper$extractFromItem();
        return customType != null;
    }

    @Unique
    private ProxyItem mcc_wrapper$proxyItem() {
        MCCCustomItemType customType = mcc_wrapper$extractFromItem();
        if (customType == null) {
            throw new IllegalStateException("MCCCustomItemType was not set");
        }
        if (mcc_wrapper$proxyItem == null) {
            mcc_wrapper$proxyItem = new ProxyItem(getItem(), mcc_wrapper$customItemType);
        }
        return mcc_wrapper$proxyItem;
    }

    @Unique
    @Nullable
    private MCCCustomItemType mcc_wrapper$extractFromItem() {
        if (getItem().equals(mcc_wrapper$cachedItem)) {
            return mcc_wrapper$customItemType;
        }

        mcc_wrapper$cachedItem = getItem();
        Optional<MCCCustomItemType> optionalMCCCustomItemType = MCCPlatform.getInstance().getGameFactory().extract(MCCPlatform.getInstance().getConversionService().wrap(self(), new TypeToken<>() {}));
        mcc_wrapper$customItemType = optionalMCCCustomItemType.orElse(null);
        return mcc_wrapper$customItemType;
    }
}

