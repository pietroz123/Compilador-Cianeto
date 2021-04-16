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
        pw.print(returnType.getJavaName() + " " + id + "(");

        // Parametros
        if (formalParamDec != null) {
            formalParamDec.genJava(pw);
        }

        pw.println(") {");

        // Statements
        if (statements != null) {
            pw.add();
            statements.genJava(pw);
            pw.sub();
        }

        pw.printlnIdent("}");
    }

    @Override
    void genC(PW pw) {
        // TODO Auto-generated method stub
    }

    public Boolean isOverride(Qualifier qualifier, MethodDec methodDec, TypeCianetoClass superclass) {
        MethodDec superclassMethod = superclass.searchPublicMethod(methodDec.getId());
        if (superclassMethod == null) {
            return false;
        }

        if (superclassMethod.getFormalParamDec() == null && methodDec.getFormalParamDec() == null) {
            return true;
        }
        else {
            // Verifica se tem a mesma quantidade de parâmetros
            Boolean hasSameParameters = true;
            if (superclassMethod.getFormalParamDec() != null && methodDec.getFormalParamDec() != null && superclassMethod.getFormalParamDec().getParamList().size() != methodDec.getFormalParamDec().getParamList().size()) {
                Integer size = superclassMethod.getFormalParamDec().getParamList().size();
                Integer i = 0;

                ParamDec p1 = methodDec.getFormalParamDec().getParamList().get(i);
                ParamDec p2 = superclassMethod.getFormalParamDec().getParamList().get(i);

                while (i != size) {
                    if ( !(p1.getVar().getId() == p2.getVar().getId() && p1.getVar().getType() == p2.getVar().getType()) ) {
                        hasSameParameters = false;
                        break;
                    }
                    i++;
                }
            }

            if (hasSameParameters == false) {
                return false;
            }
        }

        return true;
    }

    private String id;
    private FormalParamDec formalParamDec;
    private Type returnType;
    private StatementList statements;
}
