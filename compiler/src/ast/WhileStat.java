/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

/**
 * WhileStat ::= "while" Expression "{" StatementList "}"
 */
public class WhileStat extends Statement {

    public WhileStat(Expression e, StatementList s) {
        this.expr = e;
        this.statementList = s;
    }

    /**
     * Getters
     */
    public Expression getExpr() {
        return expr;
    }
    public StatementList getStatementList() {
        return statementList;
    }

    @Override
    public void genC(PW pw) {
        pw.printIdent("while (");

        expr.genC(pw);

        pw.println(") {");

        pw.add();
        statementList.genC(pw);
        pw.sub();

        pw.printlnIdent("}");
    }
    @Override
    public void genJava(PW pw) {
        pw.printIdent("while (");

        expr.genJava(pw);

        pw.println(") {");

        pw.add();
        statementList.genJava(pw);
        pw.sub();

        pw.printlnIdent("}");
    }

    private Expression expr;
    private StatementList statementList;
}
