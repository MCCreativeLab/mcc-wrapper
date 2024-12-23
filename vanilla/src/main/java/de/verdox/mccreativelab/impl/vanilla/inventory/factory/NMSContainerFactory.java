package de.verdox.mccreativelab.impl.vanilla.inventory.factory;

import com.mojang.datafixers.util.Function4;
import de.verdox.mccreativelab.conversion.ConversionService;
import de.verdox.mccreativelab.impl.vanilla.inventory.factory.creator.AbstractLocationBasedMenuCreatorInstance;
import de.verdox.mccreativelab.impl.vanilla.inventory.factory.creator.AbstractMenuCreatorInstance;
import de.verdox.mccreativelab.impl.vanilla.inventory.factory.creator.AbstractSharedBasedMenuCreatorInstance;
import de.verdox.mccreativelab.impl.vanilla.inventory.types.menu.custom.*;
import de.verdox.mccreativelab.wrapper.entity.types.MCCPlayer;
import de.verdox.mccreativelab.wrapper.inventory.MCCContainer;
import de.verdox.mccreativelab.wrapper.inventory.MCCContainerMenu;
import de.verdox.mccreativelab.wrapper.inventory.MCCMenuType;
import de.verdox.mccreativelab.wrapper.inventory.MCCMenuTypes;
import de.verdox.mccreativelab.wrapper.inventory.factory.MCCContainerFactory;
import de.verdox.mccreativelab.wrapper.inventory.types.MCCLocatedContainerMenu;
import de.verdox.mccreativelab.wrapper.inventory.types.MCCPrivateContainerMenu;
import de.verdox.mccreativelab.wrapper.inventory.types.MCCSharedContainerMenu;
import de.verdox.mccreativelab.wrapper.inventory.types.menu.*;
import de.verdox.mccreativelab.wrapper.inventory.types.menu.creator.LocationBasedMenuCreatorInstance;
import de.verdox.mccreativelab.wrapper.inventory.types.menu.creator.MenuCreatorInstance;
import de.verdox.mccreativelab.wrapper.inventory.types.menu.creator.SharedMenuCreatorInstance;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.world.MCCLocation;
import net.kyori.adventure.text.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlastFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.entity.SmokerBlockEntity;
import org.apache.commons.lang3.function.TriFunction;
import org.checkerframework.checker.index.qual.Positive;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class NMSContainerFactory implements MCCContainerFactory {
    private final Map<MCCMenuType<?>, Function<MCCLocation, ? extends LocationBasedMenuCreatorInstance<? extends MCCLocatedContainerMenu<?, ?>>>> containerBasedCache = new HashMap<>();
    private final Map<MCCMenuType<?>, Function<MCCContainer, ? extends SharedMenuCreatorInstance<MCCContainer, ? extends MCCSharedContainerMenu<?, ?>>>> sharedBasedCache = new HashMap<>();
    private final Map<MCCMenuType<?>, Supplier<MenuCreatorInstance<MCCPrivateContainerMenu<?, ?>>>> privateContainerCache = new HashMap<>();
    private final Map<MCCMenuType<?>, Supplier<? extends MenuCreatorInstance<? extends MCCContainerMenu<?, ?>>>> standardCache = new HashMap<>();
    private final MCCPlatform mccPlatform;

    public NMSContainerFactory(MCCPlatform mccPlatform) {
        this.mccPlatform = mccPlatform;
        initLocationBased();
        initShared();
        initPrivate();
    }

    @Override
    public MCCContainer createSimpleContainer(@Positive int size) {
        return mccPlatform.getConversionService().wrap(new SimpleContainer(size));
    }

    @Override
    public <F extends MCCLocatedContainerMenu<?, ?>> LocationBasedMenuCreatorInstance<F> createLocated(MCCMenuType<F> menuType, @NotNull MCCLocation location) {
        return (LocationBasedMenuCreatorInstance<F>) containerBasedCache.get(menuType).apply(location);
    }

    @Override
    public <C extends MCCContainer, F extends MCCSharedContainerMenu<?, ?>> SharedMenuCreatorInstance<C, F> createShared(MCCMenuType<F> menuType, @NotNull C container) {
        return (SharedMenuCreatorInstance<C, F>) sharedBasedCache.get(menuType).apply(container);
    }

    @Override
    public <F extends MCCPrivateContainerMenu<?, ?>> MenuCreatorInstance<F> createPrivate(MCCMenuType<F> menuType) {
        return (MenuCreatorInstance<F>) privateContainerCache.get(menuType).get();
    }

    @Override
    public <F extends MCCContainerMenu<?, ?>> MenuCreatorInstance<F> create(MCCMenuType<F> menuType) {
        return (MenuCreatorInstance<F>) standardCache.get(menuType).get();
    }

    private <F extends MCCPrivateContainerMenu<?, ?>> void registerPrivate(MCCMenuType<F> menuType, Class<F> mccType, BiFunction<Integer, Inventory, AbstractContainerMenu> menuCreator) {
        Supplier<MenuCreatorInstance<MCCPrivateContainerMenu<?, ?>>> supplier = () -> new AbstractMenuCreatorInstance<>() {
            @Override
            public MCCPrivateContainerMenu<?, ?> createMenuForPlayer(MCCPlayer player, Component title) {
                ConversionService conversionService = mccPlatform.getConversionService();
                ServerPlayer serverPlayer = conversionService.unwrap(player);

                return openViaNMSMenuProvider(serverPlayer, title, (syncId, inventory, player1) -> menuCreator.apply(syncId, inventory));
            }
        };
        privateContainerCache.put(menuType, supplier);
        standardCache.put(menuType, supplier);
    }

    protected void initLocationBased() {
        new FactoryRegistration.LocationBased<>(MCCMenuTypes.ANVIL, MCCAnvilContainerMenu.class)
                .menuProviderWithoutContainerData(CustomAnvilMenu::new)
                .register(this);

        new FactoryRegistration.LocationBased<>(MCCMenuTypes.CRAFTING, MCCWorkBenchContainerMenu.class)
                .menuProviderWithoutContainerData(CustomCraftingMenu::new)
                .register(this);

        new FactoryRegistration.LocationBased<>(MCCMenuTypes.ENCHANTMENT, MCCEnchantingTableContainerMenu.class)
                .menuProviderWithoutContainerData(CustomEnchantmentMenu::new)
                .register(this);

        new FactoryRegistration.LocationBased<>(MCCMenuTypes.GRINDSTONE, MCCGrindstoneContainerMenu.class)
                .menuProviderWithoutContainerData(CustomGrindstoneMenu::new)
                .register(this);

        new FactoryRegistration.LocationBased<>(MCCMenuTypes.LOOM, MCCLoomContainerMenu.class)
                .menuProviderWithoutContainerData(CustomLoomMenu::new)
                .register(this);

        new FactoryRegistration.LocationBased<>(MCCMenuTypes.SMITHING, MCCSmithingContainerMenu.class)
                .menuProviderWithoutContainerData(CustomSmithingMenu::new)
                .register(this);

        new FactoryRegistration.LocationBased<>(MCCMenuTypes.CARTOGRAPHY_TABLE, MCCCartographyTableContainerMenu.class)
                .menuProviderWithoutContainerData(CustomCartographyTableMenu::new)
                .register(this);

        new FactoryRegistration.LocationBased<>(MCCMenuTypes.STONECUTTER, MCCStonecutterContainerMenu.class)
                .menuProviderWithoutContainerData(CustomStonecutterMenu::new)
                .register(this);

        new FactoryRegistration.LocationBased<>(MCCMenuTypes.BEACON, MCCBeaconContainerMenu.class)
                .withContainerData(() -> new SimpleContainerData(3))
                .menuProviderWithContainerData((integer, inventory, containerLevelAccess, containerData) -> new CustomBeaconMenu(integer, inventory, containerData, containerLevelAccess))
                .register(this);
    }

    protected void initShared() {
        new FactoryRegistration.Shared<>(MCCMenuTypes.GENERIC_3x3, MCCDispenserContainerMenu.class)
                .withContainer(() -> new SimpleContainer(9))
                .menuProviderWithoutContainerData(CustomDispenserMenu::new)
                .register(this);

        new FactoryRegistration.Shared<>(MCCMenuTypes.BLAST_FURNACE, MCCFurnaceContainerMenu.class)
                .withContainer(() -> new BlastFurnaceBlockEntity(new BlockPos(0, 0, 0), Blocks.BLAST_FURNACE.defaultBlockState()))
                .withContainerData(() -> new SimpleContainerData(4))
                .menuProviderWithContainerData(CustomBlastFurnaceMenu::new)
                .register(this);

        new FactoryRegistration.Shared<>(MCCMenuTypes.BREWING_STAND, MCCBrewingStandContainerMenu.class)
                .withContainer(() -> new SimpleContainer(5))
                .withContainerData(() -> {
                    SimpleContainerData simpleContainerData = new SimpleContainerData(3);
                    simpleContainerData.set(2, 400);
                    return simpleContainerData;
                })
                .menuProviderWithContainerData(CustomBrewingStandMenu::new)
                .register(this);

        new FactoryRegistration.Shared<>(MCCMenuTypes.FURNACE, MCCFurnaceContainerMenu.class)
                .withContainer(() -> new FurnaceBlockEntity(new BlockPos(0, 0, 0), Blocks.FURNACE.defaultBlockState()))
                .withContainerData(() -> new SimpleContainerData(4))
                .menuProviderWithContainerData(CustomFurnaceMenu::new)
                .register(this);

        new FactoryRegistration.Shared<>(MCCMenuTypes.HOPPER, MCCHopperContainerMenu.class)
                .withContainer(() -> new SimpleContainer(5))
                .menuProviderWithoutContainerData(CustomHopperMenu::new)
                .register(this);


        new FactoryRegistration.Shared<>(MCCMenuTypes.LECTERN, MCCLecternContainerMenu.class)
                .withContainer(() -> new SimpleContainer(1))
                .withContainerData(() -> new SimpleContainerData(1))
                .menuProviderWithContainerData(CustomLecternMenu::new)
                .register(this);

        new FactoryRegistration.Shared<>(MCCMenuTypes.SHULKER_BOX, MCCShulkerContainerMenu.class)
                .withContainer(() -> new SimpleContainer(27))
                .menuProviderWithoutContainerData(CustomShulkerBoxMenu::new)
                .register(this);


        new FactoryRegistration.Shared<>(MCCMenuTypes.SMOKER, MCCFurnaceContainerMenu.class)
                .withContainer(() -> new SmokerBlockEntity(new BlockPos(0, 0, 0), Blocks.SMOKER.defaultBlockState()))
                .withContainerData(() -> new SimpleContainerData(4))
                .menuProviderWithContainerData(CustomSmokerMenu::new)
                .register(this);


        new FactoryRegistration.Shared<>(MCCMenuTypes.GENERIC_9x1, MCCChestContainerMenu.class)
                .withContainer(() -> new SimpleContainer(9))
                .menuProviderWithoutContainerData((syncId, inventory, container) -> new CustomChestMenu(MenuType.GENERIC_9x1, syncId, inventory, container, 1))
                .register(this);

        new FactoryRegistration.Shared<>(MCCMenuTypes.GENERIC_9x2, MCCChestContainerMenu.class)
                .withContainer(() -> new SimpleContainer(18))
                .menuProviderWithoutContainerData((syncId, inventory, container) -> new CustomChestMenu(MenuType.GENERIC_9x2, syncId, inventory, container, 2))
                .register(this);

        new FactoryRegistration.Shared<>(MCCMenuTypes.GENERIC_9x3, MCCChestContainerMenu.class)
                .withContainer(() -> new SimpleContainer(27))
                .menuProviderWithoutContainerData((syncId, inventory, container) -> new CustomChestMenu(MenuType.GENERIC_9x3, syncId, inventory, container, 3))
                .register(this);

        new FactoryRegistration.Shared<>(MCCMenuTypes.GENERIC_9x4, MCCChestContainerMenu.class)
                .withContainer(() -> new SimpleContainer(36))
                .menuProviderWithoutContainerData((syncId, inventory, container) -> new CustomChestMenu(MenuType.GENERIC_9x4, syncId, inventory, container, 4))
                .register(this);

        new FactoryRegistration.Shared<>(MCCMenuTypes.GENERIC_9x5, MCCChestContainerMenu.class)
                .withContainer(() -> new SimpleContainer(45))
                .menuProviderWithoutContainerData((syncId, inventory, container) -> new CustomChestMenu(MenuType.GENERIC_9x5, syncId, inventory, container, 5))
                .register(this);

        new FactoryRegistration.Shared<>(MCCMenuTypes.GENERIC_9x6, MCCChestContainerMenu.class)
                .withContainer(() -> new SimpleContainer(54))
                .menuProviderWithoutContainerData((syncId, inventory, container) -> new CustomChestMenu(MenuType.GENERIC_9x6, syncId, inventory, container, 6))
                .register(this);
    }

    protected void initPrivate() {
        registerPrivate(MCCMenuTypes.CRAFTER_3x3, MCCCrafterContainerMenu.class, CustomCrafterMenu::new);
        registerPrivate(MCCMenuTypes.MERCHANT, MCCMerchantContainerMenu.class, CustomMerchantMenu::new);
    }

    /**
     * A template class used to register various types of menu builders
     * @param <F>
     */
    protected abstract static class FactoryRegistration<F extends MCCContainerMenu<?, ?>> {
        protected final MCCMenuType<F> menuType;
        protected final Class<F> mccType;

        protected FactoryRegistration(MCCMenuType<F> menuType, Class<F> mccType) {
            this.menuType = menuType;
            this.mccType = mccType;
        }

        /**
         * Used for menus that depend on being at a particular location.
         * They may or may not have ContainerData
         * @param <F> the generic menu type
         */
        protected static class LocationBased<F extends MCCLocatedContainerMenu<?, ?>> extends FactoryRegistration<F> {
            private TriFunction<Integer, Inventory, ContainerLevelAccess, AbstractContainerMenu> menuCreatorWithoutContainerData;
            private Function4<Integer, Inventory, ContainerLevelAccess, ContainerData, AbstractContainerMenu> menuCreatorWithContainerData;
            private Supplier<ContainerData> containerDataCreator;

            protected LocationBased(MCCMenuType<F> menuType, Class<F> mccType) {
                super(menuType, mccType);
            }

            public LocationBased<F> menuProviderWithoutContainerData(TriFunction<Integer, Inventory, ContainerLevelAccess, AbstractContainerMenu> menuCreatorWithoutContainerData) {
                this.menuCreatorWithoutContainerData = menuCreatorWithoutContainerData;
                return this;
            }

            public LocationBased<F> menuProviderWithContainerData(Function4<Integer, Inventory, ContainerLevelAccess, ContainerData, AbstractContainerMenu> menuCreatorWithContainerData) {
                this.menuCreatorWithContainerData = menuCreatorWithContainerData;
                return this;
            }

            public LocationBased<F> withContainerData(Supplier<ContainerData> containerDataCreator) {
                this.containerDataCreator = containerDataCreator;
                return this;
            }

            public void register(NMSContainerFactory nmsContainerFactory) {
                Function<MCCLocation, LocationBasedMenuCreatorInstance<F>> menuCreator;
                if (containerDataCreator != null) {
                    menuCreator = mccLocation -> new AbstractLocationBasedMenuCreatorInstance<>(mccLocation) {
                        private final ContainerData containerData = Objects.requireNonNull(containerDataCreator.get(), "The container data supplier of "+menuType.key()+" cannot produce null containers.");

                        @Override
                        public F createMenuForPlayer(MCCPlayer player, Component title) {
                            ConversionService conversionService = nmsContainerFactory.mccPlatform.getConversionService();
                            ServerPlayer serverPlayer = conversionService.unwrap(player);
                            return openViaNMSMenuProvider(serverPlayer, title, (syncId, inventory, player1) -> menuCreatorWithContainerData.apply(syncId, inventory, containerLevelAccess, containerData));
                        }
                    };
                } else {
                    menuCreator = mccLocation -> new AbstractLocationBasedMenuCreatorInstance<>(mccLocation) {
                        @Override
                        public F createMenuForPlayer(MCCPlayer player, Component title) {
                            ConversionService conversionService = nmsContainerFactory.mccPlatform.getConversionService();
                            ServerPlayer serverPlayer = conversionService.unwrap(player);
                            return openViaNMSMenuProvider(serverPlayer, title, (syncId, inventory, player1) -> menuCreatorWithoutContainerData.apply(syncId, inventory, containerLevelAccess));
                        }
                    };
                }


                nmsContainerFactory.containerBasedCache.put(menuType, menuCreator);
                nmsContainerFactory.standardCache.put(menuType, () -> menuCreator.apply(null));
            }
        }

        protected static class Shared<F extends MCCSharedContainerMenu<?, ?>, C extends MCCContainer, N extends Container> extends FactoryRegistration<F> {

            private Supplier<ContainerData> containerDataCreator;
            private Function4<Integer, Inventory, N, ContainerData, AbstractContainerMenu> menuProviderWithContainerData;
            private TriFunction<Integer, Inventory, N, AbstractContainerMenu> menuProviderWithoutContainerData;
            private Supplier<N> containerCreator;

            protected Shared(MCCMenuType<F> menuType, Class<F> mccType) {
                super(menuType, mccType);
            }

            public Shared<F, C, N> menuProviderWithContainerData(Function4<Integer, Inventory, N, ContainerData, AbstractContainerMenu> menuProviderWithContainerData) {
                this.menuProviderWithContainerData = menuProviderWithContainerData;
                return this;
            }

            public Shared<F, C, N> menuProviderWithoutContainerData(TriFunction<Integer, Inventory, N, AbstractContainerMenu> menuProviderWithoutContainerData) {
                this.menuProviderWithoutContainerData = menuProviderWithoutContainerData;
                return this;
            }

            public Shared<F, C, N> withContainerData(Supplier<ContainerData> containerDataCreator) {
                this.containerDataCreator = containerDataCreator;
                return this;
            }

            public Shared<F, C, N> withContainer(Supplier<N> container) {
                this.containerCreator = container;
                return this;
            }

            public void register(NMSContainerFactory nmsContainerFactory) {
                Objects.requireNonNull(containerCreator, "The container creator for the menu type " + menuType.key() + " cannot be null.");

                if (menuProviderWithContainerData != null && containerDataCreator == null || containerDataCreator != null && menuProviderWithContainerData == null) {
                    throw new IllegalStateException("If you provide container data for " + menuType.key() + " you need to use the menuProvider that uses container data as well.");
                }

                Function<MCCContainer, SharedMenuCreatorInstance<MCCContainer, F>> function;
                if (containerDataCreator != null) {
                    function = mccContainer -> new AbstractSharedBasedMenuCreatorInstance<>(mccContainer) {
                        private final ContainerData containerData = Objects.requireNonNull(containerDataCreator.get(), "The container data supplier of "+menuType.key()+" cannot produce null containers.");

                        @Override
                        public boolean isRightContainerSize(MCCContainer container) {
                            return mccContainer.getSize() == container.getSize();
                        }

                        @Override
                        public F createMenuForPlayer(MCCPlayer player, Component title) {
                            ConversionService conversionService = nmsContainerFactory.mccPlatform.getConversionService();
                            ServerPlayer serverPlayer = conversionService.unwrap(player);

                            return openViaNMSMenuProvider(serverPlayer, title, (syncId, inventory, player1) -> menuProviderWithContainerData.apply(syncId, inventory, conversionService.unwrap(mccContainer), containerData));
                        }
                    };
                } else {
                    function = mccContainer -> new AbstractSharedBasedMenuCreatorInstance<>(mccContainer) {
                        @Override
                        public boolean isRightContainerSize(MCCContainer container) {
                            return mccContainer.getSize() == container.getSize();
                        }

                        @Override
                        public F createMenuForPlayer(MCCPlayer player, Component title) {
                            ConversionService conversionService = nmsContainerFactory.mccPlatform.getConversionService();
                            ServerPlayer serverPlayer = conversionService.unwrap(player);

                            return openViaNMSMenuProvider(serverPlayer, title, (syncId, inventory, player1) -> menuProviderWithoutContainerData.apply(syncId, inventory, conversionService.unwrap(mccContainer)));
                        }
                    };
                }

                nmsContainerFactory.sharedBasedCache.put(menuType, function);
                nmsContainerFactory.standardCache.put(menuType, () -> function.apply(nmsContainerFactory.mccPlatform.getConversionService().wrap(Objects.requireNonNull(containerCreator.get(), "The container supplier of "+menuType.key()+" cannot produce null containers."))));

            }
        }
    }
}
