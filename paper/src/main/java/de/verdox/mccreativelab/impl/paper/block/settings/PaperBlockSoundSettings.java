package de.verdox.mccreativelab.impl.paper.block.settings;

import de.verdox.mccreativelab.impl.paper.platform.converter.BukkitAdapter;
import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.block.MCCBlockSoundGroup;
import de.verdox.mccreativelab.wrapper.block.MCCBlockType;
import de.verdox.mccreativelab.wrapper.block.settings.MCCBlockSoundSettings;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.typed.MCCBlockTags;
import de.verdox.mccreativelab.wrapper.util.DataHolderPredicate;
import io.papermc.paper.event.player.PlayerArmSwingEvent;
import net.kyori.adventure.sound.Sound;
import org.bukkit.GameEvent;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.GenericGameEvent;

import java.util.HashMap;
import java.util.Map;

public class PaperBlockSoundSettings implements MCCBlockSoundSettings, Listener {
    public static final DataHolderPredicate.TickDelay DIGGING_SOUND_DELAY = new DataHolderPredicate.TickDelay("DiggingSoundDelay", 4);

    private final Map<MCCBlockType, MCCBlockSoundGroup> soundGroup = new HashMap<>();
    private final Map<MCCBlockSoundGroup, MCCBlockSoundGroup> swapped = new HashMap<>();

    @Override
    public void setSoundGroup(MCCBlockType blockType, MCCBlockSoundGroup mccBlockSoundGroup) {
        soundGroup.put(blockType, mccBlockSoundGroup);
    }

    @Override
    public MCCBlockSoundGroup getSoundGroup(MCCBlockType mccBlockType) {

        MCCBlockSoundGroup standard = mccBlockType.getBlockProperties().soundType();
        if (swapped.containsKey(standard)) {
            standard = swapped.get(standard);
        }

        return soundGroup.getOrDefault(mccBlockType, standard);
    }

    @Override
    public void swapVanillaSoundGroup(MCCBlockSoundGroup vanillaSoundGroup, MCCBlockSoundGroup customSoundGroup) {
        vanillaSoundGroup.requireVanilla();
        swapped.put(vanillaSoundGroup, customSoundGroup);
    }

    private void simulateDiggingSound(MCCEntity player, MCCBlock block) {
        if (!hasCustomSoundGroup(block.getBlockType())) {
            return;
        }
        if (!DIGGING_SOUND_DELAY.isAllowed(player)) {
            return;
        }
        MCCBlockSoundGroup blockSoundGroup = getSoundGroup(block.getBlockType());

        Sound soundToPlay = Sound.sound(blockSoundGroup.hitSound())
                .source(Sound.Source.BLOCK)
                .volume(0.15F)
                .pitch(blockSoundGroup.getPitch() * 0.3F)
                .build();
        player.playSound(soundToPlay, block.getLocation().x(), block.getLocation().y(), block.getLocation().z());
        DIGGING_SOUND_DELAY.reset(player);
    }

    private void simulateBreakSound(MCCEntity player, MCCBlock block) {
        if (!hasCustomSoundGroup(block.getBlockType())) {
            return;
        }
        MCCBlockSoundGroup blockSoundGroup = getSoundGroup(block.getBlockType());

        Sound soundToPlay = Sound.sound(blockSoundGroup.breakSound())
                .source(Sound.Source.BLOCK)
                .volume((blockSoundGroup.getVolume() + 1.0F) / 2.0F)
                .pitch(blockSoundGroup.getPitch() * 0.8F)
                .build();
        player.playSound(soundToPlay, block.getLocation().x(), block.getLocation().y(), block.getLocation().z());
    }

    private void simulateBlockPlaceSound(MCCEntity player, MCCBlock block) {
        if (!hasCustomSoundGroup(block.getBlockType())) {
            return;
        }
        MCCBlockSoundGroup blockSoundGroup = getSoundGroup(block.getBlockType());

        Sound soundToPlay = Sound.sound(blockSoundGroup.placeSound())
                .source(Sound.Source.BLOCK)
                .volume((blockSoundGroup.getVolume() + 1.0F) / 2.0F)
                .pitch(blockSoundGroup.getPitch() * 0.8F)
                .build();
        player.playSound(soundToPlay, block.getLocation().x(), block.getLocation().y(), block.getLocation().z());
    }

    private void simulateFallSound(MCCEntity player, MCCBlock block) {
        if (!hasCustomSoundGroup(block.getBlockType())) {
            return;
        }
        MCCBlockSoundGroup blockSoundGroup = getSoundGroup(block.getBlockType());

        Sound soundToPlay = Sound.sound(blockSoundGroup.fallSound())
                .source(Sound.Source.BLOCK)
                .volume((blockSoundGroup.getVolume() + 1.0F) / 2.0F)
                .pitch(blockSoundGroup.getPitch() * 0.8F)
                .build();
        player.playSound(soundToPlay, block.getLocation().x(), block.getLocation().y(), block.getLocation().z());
    }

    private void simulateStepSound(MCCEntity player, MCCBlock block) {
        if (!hasCustomSoundGroup(block.getBlockType())) {
            return;
        }
        MCCBlockSoundGroup blockSoundGroup;
        float volume;
        float pitch;

        // Running on carpet / snow / -> play the sound of the block below the snow block
        if (MCCBlockTags.COMBINATION_STEP_SOUND_BLOCKS.contains(block.getBlockType())) {
            MCCBlock blockProducingSound = block.getLocation().below().getBlockNow();
            blockSoundGroup = getSoundGroup(blockProducingSound.getBlockType());
            volume = (blockSoundGroup.getVolume() + 1.0F) / 3.0F;
            pitch = blockSoundGroup.getPitch() * 0.8F;
        } else {
            blockSoundGroup = getSoundGroup(block.getBlockType());
            volume = (blockSoundGroup.getVolume() + 1.0F) / 2.0F;
            pitch = blockSoundGroup.getPitch() * 0.8F;
        }
        Sound soundToPlay = Sound.sound(blockSoundGroup.stepSound())
                .source(Sound.Source.BLOCK)
                .volume(volume)
                .pitch(pitch)
                .build();
        player.playSound(soundToPlay, block.getLocation().x(), block.getLocation().y(), block.getLocation().z());
    }

    @EventHandler
    public void gameEvent(GenericGameEvent genericGameEvent) {
        MCCBlock block = BukkitAdapter.toMcc(genericGameEvent.getLocation().getBlock());
        MCCEntity entity = BukkitAdapter.toMcc(genericGameEvent.getEntity());

        if (genericGameEvent.getEvent().equals(GameEvent.BLOCK_PLACE)) {
            simulateBlockPlaceSound(entity, block);
        } else if (genericGameEvent.getEvent().equals(GameEvent.BLOCK_DESTROY)) {
            simulateBreakSound(entity, block);
        } else if (genericGameEvent.getEvent().equals(GameEvent.HIT_GROUND)) {
            simulateFallSound(entity, block);
        } else if (genericGameEvent.getEvent().equals(GameEvent.STEP)) {
            simulateStepSound(entity, block);
        }
    }

    @EventHandler
    public void resetDiggingDelayOnBlockBreak(BlockBreakEvent e) {
        MCCPlayer player = BukkitAdapter.toMcc(e.getPlayer());
        DIGGING_SOUND_DELAY.reset(player);
    }


    // The gameEvent method will not be called when the block break was cancelled. However, the client will predict a block break and needs a sound anyway.
    @EventHandler(priority = EventPriority.MONITOR)
    public void playBreakSoundEvenWhenBreakCancelled(BlockBreakEvent e) {
        // If the block break was not cancelled we do not need to simulate that here
        if (!e.isCancelled()) {
            return;
        }

        MCCBlock block = BukkitAdapter.toMcc(e.getBlock());
        MCCPlayer player = BukkitAdapter.toMcc(e.getPlayer());

        simulateBreakSound(player, block);
    }

    @EventHandler
    public void storeLastPlayerInteraction(PlayerInteractEvent e) {
        MCCPlayer player = BukkitAdapter.toMcc(e.getPlayer());
        if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            player.getTempData().storeData("last_hit_block", e.getClickedBlock());
        } else {
            player.getTempData().removeData("last_hit_block");
        }
    }

    @EventHandler
    public void simulateDiggingSoundOnArmSwing(PlayerArmSwingEvent e) {
        Player bukkitPlayer = e.getPlayer();
        if (!e.getHand().isHand())
            return;
        if (bukkitPlayer.hasMetadata("last_click_action")) {

            Action action = (Action) bukkitPlayer.getMetadata("last_click_action").get(0).value();
            if (action != null && action.isRightClick())
                return;
        }

        Block bukkitTargetBlock = bukkitPlayer.getTargetBlockExact((int) bukkitPlayer.getAttribute(Attribute.BLOCK_INTERACTION_RANGE).getValue());
        if (bukkitTargetBlock == null) {
            return;
        }

        MCCBlock block = BukkitAdapter.toMcc(bukkitTargetBlock);
        MCCPlayer player = BukkitAdapter.toMcc(bukkitPlayer);
        simulateDiggingSound(player, block);
    }
}
