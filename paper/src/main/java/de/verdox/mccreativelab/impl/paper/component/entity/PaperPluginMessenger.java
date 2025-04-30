package de.verdox.mccreativelab.impl.paper.component.entity;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.component.AbstractComponent;
import de.verdox.mccreativelab.wrapper.component.entity.MCCPluginMessenger;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import net.kyori.adventure.key.Key;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.DiscardedPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class PaperPluginMessenger extends AbstractComponent<Player, MCCPlayer> implements MCCPluginMessenger {
    public PaperPluginMessenger(MCCPlayer player) {
        super(player, new TypeToken<>() {});
    }

    @Override
    public void sendPluginMessage(Key channel, byte[] message) {
        ResourceLocation id = conversionService.unwrap(channel, ResourceLocation.class);
        ClientboundCustomPayloadPacket packet = new ClientboundCustomPayloadPacket(new DiscardedPayload(id, message));
        ((ServerPlayer) this.getHandle()).connection.send(packet);
    }
}
