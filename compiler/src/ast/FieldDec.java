/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

import java.util.Iterator;

/**
 * FieldDec ::= "var" Type IdList [ ";" ]
 */
public class FieldDec extends Member {

    public FieldDec(TypeCianetoClass currentClass, Type type, IdList idList) {
        this.currentClass = currentClass;
        this.type = type;
        this.idList = idList;
    }

    /**
     * Getters
     */
    public Type getType() {
        return type;
    }
    public IdList getIdList() {
        return idList;
    }

    @Override
    void genJava(PW pw) {
        pw.printIdent("private " + type.getJavaName() + " ");

        Iterator<String> it = idList.getIdList().iterator();
        while (it.hasNext()) {
            String id = it.next();
            pw.print(id);

            if (it.hasNext()) {
                pw.print(",");
            }
        }

        pw.println(";");
    }

    @Override
    void genC(PW pw) {
        if (this.type instanceof TypeCianetoClass) {
            pw.printIdent("_class_" + this.getType().getName() + " ");
        } else {
            pw.printIdent(this.getType().getCname() + " ");
        }

        Iterator<String> it = idList.getIdList().iterator();
        while (it.hasNext()) {
            String id = it.next();
            pw.print("_" + this.currentClass.getName() + "_" + id);

            if (it.hasNext()) {
                pw.print(",");
            }
        }

        pw.println(";");
    }

    private TypeCianetoClass currentClass;
    private Type type;
    private IdList idList;
}
