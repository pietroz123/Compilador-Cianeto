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
public class MethodDec extends Member {

    public MethodDec(String id, FormalParamDec formalParamDec, Type returnType, StatementList statements) {
        this.id = id;
        this.formalParamDec = formalParamDec;
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
    public FormalParamDec getFormalParamDec() {
        return formalParamDec;
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
    public void setFormalParamDec(FormalParamDec formalParamDec) {
        this.formalParamDec = formalParamDec;
    }
    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }
    public void setStatements(StatementList statements) {
        this.statements = statements;
    }

    @Override
    void genJava(PW pw) {
        pw.printIdent(returnType.getJavaName() + " " + id + "(");

        // Parametros
        // TODO

        pw.println(") {");

        // Statements
        // TODO

        pw.printlnIdent("}");
    }

    @Override
    void genC(PW pw) {
        // TODO Auto-generated method stub
    }

    private String id;
    private FormalParamDec formalParamDec;
    private Type returnType;
    private StatementList statements;
}
