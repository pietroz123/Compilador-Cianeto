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

		/**
		 * Verificações da classe Program
		 */
		TypeCianetoClass programClass = (TypeCianetoClass) symbolTable.getInGlobal("Program");

		// Verificar se existe uma classe Program
		if (programClass == null) {
			error("Every program in the Cianeto Language must have a class with name 'Program'");
		}

		// Verifica se existe um método sem parâmetros chamado 'run'
		MethodDec runMethod = programClass.searchPublicMethod("run");
 		if (runMethod == null || runMethod.getFormalParamDec() != null) {
			error("The Program class must have a parameterless method called 'run'");
		}

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
		Boolean isOpenClass = false;

		if ( lexer.token == Token.ID && lexer.getStringValue().equals("open") ) {
			// open class
			isOpenClass = true;
			next();
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
		currentClass.setIsOpen(isOpenClass);

		// Coloca na tabela global
		symbolTable.putInGlobal(className, currentClass);

		next();

		// [ "extends" Id ]
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

		// MemberList
		MemberList memberList = memberList();
		currentClass.setMemberList(memberList);

		// "end"
		if ( lexer.token != Token.END)
			error("'end' expected");

		next();

		return currentClass;
	}

	/**
	 * MemberList ::= { [ Qualifier ] Member }
	 * Qualifier ::= "private" | "public" | "override" | "override" "public" | "final" | "final" "public" | "final" "override" | "final" "override" "public" | "shared" "private" | "shared" "public"
	 * Member ::= FieldDec | MethodDec
	 * FieldDec ::= "var" Type IdList [ ";" ]
	 * MethodDec ::= "func" IdColon FormalParamDec [ "->" Type ] "{" StatementList "}" | "func" Id [ "->" Type ] "{" StatementList "}"
	 */
	private MemberList memberList() {
		MemberList memberList = new MemberList();

		while ( true ) {
			Qualifier qualifier = qualifier();
			Member member = null;

			/**
			 * FieldDec
			 */
			if ( lexer.token == Token.VAR ) {
				member = fieldDec();
			}
			/**
			 * MethodDec
			 */
			else if ( lexer.token == Token.FUNC ) {
				member = methodDec();
			}
			else {
				break;
			}

			memberList.add(qualifier, member);
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
		if (lexer.token != Token.ID && lexer.token != Token.IDCOLON) {
			error("An identifier or identifer: was expected after 'func'");
		}

		id = lexer.getStringValue();
		this.currentMethod.setId(id);

		// Verifica se o método já não foi declarado
		if ( symbolTable.getInLocal(id) != null ) {
			error("Duplicate method " + id + "' in type '" + currentClass.getName() + "'");
		}

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

		// StatementList
		statementList = statementList();
		this.currentMethod.setStatements(statementList);

		// Verifica "}"
		if ( lexer.token != Token.RIGHTCURBRACKET ) {
			error("'{' expected");
		}
		next();

		this.currentClass.addMethod(this.currentMethod);

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
			next();
        }

        return formalParamDec;
	}

	/**
	 * ParamDec ::= Type Id
	 */
	private ParamDec paramDec() {
		// Type
        Type type = type();

		if ( lexer.token != Token.ID ) {
			error("Identifier expected");
		}

        // Id
        String id = lexer.getStringValue();

        // Adiciona o parâmetro da função na tabela local de variáveis
        Variable var = new Variable(id, type);
        symbolTable.putInLocal(id, var);

        return new ParamDec(var);
	}

	/**
	 * StatementList ::= { Statement }
	 */
	private StatementList statementList() {
		StatementList statementList = new StatementList();

		// only '}' is necessary in this test // !?
		while ( lexer.token != Token.RIGHTCURBRACKET && lexer.token != Token.END && lexer.token != Token.ELSE ) {
			statementList.addStatement(statement());
		}

		return statementList;
	}

	/**
	 * Statement ::= AssignExpr ";" | IfStat | WhileStat | ReturnStat ";" | PrintStat ";" | "break" ";" | ";" | RepeatStat ";" | LocalDec ";" | AssertStat ";"
	 *
	 * IfStat ::= "if" Expression "{" StatementList "}" [ "else" "{" StatementList "}" ]
	 * WhileStat ::= "while" Expression "{" StatementList "}"
	 * ReturnStat ::= "return" Expression
	 * RepeatStat ::= "repeat" StatementList "until" Expression
	 * LocalDec ::= "var" Type IdList [ "=" Expression ]
	 * AssertStat ::= "assert" Expression "," StringValue
	 * PrintStat ::= "Out" "." ( "print:" | "println:" ) Expression { "," Expression }
	 * AssignExpr ::= Expression [ "=" Expression ]
	 */
	private Statement statement() {
		boolean checkSemiColon = true;

		Statement s = null;

		switch ( lexer.token ) {
			/**
			 * IfStat
			 */
			case IF:
				s = ifStat();
				checkSemiColon = false;
				break;
			/**
			 * WhileStat
			 */
			case WHILE:
				s = whileStat();
				checkSemiColon = false;
				break;
			/**
			 * ReturnStat
			 */
			case RETURN:
				s = returnStat();
				break;
			/**
			 * "break" ";"
			 */
			case BREAK:
				s = breakStat();
				break;
			case SEMICOLON:
				next();
				break;
			/**
			 * RepeatStat
			 */
			case REPEAT:
				s = repeatStat();
				break;
			/**
			 * LocalDec
			 */
			case VAR:
				s = localDec();
				break;
			/**
			 * AssertStat
			 */
			case ASSERT:
				s = assertStat();
				break;
			default:
				/**
				 * PrintStat
				 */
				if ( lexer.token == Token.ID && lexer.getStringValue().equals("Out") ) {
					s = printStat();
				}
				/**
				 * AssignExpr
				 */
				else {
					s = assignExpr();
				}
		}

		if ( checkSemiColon ) {
			check(Token.SEMICOLON, "';' expected");
		}
		next();

		return s;
	}

	/**
	 * LocalDec ::= "var" Type IdList [ "=" Expression ]
	 */
	private LocalDec localDec() {
		next();

		Type type = type();

		IdList idlList = new IdList();
		Expression expr = null;

		check(Token.ID, "A variable name was expected");

		// IdList
		while ( lexer.token == Token.ID ) {
			String id = lexer.getStringValue();
			idlList.addId(id);

			// Verifica se variável já foi declarada
			if (symbolTable.getInLocal(id) != null) {
				error("Variable " + id + " was already declared");
			} else {
				symbolTable.putInLocal(id, new Variable(id, type));
			}

			next();

			if ( lexer.token == Token.COMMA ) {
				next();
			}
			else {
				break;
			}
		}

		// [ "=" Expression ]
		if ( lexer.token == Token.ASSIGN ) {
			next();
			// check if there is just one variable
			expr = expr();
		}

		return new LocalDec(type, idlList, expr);
	}

	/**
	 * RepeatStat ::= "repeat" StatementList "until" Expression
	 */
	private RepeatStat repeatStat() {
		next();

		StatementList statementList = statementList();
		// ! modifiquei
		// while ( lexer.token != Token.UNTIL && lexer.token != Token.RIGHTCURBRACKET && lexer.token != Token.END ) {
		// 	statement();
		// }

		check(Token.UNTIL, "missing keyword 'until'");
		next();

		Expression expr = expr();

		return new RepeatStat(statementList, expr);
	}

	/**
	 * "break" ";"
	 */
	private BreakStat breakStat() {
		next();
		return new BreakStat();
	}

	/**
	 * ReturnStat ::= "return" Expression
	 */
	private ReturnStat returnStat() {
		next();
		Expression expr = expr();

		// Verifica se o método deve retornar algum valor
		if ( this.currentMethod.getReturnType() == Type.nullType ) {
			error("This method cannot return any value");
		}

		// Verifica se o método tem retorno correto
		if ( !expr.getType().isCompatible(this.currentMethod.getReturnType()) ) {
			error("This expression is not compatible with the method return type");
		}

		return new ReturnStat(expr);
	}

	/**
	 * WhileStat ::= "while" Expression "{" StatementList "}"
	 */
	private WhileStat whileStat() {
		next();

		Expression expr = expr();

		// Conferência de tipo da expressão
		if ( expr.getType() != Type.booleanType ) {
			error("Boolean expression expected");
		}

		check(Token.LEFTCURBRACKET, "missing '{' after the 'while' expression");
		next();

		StatementList statementList = statementList();
		//! modifiquei
		// Statement s = null;
		// while ( lexer.token != Token.RIGHTCURBRACKET && lexer.token != Token.END ) {
		// 	s = statement();
		// }

		check(Token.RIGHTCURBRACKET, "missing '}' after 'while' body");

		return new WhileStat(expr, statementList);
	}

	/**
	 * IfStat ::= "if" Expression "{" StatementList "}" [ "else" "{" StatementList "}" ]
	 */
	private IfStat ifStat() {
		next();

		// Expression
		Expression expr = expr();
		StatementList leftList, rightList = null;

		// Conferência de tipo da expressão
		if ( expr.getType() != Type.booleanType ) {
			error("Boolean expression expected");
		}

		check(Token.LEFTCURBRACKET, "'{' expected after the 'if' expression");
		next();

		leftList = statementList();
		//! modifiquei
		// while ( lexer.token != Token.RIGHTCURBRACKET && lexer.token != Token.END && lexer.token != Token.ELSE ) {
		// 	statement();
		// }

		check(Token.RIGHTCURBRACKET, "'}' was expected");

		if ( lexer.token == Token.ELSE ) {
			next();
			check(Token.LEFTCURBRACKET, "'{' expected after 'else'");
			next();

			rightList = statementList();
			//! modifiquei
			// while ( lexer.token != Token.RIGHTCURBRACKET ) {
			// 	statement();
			// }

			check(Token.RIGHTCURBRACKET, "'}' was expected");
		}

		return new IfStat(expr, leftList, rightList);
	}

	/**
	 * PrintStat ::= "Out" "." ( "print:" | "println:" ) Expression { "," Expression }
	 */
	private PrintStat printStat() {
		next();
		check(Token.DOT, "a '.' was expected after 'Out'");
		next();
		check(Token.IDCOLON, "'print:' or 'println:' was expected after 'Out.'");

		String printName = lexer.getStringValue();
		next();

		ExpressionList exprList = expressionList();

		return new PrintStat(printName, exprList);
	}

	/**
	 * AssignExpr ::= Expression [ "=" Expression ]
	 */
	private AssignExpr assignExpr() {
		Expression leftExpr = expr();
		Expression rightExpr = null;

		if (lexer.token == Token.EQ) {
			next();
			rightExpr = expr();
		}

		return new AssignExpr(leftExpr, rightExpr);
	}

	/**
	 * Expression { "," Expression }
	 */
	private ExpressionList expressionList() {
		ExpressionList exprList = new ExpressionList();

		exprList.addExpr(expr());

		while (lexer.token == Token.COMMA) {
			next();
			exprList.addExpr(expr());
		}

		return exprList;
	}

	/**
	 * Expression ::= SimpleExpression [ Relation SimpleExpression ]
	 */
	private Expression expr() {
		Expression left = simpleExpression();
		Token r;

		if ( relation(lexer.token) ) {
			r = lexer.token;
			next();
			Expression right = simpleExpression();
			left = new CompositeExpr(left, r, right);
		}

		return left;
	}

	/**
	 * SimpleExpression ::= SumSubExpression { "++" SumSubExpression }
	 */
	private Expression simpleExpression() {
		Expression left = sumSubExpression();
		Token r;

		while ( lexer.token == Token.PLUSPLUS ) {
			r = lexer.token;
			next();
			Expression right = sumSubExpression();
			left = new CompositeExpr(left, r, right);
		}

		return left;
	}

	/**
	 * SumSubExpression ::= Term { LowOperator Term }
	 */
	private Expression sumSubExpression() {
		Expression left = term();
		Token r;

		while ( lowOperator(lexer.token) ) {
			r = lexer.token;
			next();
			Expression right = term();
			left = new CompositeExpr(left, r, right);
		}

		return left;
	}

	/**
	 * LowOperator ::= +" | "−" | "||"
	 */
	private boolean lowOperator(Token token) {
		return token == Token.PLUS || token == Token.MINUS || token == Token.OR;
	}

	/**
	 * Term ::= SignalFactor { HighOperator SignalFactor }
	 */
	private Expression term() {
		Expression left = signalFactor();
		Token r;

		while ( highOperator(lexer.token) ) {
			r = lexer.token;
			next();
			Expression right = signalFactor();
			left = new CompositeExpr(left, r, right);
		}

		return left;
	}

	/**
	 * HighOperator ::= "∗" | "/" | "&&"
	 */
	private boolean highOperator(Token token) {
		return token == Token.MULT || token == Token.DIV || token == Token.AND;
	}

	/**
	 * SignalFactor ::= [ Signal ] Factor
	 */
	private Expression signalFactor() {
		Token signal;

		if ( signal(lexer.token) ) {
			signal = lexer.token;
			next();
			return new SignalExpr(signal, factor());
		}
		else {
			return factor();
		}
	}

	/**
	 * Signal ::= "+" | "−"
	 */
	private boolean signal(Token token) {
		return token == Token.PLUS || token == Token.MINUS;
	}

	/**
	 * Relation ::= "==" | "<" | ">" | "<=" | ">=" | "!="
	 */
	private boolean relation(Token token) {
		return token == Token.EQ || token == Token.LT || token == Token.LE ||
			token == Token.GT || token == Token.GE || token == Token.NEQ;
	}

	/**
	 * Factor ::= BasicValue | "(" Expression ")" | "!" Factor | "nil" | PrimaryExpr
	 *
	 * BasicValue ::= IntValue | BooleanValue | StringValue
	 * PrimaryExpr ::= 	"super" "." IdColon ExpressionList | "super" "." Id |
	 *                  Id | Id "." Id | Id "." IdColon ExpressionList | Id "." "new" |
	 * 				   	"self" | "self" "." Id | "self" "." IdColon ExpressionList |
	 * 				   	"self" "." Id "." IdColon ExpressionList | "self" "." Id "." Id | ReadExpr
	 */
	private Expression factor() {
		switch (lexer.token) {
			/**
			 * BasicValue
			 */
			case LITERALINT:
				return literalInt();
			case FALSE:
				next();
				return LiteralBooleanExpr.False;
			case TRUE:
				next();
				return LiteralBooleanExpr.True;
			case LITERALSTRING:
				String literalString = lexer.getLiteralStringValue();
				next();
				return new LiteralStringExpr(literalString);
			/**
			 * "(" Expression ")"
			 */
			case LEFTPAR:
				next();
				Expression expr = expr();
				if (lexer.token != Token.RIGHTPAR) { error(") expected"); }
				next();
				return new ParenthesisExpr(expr);
			/**
			 * "!" Factor
			 */
			case NOT:
				next();
				return factor();
			/**
			 * "nil"
			 */
			case NIL:
				next();
				return new NullExpr();
			/**
			 * PrimaryExpr
			 */
			default:
				return primaryExpr();
		}

	}

	/**
	 * PrimaryExpr ::= 	"super" "." IdColon ExpressionList |
	 * 				   	"super" "." Id |
	 * 				    Id | Id "." Id | Id "." IdColon ExpressionList | Id "." "new" |
	 * 				   	"self" | "self" "." Id | "self" "." IdColon ExpressionList |
	 * 				   	"self" "." Id "." IdColon ExpressionList | "self" "." Id "." Id |
	 * 					ReadExpr
	 *
	 * ReadExpr ::= "In" "." ( "readInt" | "readString" )
	*/
	private Expression primaryExpr() {
		switch (lexer.token) {
			/**
			 * Id | Id "." Id | Id "." IdColon ExpressionList | Id "." "new"
			 */
			case ID:
				String firstId = lexer.getStringValue();
				next();

				/**
				 * Id
				 */
				if (lexer.token != Token.DOT) {
					if (symbolTable.getInLocal(firstId) == null) {
						error("Identifier '" + firstId + "' does not exist");
					}

					Variable var = (Variable) symbolTable.getInLocal(firstId);
					return new VariableExpr(var);
				}
				/**
				 * Id "." Id | Id "." IdColon ExpressionList | Id "." "new"
				 */
				else {
					next();

					/**
					 * Id "." "new" => ObjectCreation
					 */
					if (lexer.token == Token.NEW) {
						return objectCreation(firstId);
					}

					/**
					 * A partir daqui pode ser:
					 *
					 * Id "." Id | Id "." IdColon ExpressionList
					 *
					 * O primeiro Id é uma classe
					 */

					// Verifica se existe uma classe com o nome firstId
					TypeCianetoClass cianetoClass = (TypeCianetoClass) symbolTable.getInGlobal(firstId);
					if (cianetoClass == null) {
						error("Class ''" + firstId + "' does not exist");
					}

					// Espera-se "Id" ou "IdColon"
					if ( !(lexer.token == Token.ID || lexer.token == Token.IDCOLON) ) {
						error("An identifier or identifer: was expected after 'identifier.'");
					}

					String secondId = lexer.getStringValue();

					/**
					 * Id "." Id => acesso à variável de instância
					 */
					if (lexer.token == Token.ID) {
						next();

						// Verifica se existe uma variável de instância nessa classe
						Variable var = (Variable) symbolTable.getInLocal(secondId);
						if (var == null) {
							error("Class '" + cianetoClass.getName() + "' does not have an instance variable of name '" + secondId + "'");
						}

						return new UnaryMessagePassingToExpr(cianetoClass, var);
					}
					/**
					 * Id "." IdColon ExpressionList => chamada de método
					 */
					else if (lexer.token == Token.IDCOLON) {
						next();

						// Verifica se existe um método nessa classe
						MethodDec classMethod = cianetoClass.searchPublicMethod(secondId);
						if (classMethod == null) {
							error("Method of class '" + firstId + "', named '" + secondId + "', does not exist");
						}

						ExpressionList exprList = expressionList();

						return new KeywordMessagePassingToExpr(cianetoClass, classMethod, exprList);
					}

				}

			/**
			 * "super" "." IdColon ExpressionList |
			 * "super" "." Id |
			 */
			case SUPER:
				// Verifica se a classe atual tem uma superclasse
				TypeCianetoClass superclass = currentClass.getSuperclass();
				if (superclass == null) {
					error("Class '" + currentClass.getName() + "' does not have a superclass");
				}
				next();

				if (lexer.token != Token.DOT) {
					error("'.' expected after 'super'");
				}
				next();

				if ( !(lexer.token == Token.ID || lexer.token == Token.IDCOLON) ) {
					error("An identifier or identifer: was expected after 'super'");
				}

				String id = lexer.getStringValue();

				/**
				 * "super" "." Id => acesso de variável de instância na superclasse da classe atual
				 */
				if (lexer.token == Token.ID) {
					next();

					// Verifica se existe uma variável de instância na superclasse da classe atual
					Variable var = superclass.searchInstanceVariable(id);
					if (var == null) {
						error("Class '" + superclass.getName() + "' does not have an instance variable of name '" + id + "'");
					}

					return new UnaryMessagePassingToSuper(currentClass, var);
				}
				/**
				 * "super" "." IdColon ExpressionList => chamada de método na superclasse da classe atual
				 */
				else if (lexer.token == Token.IDCOLON) {
					next();

					// Verifica se existe um método na superclasse da classe atual
					MethodDec superclassMethod = superclass.searchPublicMethod(id);
					if (superclassMethod == null) {
						error("Method of class '" + superclass.getName() + "', named '" + id + "', does not exist");
					}

					ExpressionList expressionList = expressionList();

					return new KeywordMessagePassingToSuper(currentClass, superclassMethod, expressionList);
				}


			/**
			 * "self" |
			 * "self" "." IdColon ExpressionList |
			 * "self" "." Id | "self" "." Id "." IdColon ExpressionList | "self" "." Id "." Id
			 */
			case SELF:
				next();

				if (lexer.token != Token.DOT) {
					// "self"
					return new MessagePassingToSelf();
				}
				else {
					next();

					if (lexer.token != Token.ID && lexer.token != Token.IDCOLON) {
						error("An identifier or identifer: was expected after 'self'");
					}

					firstId = lexer.getStringValue();

					/**
					 * "self" "." IdColon ExpressionList => chamada de método de self
					 */
					if (lexer.token == Token.IDCOLON) {
						next();

						// Verifica se existe um método nessa classe
						MethodDec classMethod = currentClass.searchPublicMethod(firstId);
						if (classMethod == null) {
							error("Method of class '" + currentClass.getName() + "', named '" + firstId + "', does not exist");
						}

						ExpressionList exprList = expressionList();

						return new KeywordMessagePassingToSelf(currentClass, classMethod, exprList);
					}
					/**
					 * "self" "." Id | "self" "." Id "." IdColon ExpressionList | "self" "." Id "." Id
					 */
					else if (lexer.token == Token.ID) {
						next();

						/**
						 * "self" "." Id => acesso de variável de instância de self
						 */
						if ( lexer.token != Token.DOT) {
							// Verifica se existe uma variável de instância na superclasse da classe atual
							Variable var = currentClass.searchInstanceVariable(firstId);
							if (var == null) {
								error("Class '" + currentClass.getName() + "' does not have an instance variable of name '" + firstId + "'");
							}

							return new UnaryMessagePassingToSelf(currentClass, var);
						}
						else {
							/**
							 * Se existe mais um ".", então o primeiro Id é uma classe de self
							 */
							// Verifica se essa classe existe
							TypeCianetoClass cianetoClass = (TypeCianetoClass)  symbolTable.getInGlobal(firstId);
							if (cianetoClass == null) {
								error("Class '" + firstId + "' does not exist");
							}

							next();

							// Espera-se ID ou IDCOLON
							if (lexer.token != Token.ID && lexer.token != Token.IDCOLON) {
								error("An identifier or identifer: was expected after 'self.Id.'");
							}

							String secondId = lexer.getStringValue();

							/**
							 * "self" "." Id "." IdColon ExpressionList => chamada de método de uma classe de self
							 */
							if (lexer.token == Token.IDCOLON) {
								next();

								// Verifica se existe um método nessa classe de self
								MethodDec classMethod = cianetoClass.searchPublicMethod(secondId);
								if (classMethod == null) {
									error("Method of class '" + cianetoClass.getName() + "', named '" + secondId + "', does not exist");
								}

								ExpressionList expressionList = expressionList();

								return new KeywordMessagePassingToSelf(currentClass, cianetoClass, classMethod, expressionList);
							}
							/**
							 * "self" "." Id "." Id => acesso de variável de instância de uma classe de self
							 */
							else if (lexer.token == Token.ID) {
								next();

								// Verifica se existe uma variável de instância nessa classe de self
								Variable var = cianetoClass.searchInstanceVariable(secondId);
								if (var == null) {
									error("Class '" + cianetoClass.getName() + "' does not have an instance variable of name '" + secondId + "'");
								}

								return new UnaryMessagePassingToSelf(currentClass, cianetoClass, var);
							}
						}

					}

				}

			default:
				/**
				 * ReadExpr
				 */
				if ( lexer.token == Token.ID && lexer.getStringValue().equals("In") ) {
					return readExpr();
				}

				error("Unexpected token");
				break;
		}

		return null;
	}

	/**
	 * ReadExpr ::= "In" "." ( "readInt" | "readString" )
	 */
	private ReadExpr readExpr() {
		next();
		check(Token.DOT, "a '.' was expected after 'In'");
		next();
		check(Token.ID, "'readInt' or 'readString' was expected after 'In.'");

		String readName = lexer.getStringValue();
		next();

		return new ReadExpr(readName);
	}

	/**
	 * ObjectCreation => Id "." "new"
	 */
	private ObjectCreation objectCreation(String id) {
		if (symbolTable.getInGlobal(id) == null) {
			error("Class '" + id + "' does not exist");
		}

		next();

		if (lexer.token != Token.DOT) {
			error(". expected");
		}

		next();

		if (lexer.token != Token.NEW) {
			error("'new' expected. Object initialization is required");
		}

		return new ObjectCreation(id);
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
				if ( symbolTable.getInLocal(id) != null ) {
					error("Duplicate local variable '" + id + "'");
				}

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

		// Coloca os campos na tabela local
		//?

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

		next();

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
	 * AssertStat ::= "assert" Expression "," StringValue
	 */
	private AssertStat assertStat() {
		next();

		int lineNumber = lexer.getLineNumber();

		Expression expr = expr();

		if ( lexer.token != Token.COMMA ) {
			this.error("',' expected after the expression of the 'assert' statement");
		}

		next();

		if ( lexer.token != Token.LITERALSTRING ) {
			this.error("A literal string expected after the ',' of the 'assert' statement");
		}

		String message = lexer.getLiteralStringValue();
		next();

		return new AssertStat(expr, message);
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

	//?
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
