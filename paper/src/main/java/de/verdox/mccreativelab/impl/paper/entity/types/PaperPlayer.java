package de.verdox.mccreativelab.impl.paper.entity.types;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.impl.vanilla.entity.types.NMSPlayer;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.util.MCCEntityProperty;
import de.verdox.mccreativelab.wrapper.util.MCCTempEntityProperty;
import de.verdox.mccreativelab.wrapper.world.Weather;
import net.minecraft.world.entity.player.Player;
import org.bukkit.WeatherType;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

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
}
