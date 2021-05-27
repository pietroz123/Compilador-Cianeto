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
        pw.printIdent("");

        Boolean isClass = type instanceof TypeCianetoClass;
        if (isClass) {
            pw.print("_class_" + type.getCname() + " ");
        } else {
            pw.print(type.getCname() + " ");
        }

        Iterator<String> it = idlList.getIdList().iterator();
        while (it.hasNext()) {
            String id = it.next();
            pw.print((isClass ? "*" : "") + "_" + id); // classes precisam de ponteiro, por isso o "*"

            if (it.hasNext()) {
                pw.print(",");
            }
        }

        if (expr != null) {
            pw.print(" = ");
            expr.genC(pw);
        }

        pw.println(";");
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
