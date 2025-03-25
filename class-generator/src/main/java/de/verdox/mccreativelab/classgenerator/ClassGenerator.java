package de.verdox.mccreativelab.classgenerator;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.classgenerator.codegen.ClassBuilder;
import de.verdox.mccreativelab.classgenerator.codegen.CodeLineBuilder;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedParameterizedType;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedType;
import de.verdox.mccreativelab.classgenerator.codegen.expressions.CodeExpression;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.clazz.ClassType;
import de.verdox.mccreativelab.classgenerator.conversion.*;
import de.verdox.mccreativelab.classgenerator.wrapper.WrappedClass;
import de.verdox.mccreativelab.classgenerator.wrapper.WrappedClassRegistry;
import de.verdox.mccreativelab.classgenerator.wrapper.WrapperInterfaceGenerator;
import de.verdox.mccreativelab.wrapper.MCCWrapped;
import de.verdox.mccreativelab.wrapper.inventory.MCCMenuType;
import de.verdox.mccreativelab.wrapper.item.components.MCCItemComponent;
import net.kyori.adventure.key.Key;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.packs.VanillaAdventureAdvancements;
import net.minecraft.data.advancements.packs.VanillaHusbandryAdvancements;
import net.minecraft.data.advancements.packs.VanillaNetherAdvancements;
import net.minecraft.data.advancements.packs.VanillaTheEndAdvancements;
import net.minecraft.data.worldgen.DimensionTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.decoration.PaintingVariants;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimMaterials;
import net.minecraft.world.item.equipment.trim.TrimPattern;
import net.minecraft.world.item.equipment.trim.TrimPatterns;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import net.minecraft.world.level.block.entity.PotDecorations;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.saveddata.maps.MapId;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.function.TriConsumer;
import org.apache.commons.lang3.function.TriFunction;
import org.bukkit.event.inventory.InventoryType;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;
import java.util.function.*;
import java.util.logging.Logger;

public class ClassGenerator {

    //TODO: ItemKeys -> Create class that contains all Item keys
    //TODO: BlockItemKeys -> Create a class that contains all BlockItems

    public static final Logger LOGGER = Logger.getLogger(ClassGenerator.class.getName());

    public static final File API_MODULE_GENERATION_DIR = new File("../../api/generated/");
    public static final File VANILLA_MODULE_GENERATION_DIR = new File("../../vanilla/generated/");
    public static final File CLASS_GENERATOR_MODULE_GENERATION_DIR = new File("../generated/");


    public static final File API_GENERATION_DIR = CLASS_GENERATOR_MODULE_GENERATION_DIR;
    public static final File VANILLA_GENERATION_DIR = CLASS_GENERATOR_MODULE_GENERATION_DIR;
    public static final List<String> EXCLUDED_PACKAGES = List.of("java.");
    ;

    private static final List<Class<?>> excludedTypes = List.of(
/*            AdventureModePredicate.class,
            Unit.class,
            WritableBookContent.class,
            WrittenBookContent.class,
            DebugStickState.class,
            BannerPatternLayers.class,
            BundleContents.class,
            Fireworks.class,
            PotDecorations.class,
            CustomData.class,
            ItemEnchantments.class,
            ChargedProjectiles.class,
            ItemContainerContents.class,*/
            Function.class,
            BiFunction.class,
            TriFunction.class,
            Consumer.class,
            BiConsumer.class,
            TriConsumer.class,
            Predicate.class,
            BiPredicate.class
    );


    public static void run() {
        LOGGER.info("Running class generator");
        try {
            generateMenuTypesClass();
            generateTypedKeys();
            //generateMCCItemComponentWrapper();
            //createItemComponentConverters();
            //generateEventClasses();
            //MCCConverterGenerator.createGeneratedConvertersClass(VANILLA_GENERATION_DIR);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static void generateEventClasses() throws IOException {
        EventGeneratorFromBukkitPreset generator = new EventGeneratorFromBukkitPreset(API_GENERATION_DIR, VANILLA_GENERATION_DIR, "MCC", "", excludedTypes, EXCLUDED_PACKAGES);
        generator.generateEventWrappers();
    }

    private static void generateMCCItemComponentWrapper() throws IOException {
        try {
            FileUtils.deleteDirectory(API_GENERATION_DIR);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        WrapperInterfaceGenerator generator = new WrapperInterfaceGenerator(API_GENERATION_DIR, VANILLA_GENERATION_DIR, "MCC", "", "NMS", excludedTypes, EXCLUDED_PACKAGES);

        generateDataComponentWrapper(generator, Unbreakable.class);
        generateDataComponentWrapper(generator, ItemLore.class);
        //generateDataComponentWrapper(generator, ItemEnchantments.class);
        //generateDataComponentWrapper(generator, ItemAttributeModifiers.class);
        generateDataComponentWrapper(generator, CustomModelData.class);
        generateDataComponentWrapper(generator, Unit.class);
        generateDataComponentWrapper(generator, FoodProperties.class);
        //generateDataComponentWrapper(generator, Consumable.class);
        generateDataComponentWrapper(generator, UseRemainder.class);
        generateDataComponentWrapper(generator, UseCooldown.class);
        //generateDataComponentWrapper(generator, DamageResistant.class);
        generateDataComponentWrapper(generator, Tool.class);
        generateDataComponentWrapper(generator, Enchantable.class);
        //generateDataComponentWrapper(generator, Equippable.class);
        generateDataComponentWrapper(generator, Repairable.class);
        //generateDataComponentWrapper(generator, DeathProtection.class);
        generateDataComponentWrapper(generator, DyedItemColor.class);
        generateDataComponentWrapper(generator, MapItemColor.class);
        generateDataComponentWrapper(generator, MapId.class);
        //generateDataComponentWrapper(generator, MapDecorations.class);
        generateDataComponentWrapper(generator, MapPostProcessing.class);
        generateDataComponentWrapper(generator, ChargedProjectiles.class);
        //generateDataComponentWrapper(generator, BundleContents.class);
        //generateDataComponentWrapper(generator, PotionContents.class);
        generateDataComponentWrapper(generator, SuspiciousStewEffects.class);
        generateDataComponentWrapper(generator, JukeboxPlayable.class);
        generateDataComponentWrapper(generator, LodestoneTracker.class);
        //generateDataComponentWrapper(generator, ItemContainerContents.class);
        generateDataComponentWrapper(generator, BlockItemStateProperties.class);
        //generateDataComponentWrapper(generator, SeededContainerLoot.class);

/*        Class<?> dataComponentClass = DataComponents.class;
        LOGGER.info("Generating wrapper classes for DataComponents in " + dataComponentClass + " to " + parentDir.getAbsolutePath());


        WrapperInterfaceGenerator generator = new WrapperInterfaceGenerator(API_GENERATION_DIR, VANILLA_GENERATION_DIR, "MCC", "", "NMS", excludedTypes, EXCLUDED_PACKAGES);
        // Alle Felder der Klasse durchgehen
        for (Field field : dataComponentClass.getDeclaredFields()) {
            // Überprüfen, ob das Feld vom Typ DataComponentType ist

            if (!DataComponentType.class.isAssignableFrom(field.getType())) {
                continue;
            }
            Type genericType = field.getGenericType();
            if (!(genericType instanceof ParameterizedType parameterizedType)) {
                continue;
            }
            Type[] typeArguments = parameterizedType.getActualTypeArguments();

            if (typeArguments.length > 0) {
                if (typeArguments[0] instanceof Class<?> componentType) {
                    if (NMSMapper.isSwapped(componentType) && !NMSMapper.swap(ClassType.from(componentType)).getPackageName().contains("de.verdox.mccreativelab")) {
                        continue;
                    }


                    if (!excludedTypes.contains(componentType)) {
                        generator.generateWrapper(componentType, "de.verdox.mccreativelab.wrapper.item.components", "de.verdox.mccreativelab.impl.vanilla.wrapper.item.components", ClassType.from(MCCItemComponent.class), true);
                    }
                }
            }
        }*/
        /*                classBuilder.writeClassFile(parentDir);*/
    }

    private static void generateDataComponentWrapper(WrapperInterfaceGenerator generator, Class<?> nmsClass) {
        if (!excludedTypes.contains(nmsClass)) {
            generator.generateWrapper(nmsClass, "de.verdox.mccreativelab.wrapper.item.components", "de.verdox.mccreativelab.impl.vanilla.wrapper.item.components", ClassType.from(MCCItemComponent.class), true);
        }
    }

    private static void generateMenuTypesClass() throws IOException {
        ClassBuilder classBuilder = new ClassBuilder();
        classBuilder.withPackage("de.verdox.mccreativelab.wrapper.inventory");
        classBuilder.withHeader("public", ClassBuilder.ClassHeader.INTERFACE, "MCCMenuTypes", "");

        for (Field field : MenuType.class.getFields()) {
            if (!Modifier.isStatic(field.getModifiers()))
                continue;
            if (!Modifier.isFinal(field.getModifiers()))
                continue;
            if (!Modifier.isPublic(field.getModifiers()))
                continue;
            if (!MenuType.class.isAssignableFrom(field.getType()))
                continue;
            if (!(field.getGenericType() instanceof ParameterizedType parameterizedType))
                continue;
            String fieldName = field.getName();
            String keyValue = fieldName.toLowerCase(Locale.ROOT);


            int containerSize = 0;
            if (keyValue.contains("generic")) {
                String[] twoInts = keyValue.replace("generic_", "").split("x");
                containerSize = Integer.parseInt(twoInts[0]) * Integer.parseInt(twoInts[1]);
            } else {
                for (InventoryType value : InventoryType.values()) {
                    if (value.getMenuType() == null)
                        continue;
                    if (value.getMenuType().key().equals(Key.key(Key.MINECRAFT_NAMESPACE, keyValue)))
                        containerSize = value.getDefaultSize();
                }
            }


            classBuilder.includeImport(NMSMapper.swap(CapturedParameterizedType.from(parameterizedType).getTypeArguments().get(0)));
            classBuilder.withField("public static final", CapturedParameterizedType.from(MCCMenuType.class).withExplicitType(NMSMapper.swap(CapturedParameterizedType.from(parameterizedType).getTypeArguments().get(0))), fieldName, "new MCCMenuType<>(Key.key(Key.MINECRAFT_NAMESPACE, \"" + keyValue + "\"), " + containerSize + ")");
        }
        classBuilder.includeImport(CapturedParameterizedType.from(Key.class));
        classBuilder.writeClassFile(API_GENERATION_DIR);
    }

    private static void generateTypedKeys() throws IOException, IllegalAccessException {
        WrapperInterfaceGenerator generator = new WrapperInterfaceGenerator(API_GENERATION_DIR, VANILLA_GENERATION_DIR, "MCC", "", "NMS", excludedTypes, EXCLUDED_PACKAGES);
        TypedKeyCollectionBuilder typedKeyCollectionBuilder = new TypedKeyCollectionBuilder(API_GENERATION_DIR, VANILLA_GENERATION_DIR, "MCC", "", excludedTypes, List.of());
        typedKeyCollectionBuilder.generateForPlatformGroupingClass(Attributes.class, new TypeToken<>() {}, Registries.ATTRIBUTE, "de.verdox.mccreativelab.wrapper.typed", "MCCAttributes");

        typedKeyCollectionBuilder.generateForPlatformGroupingClass(MobEffects.class, new TypeToken<>() {}, Registries.MOB_EFFECT, "de.verdox.mccreativelab.wrapper.typed", "MCCEffects");

        typedKeyCollectionBuilder.generateForPlatformGroupingClass(SoundEvents.class, new TypeToken<>() {}, Registries.SOUND_EVENT, "de.verdox.mccreativelab.wrapper.typed", "MCCSounds");

        typedKeyCollectionBuilder.generateForPlatformGroupingClass(Blocks.class, new TypeToken<>() {}, Registries.BLOCK, "de.verdox.mccreativelab.wrapper.typed", "MCCBlocks");

        typedKeyCollectionBuilder.generateForPlatformGroupingClass(BlockTags.class, new TypeToken<>() {}, Registries.BLOCK, "de.verdox.mccreativelab.wrapper.typed", "MCCBlockTags");
        typedKeyCollectionBuilder.generateForPlatformGroupingClass(ItemTags.class, new TypeToken<>() {}, Registries.ITEM, "de.verdox.mccreativelab.wrapper.typed", "MCCItemTags");

        typedKeyCollectionBuilder.generateForPlatformGroupingClass(Items.class, new TypeToken<>() {}, Registries.ITEM, "de.verdox.mccreativelab.wrapper.typed", "MCCItems");
        typedKeyCollectionBuilder.generateForPlatformGroupingClass(DataComponents.class, new TypeToken<>() {}, Registries.DATA_COMPONENT_TYPE, "de.verdox.mccreativelab.wrapper.typed", "MCCDataComponentTypes");

        //generator.generateWrapper(FrogVariant.class, wrapperPackage + "types", implPackage + "types", ClassType.from(MCCWrapped.class), false);
        typedKeyCollectionBuilder.generateForPlatformGroupingClass(FrogVariant.class, new TypeToken<>() {}, Registries.FROG_VARIANT, "de.verdox.mccreativelab.wrapper.typed", "MCCFrogVariants");

        //generator.generateWrapper(GameEvent.class, wrapperPackage + "types", implPackage + "types", ClassType.from(MCCWrapped.class), false);
        typedKeyCollectionBuilder.generateForPlatformGroupingClass(GameEvent.class, new TypeToken<>() {}, Registries.GAME_EVENT, "de.verdox.mccreativelab.wrapper.typed", "MCCGameEvents");

        //generator.generateWrapper(Instrument.class, wrapperPackage + "types", implPackage + "types", ClassType.from(MCCWrapped.class), false);
        typedKeyCollectionBuilder.generateForPlatformGroupingClass(Instrument.class, new TypeToken<>() {}, Registries.INSTRUMENT, "de.verdox.mccreativelab.wrapper.typed", "MCCInstruments");

        //generator.generateWrapper(JukeboxSong.class, wrapperPackage + "types", implPackage + "types", ClassType.from(MCCWrapped.class), false);
        typedKeyCollectionBuilder.generateForPlatformGroupingClass(JukeboxSongs.class, new TypeToken<>() {}, Registries.JUKEBOX_SONG, "de.verdox.mccreativelab.wrapper.typed", "MCCJukeboxSongs");

        //typedKeyCollectionBuilder.generateForPlatformGroupingClass(EntityType.class, new TypeToken<>() {}, Registries.ENTITY_TYPE, "de.verdox.mccreativelab.wrapper.typed", "MCCEntityTypes");

        //typedKeyCollectionBuilder.generateForPlatformGroupingClass(MemoryModuleType.class, new TypeToken<>() {}, Registries.MEMORY_MODULE_TYPE, "de.verdox.mccreativelab.wrapper.typed", "MCCMemoryModuleTypes");

        //generator.generateWrapper(PaintingVariant.class, wrapperPackage + "types", implPackage + "types", ClassType.from(MCCWrapped.class), false);
        typedKeyCollectionBuilder.generateForPlatformGroupingClass(PaintingVariants.class, new TypeToken<>() {}, Registries.PAINTING_VARIANT, "de.verdox.mccreativelab.wrapper.typed", "MCCPaintingVariants");

        //generator.generateWrapper(PoiType.class, wrapperPackage + "types", implPackage + "types", ClassType.from(MCCWrapped.class), false);
        typedKeyCollectionBuilder.generateForPlatformGroupingClass(PoiTypes.class, new TypeToken<>() {}, Registries.POINT_OF_INTEREST_TYPE, "de.verdox.mccreativelab.wrapper.typed", "MCCPoiTypes");

        //typedKeyCollectionBuilder.generateForPlatformGroupingClass(Potions.class, new TypeToken<>() {}, Registries.POTION, "de.verdox.mccreativelab.wrapper.typed", "MCCPotions");
        //typedKeyCollectionBuilder.generateForPlatformGroupingClass(RecipeType.class, new TypeToken<>() {}, Registries.RECIPE_TYPE, "de.verdox.mccreativelab.wrapper.typed", "MCCRecipeTypes");
        //typedKeyCollectionBuilder.generateForPlatformGroupingClass(Schedule.class, new TypeToken<>() {}, Registries.SCHEDULE, "de.verdox.mccreativelab.wrapper.typed", "MCCSchedules");
        //generator.generateWrapper(StatType.class, wrapperPackage+"types", implPackage+"types", DynamicType.of(MCCWrapped.class), false);
        //typedKeyCollectionBuilder.generateForPlatformGroupingClass(Stats.class, new TypeToken<>() {}, Registries.STAT_TYPE, "de.verdox.mccreativelab.wrapper.typed", "MCCStatTypes");

        //typedKeyCollectionBuilder.generateForPlatformGroupingClass(StructureType.class, new TypeToken<>() {}, Registries.STRUCTURE_TYPE, "de.verdox.mccreativelab.wrapper.typed", "MCCStructureTypes");

        //generator.generateWrapper(VillagerProfession.class, wrapperPackage + "types", implPackage + "types", ClassType.from(MCCWrapped.class), false);
        typedKeyCollectionBuilder.generateForPlatformGroupingClass(VillagerProfession.class, new TypeToken<>() {}, Registries.VILLAGER_PROFESSION, "de.verdox.mccreativelab.wrapper.typed", "MCCVillagerProfessions");

        //typedKeyCollectionBuilder.generateForPlatformGroupingClass(VillagerType.class, new TypeToken<>() {}, Registries.VILLAGER_TYPE, "de.verdox.mccreativelab.wrapper.typed", "MCCVillagerTypes");

        //generator.generateWrapper(DecoratedPotPattern.class, wrapperPackage + "types", implPackage + "types", ClassType.from(MCCWrapped.class), false);
        typedKeyCollectionBuilder.generateForPlatformGroupingClass(DecoratedPotPatterns.class, new TypeToken<>() {}, Registries.DECORATED_POT_PATTERN, "de.verdox.mccreativelab.wrapper.typed", "MCCDecoratedPotPatterns");

        typedKeyCollectionBuilder.generateForPlatformGroupingClass(Biomes.class, new TypeToken<>() {}, Registries.BIOME, "de.verdox.mccreativelab.wrapper.typed", "MCCBiomes");

        //generator.generateWrapper(DimensionType.class, wrapperPackage + "types", implPackage + "types", ClassType.from(MCCWrapped.class), false);
        typedKeyCollectionBuilder.generateForPlatformGroupingClass(DimensionTypes.class, new TypeToken<>() {}, Registries.DIMENSION_TYPE, "de.verdox.mccreativelab.wrapper.typed", "MCCDimensionTypes");

        //generator.generateWrapper(Enchantment.class, wrapperPackage + "types", implPackage + "types", ClassType.from(MCCWrapped.class), false);
        typedKeyCollectionBuilder.generateForPlatformGroupingClass(Enchantments.class, new TypeToken<>() {}, Registries.ENCHANTMENT, "de.verdox.mccreativelab.wrapper.typed", "MCCEnchantments");

        typedKeyCollectionBuilder.generateForPlatformGroupingClass(VanillaAdventureAdvancements.class, new TypeToken<>() {}, Registries.ADVANCEMENT, "de.verdox.mccreativelab.wrapper.typed", "MCCAdventureAdvancements");

        typedKeyCollectionBuilder.generateForPlatformGroupingClass(VanillaHusbandryAdvancements.class, new TypeToken<>() {}, Registries.ADVANCEMENT, "de.verdox.mccreativelab.wrapper.typed", "MCCHusbandryAdvancements");
        typedKeyCollectionBuilder.generateForPlatformGroupingClass(VanillaNetherAdvancements.class, new TypeToken<>() {}, Registries.ADVANCEMENT, "de.verdox.mccreativelab.wrapper.typed", "MCCNetherAdvancements");
        typedKeyCollectionBuilder.generateForPlatformGroupingClass(VanillaTheEndAdvancements.class, new TypeToken<>() {}, Registries.ADVANCEMENT, "de.verdox.mccreativelab.wrapper.typed", "MCCEndAdvancements");


        //generator.generateWrapper(TrimMaterial.class, wrapperPackage + "types", implPackage + "types", ClassType.from(MCCWrapped.class), false);
        typedKeyCollectionBuilder.generateForPlatformGroupingClass(TrimMaterials.class, new TypeToken<>() {}, Registries.TRIM_MATERIAL, "de.verdox.mccreativelab.wrapper.typed", "MCCTrimMaterials");

        //generator.generateWrapper(TrimPattern.class, wrapperPackage + "types", implPackage + "types", ClassType.from(MCCWrapped.class), false);
        typedKeyCollectionBuilder.generateForPlatformGroupingClass(TrimPatterns.class, new TypeToken<>() {}, Registries.TRIM_PATTERN, "de.verdox.mccreativelab.wrapper.typed", "MCCTrimPatterns");
        //typedKeyCollectionBuilder.generateForPlatformGroupingClass(BuiltInLootTables.class, ResourceKey.class, Registries.LOOT_TABLE,"de.verdox.mccreativelab.wrapper.typed", "MCCLootTables", converterGenerator);
    }

    public static void createItemComponentConverters() throws IOException {
        new MCCConverterGenerator(API_GENERATION_DIR, VANILLA_GENERATION_DIR, "DataComponentTypeConverter", "de.verdox.mccreativelab.impl.vanilla.platform.converter", CapturedParameterizedType.from(DataComponentType.class), CapturedParameterizedType.from(ClassType.create("NMSDataComponentType", "de.verdox.mccreativelab.impl.vanilla.item.components")), new MCCConverterGenerator.WrappingMethodBuilder() {
            @Override
            public void wrapFunction(AbstractClassGenerator abstractClassGenerator, String parameterName, CapturedParameterizedType nativeType, CapturedParameterizedType typeToWrapInto, CodeLineBuilder codeLineBuilder) {
                for (Field declaredField : DataComponents.class.getDeclaredFields()) {

                    if (!Modifier.isFinal(declaredField.getModifiers()) && !Modifier.isPublic(declaredField.getModifiers()) && !Modifier.isStatic(declaredField.getModifiers()))
                        continue;
                    if (!(declaredField.getGenericType() instanceof ParameterizedType parameterizedType))
                        continue;
                    CapturedParameterizedType capturedParameterizedType = CapturedParameterizedType.from(parameterizedType);

                    CapturedType<?, ?> componentNativeType = capturedParameterizedType.getTypeArguments().get(0);
                    CapturedType<?, ?> swappedComponentType = NMSMapper.swap(capturedParameterizedType.getTypeArguments().get(0));
                    if (abstractClassGenerator.isForbiddenType(componentNativeType))
                        continue;
                    CodeExpression defaultInstantiation = CodeExpression.create().with("null");
                    if (NMSMapper.isSwapped(componentNativeType)) {
                        WrappedClass wrappedClass = WrappedClassRegistry.INSTANCE.getWrappingInformationByWrappedClass(componentNativeType.getRawType());
                        if (wrappedClass != null && wrappedClass.implementation() != null) {
                            defaultInstantiation = CodeExpression.create().with("() -> new ").with(wrappedClass.implementation()).with("(null)");
                            codeLineBuilder.getClassBuilder().includeImport(wrappedClass.implementation());
                        }
                    }


                    codeLineBuilder.getClassBuilder().includeImport(componentNativeType);
                    codeLineBuilder.getClassBuilder().includeImport(swappedComponentType);
                    codeLineBuilder.getClassBuilder().includeImport(CapturedType.from(DataComponents.class));

                    codeLineBuilder.append("if(").append(parameterName).append(".equals(DataComponents." + declaredField.getName()).appendAndNewLine(")) {");
                    codeLineBuilder.increaseDepth(1);
                    codeLineBuilder.append("return done(").append("new ").append(typeToWrapInto).append("<>(" + parameterName + ", ").appendTypeToken(componentNativeType).append(", ").appendTypeToken(swappedComponentType).append(", ").append(defaultInstantiation).append("));");
                    codeLineBuilder.increaseDepth(-1);
                    codeLineBuilder.appendAndNewLine("}");

                }
                codeLineBuilder.append("return notDone(null);");
            }

            @Override
            public void unwrapFunction(AbstractClassGenerator abstractClassGenerator, String parameterName, CapturedParameterizedType apiType, CapturedParameterizedType nativeTypeToUnwrapTo, CodeLineBuilder codeLineBuilder) {
                codeLineBuilder.append("return done((DataComponentType) platformImplType.getHandle());");
            }
        }, excludedTypes, EXCLUDED_PACKAGES).create();

    }
}
