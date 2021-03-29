/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package comp;

import java.io.PrintWriter;
import java.util.ArrayList;  // may use Vector too
import ast.*;
import lexer.*;

public class Compiler {

	public Compiler() { }

	// compile must receive an input with an character less than
	// p_input.lenght
	public Program compile(char[] input, PrintWriter outError) {

		ArrayList<CompilationError> compilationErrorList = new ArrayList<>();
		errorSignaler = new ErrorSignaler(outError, compilationErrorList);
		symbolTable = new SymbolTable();
		lexer = new Lexer(input, errorSignaler);
		errorSignaler.setLexer(lexer);

		Program program = null;
		next();
		program = program(compilationErrorList);
		return program;
	}

	/**
	 * Program ::= { Annot } ClassDec { { Annot } ClassDec }
	 */
	private Program program(ArrayList<CompilationError> compilationErrorList) {
		ArrayList<MetaobjectAnnotation> metaobjectCallList = new ArrayList<>();
		ArrayList<TypeCianetoClass> cianetoClassList = new ArrayList<>();
		Program program = new Program(cianetoClassList, metaobjectCallList, compilationErrorList);
		boolean thereWasAnError = false;

		while ( lexer.token == Token.CLASS ||
				(lexer.token == Token.ID && lexer.getStringValue().equals("open") ) ||
				lexer.token == Token.ANNOT ) {
			try {
				// { Annot }
				while ( lexer.token == Token.ANNOT ) {
					metaobjectAnnotation(metaobjectCallList);
				}

				// ClassDec
				cianetoClassList.add(classDec());
			}
			catch( CompilerError e) {
				// if there was an exception, there is a compilation error
				thereWasAnError = true;

				return program;
			}
			catch ( Exception e ) {
				e.printStackTrace();

				thereWasAnError = true;
				error("Exception '" + e.getClass().getName() + "' was thrown and not caught. " + "Its message is '" + e.getMessage() + "'");

				return program; // add this line
			}

		}

		// Verificar se existe uma classe Program
		// TODO

		if ( !thereWasAnError && lexer.token != Token.EOF ) {
			error("End of file expected");
		}

		return program;
	}

	/**  parses a metaobject annotation as <code>{@literal @}cep(...)</code> in <br>
     * <code>
     * {@literal @}cep(5, "'class' expected") <br>
     * class Program <br>
     *     func run { } <br>
     * end <br>
     * </code>
     *

	 */
	@SuppressWarnings("incomplete-switch")
	private void metaobjectAnnotation(ArrayList<MetaobjectAnnotation> metaobjectAnnotationList) {
		String name = lexer.getMetaobjectName();
		int lineNumber = lexer.getLineNumber();
		next();
		ArrayList<Object> metaobjectParamList = new ArrayList<>();
		boolean getNextToken = false;
		if ( lexer.token == Token.LEFTPAR ) {
			// metaobject call with parameters
			next();
			while ( lexer.token == Token.LITERALINT || lexer.token == Token.LITERALSTRING ||
					lexer.token == Token.ID ) {
				switch ( lexer.token ) {
				case LITERALINT:
					metaobjectParamList.add(lexer.getNumberValue());
					break;
				case LITERALSTRING:
					metaobjectParamList.add(lexer.getLiteralStringValue());
					break;
				case ID:
					metaobjectParamList.add(lexer.getStringValue());
				}
				next();
				if ( lexer.token == Token.COMMA )
					next();
				else
					break;
			}
			if ( lexer.token != Token.RIGHTPAR )
				error("')' expected after annotation with parameters");
			else {
				getNextToken = true;
			}
		}
		switch ( name ) {
		case "nce":
			if ( metaobjectParamList.size() != 0 )
				error("Annotation 'nce' does not take parameters");
			break;
		case "cep":
			int sizeParamList = metaobjectParamList.size();
			if ( sizeParamList < 2 || sizeParamList > 4 )
				error("Annotation 'cep' takes two, three, or four parameters");

			if ( !( metaobjectParamList.get(0) instanceof Integer)  ) {
				error("The first parameter of annotation 'cep' should be an integer number");
			}
			else {
				int ln = (Integer ) metaobjectParamList.get(0);
				metaobjectParamList.set(0, ln + lineNumber);
			}
			if ( !( metaobjectParamList.get(1) instanceof String)  )
				error("The second parameter of annotation 'cep' should be a literal string");
			if ( sizeParamList >= 3 && !( metaobjectParamList.get(2) instanceof String)  )
				error("The third parameter of annotation 'cep' should be a literal string");
			if ( sizeParamList >= 4 && !( metaobjectParamList.get(3) instanceof String)  )
				error("The fourth parameter of annotation 'cep' should be a literal string");

			break;
		case "annot":
			if ( metaobjectParamList.size() < 2  ) {
				error("Annotation 'annot' takes at least two parameters");
			}
			for ( Object p : metaobjectParamList ) {
				if ( !(p instanceof String) ) {
					error("Annotation 'annot' takes only String parameters");
				}
			}
			if ( ! ((String ) metaobjectParamList.get(0)).equalsIgnoreCase("check") )  {
				error("Annotation 'annot' should have \"check\" as its first parameter");
			}
			break;
		default:
			error("Annotation '" + name + "' is illegal");
		}
		metaobjectAnnotationList.add(new MetaobjectAnnotation(name, metaobjectParamList));
		if ( getNextToken ) next();
	}

	/**
	 * ClassDec ::= [ "open" ] "class" Id [ "extends" Id ] MemberList "end"
	 */
	private TypeCianetoClass classDec() {
		this.currentClass = null;

		if ( lexer.token == Token.ID && lexer.getStringValue().equals("open") ) {
			// open class
		}

		// Verifica token "class"
		if ( lexer.token != Token.CLASS ) {
			error("'class' expected");
		}

		next();

		// Verifica "id" da classe
		if ( lexer.token != Token.ID ) {
			error("Identifier expected");
		}

		String className = lexer.getStringValue();
		currentClass = new TypeCianetoClass(className);

		// Coloca na tabela global
		symbolTable.putInGlobal(className, currentClass);

		next();

		if ( lexer.token == Token.EXTENDS ) {
			next();

			if ( lexer.token != Token.ID )
				error("Identifier expected");

			String superclassName = lexer.getStringValue();
			TypeCianetoClass superclass = new TypeCianetoClass(superclassName);

			// Verificar se a superclasse existe
			if ( symbolTable.getInGlobal(superclassName) == null ) {
				error("Superclass does not exist");
			}

			currentClass.setSuperclass(superclass);

			next();
		}

		MemberList memberList = memberList();
		currentClass.setMemberList(memberList);

		if ( lexer.token != Token.END)
			error("'end' expected");

		next();

		return currentClass;
	}

	/**
	 * MemberList ::= { [ Qualifier ] Member }
	 * Member ::= FieldDec | MethodDec
	 */
	private MemberList memberList() {
		MemberList memberList = new MemberList();

		while ( true ) {
			Qualifier q = qualifier();
			Member member = null;

			if ( lexer.token == Token.VAR ) {
				member = fieldDec();
			}
			else if ( lexer.token == Token.FUNC ) {
				member = methodDec();
			}
			else {
				break;
			}

			memberList.add(q, member);
		}

		return memberList;
	}

	private void error(String msg) {
		try {
			this.errorSignaler.showError(msg);
		}
		catch( CompilerError e) {
			throw e;
		}
		catch( Exception e) {
			System.out.println("Error when signalling an error. The exception "
					+ "thrown was '" + e.getClass().getName() + "'");
			System.exit(1);
		}


	}


	private void next() {
		lexer.nextToken();
	}

	private void check(Token shouldBe, String msg) {
		if ( lexer.token != shouldBe ) {
			error(msg);
		}
	}

	/**
	 * MethodDec ::= "func" IdColon FormalParamDec [ "->" Type ] "{" StatementList "}"
	 * 				 | "func" Id [ "->" Type ] "{" StatementList "}"
	 */
	private MethodDec methodDec() {
		this.currentMethod = new MethodDec();

		String id = null;
		FormalParamDec formalParamDec = null;
		Type returnType = null;
		StatementList statementList = null;

		next();

		// Verifica "Id" ou "IdColon"
		if (lexer.token != Token.ID || lexer.token != Token.IDCOLON) {
			error("An identifier or identifer: was expected after 'func'");
		}

		id = lexer.getStringValue();
		this.currentMethod.setId(id);

		// Verifica se o método já não foi declarado
		// TODO

		if ( lexer.token == Token.ID ) {
			// unary method
			next();
		}
		else if ( lexer.token == Token.IDCOLON ) {
			// keyword method. It has parameters
			next();
			formalParamDec = formalParamDec();
			this.currentMethod.setFormalParamDec(formalParamDec);
			next();
		}

		// Verifica [ "->" Type ]
		if ( lexer.token == Token.MINUS_GT ) {
			// method declared a return type
			next();
			returnType = type();
			this.currentMethod.setReturnType(returnType);
		}

		// Verifica "{"
		if ( lexer.token != Token.LEFTCURBRACKET ) {
			error("'{' expected");
		}

		next();

		statementList = statementList();
		this.currentMethod.setStatements(statementList);

		if ( lexer.token != Token.RIGHTCURBRACKET ) {
			error("'{' expected");
		}
		next();

		return new MethodDec(id, formalParamDec, returnType, statementList);
	}

	/**
	 * FormalParamDec ::= ParamDec { "," ParamDec }
	 */
	private FormalParamDec formalParamDec() {
		FormalParamDec formalParamDec = new FormalParamDec();

		formalParamDec.addParam(paramDec());

		// { "," ParamDec }
        while (lexer.token == Token.COMMA) {
            next();
            formalParamDec.addParam(paramDec());
        }

        return formalParamDec;
	}

	/**
	 * ParamDec ::= Type Id
	 */
	private ParamDec paramDec() {
		// Type
        Type type = type();
        // Id
        String id = lexer.getStringValue();
        lexer.nextToken();

        // Adiciona o parâmetro da função na tabela local de variáveis
        Variable var = new Variable(id, type);
        symbolTable.putInLocal(id, var);

        return new ParamDec(var);
	}

	private StatementList statementList() {
		  // only '}' is necessary in this test
		while ( lexer.token != Token.RIGHTCURBRACKET && lexer.token != Token.END ) {
			statement();
		}

		return null;
	}

	private Statement statement() {
		boolean checkSemiColon = true;

		Statement s = null;

		switch ( lexer.token ) {
			case IF:
				ifStat();
				checkSemiColon = false;
				break;
			case WHILE:
				s = whileStat();
				checkSemiColon = false;
				break;
			case RETURN:
				returnStat();
				break;
			case BREAK:
				breakStat();
				break;
			case SEMICOLON:
				next();
				break;
			case REPEAT:
				repeatStat();
				break;
			case VAR:
				localDec();
				break;
			case ASSERT:
				assertStat();
				break;
			default:
				if ( lexer.token == Token.ID && lexer.getStringValue().equals("Out") ) {
					writeStat();
				}
				else {
					expr();
				}
		}

		if ( checkSemiColon ) {
			check(Token.SEMICOLON, "';' expected");
		}

		return s;
	}

	private void localDec() {
		next();
		type();
		check(Token.ID, "A variable name was expected");
		while ( lexer.token == Token.ID ) {
			next();
			if ( lexer.token == Token.COMMA ) {
				next();
			}
			else {
				break;
			}
		}
		if ( lexer.token == Token.ASSIGN ) {
			next();
			// check if there is just one variable
			expr();
		}

	}

	private void repeatStat() {
		next();
		while ( lexer.token != Token.UNTIL && lexer.token != Token.RIGHTCURBRACKET && lexer.token != Token.END ) {
			statement();
		}
		check(Token.UNTIL, "missing keyword 'until'");
	}

	private void breakStat() {
		next();

	}

	/**
	 * ReturnStat ::= "return" Expression
	 */
	private void returnStat() {
		next();
		Expr e = expr();

		// Verifica se o método deve retornar algum valor
		if ( this.currentMethod.getReturnType() == Type.nullType ) {
			error("This method cannot return any value");
		}

		// Verifica se o método tem retorno correto
		if ( !e.getType().isCompatible(this.currentMethod.getReturnType()) ) {
			error("This expression is not compatible with the method return type");
		}
	}

	/**
	 * WhileStat ::= "while" Expression "{" StatementList "}"
	 */
	private WhileStat whileStat() {
		next();

		// Conferência de tipo da expressão
		Expr e = expr();
		if ( e.getType() != Type.booleanType ) {
			error("Boolean expression expected");
		}

		check(Token.LEFTCURBRACKET, "missing '{' after the 'while' expression");
		next();


		Statement s = null;
		while ( lexer.token != Token.RIGHTCURBRACKET && lexer.token != Token.END ) {
			s = statement();
		}

		check(Token.RIGHTCURBRACKET, "missing '}' after 'while' body");

		return new WhileStat(e, s);
	}

	/**
	 * IfStat ::= if" Expression "{" Statement "}" [ "else" "{" Statement "}" ]
	 */
	private void ifStat() {
		next();

		// Conferência de tipo da expressão
		Expr e = expr();
		if ( e.getType() != Type.booleanType ) {
			error("Boolean expression expected");
		}

		check(Token.LEFTCURBRACKET, "'{' expected after the 'if' expression");
		next();

		while ( lexer.token != Token.RIGHTCURBRACKET && lexer.token != Token.END && lexer.token != Token.ELSE ) {
			statement();
		}

		check(Token.RIGHTCURBRACKET, "'}' was expected");

		if ( lexer.token == Token.ELSE ) {
			next();
			check(Token.LEFTCURBRACKET, "'{' expected after 'else'");
			next();
			while ( lexer.token != Token.RIGHTCURBRACKET ) {
				statement();
			}
			check(Token.RIGHTCURBRACKET, "'}' was expected");
		}
	}

	/**

	 */
	private void writeStat() {
		next();
		check(Token.DOT, "a '.' was expected after 'Out'");
		next();
		check(Token.IDCOLON, "'print:' or 'println:' was expected after 'Out.'");
		String printName = lexer.getStringValue();
		expr();
	}

	/**
	 * Expression ::= SimpleExpression [ Relation SimpleExpression ]
	 */
	private Expr expr() {
		return null;
	}

	/**
	 * FieldDec ::= "var" Type IdList [ ";" ]
	 */
	private FieldDec fieldDec() {
		next();

		Type type = type();
		IdList idList = new IdList();

		if ( lexer.token != Token.ID ) {
			this.error("A field name was expected");
		}
		else {
			while ( lexer.token == Token.ID  ) {
				String id = lexer.getStringValue();

				// Verificar se a variável já não foi declarada
				// TODO

				idList.addId(id);
				next();

				if ( lexer.token == Token.COMMA ) {
					next();
				}
				else {
					break;
				}
			}
		}

		return new FieldDec(type, idList);
	}

	/**
	 * Type ::= BasicType | Id
	 * BasicType ::= "Int" | "Boolean" | "String"
	 */
	private Type type() {
		Type type = null;

		switch (lexer.token) {
			case INT:
				type = Type.intType;
				break;
			case BOOLEAN:
				type = Type.booleanType;
				break;
			case STRING:
				type = Type.stringType;
				break;
			case ID:
				type = Type.nullType;
				type.setName(lexer.getStringValue());
			default:
				error("Type not supported");
				break;
		}

		return type;
	}

	/**
	 * Qualifier ::= "private" | "public" | "override" | "override" "public" |
	 *               "final" | "final" "public" | "final" "override" | "final" "override" "public" |
	 *               "shared" "private" | "shared" "public"
	 */
	private Qualifier qualifier() {
		ArrayList<Token> tokens = new ArrayList<>();

		if ( lexer.token == Token.PRIVATE ) {
			tokens.add(Token.PRIVATE);
			next();
		}
		else if ( lexer.token == Token.PUBLIC ) {
			tokens.add(Token.PUBLIC);
			next();
		}
		else if ( lexer.token == Token.OVERRIDE ) {
			tokens.add(Token.OVERRIDE);
			next();
			if ( lexer.token == Token.PUBLIC ) {
				tokens.add(Token.PUBLIC);
				next();
			}
		}
		else if ( lexer.token == Token.FINAL ) {
			tokens.add(Token.FINAL);
			next();
			if ( lexer.token == Token.PUBLIC ) {
				tokens.add(Token.PUBLIC);
				next();
			}
			else if ( lexer.token == Token.OVERRIDE ) {
				tokens.add(Token.OVERRIDE);
				next();
				if ( lexer.token == Token.PUBLIC ) {
					tokens.add(Token.PUBLIC);
					next();
				}
			}
		}

		return new Qualifier(tokens);
	}
	/**
	 * change this method to 'private'.
	 * uncomment it
	 * implement the methods it calls
	 */
	public Statement assertStat() {

		next();
		int lineNumber = lexer.getLineNumber();
		expr();
		if ( lexer.token != Token.COMMA ) {
			this.error("',' expected after the expression of the 'assert' statement");
		}
		next();
		if ( lexer.token != Token.LITERALSTRING ) {
			this.error("A literal string expected after the ',' of the 'assert' statement");
		}
		String message = lexer.getLiteralStringValue();
		next();
		if ( lexer.token == Token.SEMICOLON )
			next();

		return null;
	}




	private LiteralIntExpr literalInt() {

		LiteralIntExpr e = null;

		// the number value is stored in lexer.getToken().value as an object of
		// Integer.
		// Method intValue returns that value as an value of type int.
		int value = lexer.getNumberValue();
		next();
		return new LiteralIntExpr(value);
	}

	private static boolean startExpr(Token token) {

		return token == Token.FALSE || token == Token.TRUE
				|| token == Token.NOT || token == Token.SELF
				|| token == Token.LITERALINT || token == Token.SUPER
				|| token == Token.LEFTPAR || token == Token.NIL
				|| token == Token.ID || token == Token.LITERALSTRING;

	}

	private TypeCianetoClass	currentClass; 		// Classe corrente
	private MethodDec			currentMethod; 		// Método corrente
	private SymbolTable			symbolTable;
	private Lexer				lexer;
	private ErrorSignaler		errorSignaler;

}
