/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

/**
 * Member ::= FieldDec | MethodDec
 */
abstract public class Member {

    abstract void genJava(PW pw);
    abstract void genC(PW pw);

}
