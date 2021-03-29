/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

/**
 * LocalDec ::= "var" Type IdList [ "=" Expression ]
 */
public class LocalDec extends Statement {

    public LocalDec(Type type, IdList idlList, Expression expr) {
        this.type = type;
        this.idlList = idlList;
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

    private Type type;
    private IdList idlList;
    private Expression expr;
}
