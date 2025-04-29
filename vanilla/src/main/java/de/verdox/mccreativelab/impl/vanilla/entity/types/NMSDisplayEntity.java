package de.verdox.mccreativelab.impl.vanilla.entity.types;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.impl.vanilla.entity.NMSEntity;
import de.verdox.mccreativelab.impl.vanilla.util.field.EntityDataField;
import de.verdox.mccreativelab.impl.vanilla.util.field.WrappedEntityDataField;
import de.verdox.mccreativelab.reflection.ReflectionUtils;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.entity.types.MCCDisplayEntity;
import de.verdox.mccreativelab.wrapper.item.MCCItemStack;
import de.verdox.mccreativelab.wrapper.platform.MCCHandle;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.util.VanillaField;
import net.kyori.adventure.text.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.util.Brightness;
import net.minecraft.world.entity.Display;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class NMSDisplayEntity<T extends Display> extends NMSEntity<T> implements MCCDisplayEntity {
    private static final EntityDataAccessor<Integer> DATA_TRANSFORMATION_INTERPOLATION_START_DELTA_TICKS_ID = ReflectionUtils.readStaticFieldFromClass(Display.class, "DATA_TRANSFORMATION_INTERPOLATION_START_DELTA_TICKS_ID", new TypeToken<>() {});
    private static final EntityDataAccessor<Integer> DATA_TRANSFORMATION_INTERPOLATION_DURATION_ID = ReflectionUtils.readStaticFieldFromClass(Display.class, "DATA_TRANSFORMATION_INTERPOLATION_DURATION_ID", new TypeToken<>() {});
    private static final EntityDataAccessor<Integer> DATA_POS_ROT_INTERPOLATION_DURATION_ID = ReflectionUtils.readStaticFieldFromClass(Display.class, "DATA_POS_ROT_INTERPOLATION_DURATION_ID", new TypeToken<>() {});
    private static final EntityDataAccessor<Vector3f> DATA_TRANSLATION_ID = ReflectionUtils.readStaticFieldFromClass(Display.class, "DATA_TRANSLATION_ID", new TypeToken<>() {});
    private static final EntityDataAccessor<Vector3f> DATA_SCALE_ID = ReflectionUtils.readStaticFieldFromClass(Display.class, "DATA_SCALE_ID", new TypeToken<>() {});
    private static final EntityDataAccessor<Quaternionf> DATA_LEFT_ROTATION_ID = ReflectionUtils.readStaticFieldFromClass(Display.class, "DATA_LEFT_ROTATION_ID", new TypeToken<>() {});
    private static final EntityDataAccessor<Quaternionf> DATA_RIGHT_ROTATION_ID = ReflectionUtils.readStaticFieldFromClass(Display.class, "DATA_RIGHT_ROTATION_ID", new TypeToken<>() {});
    private static final EntityDataAccessor<Byte> DATA_BILLBOARD_RENDER_CONSTRAINTS_ID = ReflectionUtils.readStaticFieldFromClass(Display.class, "DATA_BILLBOARD_RENDER_CONSTRAINTS_ID", new TypeToken<>() {});
    private static final EntityDataAccessor<Integer> DATA_BRIGHTNESS_OVERRIDE_ID = ReflectionUtils.readStaticFieldFromClass(Display.class, "DATA_BRIGHTNESS_OVERRIDE_ID", new TypeToken<>() {});
    private static final EntityDataAccessor<Float> DATA_VIEW_RANGE_ID = ReflectionUtils.readStaticFieldFromClass(Display.class, "DATA_VIEW_RANGE_ID", new TypeToken<>() {});
    private static final EntityDataAccessor<Float> DATA_SHADOW_RADIUS_ID = ReflectionUtils.readStaticFieldFromClass(Display.class, "DATA_SHADOW_RADIUS_ID", new TypeToken<>() {});
    private static final EntityDataAccessor<Float> DATA_SHADOW_STRENGTH_ID = ReflectionUtils.readStaticFieldFromClass(Display.class, "DATA_SHADOW_STRENGTH_ID", new TypeToken<>() {});
    private static final EntityDataAccessor<Float> DATA_WIDTH_ID = ReflectionUtils.readStaticFieldFromClass(Display.class, "DATA_WIDTH_ID", new TypeToken<>() {});
    private static final EntityDataAccessor<Float> DATA_HEIGHT_ID = ReflectionUtils.readStaticFieldFromClass(Display.class, "DATA_HEIGHT_ID", new TypeToken<>() {});
    private static final EntityDataAccessor<Integer> DATA_GLOW_COLOR_OVERRIDE_ID = ReflectionUtils.readStaticFieldFromClass(Display.class, "DATA_GLOW_COLOR_OVERRIDE_ID", new TypeToken<>() {});

    public NMSDisplayEntity(T handle) {
        super(handle);
    }

    @Override
    public VanillaField<Integer> interpolationDurationInTicks() {
        return new EntityDataField<>(this::getHandle, DATA_TRANSFORMATION_INTERPOLATION_DURATION_ID);
    }

    @Override
    public VanillaField<Integer> teleportDurationInTicks() {
        return new EntityDataField<>(this::getHandle, DATA_POS_ROT_INTERPOLATION_DURATION_ID);
    }

    @Override
    public VanillaField<Integer> interpolationDelayInTicks() {
        return new EntityDataField<>(this::getHandle, DATA_TRANSFORMATION_INTERPOLATION_START_DELTA_TICKS_ID);
    }

    @Override
    public VanillaField<Transformation> transformation() {
        return new VanillaField<>() {
            @Override
            public void set(Transformation newValue) {
                getHandle().getEntityData().set(DATA_TRANSLATION_ID, newValue.translation().get());
                getHandle().getEntityData().set(DATA_LEFT_ROTATION_ID, newValue.leftRotation().get());
                getHandle().getEntityData().set(DATA_SCALE_ID, newValue.scale().get());
                getHandle().getEntityData().set(DATA_RIGHT_ROTATION_ID, newValue.rightRotation().get());
            }

            @Override
            public Transformation get() {
                Transformation transformation = MCCPlatform.getInstance().getElementFactory().createTransformation();
                transformation.leftRotation().set(getHandle().getEntityData().get(DATA_LEFT_ROTATION_ID));
                transformation.rightRotation().set(getHandle().getEntityData().get(DATA_RIGHT_ROTATION_ID));
                transformation.scale().set(getHandle().getEntityData().get(DATA_SCALE_ID));
                transformation.translation().set(getHandle().getEntityData().get(DATA_TRANSLATION_ID));
                return transformation;
            }
        };
    }

    @Override
    public VanillaField<Billboard> billboard() {
        return new VanillaField<>() {
            @Override
            public void set(Billboard newValue) {
                getHandle().getEntityData().set(DATA_BILLBOARD_RENDER_CONSTRAINTS_ID, newValue.getId());
            }

            @Override
            public Billboard get() {
                return switch (getHandle().getEntityData().get(DATA_BILLBOARD_RENDER_CONSTRAINTS_ID)) {
                    case 0 -> Billboard.FIXED;
                    case 1 -> Billboard.VERTICAL;
                    case 2 -> Billboard.HORIZONTAL;
                    case 3 -> Billboard.CENTER;
                    default ->
                            throw new IllegalStateException("Unexpected value: " + getHandle().getEntityData().get(DATA_BILLBOARD_RENDER_CONSTRAINTS_ID));
                };
            }
        };
    }

    @Override
    public VanillaField<BrightnessOverride> brightnessOverride() {
        return new VanillaField<>() {
            @Override
            public void set(BrightnessOverride newValue) {
                getHandle().getEntityData().set(DATA_BRIGHTNESS_OVERRIDE_ID, newValue != null ? new Brightness(newValue.block(), newValue.sky()).pack() : -1);
            }

            @Override
            public BrightnessOverride get() {
                int i = getHandle().getEntityData().get(DATA_BRIGHTNESS_OVERRIDE_ID);
                if (i != -1) {
                    var nms = Brightness.unpack(i);
                    return new BrightnessOverride(nms.block(), nms.sky());
                }
                return null;
            }
        };
    }

    @Override
    public VanillaField<Float> viewRange() {
        return new EntityDataField<>(this::getHandle, DATA_VIEW_RANGE_ID);
    }

    @Override
    public VanillaField<Float> shadowStrength() {
        return new EntityDataField<>(this::getHandle, DATA_SHADOW_STRENGTH_ID);
    }

    @Override
    public VanillaField<Float> shadowRadius() {
        return new EntityDataField<>(this::getHandle, DATA_SHADOW_RADIUS_ID);
    }

    @Override
    public VanillaField<Float> width() {
        return new EntityDataField<>(this::getHandle, DATA_WIDTH_ID);
    }

    @Override
    public VanillaField<Float> height() {
        return new EntityDataField<>(this::getHandle, DATA_HEIGHT_ID);
    }

    @Override
    public VanillaField<Integer> glowColorOverride() {
        return new EntityDataField<>(this::getHandle, DATA_GLOW_COLOR_OVERRIDE_ID);
    }

    public static class NMSTransformation extends MCCHandle<com.mojang.math.Transformation> implements Transformation {
        public static final MCCConverter<com.mojang.math.Transformation, NMSTransformation> CONVERTER = converter(NMSTransformation.class, com.mojang.math.Transformation.class, NMSTransformation::new, MCCHandle::getHandle);

        public NMSTransformation(com.mojang.math.Transformation handle) {
            super(handle);
        }

        @Override
        public VanillaField<Vector3f> translation() {
            return new VanillaField<>() {
                @Override
                public void set(Vector3f newValue) {
                    handle.getTranslation().set(newValue);
                }

                @Override
                public Vector3f get() {
                    return handle.getTranslation();
                }
            };
        }

        @Override
        public VanillaField<Quaternionf> leftRotation() {
            return new VanillaField<>() {
                @Override
                public void set(Quaternionf newValue) {
                    handle.getLeftRotation().set(newValue);
                }

                @Override
                public Quaternionf get() {
                    return handle.getLeftRotation();
                }
            };
        }

        @Override
        public VanillaField<Quaternionf> rightRotation() {
            return new VanillaField<>() {
                @Override
                public void set(Quaternionf newValue) {
                    handle.getRightRotation().set(newValue);
                }

                @Override
                public Quaternionf get() {
                    return handle.getRightRotation();
                }
            };
        }

        @Override
        public VanillaField<Vector3f> scale() {
            return new VanillaField<>() {
                @Override
                public void set(Vector3f newValue) {
                    handle.getScale().set(newValue);
                }

                @Override
                public Vector3f get() {
                    return handle.getScale();
                }
            };
        }
    }

    public static class Item extends NMSDisplayEntity<Display.ItemDisplay> implements MCCDisplayEntity.Item {
        private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK_ID = ReflectionUtils.readStaticFieldFromClass(net.minecraft.world.entity.Display.ItemDisplay.class, "DATA_ITEM_STACK_ID", new TypeToken<>() {});
        private static final EntityDataAccessor<Byte> DATA_ITEM_DISPLAY_ID = ReflectionUtils.readStaticFieldFromClass(net.minecraft.world.entity.Display.ItemDisplay.class, "DATA_ITEM_DISPLAY_ID", new TypeToken<>() {});

        public static final MCCConverter<net.minecraft.world.entity.Display.ItemDisplay, NMSDisplayEntity.Item> CONVERTER = converter(NMSDisplayEntity.Item.class, net.minecraft.world.entity.Display.ItemDisplay.class, NMSDisplayEntity.Item::new, MCCHandle::getHandle);

        public Item(net.minecraft.world.entity.Display.ItemDisplay handle) {
            super(handle);
        }

        @Override
        public VanillaField<MCCItemStack> itemStack() {
            return new WrappedEntityDataField<>(new TypeToken<>() {}, new EntityDataField<>(this::getHandle, DATA_ITEM_STACK_ID));
        }

        @Override
        public VanillaField<Display> display() {
            return new VanillaField<>() {
                @Override
                public void set(Display newValue) {
                    getHandle().getEntityData().set(DATA_ITEM_DISPLAY_ID, newValue.getId());
                }

                @Override
                public Display get() {
                    return Display.byId(getHandle().getEntityData().get(DATA_BILLBOARD_RENDER_CONSTRAINTS_ID));
                }
            };
        }
    }

    public static class Block extends NMSDisplayEntity<Display.BlockDisplay> implements MCCDisplayEntity.Block {
        private static final EntityDataAccessor<BlockState> DATA_BLOCK_STATE_ID = ReflectionUtils.readStaticFieldFromClass(Display.BlockDisplay.class, "DATA_BLOCK_STATE_ID", new TypeToken<>() {});

        public static final MCCConverter<Display.BlockDisplay, NMSDisplayEntity.Block> CONVERTER = converter(NMSDisplayEntity.Block.class, Display.BlockDisplay.class, NMSDisplayEntity.Block::new, MCCHandle::getHandle);

        public Block(Display.BlockDisplay handle) {
            super(handle);
        }

        @Override
        public VanillaField<MCCBlockState> blockState() {
            return new WrappedEntityDataField<>(new TypeToken<>() {}, new EntityDataField<>(this::getHandle, DATA_BLOCK_STATE_ID));
        }
    }

    public static class Text extends NMSDisplayEntity<Display.TextDisplay> implements MCCDisplayEntity.Text {
        private static final EntityDataAccessor<net.minecraft.network.chat.Component> DATA_TEXT_ID = ReflectionUtils.readStaticFieldFromClass(Display.TextDisplay.class, "DATA_TEXT_ID", new TypeToken<>() {});
        private static final EntityDataAccessor<Integer> DATA_LINE_WIDTH_ID = ReflectionUtils.readStaticFieldFromClass(Display.TextDisplay.class, "DATA_LINE_WIDTH_ID", new TypeToken<>() {});
        private static final EntityDataAccessor<Integer> DATA_BACKGROUND_COLOR_ID = ReflectionUtils.readStaticFieldFromClass(Display.TextDisplay.class, "DATA_BACKGROUND_COLOR_ID", new TypeToken<>() {});
        private static final EntityDataAccessor<Byte> DATA_TEXT_OPACITY_ID = ReflectionUtils.readStaticFieldFromClass(Display.TextDisplay.class, "DATA_TEXT_OPACITY_ID", new TypeToken<>() {});
        private static final EntityDataAccessor<Byte> DATA_STYLE_FLAGS_ID = ReflectionUtils.readStaticFieldFromClass(Display.TextDisplay.class, "DATA_STYLE_FLAGS_ID", new TypeToken<>() {});

        public static final MCCConverter<Display.TextDisplay, NMSDisplayEntity.Text> CONVERTER = converter(NMSDisplayEntity.Text.class, Display.TextDisplay.class, NMSDisplayEntity.Text::new, MCCHandle::getHandle);

        public Text(Display.TextDisplay handle) {
            super(handle);
        }

        @Override
        public VanillaField<Alignment> alignment() {
            return new VanillaField<>() {
                @Override
                public void set(Alignment newValue) {
                    switch (newValue) {
                        case LEFT:
                            setFlag(Display.TextDisplay.FLAG_ALIGN_LEFT, true);
                            setFlag(Display.TextDisplay.FLAG_ALIGN_RIGHT, false);
                            break;
                        case RIGHT:
                            setFlag(Display.TextDisplay.FLAG_ALIGN_LEFT, false);
                            setFlag(Display.TextDisplay.FLAG_ALIGN_RIGHT, true);
                            break;
                        case CENTER:
                            setFlag(Display.TextDisplay.FLAG_ALIGN_LEFT, false);
                            setFlag(Display.TextDisplay.FLAG_ALIGN_RIGHT, false);
                            break;
                        default:
                            throw new IllegalArgumentException("Unknown alignment " + newValue);
                    }
                }

                @Override
                public Alignment get() {
                    byte flagBits = getHandle().getEntityData().get(DATA_STYLE_FLAGS_ID);
                    return MCCPlatform.getInstance().getConversionService().wrap(Display.TextDisplay.getAlign(flagBits), Alignment.class);
                }
            };
        }

        @Override
        public VanillaField<Component> text() {
            return new WrappedEntityDataField<>(new TypeToken<>() {}, new EntityDataField<>(this::getHandle, DATA_TEXT_ID));
        }

        @Override
        public VanillaField<Integer> lineWidth() {
            return new EntityDataField<>(this::getHandle, DATA_LINE_WIDTH_ID);
        }

        @Override
        public VanillaField<Integer> backgroundColorArgb() {
            return new EntityDataField<>(this::getHandle, DATA_BACKGROUND_COLOR_ID);
        }

        @Override
        public VanillaField<Boolean> default_background() {
            return new VanillaField<>() {
                private static final Byte MASK = (byte) 4;

                @Override
                public void set(Boolean newValue) {
                    setFlag(Display.TextDisplay.FLAG_USE_DEFAULT_BACKGROUND, newValue);
                }

                @Override
                public Boolean get() {
                    return getFlag(Display.TextDisplay.FLAG_USE_DEFAULT_BACKGROUND);
                }
            };
        }

        @Override
        public VanillaField<Boolean> see_through() {
            return new VanillaField<>() {
                private static final Byte MASK = (byte) 2;

                @Override
                public void set(Boolean newValue) {
                    setFlag(Display.TextDisplay.FLAG_SEE_THROUGH, newValue);
                }

                @Override
                public Boolean get() {
                    return getFlag(Display.TextDisplay.FLAG_SEE_THROUGH);
                }
            };
        }

        @Override
        public VanillaField<Boolean> shadow() {
            return new VanillaField<>() {
                private static final Byte MASK = (byte) 1;

                @Override
                public void set(Boolean newValue) {
                    setFlag(Display.TextDisplay.FLAG_SHADOW, newValue);
                }

                @Override
                public Boolean get() {
                    return getFlag(Display.TextDisplay.FLAG_SHADOW);
                }
            };
        }

        @Override
        public VanillaField<Byte> textOpacity() {
            return new EntityDataField<>(this::getHandle, DATA_TEXT_OPACITY_ID);
        }

        private boolean getFlag(int flag) {
            return (getHandle().getEntityData().get(DATA_STYLE_FLAGS_ID) & flag) != 0;
        }

        private void setFlag(int flag, boolean set) {
            byte flagBits = getHandle().getEntityData().get(DATA_STYLE_FLAGS_ID);

            if (set) {
                flagBits |= flag;
            } else {
                flagBits &= ~flag;
            }

            getHandle().getEntityData().set(DATA_STYLE_FLAGS_ID, flagBits);
        }
    }
    

}
