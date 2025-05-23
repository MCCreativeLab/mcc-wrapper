package de.verdox.mccreativelab.impl.vanilla.item.components;

import java.util.Optional;

import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;

import java.util.List;

import de.verdox.mccreativelab.wrapper.registry.MCCReferenceSet;
import de.verdox.mccreativelab.wrapper.item.components.MCCTool;
import net.minecraft.core.HolderSet;
import de.verdox.mccreativelab.wrapper.block.MCCBlockType;

import java.lang.Float;

import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import net.minecraft.world.level.block.Block;

import java.lang.Boolean;

import net.minecraft.world.item.component.Tool;

public class NMSTool extends MCCHandle<Tool> implements MCCTool {
    public static final MCCConverter<Tool, NMSTool> CONVERTER = converter(NMSTool.class, Tool.class, NMSTool::new, MCCHandle::getHandle);

    public NMSTool(Tool handle) {
        super(handle);
    }

    public MCCTool.Rule createRule() {
        return new NMSRule(null);
    }

    public List<MCCTool.Rule> getRules() {
        var nms = getRulesFromImpl();
        return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<List<MCCTool.Rule>>() {});
    }

    private List<Tool.Rule> getRulesFromImpl() {
        return handle == null ? List.of() : handle.rules();
    }

    public MCCTool withRules(List<MCCTool.Rule> rules) {
        var param0 = MCCPlatform.getInstance().getConversionService().unwrap(rules, new TypeToken<List<Tool.Rule>>() {});
        var param1 = getDefaultMiningSpeedFromImpl();
        var param2 = getDamagePerBlockFromImpl();
        return new NMSTool(new Tool(param0, param1, param2));
    }

    public float getDefaultMiningSpeed() {
        var nms = getDefaultMiningSpeedFromImpl();
        return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Float>() {});
    }

    private float getDefaultMiningSpeedFromImpl() {
        return handle == null ? 0 : handle.defaultMiningSpeed();
    }

    public MCCTool withDefaultMiningSpeed(float defaultMiningSpeed) {
        var param0 = getRulesFromImpl();
        var param1 = MCCPlatform.getInstance().getConversionService().unwrap(defaultMiningSpeed, new TypeToken<Float>() {});
        var param2 = getDamagePerBlockFromImpl();
        return new NMSTool(new Tool(param0, param1, param2));
    }

    public int getDamagePerBlock() {
        var nms = getDamagePerBlockFromImpl();
        return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Integer>() {});
    }

    private int getDamagePerBlockFromImpl() {
        return handle == null ? 0 : handle.damagePerBlock();
    }

    public MCCTool withDamagePerBlock(int damagePerBlock) {
        var param0 = getRulesFromImpl();
        var param1 = getDefaultMiningSpeedFromImpl();
        var param2 = MCCPlatform.getInstance().getConversionService().unwrap(damagePerBlock, new TypeToken<Integer>() {});
        return new NMSTool(new Tool(param0, param1, param2));
    }

    public static class NMSRule extends MCCHandle<Tool.Rule> implements MCCTool.Rule {
        public static final MCCConverter<Tool.Rule, NMSTool.NMSRule> CONVERTER = converter(NMSTool.NMSRule.class, Tool.Rule.class, NMSTool.NMSRule::new, MCCHandle::getHandle);

        public NMSRule(Tool.Rule handle) {
            super(handle);
        }

        public MCCReferenceSet<MCCBlockType> getBlocks() {
            var nms = getBlocksFromImpl();
            return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<MCCReferenceSet<MCCBlockType>>() {});
        }

        private HolderSet<Block> getBlocksFromImpl() {
            return handle == null ? null : handle.blocks();
        }

        public MCCTool.Rule withBlocks(MCCReferenceSet<MCCBlockType> blocks) {
            var param0 = MCCPlatform.getInstance().getConversionService().unwrap(blocks, new TypeToken<HolderSet<Block>>() {});
            var param1 = getSpeedFromImpl();
            var param2 = getCorrectForDropsFromImpl();
            return new NMSTool.NMSRule(new Tool.Rule(param0, param1, param2));
        }

        public Optional<Float> getSpeed() {
            var nms = getSpeedFromImpl();
            return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Optional<Float>>() {});
        }

        private Optional<Float> getSpeedFromImpl() {
            return handle == null ? null : handle.speed();
        }

        public MCCTool.Rule withSpeed(Optional<Float> speed) {
            var param0 = getBlocksFromImpl();
            var param1 = MCCPlatform.getInstance().getConversionService().unwrap(speed, new TypeToken<Optional<Float>>() {});
            var param2 = getCorrectForDropsFromImpl();
            return new NMSTool.NMSRule(new Tool.Rule(param0, param1, param2));
        }

        public Optional<Boolean> getCorrectForDrops() {
            var nms = getCorrectForDropsFromImpl();
            return MCCPlatform.getInstance().getConversionService().wrap(nms, new TypeToken<Optional<Boolean>>() {});
        }

        private Optional<Boolean> getCorrectForDropsFromImpl() {
            return handle == null ? null : handle.correctForDrops();
        }

        public MCCTool.Rule withCorrectForDrops(Optional<Boolean> correctForDrops) {
            var param0 = getBlocksFromImpl();
            var param1 = getSpeedFromImpl();
            var param2 = MCCPlatform.getInstance().getConversionService().unwrap(correctForDrops, new TypeToken<Optional<Boolean>>() {});
            return new NMSTool.NMSRule(new Tool.Rule(param0, param1, param2));
        }
    }
}