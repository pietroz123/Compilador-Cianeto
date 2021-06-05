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
        pw.printIdent("");
        leftExpr.genC(pw);

        if (rightExpr != null) {
            pw.print(" = ");

            if (leftExpr.getType() instanceof TypeCianetoClass && rightExpr.getType() instanceof TypeCianetoClass) {
                pw.print("(_class_"+leftExpr.getType().getName()+" *) ");
            }

            rightExpr.genC(pw);
        }

        pw.println(";");
    }

    @Override
    public void genJava(PW pw) {
        pw.printIdent("");
        leftExpr.genJava(pw);

        if (rightExpr != null) {
            pw.print(" = ");
            rightExpr.genJava(pw);
        }

        pw.println(";");
    }

    private Expression leftExpr, rightExpr;
}
