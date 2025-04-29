package de.verdox.mccreativelab.wrapper.world;

/**
 * Describes a block position
 */
public class MCCBlockPos extends MCCVector {
    public MCCBlockPos(int x, int y, int z) {
        super(x, y, z);
    }

    /**
     * Returns the x coordinate of the block position
     *
     * @return the x coordinate
     */
    public int blockX() {
        return (int) x;
    }

    /**
     * Returns the y coordinate of the block position
     *
     * @return the y coordinate
     */
    public int blockY() {
        return (int) y;
    }

    /**
     * Returns the z coordinate of the block position
     *
     * @return the z coordinate
     */
    public int blockZ() {
        return (int) z;
    }
}
