/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

import java.util.ArrayList;

/**
 * MemberList ::= { [ Qualifier ] Member }
 */
public class MemberList {

    public MemberList() {
        this.memberList = new ArrayList<>();
    }

    /**
     * Getters
     */
    public ArrayList<MemberListPair> getMemberList() {
        return memberList;
    }

    /**
     * Adiciona um par qualifier/member na lista de members
     * @param qualifier
     * @param member
     */
    public void add(Qualifier qualifier, Member member) {
        memberList.add(new MemberListPair(qualifier, member));
    }

    private ArrayList<MemberListPair> memberList;
}
