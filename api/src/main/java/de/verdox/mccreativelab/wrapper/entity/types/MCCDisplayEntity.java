package de.verdox.mccreativelab.wrapper.entity.types;

import de.verdox.mccreativelab.wrapper.annotations.MCCRequireVanillaElement;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.entity.MCCEntity;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.util.VanillaField;
import net.kyori.adventure.text.Component;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public interface MCCDisplayEntity extends MCCEntity {

    /**
     * Interpolation duration in ticks
     */
    VanillaField<Integer> interpolationDurationInTicks();

    /**
     * Interpolation duration in ticks for teleportation. Values are clamped between 0 and 59 (inclusive).
     */
    VanillaField<Integer> teleportDurationInTicks();

    /**
     * Delay before starting interpolation. If 0, interpolation starts immediately. (Not saved to entity, but can be set by commands.)
     */
    VanillaField<Integer> interpolationDelayInTicks();

    /**
     * The transformation of the entity. This field works with copies.
     * If you set a new value it is copied from your provided parameter.
     * If you get the current value it is copied from the entity.
     * <p>
     * You cannot change an entities parameters by changing any values without saving it onto the entity!
     */
    VanillaField<Transformation> transformation();

    /**
     * Controls if this entity should pivot to face player when rendered. It can be fixed (both vertical and horizontal angles are fixed), vertical (faces player around vertical axis), horizontal (pivots around horizontal axis), and center (pivots around center point).
     * Defaults to {@code fixed}
     */
    VanillaField<Billboard> billboard();

    /**
     * If present, overrides light values used for rendering. Omitted by default (which means rendering uses values from entity position).
     */
    VanillaField<BrightnessOverride> blockBrightness();

    /**
     * Maximum view range of the entity. When the distance is more than {@code view_range x entityDistanceScaling x 64}, the entity is not rendered.
     * Defaults to 1.0.
     */
    VanillaField<Float> viewRange();

    /**
     * Controls the opacity of the shadow as a function of distance to block below. Defaults to 1. Interpolated.
     */
    VanillaField<Float> shadowStrength();

    /**
     * Shadow radius. Value is treated as 64 when higher than 64. If less than or equal to 0, the entity has no shadow. Defaults to 0. Interpolated.
     */
    VanillaField<Float> shadowRadius();

    /**
     * The maximum width of the entity. Rendering culling bounding box spans horizontally width/2 from entity position, and the part beyond will be culled. If set to 0, no culling on both vertical and horizontal directions. Defaults to 0.
     */
    VanillaField<Float> width();

    /**
     * The maximum height of the entity. Rendering culling bounding box spans vertically y to y+height, and the part beyond will be culled. If set to 0, no culling on both vertical and horizontal directions. Defaults to 0.
     */
    VanillaField<Float> height();

    /**
     * Overrides the glow border color. If 0, uses the color of the team that the display entity is in. Defaults to 0. Is not preserved when removed.
     */
    VanillaField<Integer> glowColorOverride();

    interface Transformation {
        /**
         * (Required) Translation transformation. This tag corresponds to the last column in the matrix form.
         */
        VanillaField<Vector3f> translation();

        /**
         * (Required) Rotates the model again. This tag corresponds to the left-singular vector matrix after the matrix singular value decomposition
         */
        VanillaField<Quaternionf> leftRotation();

        /**
         * (Required) Initial rotation. This tag corresponds to the right-singular vector matrix after the matrix singular value decomposition
         */
        VanillaField<Quaternionf> rightRotation();

        /**
         * (Required) Scale the model centered on the origin. This tag corresponds to the singular values of the matrix after singular value decomposition.
         */
        VanillaField<Vector3f> scale();
    }

    record BrightnessOverride(int block, int sky) {}

    enum Billboard {
        FIXED((byte) 0),
        VERTICAL((byte) 1),
        HORIZONTAL((byte) 2),
        CENTER((byte) 3),
        ;
        private final byte id;

        Billboard(byte id) {
            this.id = id;
        }

        public byte getId() {
            return this.id;
        }
    }

    interface Item extends MCCDisplayEntity {

        /**
         * The item to display
         */
        VanillaField<MCCItemStack> itemStack();

        /**
         * How to display the item
         */
        VanillaField<Display> display();

        enum Display {
            NONE(0),
            THIRD_PERSON_LEFT_HAND(1),
            THIRD_PERSON_RIGHT_HAND(2),
            FIRST_PERSON_LEFT_HAND(3),
            FIRST_PERSON_RIGHT_HAND(4),
            HEAD(5),
            GUI(6),
            GROUND(7),
            FIXED(8);

            private final byte id;

            Display(int id) {
                this.id = (byte) id;
            }

            public byte getId() {
                return id;
            }

            public static Display byId(byte id) {
                for (Display value : Display.values()) {
                    if (value.getId() == id) {
                        return value;
                    }
                }
                throw new IllegalArgumentException("No display found with id " + id);
            }
        }
    }

    interface Block extends MCCDisplayEntity {
        /**
         * The displayed block state
         */
        @MCCRequireVanillaElement
        VanillaField<MCCBlockState> blockState();
    }

    interface Text extends MCCDisplayEntity {
        /**
         * Text alignment direction. Can be center, left, and right.
         * Defaults to center
         */
        VanillaField<Alignment> alignment();

        /**
         * The text to be displayed in the format of raw JSON text, which are resolved with the context of the display entity
         */
        VanillaField<Component> text();

        /**
         * Maximum line width used to split lines (note: new line can be also added with \n characters). Defaults to 200.
         */
        VanillaField<Integer> lineWidth();

        /**
         * The background color, arranged by ARGB. Since pixel with an alpha channel less than 0.1 are discarded when rendering in vanilla shader, the background becomes fully transparent when A is less than 26 (0x1A). Defaults to 1073741824 (0x40000000). Interpolated.
         */
        VanillaField<Integer> backgroundColorArgb();

        /**
         * If true, rendering uses default text background color (same as in chat), which overrides background. Defaults to false.
         */
        VanillaField<Boolean> default_background();

        /**
         * Whether the text be visible through blocks.
         * Defaults to false
         */
        VanillaField<Boolean> see_through();

        /**
         * Whether the text is displayed with shadow.
         * Defaults to false.
         */
        VanillaField<Boolean> shadow();

        /**
         * Alpha value of rendered text. Value ranges from 0 to 255. Values up to 3 are treated as fully opaque (255). Similar to the background, the text rendering is discarded for values between 4 and 26. NBT stores the value as signed byte, -128 to 127. Defaults to -1, which represents 255 and is completely opaque. SNBT to NBT handles conversion from unsigned to signed, but if needed, replace values greater than 127 with alpha-256 or alphaUB. Interpolated
         */
        VanillaField<Byte> textOpacity();

        enum Alignment {
            CENTER,
            LEFT,
            RIGHT,
        }
    }

}
