package de.verdox.mccreativelab.impl.vanilla.world.chunk;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.wrapper.annotations.MCCReflective;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.exceptions.OperationNotPossibleOnNMS;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.world.MCCWorld;
import de.verdox.mccreativelab.wrapper.world.chunk.MCCChunk;
import de.verdox.mccreativelab.wrapper.world.chunk.MCCChunkSection;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.LinkedList;
import java.util.List;

public class NMSChunk extends MCCHandle<LevelChunk> implements MCCChunk {
    public static final MCCConverter<LevelChunk, NMSChunk> CONVERTER = converter(NMSChunk.class, LevelChunk.class, NMSChunk::new, MCCHandle::getHandle);

    public NMSChunk(LevelChunk handle) {
        super(handle);
    }

    @Override
    @MCCReflective
    public boolean isLoaded() {
        return readFieldFromHandle("loaded", new TypeToken<Boolean>() {});
    }

    @Override
    public MCCWorld getWorld() {
        return conversionService.wrap(((ServerLevel) handle.getLevel()), new TypeToken<>() {});
    }

    @Override
    public int getX() {
        return handle.getPos().x;
    }

    @Override
    public int getZ() {
        return handle.getPos().z;
    }

    @Override
    public int getHeight() {
        return handle.getHeight();
    }

    @Override
    public int getHighestFilledSectionIndex() {
        return handle.getHighestFilledSectionIndex();
    }

    @Override
    public List<MCCEntity> getEntitiesInChunk() {
        throw new OperationNotPossibleOnNMS();
    }

    @Override
    public int getHighestNonAirBlock(int globalX, int globalZ) {
        return handle.getHeight(Heightmap.Types.WORLD_SURFACE, globalX, globalZ);
    }

    @Override
    public int getAmountChunkSections() {
        return handle.getSections().length;
    }

    @Override
    public List<MCCChunkSection> getChunkSections() {
        List<MCCChunkSection> list = new LinkedList<>();
        for (int i = 0; i < getAmountChunkSections(); i++) {
            list.add(getChunkSectionByIndex(i));
        }
        return list;
    }

    @Override
    public MCCChunkSection getChunkSectionByIndex(int index) {
        LevelChunkSection levelChunkSection = handle.getSection(index);
        return new NMSChunkSection(handle, levelChunkSection, index, getX(), getZ());
    }

    @Override
    public MCCChunkSection getChunkSectionByGlobalYCoordinate(int blockHeight) {
        int maxBuildHeight = getWorld().getMaxBuildHeight();
        int minBuildHeight = getWorld().getMinBuildHeight();

        // Prüfen, ob die Y-Koordinate im gültigen Bereich liegt
        if (blockHeight < minBuildHeight || blockHeight > maxBuildHeight) {
            return null; // Außerhalb des Bauhöhenbereichs
        }

        // Chunk-Abschnittsindex berechnen
        int sectionIndex = (blockHeight - minBuildHeight) >> 4; // Division durch 16 (Schichtenhöhe)

        // Chunk-Abschnitt anhand des Index abrufen
        return getChunkSectionByIndex(sectionIndex);
    }
}
