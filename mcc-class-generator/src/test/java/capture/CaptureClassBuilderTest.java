package capture;

import de.verdox.mccreativelab.classgenerator.codegen.ClassBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CaptureClassBuilderTest {

    @Test
    public void testSimple(){
        ClassBuilder class1 = new ClassBuilder();
        class1.withHeader("public", ClassBuilder.ClassHeader.CLASS, "TestClass", "");
        class1.withPackage("de.verdox.test");

        ClassBuilder class2 = new ClassBuilder();
        class1.withHeader("public", ClassBuilder.ClassHeader.CLASS, "Inner", "");
        class1.withPackage("de.verdox.test");
        class1.includeInnerClass(class2);

        Assertions.assertNotNull(class2.asCapturedClassType().getDeclaringClass());
    }

}
