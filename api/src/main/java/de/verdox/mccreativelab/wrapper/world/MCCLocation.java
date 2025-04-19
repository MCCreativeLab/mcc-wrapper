package de.verdox.mccreativelab.wrapper.world;

import de.verdox.mccreativelab.wrapper.MCCWrapped;
import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public record MCCLocation(MCCWorld world, MCCPos pos, float yaw, float pitch) implements MCCWrapped {

    public static final int CHUNK_X_LENGTH = 16;
    public static final int SECTION_Y_LENGTH = 16;
    public static final int CHUNK_Z_LENGTH = 16;

    public MCCLocation(MCCWorld world, MCCPos pos) {
        this(world, pos, 0, 0);
    }

    public MCCLocation(MCCWorld world, double x, double y, double z) {
        this(world, new MCCPos(x, y, z), 0, 0);
    }

    public MCCLocation(MCCWorld world, double x, double y, double z, float yaw, float pitch) {
        this(world, new MCCPos(x, y, z), yaw, pitch);
    }

    public MCCLocation above() {
        return new MCCLocation(this.world(), this.pos().x(), this.pos().y() + 1, this.pos().z(), this.yaw(), this.pitch());
    }

    public MCCLocation below() {
        return new MCCLocation(this.world(), this.pos().x(), this.pos().y() - 1, this.pos().z(), this.yaw(), this.pitch());
    }

    public MCCLocation add(double x, double y, double z) {
        return new MCCLocation(this.world(), this.pos().x() + x, this.pos().y() + y, this.pos().z() + z, this.yaw(), this.pitch());
    }

    public MCCLocation left() {
        return new MCCLocation(this.world(), this.pos().x() - 1, this.pos().y(), this.pos().z(), this.yaw(), this.pitch());
    }

    public MCCLocation right() {
        return new MCCLocation(this.world(), this.pos().x() + 1, this.pos().y(), this.pos().z(), this.yaw(), this.pitch());
    }

    public MCCLocation forward() {
        return new MCCLocation(this.world(), this.pos().x(), this.pos().y(), this.pos().z() + 1, this.yaw(), this.pitch());
    }

    public MCCLocation backward() {
        return new MCCLocation(this.world(), this.pos().x(), this.pos().y(), this.pos().z() - 1, this.yaw(), this.pitch());
    }

    public CompletableFuture<MCCBlock> getBlock() {
        return world().getBlockAt(this);
    }

    @Nullable
    public MCCBlock getBlockNow() {
        return world().getBlockAt(this).getNow(null);
    }

    public static int toChunkBlockLocalX(int globalX) {
        return Math.floorMod(globalX, CHUNK_X_LENGTH);
    }

    public int toChunkBlockLocalX() {
        return toChunkBlockLocalX(blockX());
    }

    public static int toChunkBlockLocalY(int globalY) {
        return Math.floorMod(globalY, SECTION_Y_LENGTH);
    }

    public int toChunkBlockLocalY() {
        return toChunkBlockLocalY(blockY());
    }

    public static int toChunkBlockLocalZ(int globalZ) {
        return Math.floorMod(globalZ, CHUNK_Z_LENGTH);
    }

    public int toChunkBlockLocalZ() {
        return toChunkBlockLocalZ(blockZ());
    }

    public int getChunkX() {
        return calculateChunkX(blockX());
    }

    public int getChunkZ() {
        return calculateChunkZ(blockZ());
    }

    public static int calculateChunkX(int globalX) {
        return globalX >> 4;
    }

    public static int calculateChunkZ(int globalZ) {
        return globalZ >> 4;
    }

    public int blockX() {
        return (int) this.pos().x();
    }

    public int blockY() {
        return (int) this.pos().y();
    }

    public int blockZ() {
        return (int) this.pos().z();
    }

    public double distanceSquared(@NotNull MCCLocation o) {
        if (o == null) {
            throw new IllegalArgumentException("Cannot measure distance to a null location");
        } else if (o.world() == null || world() == null) {
            throw new IllegalArgumentException("Cannot measure distance to a null world");
        } else if (o.world() != world()) {
            throw new IllegalArgumentException("Cannot measure distance between " + world().getName() + " and " + o.world().getName());
        }

        return square(this.pos().x() - o.pos().x()) + square(this.pos().y() - o.pos().y()) + square(this.pos().z() - o.pos().z());
    }

    /**
     * Check if each component of this Location is finite.
     *
     * @throws IllegalArgumentException if any component is not finite
     */
    public void checkFinite() throws IllegalArgumentException {
        checkFinite(this.pos().x(), "x not finite");
        checkFinite(this.pos().y(), "y not finite");
        checkFinite(this.pos().z(), "z not finite");
        checkFinite(pitch, "pitch not finite");
        checkFinite(yaw, "yaw not finite");
    }

    private static double square(double num) {
        return num * num;
    }

    private static void checkFinite(double d, @NotNull String message) {
        if (!isFinite(d)) {
            throw new IllegalArgumentException(message);
        }
    }

    private static void checkFinite(float d, @NotNull String message) {
        if (!isFinite(d)) {
            throw new IllegalArgumentException(message);
        }
    }

    private static boolean isFinite(double d) {
        return Math.abs(d) <= Double.MAX_VALUE;
    }

    private static boolean isFinite(float f) {
        return Math.abs(f) <= Float.MAX_VALUE;
    }
}
