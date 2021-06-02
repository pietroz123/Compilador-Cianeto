/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

/**
 * ReadExpr ::= "In" "." ( "readInt" | "readString" )
 */
public class ReadExpr extends Expression {

    public ReadExpr(String readName) {
        this.readName = readName;
    }

    @Override
    public void genC(PW pw, boolean putParenthesis) {
        // TODO Auto-generated method stub
    }

    @Override
    public Type getType() {
        if (readName.equals("readString")) {
            return Type.stringType;
        }

        return Type.intType;
    }

    @Override
    public void genJava(PW pw) {
        if (readName.equals("readString")) {
            pw.print("inputScanner.next()");
        }
        else if (readName.equals("readInt")) {
            pw.print("inputScanner.nextInt()");
        }
    }

    private String readName;
}
