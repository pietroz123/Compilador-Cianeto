/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

import java.util.ArrayList;
import lexer.Token;

/**
 * Qualifier ::= "private" | "public" | "override" | "override" "public" |
 *               "final" | "final" "public" | "final" "override" | "final" "override" "public" |
 *               "shared" "private" | "shared" "public"
 */
public class Qualifier {

    public Qualifier(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    /**
     * Getters
     */
    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public void genJava(PW pw) {
        /**
         * Em Java existem 2 possibilidades:
         *      @Override
         *      metodo
         * OU
         *      metodo
         */
        tokens.remove(Token.OVERRIDE); // não é necessário printar override

        for (Token tk : tokens) {
            pw.printIdent(tk.toString() + " ");
        }
    }

    private ArrayList<Token> tokens = new ArrayList<Token>();
}
