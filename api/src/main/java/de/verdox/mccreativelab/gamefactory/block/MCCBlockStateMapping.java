package de.verdox.mccreativelab.gamefactory.block;

import de.verdox.mccreativelab.gamefactory.MCCGameFactory;
import de.verdox.mccreativelab.wrapper.annotations.MCCRequireVanillaElement;
import de.verdox.mccreativelab.wrapper.block.MCCBlockState;
import de.verdox.mccreativelab.wrapper.block.MCCBlockType;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import de.verdox.mccreativelab.wrapper.typed.MCCBlocks;
import de.verdox.mccreativelab.wrapper.typed.MCCRegistries;
import de.verdox.vserializer.SerializableField;
import de.verdox.vserializer.exception.SerializationException;
import de.verdox.vserializer.generic.SerializationElement;
import de.verdox.vserializer.generic.Serializer;
import de.verdox.vserializer.generic.SerializerBuilder;
import de.verdox.vserializer.json.JsonSerializerContext;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * This class is responsible for mapping unused vanilla block states to custom block states.
 * <p>
 * The mapper will persistently save its mappings to a file so they can be recovered after a restart.
 */
public class MCCBlockStateMapping {
    private MappingStorage mappingStorage = new MappingStorage();

    private static MCCBlockStateMapping INSTANCE;

    public static MCCBlockStateMapping getInstance() {
        if(INSTANCE == null) {
            try {
                INSTANCE = new MCCBlockStateMapping();
            } catch (IOException | SerializationException e) {
                throw new RuntimeException(e);
            }
        }
        return INSTANCE;
    }

    private MCCBlockStateMapping() throws IOException, SerializationException {
        File file = new File("/mcc_block_mapping/mapping_storage.json");
        file.getParentFile().mkdirs();
        if(file.exists()) {
            JsonSerializerContext serializerContext = new JsonSerializerContext();
            SerializationElement serializationElement = serializerContext.readFromFile(file);
            mappingStorage = MappingStorage.SERIALIZER.deserialize(serializationElement);
        }
    }

    public MCCBlockState getVanillaMapping(MCCCustomBlockState customBlockState) {
        if (customBlockState.isVanilla()) {
            throw new IllegalArgumentException("Only custom block states are allowed here");
        }
        if (!mappingStorage.customToVanillaMapping.containsKey(customBlockState)) {
            return null;
        }
        Mapping mapping = mappingStorage.customToVanillaMapping.get(customBlockState);
        return mapping.mappingType().getBlockType().get().getAllBlockStates().get(mapping.stateId());
    }

    public MCCCustomBlockState getCustomBlockState(@MCCRequireVanillaElement MCCBlockState blockState) {
        blockState.requireVanilla();
        Mapping mapping = Mapping.fromState(blockState);
        if (mapping == null) {
            return null;
        }
        return (MCCCustomBlockState) mappingStorage.vanillaToCustomMapping.getOrDefault(mapping, null);
    }

    public void requireMapping(MCCCustomBlockState customBlockState, MappingType mappingType) {
        if (customBlockState.isVanilla()) {
            throw new IllegalArgumentException("Only custom block states are allowed here");
        }
        int stateId = mappingStorage.getNextAvailableStateId(mappingType);
        Mapping mapping = new Mapping(mappingType, stateId);
        mappingStorage.putMapping(mapping, customBlockState);
    }

    public enum MappingType {
        NOTE_BLOCK(MCCBlocks.NOTE_BLOCK),
        ;
        @org.jetbrains.annotations.NotNull
        private final MCCReference<MCCBlockType> blockType;

        MappingType(@MCCRequireVanillaElement MCCReference<MCCBlockType> blockType) {
            blockType.requireVanilla();
            this.blockType = blockType;
        }

        @Nullable
        public static MappingType byType(@MCCRequireVanillaElement MCCBlockType blockType) {
            blockType.requireVanilla();
            for (MappingType value : MappingType.values()) {
                if (value.blockType.equals(blockType)) {
                    return value;
                }
            }
            return null;
        }

        public @NotNull MCCReference<MCCBlockType> getBlockType() {
            return blockType;
        }
    }

    public record Mapping(MappingType mappingType, int stateId) {
        public static final Serializer<Mapping> SERIALIZER = SerializerBuilder.create("mapping", Mapping.class)
                .constructor(
                        new SerializableField<>("mappingType", Serializer.Enum.create("mappingType", MappingType.class), Mapping::mappingType),
                        new SerializableField<>("stateId", Serializer.Primitive.INTEGER, Mapping::stateId),
                        Mapping::new
                )
                .build();

        @Nullable
        public static Mapping fromState(@MCCRequireVanillaElement MCCBlockState vanilla) {
            vanilla.requireVanilla();
            int stateId = vanilla.getBlockType().getIndexOfState(vanilla);
            MappingType mappingType = MappingType.byType(vanilla.getBlockType());
            if (mappingType == null) {
                return null;
            }
            return new Mapping(mappingType, stateId);
        }
    }

    private static class MappingStorage {
        private static final Serializer<MCCBlockState> BLOCK_STATE_SERIALIZER = SerializerBuilder.create("block_state", MCCBlockState.class)
                .constructor(
                        new SerializableField<>("type", Serializer.Primitive.STRING, blockState -> blockState.getBlockType().key().asString()),
                        new SerializableField<>("state_id", Serializer.Primitive.INTEGER, blockState -> blockState.getBlockType().getIndexOfState(blockState)),
                        (block, stateId) -> {
                            Key key = Key.key(block);
                            MCCBlockType blockType;
                            if (key.namespace().equals("minecraft")) {
                                blockType = MCCRegistries.BLOCK_REGISTRY.get().get(key);
                            } else {
                                blockType = MCCGameFactory.BLOCK_REGISTRY.get().get(key);
                            }
                            return blockType.getAllBlockStates().get(stateId);
                        }
                )
                .build();


        private static final Serializer<MappingStorage> SERIALIZER = SerializerBuilder.create("mapping_storage", MappingStorage.class)
                .constructor(
                        new SerializableField<>("customToVanilla", Serializer.Map.create(Mapping.SERIALIZER, BLOCK_STATE_SERIALIZER, HashMap::new), mappingStorage -> mappingStorage.vanillaToCustomMapping),
                        (vanillaToCustomMapping) -> {
                            MappingStorage mappingStorage = new MappingStorage();
                            vanillaToCustomMapping.forEach((mapping, custom) -> mappingStorage.putMapping(mapping, (MCCCustomBlockState) custom));
                            return mappingStorage;
                        }
                )
                .build();

        private final Map<Mapping, MCCBlockState> vanillaToCustomMapping = new HashMap<>();
        private final Map<MCCBlockState, Mapping> customToVanillaMapping = new HashMap<>();
        private final Map<MappingType, boolean[]> freeMappingsLeft = new HashMap<>();

        public MappingStorage() {
            for (MappingType value : MappingType.values()) {
                boolean[] states = new boolean[value.getBlockType().get().getAllBlockStates().size()];
                Arrays.fill(states, true);
                freeMappingsLeft.put(value, states);
            }
        }

        void putMapping(Mapping mapping, MCCCustomBlockState customBlockState) {
            if (customBlockState.isVanilla()) {
                throw new IllegalArgumentException("The provided custom block state is marked as a vanilla block state");
            }

            if (vanillaToCustomMapping.containsKey(mapping)) {
                throw new IllegalArgumentException("Mapping " + mapping + " already taken by " + vanillaToCustomMapping.get(mapping));
            }
            if (customToVanillaMapping.containsKey(customBlockState)) {
                throw new IllegalArgumentException("Custom block state " + customBlockState + " already mapped to " + customToVanillaMapping.get(customBlockState));
            }

            List<MCCBlockState> states = mapping.mappingType().getBlockType().get().getAllBlockStates();
            if (mapping.stateId <= 0) {
                throw new IllegalArgumentException("Please pick a state id above 0");
            }
            if (mapping.stateId >= states.size()) {
                throw new IllegalArgumentException("The state id " + mapping.stateId + " is out of bounds for max state id " + (states.size() - 1));
            }

            boolean[] freeMapping = freeMappingsLeft.get(mapping.mappingType());
            freeMapping[mapping.stateId()] = false;
            freeMappingsLeft.put(mapping.mappingType(), freeMapping);

            vanillaToCustomMapping.put(mapping, customBlockState);
            customToVanillaMapping.put(customBlockState, mapping);
        }

        public int getNextAvailableStateId(MappingType mappingType) {
            boolean[] values = freeMappingsLeft.get(mappingType);
            for (int i = 0; i < freeMappingsLeft.get(mappingType).length; i++) {
                var free = values[i];
                if(free) {
                    return i;
                }
            }
            throw new IllegalStateException("No block state id is left for mapping type "+mappingType+". All states are taken.");
        }
    }

}
