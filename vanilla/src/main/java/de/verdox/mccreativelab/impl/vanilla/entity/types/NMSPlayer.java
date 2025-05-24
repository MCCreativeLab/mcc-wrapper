package de.verdox.mccreativelab.impl.vanilla.entity.types;

import com.google.common.base.Preconditions;
import com.google.common.io.BaseEncoding;
import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.impl.vanilla.entity.player.client.NMSSkinParts;
import de.verdox.mccreativelab.reflection.ReflectionUtils;
import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.player.MCCGameMode;
import de.verdox.mccreativelab.wrapper.entity.player.client.MCCClientOption;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.exceptions.OperationNotPossibleOnNMS;
import de.verdox.mccreativelab.wrapper.inventory.MCCContainerCloseReason;
import de.verdox.mccreativelab.wrapper.inventory.MCCContainerMenu;
import de.verdox.mccreativelab.wrapper.inventory.types.container.MCCPlayerInventory;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.platform.cached.signal.ObservedSignal;
import de.verdox.mccreativelab.wrapper.platform.properties.MCCPropertyKey;
import de.verdox.mccreativelab.wrapper.util.MCCEntityMultiProperty;
import de.verdox.mccreativelab.wrapper.util.MCCEntityProperty;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import de.verdox.mccreativelab.wrapper.world.Weather;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.chat.SignedMessage;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.resource.ResourcePackRequest;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.SoundStop;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.protocol.common.ClientboundResourcePackPopPacket;
import net.minecraft.network.protocol.common.ClientboundResourcePackPushPacket;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.*;

public class NMSPlayer extends NMSLivingEntity<Player> implements MCCPlayer {
    public static final MCCConverter<Player, NMSPlayer> CONVERTER = MCCHandle.converter(NMSPlayer.class, Player.class, NMSPlayer::new, MCCHandle::getHandle);

    public NMSPlayer(Player handle) {
        super(handle);
    }

    @Override
    public MCCPlayerInventory getInventory() {
        return MCCPlatform.getInstance().getConversionService().wrap(handle.getInventory(), new TypeToken<>() {});
    }

    @Override
    public String getPlayerName() {
        return handle.getGameProfile().getName();
    }

    @Override
    public void syncInventory() {
        this.getHandle().containerMenu.sendAllDataToRemote();
    }

    @Override
    public MCCEntityProperty<Long, MCCPlayer> getTimeProperty() {
        throw new OperationNotPossibleOnNMS(); // TODO: implement
    }

    @Override
    public MCCEntityProperty<Weather, MCCPlayer> getWeatherProperty() {
        throw new OperationNotPossibleOnNMS();
    }

    @Override
    public MCCEntityMultiProperty<MCCPlayer, MCCPlayer> getHideProperty() {
        throw new OperationNotPossibleOnNMS();
    }

    @Override
    public MCCEntityProperty<Boolean, MCCPlayer> getInventoryClickProperty() {
        throw new OperationNotPossibleOnNMS();
    }

    @Override
    public MCCEntityProperty<Boolean, MCCPlayer> getInventoryInteractProperty() {
        throw new OperationNotPossibleOnNMS();
    }

    @Override
    public MCCEntityProperty<Boolean, MCCPlayer> getSwapHandsProperty() {
        throw new OperationNotPossibleOnNMS();
    }

    @Override
    public MCCEntityProperty<MCCGameMode, MCCPlayer> getGameModeProperty() {


        return new MCCEntityProperty<>() {
            @Override
            public @Nullable MCCGameMode get() {
                return conversionService.wrap(getServerPlayer().gameMode.getGameModeForPlayer(), new TypeToken<>() {});
            }

            @Override
            public void set(@Nullable MCCGameMode newValue) {
                GameType gameMode = conversionService.unwrap(newValue, GameType.class);
                getServerPlayer().setGameMode(gameMode);
            }

            @Override
            public void sync() {

            }
        };
    }

    @Override
    public @Nullable MCCGameMode getPreviousGameMode() {
        return conversionService.wrap(getServerPlayer().gameMode.getPreviousGameModeForPlayer(), new TypeToken<>() {});
    }

    @Override
    public MCCEntityProperty<Boolean, MCCPlayer> getInteractProperty() {
        throw new OperationNotPossibleOnNMS();
    }

    @Override
    public boolean isOnline() {
        return !getServerPlayer().hasDisconnected();
    }

    @Override
    public void setResourcePack(UUID uuid, String downloadURL, byte[] bytes, @Nullable Component prompt, boolean required) {
        Preconditions.checkArgument(uuid != null, "Resource pack ID cannot be null");
        Preconditions.checkArgument(downloadURL != null, "Resource pack URL cannot be null");

        String hashStr = "";
        if (bytes != null) {
            Preconditions.checkArgument(bytes.length == 20, "Resource pack hash should be 20 bytes long but was %s", bytes.length);
            hashStr = BaseEncoding.base16().lowerCase().encode(bytes);
        }

        getServerPlayer().connection.send(new ClientboundResourcePackPopPacket(Optional.empty()));
        getServerPlayer().connection.send(new ClientboundResourcePackPushPacket(uuid, downloadURL, hashStr, required, Optional.ofNullable(conversionService.unwrap(prompt, new TypeToken<>() {}))));
    }

    @Override
    public void sendBlockDamage(@NotNull MCCLocation location, @Range(from = 0, to = 1) float progress, int entityId) {
        Preconditions.checkArgument(location != null, "loc must not be null");
        Preconditions.checkArgument(progress >= 0.0 && progress <= 1.0, "progress must be between 0.0 and 1.0 (inclusive)");

        if (getServerPlayer().connection == null) {
            return;
        }

        int stage = (int) (9 * progress); // There are 0 - 9 damage states
        if (progress == 0.0F) {
            stage = -1; // The protocol states that any other value will reset the damage, which this API promises
        }

        ClientboundBlockDestructionPacket packet = new ClientboundBlockDestructionPacket(entityId, new BlockPos(location.blockX(), location.blockY(), location.blockZ()), stage);
        getServerPlayer().connection.send(packet);
    }

    @Override
    public boolean breakBlock(MCCBlock block) {
        Preconditions.checkArgument(block != null, "Block cannot be null");
        Preconditions.checkArgument(block.getLocation().world().equals(this.getLocation().world()), "Cannot break blocks across worlds");

        return getServerPlayer().gameMode.destroyBlock(new BlockPos(getLocation().blockX(), getLocation().blockY(), getLocation().blockZ()));
    }

    @Override
    public <T> T getClientOption(MCCClientOption<T> type) {
        if (MCCClientOption.SKIN_PARTS == type) {
            return type.getType().cast(new NMSSkinParts(getHandle()));
        } else if (MCCClientOption.CHAT_COLORS_ENABLED == type) {
            return type.getType().cast(getServerPlayer().canChatInColor());
        } else if (MCCClientOption.CHAT_VISIBILITY == type) {
            return type.getType().cast(getServerPlayer().getChatVisibility() == null ? MCCClientOption.ChatVisibility.UNKNOWN : MCCClientOption.ChatVisibility.valueOf(getServerPlayer().getChatVisibility().name()));
        } else if (MCCClientOption.LOCALE == type) {
            return type.getType().cast(getLocale());
        } else if (MCCClientOption.MAIN_HAND == type) {
            return type.getType().cast(getMainHand());
        } else if (MCCClientOption.VIEW_DISTANCE == type) {
            return type.getType().cast(getClientViewDistance());
        } else if (MCCClientOption.ALLOW_SERVER_LISTINGS == type) {
            return type.getType().cast(getServerPlayer().allowsListing());
        } else if (MCCClientOption.TEXT_FILTERING_ENABLED == type) {
            return type.getType().cast(getServerPlayer().isTextFilteringEnabled());
        }
        throw new RuntimeException("Unknown settings type");
    }

    @Override
    public boolean hasCorrectToolForDrops(MCCBlockState state) {
        return handle.hasCorrectToolForDrops(conversionService.unwrap(state, new TypeToken<>() {}));
    }

    @Override
    public void setCamera(@Nullable MCCEntity entityToSpectate) {
        getServerPlayer().setCamera(conversionService.unwrap(entityToSpectate, Entity.class));
    }

    @Override
    @Nullable
    public MCCEntity getCamera() {
        return conversionService.wrap(getServerPlayer().getCamera(), MCCEntity.class);
    }

    @Override
    public void closeCurrentInventory(MCCContainerCloseReason closeReason) {
        handle.containerMenu = handle.inventoryMenu;
    }

    @Override
    public @Nullable MCCContainerMenu<?, ?> getCurrentlyViewedInventory() {
        return conversionService.wrap(handle.containerMenu, new TypeToken<>() {});
    }

    @Override
    public MCCItemStack getCursorItem() {
        return conversionService.wrap(this.getHandle().containerMenu.getCarried(), new TypeToken<>() {});
    }

    @Override
    public void setCursorItem(@Nullable MCCItemStack cursorItem) {
        ItemStack stack = conversionService.unwrap(cursorItem);
        this.getHandle().containerMenu.setCarried(stack);

        if (this.getHandle() instanceof ServerPlayer player) {
            player.connection.send(new ClientboundSetCursorItemPacket(stack));
        }
    }

    protected ServerPlayer getServerPlayer() {
        return (ServerPlayer) getHandle();
    }

    private String getLocale() {
        final String locale = getServerPlayer().clientInformation().language();
        return locale != null ? locale : "en_us";
    }

    // TODO: check if this casts correctly
    private MCCClientOption.MainHand getMainHand() {
        return this.getHandle().getMainArm() == HumanoidArm.LEFT ? MCCClientOption.MainHand.LEFT : MCCClientOption.MainHand.RIGHT;
    }


    private int getClientViewDistance() {
        return (getServerPlayer().requestedViewDistance() == 0) ? MCCPlatform.getInstance().getServerProperties().read(MCCPropertyKey.VIEW_DISTANCE) : getServerPlayer().requestedViewDistance();
    }

    @Override
    public net.kyori.adventure.pointer.Pointers pointers() {
        var locale = Objects.requireNonNullElse(net.kyori.adventure.translation.Translator.parseLocale(getServerPlayer().clientInformation().language()), java.util.Locale.US); // Paper
        if (this.adventurePointer == null) {
            this.adventurePointer = net.kyori.adventure.pointer.Pointers.builder()
                    .withDynamic(net.kyori.adventure.identity.Identity.DISPLAY_NAME, this::displayName)
                    .withDynamic(net.kyori.adventure.identity.Identity.UUID, this::getUUID)
                    .withStatic(net.kyori.adventure.permission.PermissionChecker.POINTER, permission -> getPermissions().permissionValue(permission))
                    .withDynamic(net.kyori.adventure.identity.Identity.NAME, this::getPlayerName)
                    .withDynamic(net.kyori.adventure.identity.Identity.LOCALE, () -> locale)
                    .build();
        }

        return this.adventurePointer;
    }

    @Override
    public void sendMessage(@NotNull Identity source, @NotNull Component message, @NotNull MessageType type) {
        if (getServerPlayer().connection != null) {
            Registry<ChatType> chatTypeRegistry = this.getHandle().level().registryAccess().lookupOrThrow(Registries.CHAT_TYPE);
            getServerPlayer().connection.send(new ClientboundSystemChatPacket(conversionService.unwrap(message), false));
        }
    }

    @Override
    public void sendActionBar(@NotNull Component message) {
        ClientboundSetActionBarTextPacket packet = new ClientboundSetActionBarTextPacket(conversionService.unwrap(message));
        getServerPlayer().connection.send(packet);
    }

    @Override
    public void sendPlayerListHeader(@NotNull Component header) {
        ClientboundTabListPacket packet = new ClientboundTabListPacket(conversionService.unwrap(header), CommonComponents.EMPTY);
        getServerPlayer().connection.send(packet);
    }

    @Override
    public void sendPlayerListFooter(@NotNull Component footer) {
        ClientboundTabListPacket packet = new ClientboundTabListPacket(CommonComponents.EMPTY, conversionService.unwrap(footer));
        getServerPlayer().connection.send(packet);
    }

    @Override
    public void sendPlayerListHeaderAndFooter(@NotNull Component header, @NotNull Component footer) {
        ClientboundTabListPacket packet = new ClientboundTabListPacket(conversionService.unwrap(header), conversionService.unwrap(footer));
        getServerPlayer().connection.send(packet);
    }

    @Override
    public void showTitle(@NotNull Title title) {
        ServerGamePacketListenerImpl connection = getServerPlayer().connection;
        net.kyori.adventure.title.Title.Times times = title.times();
        if (times != null) {
            connection.send(new ClientboundSetTitlesAnimationPacket(ticks(times.fadeIn()), ticks(times.stay()), ticks(times.fadeOut())));
        }

        ClientboundSetSubtitleTextPacket sp = new ClientboundSetSubtitleTextPacket(conversionService.unwrap(title.subtitle()));
        connection.send(sp);
        ClientboundSetTitleTextPacket tp = new ClientboundSetTitleTextPacket(conversionService.unwrap(title.title()));
        connection.send(tp);
    }

    @Override
    public <T> void sendTitlePart(@NotNull TitlePart<T> part, @NotNull T value) {
        Objects.requireNonNull(part, "part");
        Objects.requireNonNull(value, "value");
        if (part == TitlePart.TITLE) {
            ClientboundSetTitleTextPacket tp = new ClientboundSetTitleTextPacket(conversionService.unwrap(value, net.minecraft.network.chat.Component.class));
            getServerPlayer().connection.send(tp);
        } else if (part == TitlePart.SUBTITLE) {
            ClientboundSetSubtitleTextPacket sp = new ClientboundSetSubtitleTextPacket(conversionService.unwrap(value, net.minecraft.network.chat.Component.class));
            getServerPlayer().connection.send(sp);
        } else {
            if (part != TitlePart.TIMES) {
                throw new IllegalArgumentException("Unknown TitlePart");
            }

            net.kyori.adventure.title.Title.Times times = (net.kyori.adventure.title.Title.Times) value;
            getServerPlayer().connection.send(new ClientboundSetTitlesAnimationPacket(ticks(times.fadeIn()), ticks(times.stay()), ticks(times.fadeOut())));
        }

    }

    @Override
    public void clearTitle() {
        getServerPlayer().connection.send(new ClientboundClearTitlesPacket(false));
    }

    @Override
    public void hideBossBar(@NotNull BossBar bar) {
        throw new OperationNotPossibleOnNMS();
    }

    @Override
    public void showBossBar(@NotNull BossBar bar) {
        throw new OperationNotPossibleOnNMS();
    }

    @Override
    public void playSound(@NotNull Sound sound, double x, double y, double z) {
        throw new OperationNotPossibleOnNMS("Not implemented yet.");
    }

    @Override
    public void playSound(@NotNull Sound sound, Sound.@NotNull Emitter emitter) {
        throw new OperationNotPossibleOnNMS("Not implemented yet.");
    }

    @Override
    public void playSound(@NotNull Sound sound) {
        Vec3 pos = this.getHandle().position();
        this.playSound(sound, pos.x, pos.y, pos.z);
    }

    @Override
    public void deleteMessage(SignedMessage.@NotNull Signature signature) {
        if (getServerPlayer().connection != null) {
            MessageSignature sig = new MessageSignature(signature.bytes());
            getServerPlayer().connection.send(new ClientboundDeleteChatPacket(new MessageSignature.Packed(sig)));
        }
    }

    @Override
    public void resetTitle() {
        ClientboundClearTitlesPacket packetReset = new ClientboundClearTitlesPacket(true);
        getServerPlayer().connection.send(packetReset);
    }

    @Override
    public void stopSound(@NotNull SoundStop stop) {
        throw new OperationNotPossibleOnNMS("Not implemented yet.");
    }

    @Override
    public void openBook(@NotNull Book book) {
        throw new OperationNotPossibleOnNMS("Not implemented yet.");
    }

    @Override
    public void sendResourcePacks(@NotNull ResourcePackRequest request) {
        throw new OperationNotPossibleOnNMS();
    }

    @Override
    public void removeResourcePacks(@NotNull UUID id, @NotNull UUID @NotNull ... others) {
        throw new OperationNotPossibleOnNMS();
    }

    @Override
    public void clearResourcePacks() {
        if (getServerPlayer().connection != null) {
            getServerPlayer().connection.send(new ClientboundResourcePackPopPacket(Optional.empty()));
        }
    }

    private static int ticks(Duration duration) {
        return duration == null ? -1 : (int) (duration.toMillis() / 50L);
    }
}
