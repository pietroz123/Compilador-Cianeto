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

    public FieldDec(Type type, IdList idList) {
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
        pw.printIdent(type.getJavaName() + " ");

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
        // TODO Auto-generated method stub
    }

    private Type type;
    private IdList idList;
}
