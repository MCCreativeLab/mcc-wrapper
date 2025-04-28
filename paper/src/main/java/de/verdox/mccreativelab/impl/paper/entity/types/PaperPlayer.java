package de.verdox.mccreativelab.impl.paper.entity.types;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.impl.vanilla.entity.types.NMSPlayer;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.util.MCCEntityProperty;
import de.verdox.mccreativelab.wrapper.util.MCCTempEntityProperty;
import de.verdox.mccreativelab.wrapper.world.Weather;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.resource.ResourcePackRequest;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.SoundStop;
import net.minecraft.world.entity.player.Player;
import org.bukkit.Bukkit;
import org.bukkit.WeatherType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

public class PaperPlayer extends NMSPlayer {
    public static final MCCConverter<Player, PaperPlayer> CONVERTER = MCCHandle.converter(PaperPlayer.class, Player.class, PaperPlayer::new, MCCHandle::getHandle);

    public PaperPlayer(Player handle) {
        super(handle);
    }

    @Override
    public MCCEntityProperty<Long, MCCPlayer> getTimeProperty() {
        return new MCCEntityProperty<>() {
            @Override
            public @Nullable Long get() {
                return getServerPlayer().getPlayerTime();
            }

            @Override
            public void set(@Nullable Long newValue) {
                Objects.requireNonNull(newValue);
                getServerPlayer().timeOffset = newValue;
                getServerPlayer().relativeTime = false;
            }

            @Override
            public void sync() {
                getServerPlayer().timeOffset = 0;
                getServerPlayer().relativeTime = false;
            }
        };
    }

    @Override
    public MCCEntityProperty<Weather, MCCPlayer> getWeatherProperty() {
        return new MCCEntityProperty<>() {
            @Override
            public @Nullable Weather get() {
                WeatherType weatherType = getServerPlayer().getPlayerWeather();
                return switch (weatherType) {
                    case DOWNFALL -> Weather.RAIN;
                    case CLEAR -> Weather.CLEAR;
                };
            }

            @Override
            public void set(@Nullable Weather newValue) {
                getServerPlayer().setPlayerWeather(newValue.equals(Weather.CLEAR) ? WeatherType.CLEAR : WeatherType.DOWNFALL, true);
            }

            @Override
            public void sync() {
                getServerPlayer().resetPlayerWeather();
            }
        };
    }

    @Override
    public MCCEntityProperty<Boolean, MCCPlayer> getInventoryClickProperty() {
        return new MCCTempEntityProperty<>("mcc:inventory_click_property", Boolean.class, this, true);
    }

    @Override
    public MCCEntityProperty<Boolean, MCCPlayer> getInventoryInteractProperty() {
        return new MCCTempEntityProperty<>("mcc:inventory_interact_property", Boolean.class, this, true);
    }

    @Override
    public MCCEntityProperty<Boolean, MCCPlayer> getSwapHandsProperty() {
        return new MCCTempEntityProperty<>("mcc:inventory_swap_hands", Boolean.class, this, true);
    }

    @Override
    public MCCEntityProperty<Boolean, MCCPlayer> getInteractProperty() {
        return new MCCTempEntityProperty<>("mcc:interact", Boolean.class, this, true);
    }

    @Override
    public void hideBossBar(@NotNull BossBar bar) {
        Bukkit.getPlayer(getUUID()).hideBossBar(bar);
    }

    @Override
    public void showBossBar(@NotNull BossBar bar) {
        Bukkit.getPlayer(getUUID()).showBossBar(bar);
    }

    @Override
    public void removeResourcePacks(@NotNull UUID id, @NotNull UUID @NotNull ... others) {
        Bukkit.getPlayer(getUUID()).removeResourcePacks(id, others);
    }

    @Override
    public void sendResourcePacks(@NotNull ResourcePackRequest request) {
        Bukkit.getPlayer(getUUID()).sendResourcePacks(request);
    }

    @Override
    public void openBook(@NotNull Book book) {
        Bukkit.getPlayer(getUUID()).openBook(book);
    }

    @Override
    public void stopSound(@NotNull SoundStop stop) {
        Bukkit.getPlayer(getUUID()).stopSound(stop);
    }

    @Override
    public void playSound(@NotNull Sound sound, double x, double y, double z) {
        Bukkit.getPlayer(getUUID()).playSound(sound, x, y, z);
    }

    @Override
    public void playSound(@NotNull Sound sound, Sound.@NotNull Emitter emitter) {
        Bukkit.getPlayer(getUUID()).playSound(sound, emitter);
    }
}
