/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

/**
 * Classe de utilidades para geração de código
 */
public class PWHelper {

    /**
     *
     * @param pw
     */
    public static void createCHeaders(PW pw) {
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

        // Funções plusplus
        pw.println("char *plusplus_is(int n, char *s) {");
        pw.println("   char *r;");
        pw.println("   if ((r = malloc(strlen(s) + 20)) != NULL) {");
        pw.println("      itoa(n, r, 10);");
        pw.println("   }");
        pw.println("   strcpy(r + strlen(r), s);");
        pw.println("   return r;");
        pw.println("}");

        pw.println("char *plusplus_si(char *s, int n) {");
        pw.println("   char *r;");
        pw.println("   if ((r = malloc(strlen(s) + 20)) != NULL) {");
        pw.println("      strcpy(r, s);");
        pw.println("   }");
        pw.println("   itoa(n, r + strlen(s), 10);");
        pw.println("   return r;");
        pw.println("}");

        pw.println("char *plusplus_ii(int n, int last) {");
        pw.println("   char *r;");
        pw.println("   if ((r = malloc(40)) != NULL) {");
        pw.println("      itoa(n, r, 10);");
        pw.println("   }");
        pw.println("   itoa(last, r + strlen(r), 10);");
        pw.println("   return r;");
        pw.println("}");

        pw.println("char *plusplus_ss(char *first, char *second) {");
        pw.println("   char *r;");
        pw.println("   if ((r = malloc(strlen(first) + strlen(second))) != NULL) {");
        pw.println("      strcpy(r, first);");
        pw.println("      strcpy(r + strlen(r), second);");
        pw.println("      return r;");
        pw.println("   } else {");
        pw.println("      puts(\"Erro: não há memória suficiente\");");
        pw.println("      return NULL;");
        pw.println("   }");
        pw.println("}");
        pw.println();

		// Tipo Func
		pw.println("/* define um tipo Func que é um ponteiro para função */");
		pw.println("typedef void (*Func)();");
		pw.println();
    }

}
