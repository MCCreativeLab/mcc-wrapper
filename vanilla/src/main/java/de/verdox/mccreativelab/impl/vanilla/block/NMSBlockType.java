package de.verdox.mccreativelab.impl.vanilla.block;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.block.MCCBlockProperties;
import de.verdox.mccreativelab.wrapper.block.MCCBlockSoundGroup;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.block.MCCBlockType;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import net.kyori.adventure.key.Key;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NMSBlockType extends MCCHandle<Block> implements MCCBlockType {
    public static final MCCConverter<Block, NMSBlockType> CONVERTER = converter(NMSBlockType.class, Block.class, NMSBlockType::new, MCCHandle::getHandle);

    public NMSBlockType(Block handle) {
        super(handle);
    }

    @Override
    public List<MCCBlockState> getAllBlockStates() {
        return handle.getStateDefinition().getPossibleStates().stream().map(blockState -> MCCPlatform.getInstance().getConversionService().wrap(blockState, new TypeToken<MCCBlockState>() {})).toList();
    }

    @Override
    public MCCBlockState getDefaultState() {
        return new NMSBlockState(handle.defaultBlockState());
    }

    @Override
    public MCCBlockProperties getBlockProperties() {
        return conversionService.wrap(handle.properties());
    }

    @Override
    public @NotNull Key key() {
        ResourceLocation resourceLocation = BuiltInRegistries.BLOCK.getKey(handle);
        return Key.key(resourceLocation.getNamespace(), resourceLocation.getPath());
    }
}
