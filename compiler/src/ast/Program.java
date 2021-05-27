/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

import java.util.*;
import comp.CompilationError;

public class Program {

	public Program(ArrayList<TypeCianetoClass> classList, ArrayList<MetaobjectAnnotation> metaobjectCallList,
			ArrayList<CompilationError> compilationErrorList) {
		this.classList = classList;
		this.metaobjectCallList = metaobjectCallList;
		this.compilationErrorList = compilationErrorList;
	}

	public void genJava(PW pw) {

		// Imports
		pw.print("import java.util.*;");
		pw.println();

		pw.println("public class " + mainJavaClassName + " {");
		pw.add();
		pw.printlnIdent("public static Scanner inputScanner = new Scanner(System.in);");
		pw.println();
		pw.printlnIdent("public static void main(String []args) {");
		pw.add();
		pw.printlnIdent("new Program().run();");
		pw.sub();
		pw.printlnIdent("}");
		pw.println();

		// Classes
		for (TypeCianetoClass classDec : this.classList) {
			classDec.genJava(pw);
		}

		pw.println("}");

	}

	public void genC(PW pw) {

		// Headers
		pw.println("/* deve-se incluir alguns headers porque algumas funções da biblioteca padrão de C são utilizadas na tradução. */");
		pw.println("#include <malloc.h>");
		pw.println("#include <stdlib.h>");
		pw.println("#include <stdio.h>");
		pw.println("#include <string.h>");
		pw.println();

		// Tipo Boolean
		pw.println("/* define o tipo boolean */");
		pw.println("typedef int boolean;");
		pw.println("#define true 1");
		pw.println("#define false 0");
		pw.println();

		// Funções readInt e readString
		pw.println("// crie funções readInt e readString");

		// readInt
		pw.println("int readInt() {");
		pw.add();
		pw.printlnIdent("int n;");
		pw.printlnIdent("char __s[512];");
		pw.printlnIdent("gets(__s);");
		pw.printlnIdent("sscanf(__s, \"%d\", &n);");
		pw.printlnIdent("return n;");
		pw.sub();
		pw.println("}");
		pw.println();

		// readString
		pw.println("char *readString() {");
		pw.add();
		pw.printlnIdent("char s[512];");
		pw.printlnIdent("gets(s);");
		pw.printlnIdent("char *ret = malloc(strlen(s) + 1);");
		pw.printlnIdent("strcpy(ret, s);");
		pw.printlnIdent("return ret;");
		pw.sub();
		pw.println("}");
		pw.println();

		// Tipo Func
		pw.println("/* define um tipo Func que é um ponteiro para função */");
		pw.println("typedef void (*Func)();");
		pw.println();

		// Classes
		for (TypeCianetoClass classDec : this.classList) {
			classDec.genC(pw);
		}

		// Main
		pw.println("int main() {");

		pw.add();
		pw.printlnIdent("_class_Program *program;");
		pw.printlnIdent("program = new_Program();");
		pw.printlnIdent("_Program_run(program);");
		pw.printlnIdent("return 0;");
		pw.sub();

		pw.println("}");

	}

	public ArrayList<TypeCianetoClass> getClassList() {
		return classList;
	}

	public ArrayList<MetaobjectAnnotation> getMetaobjectCallList() {
		return metaobjectCallList;
	}

	public boolean hasCompilationErrors() {
		return compilationErrorList != null && compilationErrorList.size() > 0;
	}

	public ArrayList<CompilationError> getCompilationErrorList() {
		return compilationErrorList;
	}

	public void setMainJavaClassName(String mainJavaClassName) {
		this.mainJavaClassName = mainJavaClassName;
	}

	private ArrayList<TypeCianetoClass> classList;
	private ArrayList<MetaobjectAnnotation> metaobjectCallList;

	ArrayList<CompilationError> compilationErrorList;

	/**
	 * the name of the main Java class when the code is generated to Java. This name
	 * is equal to the file name (without extension)
	 */
	private String mainJavaClassName;

}