package de.verdox.mccreativelab.wrapper.block.settings;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.block.MCCBlockType;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.typed.MCCAttributes;
import de.verdox.mccreativelab.wrapper.typed.MCCDataComponentTypes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

public abstract class AbstractBlockHardnessSettings implements MCCBlockHardnessSettings {
    private final Map<MCCPlayer, BlockBreakProgress> map = new HashMap<>();
    private final Map<MCCBlock, Set<MCCPlayer>> blockBrokenToPlayerMapping = new HashMap<>();
    private final Map<MCCBlockType, Float> customHardnessMap = new HashMap<>();


    @Override
    public final float getHardness(MCCBlockType mccBlockType) {
        return customHardnessMap.getOrDefault(mccBlockType, mccBlockType.getBlockProperties().getBlockHardness());
    }

    @Override
    public final boolean doesHardnessDifferFromVanilla(MCCBlockType mccBlockType) {
        if (!mccBlockType.isVanilla()) {
            return true;
        }
        return getHardness(mccBlockType) != mccBlockType.getBlockProperties().getBlockHardness();
    }

    @Override
    public final void setHardness(MCCBlockType mccBlockType, float hardness) {
        customHardnessMap.put(mccBlockType, hardness);
    }

    /**
     * Is called when progress is being made by a player while breaking a block with custom hardness
     * @param player the player
     * @param block the block
     * @param progress the progress [0;1]
     */
    protected abstract void onProgress(MCCPlayer player, MCCBlock block, float progress);

    /**
     * Applies the block break modifier to a player
     * @param player the player
     */
    private void applyBlockBreakModifier(MCCPlayer player) {
        if (player.getAttributes().hasModifier(MCCAttributes.BLOCK_BREAK_SPEED, MODIFIER_KEY)) {
            player.getAttributes().getAttributeInstance(MCCAttributes.BLOCK_BREAK_SPEED).addTransientModifier(NO_BLOCK_BREAK_MODIFIER);
        }
    }

    /**
     * Applies the block break modifier from a player
     * @param player the player
     */
    private void removeBlockModifier(MCCPlayer player) {
        if (player.getAttributes().hasModifier(MCCAttributes.BLOCK_BREAK_SPEED, MODIFIER_KEY)) {
            player.getAttributes().getAttributeInstance(MCCAttributes.BLOCK_BREAK_SPEED).removeModifier(NO_BLOCK_BREAK_MODIFIER);
        }
    }

    /**
     * Stops all block break actions at a given block
     * @param block the block
     */
    public void stopBlockBreakAtBlock(MCCBlock block) {
        if (!blockBrokenToPlayerMapping.containsKey(block))
            return;
        for (MCCPlayer player : blockBrokenToPlayerMapping.get(block)) {
            stopBlockBreakAction(player);
        }
    }

    public boolean startBlockBreakAction(MCCPlayer player, MCCBlock block) {
        if (!doesHardnessDifferFromVanilla(block.getBlockType())) {
            player.getTempData().storeData("isBreakingNormalBlock", block);
            return false;
        }

        if (map.containsKey(player)) {
            stopBlockBreakAction(player);
        }

        float customHardness = getHardness(block.getBlockType());
        map.put(player, new BlockBreakProgress(player, block, customHardness));
        blockBrokenToPlayerMapping.computeIfAbsent(block, block1 -> new HashSet<>()).add(player);

        applyBlockBreakModifier(player);
        return true;
    }

    /**
     * Stops all block break actions of a given player
     * @param player the player
     */
    public void stopBlockBreakAction(MCCPlayer player) {
        if (player.getTempData().containsData("isBreakingNormalBlock")) {
            player.getTempData().removeData("isBreakingNormalBlock");
        }
        removeBlockModifier(player);
        if (!map.containsKey(player)) {
            return;
        }
        BlockBreakProgress blockBreakProgress = map.remove(player);
        blockBreakProgress.resetBlockDamage();
        if (blockBrokenToPlayerMapping.containsKey(blockBreakProgress.block)) {
            Set<MCCPlayer> playersBreakingBlock = blockBrokenToPlayerMapping.get(blockBreakProgress.block);
            playersBreakingBlock.remove(player);
            if (playersBreakingBlock.isEmpty())
                blockBrokenToPlayerMapping.remove(blockBreakProgress.block);
        }

        blockBrokenToPlayerMapping.remove(blockBreakProgress.block);
    }

    private static final Predicate<MCCItemStack> IS_TOOL = stack -> stack.hasDataComponentType(MCCDataComponentTypes.TOOL.get());

    /**
     * The tick that needs to be injected into every player's tick loop
     * @param player a player
     */
    public void tick(MCCPlayer player) {
        if (IS_TOOL.test(player.getInventory().getItemInMainHand()) && !player.getTempData().containsData("isBreakingNormalBlock"))
            applyBlockBreakModifier(player);
        else if (!map.containsKey(player))
            removeBlockModifier(player);

        if (!map.containsKey(player))
            return;

        var data = map.get(player);
        data.incrementTicks();
    }

    public class BlockBreakProgress {
        private final MCCPlayer player;
        private final MCCBlock block;
        private final float hardness;
        private float damageTaken;
        private int lastStage = -1;
        private final int[] idsPerStage = new int[10];

        public BlockBreakProgress(MCCPlayer player, MCCBlock block, float hardness) {
            this.player = player;
            this.block = block;
            this.hardness = hardness;
            sendBlockDamage(0, getDestructionID(0));
        }

        public void incrementTicks() {
            //applyBlockBreakModifier(player);
            if (!DELAY_BETWEEN_BLOCK_BREAKS.isAllowed(player))
                return;


            var damageThisTick = calculateBlockDestroyDamageDick(player, hardness, block);
            damageTaken += damageThisTick;
            damageTaken = Math.min(1, damageTaken);

            int stage = (int) (damageTaken * 10) - 1;

            if (stage != lastStage) {
                if (stage < 0 || stage > 9) {
                    return;
                }

                for (int i = lastStage + 1; i <= stage; i++) {
                    var entityID = getDestructionID(i);
                    sendBlockDamage(i, entityID);
                }
                lastStage = stage;
            }


            if (stage == 9) {
                //FakeBlockUtil.simulateBlockBreakWithParticlesAndSound(fakeBlockState, block);
                player.breakBlock(block);
                stopBlockBreakAction(player);
                DELAY_BETWEEN_BLOCK_BREAKS.reset(player);
            }
        }

        public void resetBlockDamage() {
            for (int id : idsPerStage) {
                sendBlockDamage(-1, id);
            }
        }

        public int getDestructionID(int stage) {
            if (idsPerStage[stage] == 0)
                idsPerStage[stage] = ThreadLocalRandom.current().nextInt(1000);
            return idsPerStage[stage];
        }

        private void sendBlockDamage(int stage, int entityId) {
            float progress;
            if (stage == -1)
                progress = 0;
            else
                progress = stage * (1f / 9);

            if (progress > 0) {
                onProgress(player, block, progress);
            }

            for (MCCPlayer onlinePlayer : MCCPlatform.getInstance().getOnlinePlayers()) {
                if (onlinePlayer.getEntityID() == entityId) {
                    continue;
                }
                if (playerNotInEffectRange(onlinePlayer, block)) {
                    continue;
                }

                player.sendBlockDamage(block.getLocation(), progress, entityId);
            }
        }
    }
}
