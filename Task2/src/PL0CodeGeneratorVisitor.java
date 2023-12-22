import org.antlr.v4.runtime.tree.ParseTree;

class PL0CodeGeneratorVisitor extends PL0BaseVisitor<String> {
    private StringBuilder code = new StringBuilder();
    private int labelCounter = 0;
    private int tempVariableCounter = 0;

    @Override
    public String visitAssignmentStatement(PL0Parser.AssignmentStatementContext ctx) {
        String dest = ctx.identifier().getText();

        // <赋值语句>→<标识符>:=<表达式>
        String src = visitExpression(ctx.expression());
        emit(dest + " := " + src);
        return null;
    }

    @Override
    public String visitExpression(PL0Parser.ExpressionContext ctx){
        if (ctx.getChildCount() == 3){
            String tempVar = generateTempVariable();
            String src1 = visitExpression(ctx.expression());
            String op = ctx.additionOperator().getText();
            String src2 = visitTerm(ctx.term());
            emit(tempVar + " := " + src1 +  op + src2);
            return tempVar;
        }
        else if (ctx.getChildCount() == 2){
            // 项的情况
            String tempVar = generateTempVariable();
            String op = ctx.additionOperator().getText();
            String src = visitTerm(ctx.term());
            emit(tempVar + " := " + op +  src);
            return tempVar;

        }
        else{
            String src = visitTerm(ctx.term());
            return src;
        }
    }

    @Override
    public String visitTerm(PL0Parser.TermContext ctx) {
        if (ctx.getChildCount() == 1){
            // 因子
            String src = visitFactor(ctx.factor());
            return src;
        }
        else{
            // <项><乘法运算符><因?>
            String tempVar = generateTempVariable();
            String src1 = visitTerm(ctx.term());
            String src2 = visitFactor(ctx.factor());
            String op = ctx.multiplicationOperator().getText();
            emit(tempVar + " := " + src1 +  op + src2);
            return tempVar;
        }
    }

    @Override
    public String visitFactor(PL0Parser.FactorContext ctx){
        if(ctx.expression()!=null){
            String tempVar = generateTempVariable();
            String src = visitExpression(ctx.expression());
            emit(tempVar + " := " + src);
            return tempVar;
        }
        else {
            return ctx.getText();
        }
    }
    @Override
    public String visitConditionStatement(PL0Parser.ConditionStatementContext ctx) {
        String label = generateLabel();
        String condition = ctx.condition().getText();
        emit("if " + condition +" goto " + label);
        visit(ctx.statement());
        emit(label + ":");
        return null;
    }

    @Override
    public String visitLoopStatement(PL0Parser.LoopStatementContext ctx) {
        String startLabel = generateLabel();
        String endLabel = generateLabel();
        emit(startLabel + ":");
        String condition = ctx.condition().getText();
        emit("if " + condition +" goto " + endLabel);
        visit(ctx.statement());
        emit("goto " + startLabel);
        emit(endLabel + ":");
        return null;
    }

    private String generateTempVariable() {
        return "T" + tempVariableCounter++;
    }

    private String getTempVariable(){
        return "T" + tempVariableCounter;
    }

    private String generateLabel() {
        return "L" + labelCounter++;
    }

    private void emit(String codeLine) {
        code.append(codeLine).append("\n");
    }

    public String getGeneratedCode() {
        return code.toString();
    }
}