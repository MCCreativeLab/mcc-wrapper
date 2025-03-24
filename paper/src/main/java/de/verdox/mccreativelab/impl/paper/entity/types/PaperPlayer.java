package de.verdox.mccreativelab.impl.paper.entity.types;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.impl.vanilla.entity.types.NMSPlayer;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.util.MCCEntityProperty;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class PaperPlayer extends NMSPlayer {
    public static final MCCConverter<Player, PaperPlayer> CONVERTER = MCCHandle.converter(PaperPlayer.class, Player.class, PaperPlayer::new, MCCHandle::getHandle);

    public PaperPlayer(Player handle) {
        super(handle);
    }

    @Override
    public MCCEntityProperty<MCCItemStack, MCCPlayer> getCursorProperty() {
        return new MCCEntityProperty<>() {
            @Override
            public @Nullable MCCItemStack get() {
                return MCCPlatform.getInstance().getConversionService().wrap(getServerPlayer().containerMenu.getCarried(), new TypeToken<>() {});
            }

            @Override
            public void set(@Nullable MCCItemStack newValue) {
                ItemStack stack = MCCPlatform.getInstance().getConversionService().unwrap(newValue, new TypeToken<>() {});
                getServerPlayer().containerMenu.setCarried(stack);
                getServerPlayer().containerMenu.broadcastCarriedItem();
            }

            @Override
            public void sync() {

            }
        };
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
}
