import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class JavaValidator {
    public static boolean isValidJava(String code) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        return (compiler != null);
    }
}
