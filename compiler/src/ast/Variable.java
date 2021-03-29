/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

/**
 * Classe para as variáveis
 */
public class Variable {
    public Variable(String id, Type type) {
        this.id = id;
        this.type = type;
    }

    /**
     * Getters
     */
    public String getId() {
        return id;
    }
    public Type getType() {
        return type;
    }

    /**
     * Setters
     */
    public void setName(String id) {
        this.id = id;
    }
    public void setType(Type type) {
        this.type = type;
    }

    private String id;
    private Type type;
}
