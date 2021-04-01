/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

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

    private Type type;
    private IdList idList;
}
