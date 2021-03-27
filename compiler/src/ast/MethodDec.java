/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

/**
 * MethodDec ::= "func" IdColon FormalParamDec [ "->" Type ]
 * "{" StatementList "}" |
 * "func" Id [ "->" Type ] "{" StatementList "}"
 */
public class MethodDec {

    public MethodDec(String id, Type returnType, StatementList statements) {
        this.id = id;
        this.returnType = returnType;
        this.statements = statements;
    }

    public MethodDec() {
    }

    /**
     * Getters
     */
    public String getId() {
        return id;
    }
    public Type getReturnType() {
        return returnType;
    }
    public StatementList getStatements() {
        return statements;
    }

    /**
     * Setters
     */
    public void setId(String id) {
        this.id = id;
    }
    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }
    public void setStatements(StatementList statements) {
        this.statements = statements;
    }

    private String id;
    private Type returnType;
    private StatementList statements;
}
