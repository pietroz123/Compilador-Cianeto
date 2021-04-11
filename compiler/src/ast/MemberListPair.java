/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

/**
 * Classe de utilidade para guardar os pares de Qualifier/Member
 */
public class MemberListPair {
    public MemberListPair(Qualifier qualifier, Member member) {
        this.qualifier = qualifier;
        this.member = member;
    }

    /**
     * Getters
     */
    public Qualifier getQualifier() {
        return qualifier;
    }
    public Member getMember() {
        return member;
    }

    /**
     * Setters
     */
    public void setQualifier(Qualifier qualifier) {
        this.qualifier = qualifier;
    }
    public void setMember(Member member) {
        this.member = member;
    }

    private Qualifier qualifier;
    private Member member;
}