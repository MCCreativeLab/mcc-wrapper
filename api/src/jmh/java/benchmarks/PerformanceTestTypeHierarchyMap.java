package benchmarks;

import de.verdox.mccreativelab.conversion.TypeHierarchyMap;
import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class PerformanceTestTypeHierarchyMap {

    // Dummy-Hierarchy
    static class A {}
    static class B extends A {}
    static class C extends B {}
    static class D extends B {}
    static class E extends C {}

    private TypeHierarchyMap<String> map;
    private List<Class<?>> testClasses;
    private int index;

    @Setup(Level.Invocation)
    public void setup() {
        map = new TypeHierarchyMap<>();
        testClasses = List.of(A.class, B.class, C.class, D.class, E.class);
        index = 0;
    }

    @Benchmark
    public void benchmarkPut() {
        Class<?> clazz = testClasses.get(index % testClasses.size());
        map.put(clazz, "val" + index);
        index++;
    }

    @Benchmark
    public void benchmarkGet() {
        Class<?> clazz = testClasses.get(index % testClasses.size());
        map.get(clazz);
        index++;
    }
}
