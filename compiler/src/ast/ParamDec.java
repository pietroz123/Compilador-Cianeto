/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

/**
 * ParamDec ::= Type Id
 */
public class ParamDec {

    public ParamDec(Variable var) {
        this.var = var;
    }

    private Variable var;
}
