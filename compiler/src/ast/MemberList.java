package ast;

import java.util.ArrayList;

/**
 * MemberList ::= { [ Qualifier ] Member }
 */
public class MemberList {

    public MemberList() {
        this.memberList = new ArrayList<>();
    }

    public void add(Qualifier q, Member member) {
        memberList.add(new MemberListPair(q, member));
    }

    // Classe de utilidade para guardar os pares de Qualifier/Member
    private class MemberListPair {
        public MemberListPair(Qualifier qualifier, Member member) {
            this.qualifier = qualifier;
            this.member = member;
        }

        private Qualifier qualifier;
        private Member member;
    }

    private ArrayList<MemberListPair> memberList;

}
