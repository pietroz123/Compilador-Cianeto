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

    private ArrayList<Token> tokens = new ArrayList<Token>();
}
