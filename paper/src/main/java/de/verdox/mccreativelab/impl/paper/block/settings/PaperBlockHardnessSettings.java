package de.verdox.mccreativelab.impl.paper.block.settings;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.impl.paper.platform.converter.BukkitAdapter;
import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import de.verdox.mccreativelab.wrapper.block.settings.AbstractBlockHardnessSettings;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import io.papermc.paper.event.block.BlockBreakProgressUpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageAbortEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PaperBlockHardnessSettings extends AbstractBlockHardnessSettings implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        MCCPlayer player = BukkitAdapter.wrap(e.getPlayer(), new TypeToken<>() {});
        if (e.getClickedBlock() == null || e.getAction().isRightClick()) {
            stopBlockBreakAction(player);
        }
        if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }
        if (e.getClickedBlock() != null && e.getAction().isLeftClick()) {

            if (startBlockBreakAction(BukkitAdapter.wrap(e.getPlayer()), BukkitAdapter.wrap(e.getClickedBlock()))) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onStartDigging(BlockDamageEvent e) {
        if (startBlockBreakAction(BukkitAdapter.wrap(e.getPlayer()), BukkitAdapter.wrap(e.getBlock()))) {
            e.setCancelled(true);
        }
    }


    @EventHandler
    public void onStopDigging(BlockDamageAbortEvent e) {
        MCCPlayer player = BukkitAdapter.wrap(e.getPlayer(), new TypeToken<>() {});
        stopBlockBreakAction(player);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent e) {
        MCCPlayer player = BukkitAdapter.wrap(e.getPlayer(), new TypeToken<>() {});
        stopBlockBreakAction(player);
    }

    @EventHandler
    public void stopDiggingOnQuit(PlayerQuitEvent e) {
        MCCPlayer player = BukkitAdapter.wrap(e.getPlayer(), new TypeToken<>() {});
        stopBlockBreakAction(player);
    }

    @EventHandler
    public void tickPlayers(ServerTickEndEvent e) {
        Bukkit.getOnlinePlayers().forEach(player -> tick(BukkitAdapter.wrap(player, new TypeToken<>() {})));
    }

    @Override
    protected void onProgress(MCCPlayer player, MCCBlock block, float progress) {
        Block bukkitBlock = BukkitAdapter.unwrap(block, new TypeToken<>() {});
        Player bukkitPlayer = BukkitAdapter.unwrap(player, new TypeToken<>() {});
        BlockBreakProgressUpdateEvent blockBreakProgressUpdateEvent = new BlockBreakProgressUpdateEvent(bukkitBlock, progress, bukkitPlayer);
        blockBreakProgressUpdateEvent.callEvent();
    }
  
    public void startBlockBreakAction(Player bukkitPlayer, Block bukkitBlock, Cancellable cancellable) {
        MCCPlayer player = BukkitAdapter.wrap(bukkitPlayer, new TypeToken<>() {});
        MCCBlock block = BukkitAdapter.wrap(bukkitBlock, new TypeToken<>() {});

        if (!doesHardnessDifferFromVanilla(block.getBlockType())) {
            player.getTempData().storeData("isBreakingNormalBlock", block);
            return;
        }

        if (map.containsKey(player)) {
            stopBlockBreakAction(player);
        }

        float customHardness = getHardness(block.getBlockType());

        //TODO: Event?

        cancellable.setCancelled(true);
        map.put(player, new BlockBreakProgress(player, block, customHardness));
        blockBrokenToPlayerMapping.computeIfAbsent(block, block1 -> new HashSet<>()).add(player);

        applyBlockBreakModifier(player);
    }

    private static void applyBlockBreakModifier(MCCPlayer player) {
        if (player.getAttributes().hasModifier(MCCAttributes.BLOCK_BREAK_SPEED, MODIFIER_KEY)) {
            player.getAttributes().getAttributeInstance(MCCAttributes.BLOCK_BREAK_SPEED).addTransientModifier(NO_BLOCK_BREAK_MODIFIER);
        }
    }

    private static void removeBlockModifier(MCCPlayer player) {
        if (player.getAttributes().hasModifier(MCCAttributes.BLOCK_BREAK_SPEED, MODIFIER_KEY)) {
            player.getAttributes().getAttributeInstance(MCCAttributes.BLOCK_BREAK_SPEED).removeModifier(NO_BLOCK_BREAK_MODIFIER);
        }
    }

    public void stopBlockBreakAtBlock(MCCBlock block) {
        if (!blockBrokenToPlayerMapping.containsKey(block))
            return;
        for (MCCPlayer player : blockBrokenToPlayerMapping.get(block)) {
            stopBlockBreakAction(player);
        }
    }

    public void stopBlockBreakAction(MCCPlayer player) {
        if(player.getTempData().containsData("isBreakingNormalBlock")){
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


            var damageThisTick = getBlockDestroyProgress(player, hardness, block);
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
                Block bukkitBlock = BukkitAdapter.unwrap(block, new TypeToken<>() {});
                Player bukkitPlayer = BukkitAdapter.unwrap(player, new TypeToken<>() {});
                BlockBreakProgressUpdateEvent blockBreakProgressUpdateEvent = new BlockBreakProgressUpdateEvent(bukkitBlock, progress, bukkitPlayer);
                blockBreakProgressUpdateEvent.callEvent();
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

    private static boolean playerNotInEffectRange(MCCPlayer onlinePlayer, MCCBlock block) {
        if (!onlinePlayer.getLocation().world().equals(block.getLocation().world())) {
            return true;
        }

        double xDistance = block.getLocation().x() - onlinePlayer.getLocation().x();
        double yDistance = block.getLocation().y() - onlinePlayer.getLocation().y();
        double zDistance = block.getLocation().z() - onlinePlayer.getLocation().z();

        return xDistance * xDistance + yDistance * yDistance + zDistance * zDistance >= 1024.0D;
    }

    private static float getBlockDestroyProgress(MCCPlayer player, float hardness, MCCBlock block) {
        MCCItemStack hand = player.getInventory().getItemInMainHand();

        boolean hasCorrectToolForDrops = block.getBlockState().isPreferredTool(hand);
        return getDestroySpeed(player, block) / hardness / (hasCorrectToolForDrops ? 30f : 100f);
    }

    private static float getDestroySpeed(MCCPlayer player, MCCBlock mccBlock) {
        MCCItemStack hand = player.getInventory().getItemInMainHand();
        float destroySpeed = mccBlock.getBlockState().getDestroySpeed(player, hand, true);

        // Haste effect
        if (player.asEffectTarget().hasEffect(MCCEffects.DIG_SPEED))
            destroySpeed *= (0.2F * player.asEffectTarget().getEffect(MCCEffects.DIG_SPEED).getAmplifier() + 1.0F);
        // water check
        if (player.isInWater() && hasEnchantmentLevel(player, MCCEnchantments.AQUA_AFFINITY.get()))
            destroySpeed /= 5.0F;
        // in air check
        if (!player.isOnGround())
            destroySpeed /= 5.0F;

        return destroySpeed;
    }

    private static boolean hasEnchantmentLevel(MCCPlayer player, MCCEnchantment enchantment) {
        return getEnchantmentLevel(player, enchantment) > 0;
    }

    private static int getEnchantmentLevel(MCCPlayer player, MCCEnchantment enchantment) {
        int level = 0;
        for (MCCEquipmentSlotGroup equipmentSlotGroup : enchantment.getDefinition().getSlots()) {
            MCCItemStack stack = player.getInventory().getItem(equipmentSlotGroup);
            int foundLevel = stack.getEnchantmentLevel(enchantment);
            if (foundLevel > level)
                level = foundLevel;
        }
        return level;
    }
}
