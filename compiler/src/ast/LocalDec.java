/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

import java.util.Iterator;

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
        pw.printIdent("");
        pw.print(type.getJavaName() + " ");

        Iterator<String> it = idlList.getIdList().iterator();
        while (it.hasNext()) {
            String id = it.next();
            pw.print(id);

            if (it.hasNext()) {
                pw.print(",");
            }
        }

        if (expr != null) {
            pw.print(" = ");
            expr.genJava(pw);
        }

        pw.println(";");
    }

    private Type type;
    private IdList idlList;
    private Expression expr;
}
