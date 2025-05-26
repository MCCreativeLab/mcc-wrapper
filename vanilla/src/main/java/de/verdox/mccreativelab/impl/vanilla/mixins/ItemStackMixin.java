package de.verdox.mccreativelab.impl.vanilla.mixins;

import de.verdox.mccreativelab.gamefactory.MCCGameFactory;
import de.verdox.mccreativelab.gamefactory.item.MCCCustomItemType;
import de.verdox.mccreativelab.impl.vanilla.gamefactory.proxy.ProxyItem;
import de.verdox.mccreativelab.impl.vanilla.util.mixin.ItemStackWithCustomType;
import net.kyori.adventure.key.Key;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ItemStackWithCustomType {

    @Unique
    private MCCCustomItemType mcc_wrapper$customItemType;

    @Unique
    private boolean mcc_wrapper$loadedCustomItemType = false;

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

    @Final
    @Shadow
    PatchedDataComponentMap components;

    @Unique
    public ItemStack self() {
        return (ItemStack) (Object) this;
    }

    @Unique
    @Override
    public MCCCustomItemType getMcc_wrapper$customItemType() {
        if (mcc_wrapper$customItemType == null && !mcc_wrapper$loadedCustomItemType) {
            mcc_wrapper$loadedCustomItemType = true;
            if (this.components.has(DataComponents.CUSTOM_DATA)) {
                CompoundTag compoundTag = this.components.get(DataComponents.CUSTOM_DATA).copyTag();
                if (compoundTag.contains("mcc.customItemType")) {
                    try {
                        Key key = Key.key(compoundTag.getString("mcc.customItemType"));
                        var found = MCCGameFactory.ITEM_REGISTRY.get().getOptional(key).orElse(null);
                        setMcc_wrapper$customItemType(found);
                        return found;
                    } catch (Exception e) {
                        MCCGameFactory.LOGGER.log(java.util.logging.Level.SEVERE, "Could not extract a custom item type", e);
                    }
                }
            }
        }
        return mcc_wrapper$customItemType;
    }

    @Unique
    @Override
    public void setMcc_wrapper$customItemType(MCCCustomItemType mcc_wrapper$customItemType) {
        this.mcc_wrapper$customItemType = mcc_wrapper$customItemType;
        mcc_wrapper$loadedCustomItemType = true;
        CustomData data;
        if (this.components.has(DataComponents.CUSTOM_DATA)) {
            data = this.components.get(DataComponents.CUSTOM_DATA).update(compoundTag -> compoundTag.putString("mcc.customItemType", mcc_wrapper$customItemType.key().asString()));
        } else {
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.putString("mcc.customItemType", mcc_wrapper$customItemType.key().asString());
            data = CustomData.of(compoundTag);
        }
        this.components.set(DataComponents.CUSTOM_DATA, data);
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
        } else {
            cir.setReturnValue(getItem().isCorrectToolForDrops(self(), state));
        }
    }

    @Inject(method = "interactLivingEntity", at = @At("HEAD"), cancellable = true)
    public void redirectInteractLiving(Player player, LivingEntity entity, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (mcc_wrapper$hasProxyItem()) {
            cir.setReturnValue(mcc_wrapper$proxyItem().interactLivingEntity(self(), player, entity, hand));
        } else {
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
        } else {
            getItem().inventoryTick(self(), level, entity, slot, selected);
        }
        ci.cancel();
    }

    @Inject(method = "onCraftedBy", at = @At("HEAD"), cancellable = true)
    public void redirectOnCraftedBy(Level level, Player player, int amount, CallbackInfo ci) {
        player.awardStat(Stats.ITEM_CRAFTED.get(getItem()), amount);
        if (mcc_wrapper$hasProxyItem()) {
            mcc_wrapper$proxyItem().onCraftedBy(self(), level, player);
        } else {
            getItem().onCraftedBy(self(), level, player);
        }
        ci.cancel();
    }

    @Inject(method = "onCraftedBySystem", at = @At("HEAD"), cancellable = true)
    public void redirectOnCraftedBySystem(Level level, CallbackInfo ci) {
        if (mcc_wrapper$hasProxyItem()) {
            mcc_wrapper$proxyItem().onCraftedPostProcess(self(), level);
        } else {
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

        if (mcc_wrapper$proxyItem().isProxy()) {
            mcc_wrapper$proxyItem().onUseTick(level, entity, self(), remainingUseDuration);
        } else {
            this.getItem().onUseTick(level, entity, self(), remainingUseDuration);
        }
        ci.cancel();
    }

    @Inject(method = "onDestroyed", at = @At("HEAD"), cancellable = true)
    public void redirectOnDestroyed(ItemEntity entity, CallbackInfo ci) {
        if (mcc_wrapper$proxyItem().isProxy()) {
            mcc_wrapper$proxyItem().onDestroyed(entity);
        } else {
            getItem().onDestroyed(entity);
        }
        ci.cancel();
    }

    @Unique
    private boolean mcc_wrapper$hasProxyItem() {
        return mcc_wrapper$customItemType != null;
    }

    @Unique
    private ProxyItem mcc_wrapper$proxyItem() {
        if (mcc_wrapper$customItemType == null) {
            throw new IllegalStateException("MCCCustomItemType was not set");
        }
        if (mcc_wrapper$proxyItem == null) {
            mcc_wrapper$proxyItem = new ProxyItem(getItem(), mcc_wrapper$customItemType);
        }
        return mcc_wrapper$proxyItem;
    }
}

