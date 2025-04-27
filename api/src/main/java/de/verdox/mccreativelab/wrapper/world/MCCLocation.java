package de.verdox.mccreativelab.wrapper.world;

import de.verdox.mccreativelab.wrapper.MCCWrapped;
import de.verdox.mccreativelab.wrapper.block.MCCBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MCCLocation extends MCCVector implements MCCWrapped {

    public static final int CHUNK_X_LENGTH = 16;
    public static final int SECTION_Y_LENGTH = 16;
    public static final int CHUNK_Z_LENGTH = 16;

    private final MCCWorld world;
    private final float yaw;
    private final float pitch;

    public MCCLocation(MCCWorld world, double x, double y, double z, float yaw, float pitch) {
        super(x, y, z);
        this.world = world;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public MCCLocation(MCCWorld world, double x, double y, double z) {
        this(world, x, y, z, 0, 0);
    }

    public MCCWorld world() {
        return world;
    }

    public float yaw() {
        return yaw;
    }

    public float pitch() {
        return pitch;
    }

    @Override
    public MCCLocation withX(double x) {
        return new MCCLocation(world, x, y, z, yaw, pitch);
    }

    @Override
    public MCCLocation withY(double y) {
        return new MCCLocation(world, x, y, z, yaw, pitch);
    }

    @Override
    public MCCLocation withZ(double z) {
        return new MCCLocation(world, x, y, z, yaw, pitch);
    }

    @Override
    public MCCLocation add(int x, int y, int z) {
        return new MCCLocation(world, x() + x, y() + y, z() + z, yaw(), pitch());
    }

    public MCCVector withYaw(float yaw) {
        return new MCCLocation(world, x, y, z, yaw, pitch);
    }

    public MCCVector withPitch(float pitch) {
        return new MCCLocation(world, x, y, z, yaw, pitch);
    }

    public MCCVector withWorld(MCCWorld world) {
        return new MCCLocation(world, x, y, z, yaw, pitch);
    }

    public MCCLocation above() {
        return new MCCLocation(this.world(), this.x(), this.y() + 1, this.z(), this.yaw(), this.pitch());
    }

    public MCCLocation below() {
        return new MCCLocation(this.world(), this.x(), this.y() - 1, this.z(), this.yaw(), this.pitch());
    }

    public MCCLocation add(double x, double y, double z) {
        return new MCCLocation(this.world(), this.x() + x, this.y() + y, this.z() + z, this.yaw(), this.pitch());
    }

    public MCCLocation left() {
        return new MCCLocation(this.world(), this.x() - 1, this.y(), this.z(), this.yaw(), this.pitch());
    }

    public MCCLocation right() {
        return new MCCLocation(this.world(), this.x() + 1, this.y(), this.z(), this.yaw(), this.pitch());
    }

    public MCCLocation forward() {
        return new MCCLocation(this.world(), this.x(), this.y(), this.z() + 1, this.yaw(), this.pitch());
    }

    public MCCLocation backward() {
        return new MCCLocation(this.world(), this.x(), this.y(), this.z() - 1, this.yaw(), this.pitch());
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
        return (int) x();
    }

    public int blockY() {
        return (int) y();
    }

    public int blockZ() {
        return (int) z();
    }

    public double distanceSquared(@NotNull MCCLocation o) {
        if (o == null) {
            throw new IllegalArgumentException("Cannot measure distance to a null location");
        } else if (o.world() == null || world() == null) {
            throw new IllegalArgumentException("Cannot measure distance to a null world");
        } else if (o.world() != world()) {
            throw new IllegalArgumentException("Cannot measure distance between " + world().getName() + " and " + o.world().getName());
        }

        return square(x - o.x) + square(y - o.y) + square(z - o.z);
    }

    /**
     * Check if each component of this Location is finite.
     *
     * @throws IllegalArgumentException if any component is not finite
     */
    public void checkFinite() throws IllegalArgumentException {
        checkFinite(x, "x not finite");
        checkFinite(y, "y not finite");
        checkFinite(z, "z not finite");
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
