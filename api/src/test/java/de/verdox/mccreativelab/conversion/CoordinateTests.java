package de.verdox.mccreativelab.conversion;

import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoordinateTests {

    private static final int CHUNK_LENGTH = 16;

    // Record für die Eingabekoordinaten
    record BlockPosition(int globalX, int globalY, int globalZ) {}

    // Parameterquelle
    static Stream<BlockPosition> provideBlockPositions() {
        return Stream.of(
                new BlockPosition(0, 0, 0),
                new BlockPosition(15, 15, 15),
                new BlockPosition(16, 16, 16),
                new BlockPosition(-1, -1, -1),
                new BlockPosition(-16, -16, -16),
                new BlockPosition(-397, 19, 150),
                new BlockPosition(12345, 70, -9876)
        );
    }

    @ParameterizedTest
    @MethodSource("provideBlockPositions")
    void testRoundTripConversion(BlockPosition pos) {
        int globalX = pos.globalX();
        int globalY = pos.globalY();
        int globalZ = pos.globalZ();

        int localX = MCCLocation.calculateBlockLocalX(globalX);
        int localY = MCCLocation.calculateBlockLocalY(globalY);
        int localZ = MCCLocation.calculateBlockLocalZ(globalZ);

        Assertions.assertTrue(localX < 16);
        Assertions.assertTrue(localY < 16);
        Assertions.assertTrue(localZ < 16);

        int chunkX = Math.floorDiv(globalX, CHUNK_LENGTH);
        int sectionY = Math.floorDiv(globalY, CHUNK_LENGTH);
        int chunkZ = Math.floorDiv(globalZ, CHUNK_LENGTH);

        int resultX = MCCLocation.calculateBlockGlobalX(chunkX, localX);
        int resultY = MCCLocation.calculateBlockGlobalY(sectionY, localY);
        int resultZ = MCCLocation.calculateBlockGlobalZ(chunkZ, localZ);

        assertEquals(globalX, resultX, "Global X mismatch");
        assertEquals(globalY, resultY, "Global Y mismatch");
        assertEquals(globalZ, resultZ, "Global Z mismatch");
    }
}
