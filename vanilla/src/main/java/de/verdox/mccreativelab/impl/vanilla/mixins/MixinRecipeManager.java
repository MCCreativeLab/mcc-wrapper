package de.verdox.mccreativelab.impl.vanilla.mixins;

import de.verdox.mccreativelab.impl.vanilla.platform.NMSPlatform;
import de.verdox.mccreativelab.impl.vanilla.util.mixin.MutableRecipeManager;
import de.verdox.mccreativelab.impl.vanilla.util.mixin.MutableRecipeMap;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.crafting.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RecipeManager.class)
public abstract class MixinRecipeManager implements MutableRecipeManager {
    @Accessor("recipes")
    public abstract RecipeMap getRecipes();

    @Shadow
    public abstract void finalizeRecipeLoading(FeatureFlagSet enabledFeatures);

    @Shadow
    public abstract Map<ResourceKey<RecipePropertySet>, RecipePropertySet> getSynchronizedItemProperties();

    @Shadow
    public abstract SelectableRecipe.SingleInputSet<StonecutterRecipe> getSynchronizedStonecutterRecipes();

    @Override
    public void mcc_wrapper$addCustomRecipe(RecipeHolder<?> holder) {
        ((MutableRecipeMap) (Object) getRecipes()).mcc_wrapper$addCustomRecipe(holder);
        this.mcc_wrapper$reloadRecipes();
    }

    @Unique
    public void mcc_wrapper$reloadRecipes() {
        MinecraftServer minecraftServer = ((NMSPlatform) MCCPlatform.getInstance()).getServer();
        FeatureFlagSet featureFlagSet = minecraftServer.getPackRepository().getSelectedPacks().stream().map(Pack::getRequestedFeatures).reduce(FeatureFlagSet::join).orElse(FeatureFlagSet.of());
        finalizeRecipeLoading(featureFlagSet);
        PlayerList playerList = minecraftServer.getPlayerList();
        ClientboundUpdateRecipesPacket clientboundUpdateRecipesPacket = new ClientboundUpdateRecipesPacket(getSynchronizedItemProperties(), getSynchronizedStonecutterRecipes());
        for (ServerPlayer serverPlayer : playerList.getPlayers()) {
            serverPlayer.connection.send(clientboundUpdateRecipesPacket);
            serverPlayer.getRecipeBook().sendInitialRecipeBook(serverPlayer);
        }
    }
}
