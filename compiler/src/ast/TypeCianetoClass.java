/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import lexer.Token;

/**
 * ClassDec ::= [ "open" ] "class" Id [ "extends" Id ] MemberList "end"
 */
public class TypeCianetoClass extends Type {

    // O construtor de TypeCianetoClass deve ter um unico parametro, o nome da
    // classe
    public TypeCianetoClass(String name) {
        super(name);
        this.name = name;
        this.publicMethodList = new ArrayList<>();
        this.privateMethodList = new ArrayList<>();
        this.virtualTable = new ArrayList<>();
        this.fieldList = new ArrayList<>();
    }

    @Override
    public String getCname() {
        return "_class_" + this.name;
    }
    @Override
    public String getJavaName() {
        return getName();
    }
    @Override
    public String getCspecifier() {
        return "";
    }

    /**
     * Getters
     */
    public TypeCianetoClass getSuperclass() {
        return superclass;
    }
    public ArrayList<MethodDec> getPublicMethodList() {
        return publicMethodList;
    }
    public ArrayList<MethodDec> getPrivateMethodList() {
        return privateMethodList;
    }
    public ArrayList<MethodDec> getVirtualTable() {
        return virtualTable;
    }

    /**
     * Setters
     */
    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }
    public void setSuperclass(TypeCianetoClass superclass) {
        this.superclass = superclass;
    }
    public void setMemberList(MemberList memberList) {
        // // Coloca os membros nas váriaveis de instância também
        // for (MemberListPair pair : memberList.getMemberList()) {
        //     ArrayList<Token> qualifierTokens = pair.getQualifier().getTokens();

        //     // MethodDec
        //     if (pair.getMember() instanceof MethodDec) {
        //         if (qualifierTokens.contains(Token.PRIVATE)) {
        //             privateMethodList.add((MethodDec) pair.getMember());
        //         } else {
        //             publicMethodList.add((MethodDec) pair.getMember());
        //         }
        //     }
        //     // FieldDec
        //     if (pair.getMember() instanceof FieldDec) {
        //         fieldList.add((FieldDec) pair.getMember());
        //     }
        // }

        this.memberList = memberList;
    }
    public void setVirtualTable(ArrayList<MethodDec> virtualTable) {
        this.virtualTable = virtualTable;
    }


    /**
     * Verifica se é subclasse
     * @param other
     * @return
     */
    public boolean isSubclassOf(Type other) {
        TypeCianetoClass current = this;

        while (current != other) {
            current = current.getSuperclass();
            if (current == null) {
                return false;
            }
        }

        return true;
    }

    /**
     * Insere um método
     *
     * @param method
     * @param qualifier
     */
    public void addMethod(MethodDec method, Qualifier qualifier) {
        if (qualifier.getTokens().contains(Token.PRIVATE)) {
            privateMethodList.add(method);
        }
        else {
            publicMethodList.add(method);
        }

        if (!qualifier.getTokens().contains(Token.PRIVATE) && qualifier.getTokens().contains(Token.OVERRIDE)) {
            Integer i = 0;

            for  (MethodDec theMethod : virtualTable) {
                if (method.getId().equals(theMethod.getId())) {
                    virtualTable.set(i, method);
                    return; // já encontrou
                }
                i++;
            }
        }
        else {
            virtualTable.add(method);
        }
    }

    /**
     * Insere uma declaração de campo(s)
     * @param fieldDec
     */
    public void addFieldDec(FieldDec fieldDec) {
        fieldList.add(fieldDec);
    }

    /**
     * Verifica se um método existe na lista de métodos públicos
     * @param methodId : Identificador do método
     * @return MethodDec
     */
    public MethodDec searchPublicMethod(String methodId) {
        for (MethodDec method : this.publicMethodList) {
            if (method.getId().equals(methodId)) {
                return method;
            }
        }

        /**
         * Verifica os métodos das superclasses
         */
        if (superclass != null) {
            TypeCianetoClass current = this.getSuperclass();

            while (current != null) {
                for (MethodDec method : current.getPublicMethodList()) {
                    if (method.getId().equals(methodId)) {
                        return method;
                    }
                }
                current = current.getSuperclass();
            }
        }

        return null;
    }
    /**
     * Verifica se um método existe na lista de métodos públicos
     * @param methodId : Identificador do método
     * @return MethodDec
     */
    public MethodDec searchPrivateMethod(String methodId) {
        for (MethodDec method : this.privateMethodList) {
            if (method.getId().equals(methodId)) {
                return method;
            }
        }

        /**
         * Verifica os métodos das superclasses
         */
        if (superclass != null) {
            TypeCianetoClass current = this.getSuperclass();

            while (current != null) {
                for (MethodDec method : current.getPrivateMethodList()) {
                    if (method.getId().equals(methodId)) {
                        return method;
                    }
                }
                current = current.getSuperclass();
            }
        }

        return null;
    }

    /**
     * Busca um método em ambas as listas de métodos públicos e privados
     * @param methodId
     * @return MethodDec
     */
    public MethodDec searchAllMethods(String methodId) {
        MethodDec m = searchPublicMethod(methodId);

        if (m == null) {
            m = searchPrivateMethod(methodId);
        }

        return m;
    }

    /**
     *
     * @param methodId
     * @return
     */
    public Pairs.MethodIndex searchMethodInVirtualTable(String methodId) {
        Integer index = 0;

        for (MethodDec method : this.virtualTable) {
            if (method.getId().equals(methodId)) {
                return new Pairs.MethodIndex(method, index);
            }
            index++;
        }

        return null;
    }

    /**
     * Verifica se existe um campo de nome id na lista de campos
     * @param id
     * @return
     */
    public Variable searchInstanceVariable(String id) {
        for (FieldDec field : this.fieldList) {
            for (String fieldId : field.getIdList().getIdList()) {
                if (fieldId.equals(id)) {
                    return new Variable(fieldId, field.getType());
                }
            }
        }

        return null;
    }

    // =====================
    // Geração de Código
    // =====================

    /**
     * GERAÇÃO DE CÓDIGO EM JAVA
     * @param pw
     */
    public void genJava(PW pw) {
        pw.printIdent("");
        if (isOpen) {
            pw.print("public ");
        }

        // static para criação de objetos das classes
        pw.print("static class " + name);

        if (superclass != null) {
            pw.print(" extends " + superclass.getName());
        }

		pw.println(" {");
		pw.add();
		pw.println();

        // Corpo da classe
        for (MemberListPair member : memberList.getMemberList()) {
            member.getQualifier().genJava(pw);
            member.getMember().genJava(pw);
        }

        pw.println();
		pw.sub();
		pw.printlnIdent("}");
		pw.println();
    }

    /**
     * GERAÇÃO DE CÓDIGO EM C
     * @param pw
     */
    public void genC(PW pw) {
        /**
         * struct da classe
         */
        pw.println("typedef struct _St_" + this.name + " _class_" + this.name + ";"); // forward declaration
        pw.println("struct _St_" + this.name + " {");
        pw.add();
        pw.printlnIdent("Func *vt; /* ponteiro para um vetor de métodos da classe */");

        // Adiciona as variáveis das superclasses também
        TypeCianetoClass current = this;
        Stack<TypeCianetoClass> cs = new Stack<>();
        while ( current != null ) { // empilha todos
            cs.push(current);
            current = current.getSuperclass();
        }
        while ( !cs.empty() ) {
            current = cs.pop();
            for (FieldDec f : current.fieldList ) {
                f.genC(pw);
            }
        }
        // // Variáveis de instância
        // for (FieldDec fieldDec : this.fieldList) {
        //     fieldDec.genC(pw);
        // }

        pw.sub();
        pw.println("};");
        pw.println();

        // Método de construção da "classe"
        pw.println("_class_" + this.name + " *new_" + this.name + "();");
        pw.println();

        /**
         * Declarações dos métodos
         */
        for (MethodDec methodDec : this.privateMethodList) {
            methodDec.genC(pw);
        }
        for (MethodDec methodDec : this.publicMethodList) {
            methodDec.genC(pw);
        }

        pw.println();

        /**
         * Tabela de métodos da classe
         */
        pw.println("// tabela de métodos da classe " + this.name + " -- virtual table");
        pw.println("Func VTclass_" + this.name + "[] = {");
        pw.add();

        // System.out.print("virtual table de "+this.name+": ");
        // for (Iterator<MethodDec> it = this.virtualTable.iterator(); it.hasNext(); ) {
        //     MethodDec m = it.next();
        //     System.out.print("{"+m.getCurrentClass().getName()+", "+m.getId()+"}");
        // }
        // System.out.println();

        Iterator<MethodDec> it = this.virtualTable.iterator();
        while (it.hasNext()) {
            MethodDec methodDec = it.next();
            pw.printIdent("( Func ) _" + methodDec.getCurrentClass().getName() + "_" + methodDec.getId());

            if (it.hasNext()) {
                pw.print(",");
            }
            pw.println();
        }

        pw.sub();
        pw.println("};");
        pw.println();

        /**
         * Construtor da classe
         */
        pw.println("_class_" + this.name + " *new_" + this.name + "() {");
        pw.add();
        pw.printlnIdent("_class_" + this.name + " *t;");
        pw.println();
        pw.printlnIdent("if ( (t = malloc(sizeof(_class_" + this.name + "))) != NULL )");
        pw.add();
        pw.printlnIdent("t->vt = VTclass_" + this.name + ";");
        pw.sub();
        pw.printlnIdent("return t;");
        pw.sub();
        pw.println("}");
        pw.println();
    }

    private Boolean isOpen = false;
    private String name;
    private TypeCianetoClass superclass;
    private MemberList memberList;
    private ArrayList<MethodDec> publicMethodList, privateMethodList;
    private ArrayList<MethodDec> virtualTable;
    private ArrayList<FieldDec> fieldList;
}
