package benchmarks;

import de.verdox.mccreativelab.RegistryHelper;
import de.verdox.mccreativelab.TestBase;
import de.verdox.mccreativelab.conversion.ConversionCache;
import de.verdox.mccreativelab.conversion.ConversionServiceImpl;
import de.verdox.mccreativelab.conversion.converter.MCCConverter;
import de.verdox.mccreativelab.impl.vanilla.platform.NMSPlatform;
import de.verdox.mccreativelab.impl.vanilla.registry.NMSReference;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import de.verdox.mccreativelab.wrapper.registry.MCCReference;
import de.verdox.mccreativelab.wrapper.registry.MCCTag;
import de.verdox.mccreativelab.wrapper.registry.MCCTypedKey;
import de.verdox.mccreativelab.wrapper.typed.MCCBlocks;
import org.junit.platform.commons.util.ReflectionUtils;
import org.openjdk.jmh.annotations.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class ConversionBenchmarkTests extends TestBase {

    private static final List<MCCReference<?>> collectedReferences = new ArrayList<>();
    private static final List<MCCTag<?>> collectedTags = new ArrayList<>();
    private static final List<MCCTypedKey<?>> collectedTypedKeys = new ArrayList<>();
    private ConversionServiceImpl conversionService;
    private ConversionCache<MCCConverter<?, ?>> conversionCache;

    @Setup(Level.Trial)
    public void setup() {
        bootstrap(() -> new NMSPlatform(RegistryHelper.getRegistry(), RegistryHelper.getDataPack().fullRegistries().lookup()));
        for (Class<?> aClass : ReflectionUtils.findAllClassesInPackage(MCCBlocks.class.getPackageName(), aClass -> !aClass.isSynthetic() && !aClass.isAnonymousClass(), s -> true)) {
            addAllFields(aClass);
        }
        conversionService = (ConversionServiceImpl) MCCPlatform.getInstance().getConversionService();
        conversionCache = conversionService.getConversionCache();
    }

    private static void addAllFields(Class<?> typedClass) {
        for (Field declaredField : typedClass.getDeclaredFields()) {
            if (!Modifier.isStatic(declaredField.getModifiers())) {
                continue;
            }
            if (!Modifier.isFinal(declaredField.getModifiers())) {
                continue;
            }
            try {
                Object object = declaredField.get(null);
                if (object instanceof MCCReference<?> reference) {
                    if (!getExcludedRegistries().contains(reference.unwrapKey().get().getRegistryKey())) {
                        collectedReferences.add(reference);
                    }
                } else if (object instanceof MCCTag<?> tag) {
                    if (!getExcludedRegistries().contains(tag.getRegistryKey().key())) {
                        collectedTags.add(tag);
                    }
                } else if (object instanceof MCCTypedKey<?> typedKey) {
                    if (!getExcludedRegistries().contains(typedKey.getRegistryKey())) {
                        collectedTypedKeys.add(typedKey);
                    }
                }
            } catch (IllegalAccessException e) {

            }
        }
    }

    @State(Scope.Thread)
    public static class BenchmarkState {
        int index = 0;
        NMSReference<?> reference;

        @Setup(Level.Invocation)
        public void setup() {
            int safeIndex = (index % collectedReferences.size() + collectedReferences.size()) % collectedReferences.size();
            reference = (NMSReference<?>) collectedReferences.get(safeIndex);
            index++;
        }
    }

    @Benchmark
    public void benchmarkNativeToImplGet(BenchmarkState state) {
        conversionCache.getNativeToImpls().get(state.reference.getHandle().getClass());
    }

    @Benchmark
    public void benchmarkGetAllVariantsForNativeType(BenchmarkState state) {
        conversionCache.streamAllVariantsForNativeType(state.reference.getHandle().getClass());
    }

    @Benchmark
    public void benchmarkConversionCacheWrap(BenchmarkState state) {
        MCCPlatform.getInstance().getConversionService().wrap(state.reference.getHandle());
    }
}
