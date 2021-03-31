/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

/**
 * IfStat ::= "if" Expression "{" StatementList "}" [ "else" "{" StatementList "}" ]
 */
public class IfStat extends Statement {

    public IfStat(Expression expr, StatementList leftList, StatementList rightList) {
        this.expr = expr;
        this.leftList = leftList;
        this.rightList = rightList;
    }

    @Override
    public void genC(PW pw) {
        // TODO Auto-generated method stub
    }

    @Override
    public void genJava(PW pw) {
        // TODO Auto-generated method stub
    }

    Expression expr;
    StatementList leftList, rightList;
}
