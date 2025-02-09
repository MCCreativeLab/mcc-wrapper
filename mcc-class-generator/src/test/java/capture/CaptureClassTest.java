package capture;

import com.google.common.reflect.TypeToken;
import de.verdox.mccreativelab.classgenerator.codegen.CodeLineBuilder;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.CapturedType;
import de.verdox.mccreativelab.classgenerator.codegen.type.impl.clazz.ClassType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class CaptureClassTest {

    @Test
    public void testRightCapture() {
        CapturedType<?, ?> capturedType = CapturedType.from(SimpleClass.class);

        Assertions.assertInstanceOf(ClassType.class, capturedType);
    }

    @Test
    public void testRightCapture2() {
        CapturedType<?, ?> capturedType = CapturedType.from(Map.class);

        Assertions.assertInstanceOf(ClassType.class, capturedType);
    }

    @Test
    public void testRightName() {
        ClassType<?> capturedType = (ClassType<?>) CapturedType.from(SimpleClass.class);

        Assertions.assertEquals("SimpleClass", capturedType.getClassName());
    }

    @Test
    public void testRightWriteSimple() {
        CodeLineBuilder codeLineBuilder = new CodeLineBuilder(null, 0);

        CapturedType.from(SimpleClass.class).write(codeLineBuilder);
        Assertions.assertEquals("SimpleClass", codeLineBuilder.toString());
    }

    @Test
    public void testRightWriteMemberClass() {
        CodeLineBuilder codeLineBuilder = new CodeLineBuilder(null, 0);

        CapturedType.from(SimpleClass.SimpleInnerClass.class).write(codeLineBuilder);
        Assertions.assertEquals("SimpleClass.SimpleInnerClass", codeLineBuilder.toString());
    }

    @Test
    public void testWrite4() {
        CodeLineBuilder codeLineBuilder = new CodeLineBuilder(null, 0);

        CapturedType.from(Map.class).write(codeLineBuilder);
        Assertions.assertEquals("Map<K, V>", codeLineBuilder.toString());
    }

    @Test
    public void testWrite5() {
        CodeLineBuilder codeLineBuilder = new CodeLineBuilder(null, 0);

        var token = new TypeToken<Map<? extends String, ? super Integer>>() {};

        CapturedType.from(token).write(codeLineBuilder);
        Assertions.assertEquals("Map<? extends String, ? super Integer>", codeLineBuilder.toString());
    }
}
