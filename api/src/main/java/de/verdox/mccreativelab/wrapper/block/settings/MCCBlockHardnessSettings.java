package de.verdox.mccreativelab.wrapper.block.settings;

import de.verdox.mccreativelab.wrapper.annotations.MCCLogic;
import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.block.MCCBlockType;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.item.MCCAttributeModifier;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.typed.MCCAttributes;
import de.verdox.mccreativelab.wrapper.typed.MCCEffects;
import de.verdox.mccreativelab.wrapper.util.DataHolderPredicate;
import net.kyori.adventure.key.Key;

/**
 * Used to change the block hardness of any block type.
 * This logic is not supported by the minecraft native platform so here is a quick guide on how to achieve that.
 * <p>
 * When a minecraft player breaks a block he will send block damage packets to the server. The server then distributes this information to all other players
 * who could potentially see the player breaking the block. Since the player simulates the block breaking process on the client side the server will just accept a block break
 * by the player if it was legal on the server. So there is some logic happening on the client side. Thus, we cannot just change the hardness of a block on the server
 * platform and expect the client to accept that. Instead, we will get a weird desync.
 * Fortunately, minecraft has added a new player attribute that we can use to control the block breaking speed of a player.
 * With this attribute we can manipulate the client to have the same block hardness as the server.
 */
@MCCLogic
public interface MCCBlockHardnessSettings {
    DataHolderPredicate.TickDelay DELAY_BETWEEN_BLOCK_BREAKS = new DataHolderPredicate.TickDelay("BlockBreakDelay", 5);
    Key MODIFIER_KEY = Key.key("mcc", "fake_block_break_effect");
    MCCAttributeModifier NO_BLOCK_BREAK_MODIFIER = new MCCAttributeModifier(MODIFIER_KEY, -1024, MCCAttributeModifier.Operation.ADD_VALUE);


    /**
     * Returns the hardness of the provided block type
     *
     * @param mccBlockType the block type
     * @return the hardness
     */
    float getHardness(MCCBlockType mccBlockType);

    /**
     * Checks whether the provided block types hardness was changed by this logic
     *
     * @param mccBlockType the block type
     * @return true if the hardness was changed
     */
    boolean doesHardnessDifferFromVanilla(MCCBlockType mccBlockType);

    /**
     * Sets the custom block hardness for a specified block type
     *
     * @param mccBlockType the block type
     * @param hardness     the hardness
     */
    void setHardness(MCCBlockType mccBlockType, float hardness);

    /**
     * Calculates the block hardness of the provided block state but with custom hardness taken into account
     * @param player the player
     * @param mccBlockState the block state
     * @return the hardness
     */
    default float getBlockHardness(MCCPlayer player, MCCBlockState mccBlockState) {
        float destroySpeed = mccBlockState.getBlockType().getBlockProperties().getBlockHardness();

        if (destroySpeed > 1.0F) {
            destroySpeed += (float) player.getAttributes().getValue(MCCAttributes.MINING_EFFICIENCY);
        }

        // Haste effect
        if (player.hasActiveEffect(MCCEffects.DIG_SPEED.get())) {
            destroySpeed *= (0.2F * player.getActiveEffect(MCCEffects.DIG_SPEED.get()).getAmplifier() + 1.0F);
        }

        if (player.hasActiveEffect(MCCEffects.DIG_SLOWDOWN.get())) {
            float slowDown = switch (player.getActiveEffect(MCCEffects.DIG_SLOWDOWN.get()).getAmplifier()) {
                case 0 -> 0.3F;
                case 1 -> 0.09F;
                case 2 -> 0.0027F;
                default -> 8.1E-4F;
            };
            destroySpeed *= slowDown;
        }

        destroySpeed *= (float) player.getAttributes().getValueWithoutModifiers(MCCAttributes.BLOCK_BREAK_SPEED, NO_BLOCK_BREAK_MODIFIER);

        if (player.isInWater()) {
            destroySpeed *= (float) player.getAttributes().getValue(MCCAttributes.SUBMERGED_MINING_SPEED);
        }

        if (!player.isOnGround()) {
            destroySpeed /= 5.0F;
        }
        return destroySpeed;
    }

    /**
     * @param player the player
     * @param block the block
     * @return whether a player is in the block break effect range
     */
    default boolean playerNotInEffectRange(MCCPlayer player, MCCBlock block) {
        if (!player.getLocation().world().equals(block.getLocation().world())) {
            return true;
        }

        double xDistance = block.getLocation().pos().x() - player.getLocation().pos().x();
        double yDistance = block.getLocation().pos().y() - player.getLocation().pos().y();
        double zDistance = block.getLocation().pos().z() - player.getLocation().pos().z();

        return xDistance * xDistance + yDistance * yDistance + zDistance * zDistance >= 1024.0D;
    }

    /**
     * Calculates the block destroy damage for a single tick
     * @param player the player
     * @param hardness the provided hardness of the block
     * @param block the block
     * @return the damage for a single tick
     */
    default float calculateBlockDestroyDamageDick(MCCPlayer player, float hardness, MCCBlock block) {
        MCCItemStack hand = player.getInventory().getItemInMainHand();

        boolean hasCorrectToolForDrops = block.getBlockState().isPreferredTool(hand);
        return getBlockHardness(player, block.getBlockState()) / hardness / (hasCorrectToolForDrops ? 30f : 100f);
    }
}
