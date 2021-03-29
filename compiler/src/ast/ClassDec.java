/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

/**
 * ClassDec ::= [ "open" ] "class" Id [ "extends" Id ] MemberList "end"
 */
public class ClassDec extends Type {

    public ClassDec(String name) {
        super(name);
        //TODO Auto-generated constructor stub
    }

    @Override
    public String getCname() {
        // TODO Auto-generated method stub
        return null;
    }

}
