package de.verdox.mccreativelab.impl.vanilla.world.chunk;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.wrapper.annotations.MCCReflective;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.world.chunk.MCCChunk;
import de.verdox.mccreativelab.wrapper.world.chunk.MCCChunkSection;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;

public class NMSChunkSection extends MCCHandle<LevelChunkSection> implements MCCChunkSection {
    private final ChunkAccess levelChunk;
    private final int sectionIndex;
    private final int chunkX;
    private final int chunkZ;

    public NMSChunkSection(ChunkAccess levelChunk, LevelChunkSection handle, int sectionIndex, int chunkX, int chunkZ) {
        super(handle);
        this.levelChunk = levelChunk;
        this.sectionIndex = sectionIndex;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    @Override
    public int getChunkSectionIndex() {
        return sectionIndex;
    }

    @Override
    public int getChunkX() {
        return chunkX;
    }

    @Override
    public int getChunkZ() {
        return chunkZ;
    }

    @Override
    public boolean isEmpty() {
        return getHandle().hasOnlyAir();
    }

    @Override
    public boolean isRandomlyTickingBlocks() {
        return getHandle().isRandomlyTickingBlocks();
    }

    @Override
    public boolean isRandomlyTickingFluids() {
        return getHandle().isRandomlyTickingFluids();
    }

    @Override
    public MCCBlockState setBlockState(int localX, int localY, int localZ, MCCBlockState state) {
        if (!state.isVanilla()) {
            throw new IllegalArgumentException("You can only provide vanilla block types here");
        }
        checkBounds(localX, localY, localZ);
        var service = MCCPlatform.getInstance().getConversionService();
        return service.wrap(getHandle().setBlockState(localX, localY, localZ, service.unwrap(state, new TypeToken<>() {})), new TypeToken<>() {});
    }

    private void checkBounds(int localX, int localY, int localZ) {
        if (localX < 0 || localX > 15) {
            throw new IllegalArgumentException("The x coordinate " + localX + " is not in its bounds [0-15]");
        } else if (localY < 0 || localY > 15) {
            throw new IllegalArgumentException("The y coordinate " + localY + " is not in its bounds [0-15]");
        } else if (localZ < 0 || localZ > 15) {
            throw new IllegalArgumentException("The z coordinate " + localZ + " is not in its bounds [0-15]");
        }
    }

    @Override
    public MCCBlockState getBlockState(int localX, int localY, int localZ) {
        checkBounds(localX, localY, localZ);

        return MCCPlatform.getInstance().getConversionService().wrap(getHandle().getBlockState(localX, localY, localZ), new TypeToken<>() {});
    }

    @Override
    @MCCReflective
    public void disableFluidTicking() {
        short tickingFluidCount = readFieldFromHandle("tickingFluidCount", new TypeToken<>() {});
        writeFieldInHandle("tickingFluidCount", tickingFluidCount - 4097);
    }

    @Override
    @MCCReflective
    public void disableBlockTicking() {
        short tickingBlockCount = readFieldFromHandle("tickingBlockCount", new TypeToken<>() {});
        writeFieldInHandle("tickingBlockCount", tickingBlockCount - 4097);
    }

    @Override
    public void enableBlockTicking() {
        short tickingBlockCount = readFieldFromHandle("tickingBlockCount", new TypeToken<>() {});
        writeFieldInHandle("tickingBlockCount", tickingBlockCount + 4097);
    }

    @Override
    public void enableFluidTicking() {
        short tickingFluidCount = readFieldFromHandle("tickingFluidCount", new TypeToken<>() {});
        writeFieldInHandle("tickingFluidCount", tickingFluidCount + 4097);
    }

    @Override
    public MCCChunk getParent() {
        return MCCPlatform.getInstance().getConversionService().wrap(levelChunk, new TypeToken<>() {});
    }
}
