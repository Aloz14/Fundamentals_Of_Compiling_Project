import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class Main {
    public static void main(String[] args) {
        // PL0源程序
        String sourceCode =
                "PROGRAM add\n" +
                        "VAR x, y;\n" +
                        "BEGIN\n" +
                        "  x := 1;\n" +
                        "  y := 2;\n" +
                        "  WHILE x < 5 DO x := x + 1;\n" +
                        "  IF y > 0 THEN y := y - 1; ENDIF;\n" +
                        "  y := y + x;\n" +
                        "END";

        // 词法分析器
        PL0Lexer lexer = new PL0Lexer(CharStreams.fromString(sourceCode));

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // 语法分析器
        PL0Parser parser = new PL0Parser(tokens);

        // 生成程序结构
        PL0Parser.ProgramContext programContext = parser.program();

        // 生成器，遍历AST树从而生成中间代码
        PL0CodeGeneratorVisitor codeGenerator = new PL0CodeGeneratorVisitor();
        codeGenerator.visit(programContext);
        String generatedCode = codeGenerator.getGeneratedCode();

        System.out.println(generatedCode);
    }
}