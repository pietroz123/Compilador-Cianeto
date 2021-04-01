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
        // TODO Auto-generated method stub
    }

    private StatementList statementList;
    private Expression expr;
}
