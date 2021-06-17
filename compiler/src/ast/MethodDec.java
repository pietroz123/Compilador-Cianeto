/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

import java.util.Iterator;

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

    public MethodDec(TypeCianetoClass currentClass) {
        this.currentClass = currentClass;
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
    public TypeCianetoClass getCurrentClass() {
        return currentClass;
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
        // Tipo de retorno
        if (this.returnType instanceof TypeCianetoClass) {
            pw.print("_class_" + this.getReturnType().getName() + "* ");
        } else {
            pw.print(this.getReturnType().getCname() + " ");
        }

        // Nome do método
        pw.print("_" + this.currentClass.getName() + "_" + this.getId() + " (");

        // Parâmetros da função
        pw.print("_class_" + this.currentClass.getName() + " *self"); // o primeiro parâmetro sempre é um ponteiro para self
        // Outros parâmetros
        if (formalParamDec != null) {
            pw.print(", ");
            formalParamDec.genC(pw);
        }

        pw.println(") {");

        // Corpo do método
        if (statements != null) {
            pw.add();
            this.statements.genC(pw);
            pw.sub();
        }

        // Fechamento
        pw.println("}");
    }

    /**
     * Imprime a assinatura de uma função
     * ex:
     *  - Cianeto:  func m3 : Int n
     *  - C:        (void (*)(_class_C *,int))
     * @param pw
     */
    void printCSignature(PW pw) {
        pw.print("(");
        pw.print(this.getReturnType().getCname() + " (*)"); // cast retorno

        // cast parâmetros
        pw.print("(");
        pw.print(this.getCurrentClass().getCname()+" *");

        if (this.getFormalParamDec() != null) {
            pw.print(", ");

            Iterator<ParamDec> it = this.getFormalParamDec().getParamList().iterator();
            while (it.hasNext()) {
                ParamDec paramDec = it.next();
                pw.print(paramDec.getVar().getType().getCname());
                pw.print(paramDec.getVar().getType() instanceof TypeCianetoClass ? "*" : "");

                if (it.hasNext()) {
                    pw.print(", ");
                }
            }
        }

        pw.print(")"); // fecha cast parâmetros
        pw.print(")"); // fecha assinatura
    }

    /**
     * Verifica se um método é uma sobrecarga (override) de algum outro em uma superclasse
     *
     * Um método tem uma sobrecarga em uma superclasse se:
     *  - existe um método
     *      > com mesmo nome
     *      > com mesma quantidade de parâmetros
     *      > com parâmetros com tipos na mesma ordem:  m1(Int a, String b) -> m1(Int c, String d)
     *      > com mesmo tipo de retorno
     *
     * @param superclass
     * @return Boolean : true-sim, false-não
     */
    public Boolean isOverride(TypeCianetoClass superclass) {
        MethodDec superclassMethod = superclass.searchPublicMethod(this.getId());
        if (superclassMethod == null) {
            return false;
        }

        Boolean hasSameSignature = this.hasSameSignature(superclassMethod);

        if (!hasSameSignature) {
            return false;
        }

        return true;
    }

    /**
     * Verifica se o método tem a mesma assinatura (signature)
     * @param other : outro método para comparação
     * @return Boolean : true-sim, false-não
     */
    public Boolean hasSameSignature(MethodDec other) {
        /**
         * Verifica se tem os mesmos parâmetros
         */
        Boolean hasSameParameters = true;

        // Se os métodos têm parâmetros, precisamos verificar se estes tem mesma quantidade de parâmetros
        // e se tem parâmetros com tipos na mesma ordem
        if ( !(this.getFormalParamDec() == null && other.getFormalParamDec() == null) ) {

            // Verifica mesma quantidade
            if (this.getFormalParamDec().getParamList().size() != other.getFormalParamDec().getParamList().size()) {
                hasSameParameters = false;
            }
            else {
                // Verifica se os tipos estão na mesma ordem nos dois métodos
                Integer size = this.getFormalParamDec().getParamList().size();
                Integer i = 0;

                while (i < size) {
                    ParamDec p1 = this.getFormalParamDec().getParamList().get(i);
                    ParamDec p2 = other.getFormalParamDec().getParamList().get(i);

                    if ( !(p1.getVar().getType() == p2.getVar().getType()) ) {
                        hasSameParameters = false;
                        break;
                    }

                    i++;
                }
            }
        }

        // Verifica se tem mesmo tipo de retorno
        Boolean hasSameReturnType = this.getReturnType() == other.getReturnType();

        if (!hasSameParameters || !hasSameReturnType) {
            return false;
        }

        return true;
    }

    /**
     * Método de utilidade para retornar o índice do método
     * na tabela de métodos em C. O índice será a posição em ordem dos métodos - 1
     * @return
     */
    public Integer getCTableIndex() {
        Integer i = 0;
        for (MethodDec method : currentClass.getPublicMethodList()) {
            if (method.getId().equals(this.id)) {
                return i;
            }
            i++;
        }
        return null;
    }

    private TypeCianetoClass currentClass;
    private String id;
    private FormalParamDec formalParamDec;
    private Type returnType;
    private StatementList statements;
}
