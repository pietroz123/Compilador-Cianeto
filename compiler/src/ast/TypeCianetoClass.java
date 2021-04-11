/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

import java.util.ArrayList;

import lexer.Token;

/**
 * ClassDec ::= [ "open" ] "class" Id [ "extends" Id ] MemberList "end"
 */
public class TypeCianetoClass extends Type {

    // O construtor de TypeCianetoClass deve ter um unico parametro, o nome da
    // classe
    public TypeCianetoClass(String name) {
        super(name);
        this.publicMethodList = new ArrayList<>();
        this.privateMethodList = new ArrayList<>();
        this.fieldList = new ArrayList<>();
    }

    @Override
    public String getCname() {
        return getName();
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
     * TODO : Verificar se o método é público ou privado
     *
     * @param method
     */
    public void addMethod(MethodDec method) {
        publicMethodList.add(method);
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
     * @return
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
     * Verifica se existe um campo de nome id na lista de campos
     * @param id
     * @return
     */
    public Variable searchInstanceVariable(String id) {
        for (FieldDec field : this.fieldList) {
            for (String fieldId : field.getIdList().getIdList()) {
                if (fieldId.equals(id)) {
                    return new Variable(id, field.getType());
                }
            }
        }

        return null;
    }

    private Boolean isOpen = false;
    private String name;
    private TypeCianetoClass superclass;
    private MemberList memberList;
    private ArrayList<MethodDec> publicMethodList, privateMethodList;
    private ArrayList<FieldDec> fieldList;
}
