/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

/**
 * AssignExpr ::= Expression [ "=" Expression ]
 */
public class AssignExpr extends Statement {

    public AssignExpr(Expression leftExpr, Expression rightExpr) {
        this.leftExpr = leftExpr;
        this.rightExpr = rightExpr;
    }

    @Override
    public void genC(PW pw) {
        // TODO Auto-generated method stub
    }

    @Override
    public void genJava(PW pw) {
        // TODO Auto-generated method stub
    }

    private Expression leftExpr, rightExpr;
}
