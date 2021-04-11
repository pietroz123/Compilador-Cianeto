/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

/**
 * RepeatStat ::= "repeat" StatementList "until" Expression
 */
public class RepeatStat extends Statement {

    public RepeatStat(StatementList statementList, Expression expr) {
        this.statementList = statementList;
        this.expr = expr;
    }

    @Override
    public void genC(PW pw) {
        // TODO Auto-generated method stub
    }
    @Override
    public void genJava(PW pw) {
		pw.printlnIdent("do {");
		pw.add();

		if (statementList != null) {
			statementList.genJava(pw);
		}

		pw.sub();
		pw.printIdent("} while (");

		expr.genJava(pw);

		pw.println(");");
    }

    private StatementList statementList;
    private Expression expr;
}
