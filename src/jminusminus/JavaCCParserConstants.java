/* Generated By:JavaCC: Do not edit this line. JavaCCParserConstants.java */
package jminusminus;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface JavaCCParserConstants {

    /**
     * End of File.
     */
    int EOF = 0;
    /**
     * RegularExpression Id.
     */
    int BEGIN_COMMENT = 6;
    /**
     * RegularExpression Id.
     */
    int END_COMMENT = 7;
    /**
     * RegularExpression Id.
     */
    int COMMENT = 8;
    /**
     * RegularExpression Id.
     */
    int ABSTRACT = 9;
    /**
     * RegularExpression Id.
     */
    int BOOLEAN = 10;
    /**
     * RegularExpression Id.
     */
    int CHAR = 11;
    /**
     * RegularExpression Id.
     */
    int CLASS = 12;
    /**
     * RegularExpression Id.
     */
    int ELSE = 13;
    /**
     * RegularExpression Id.
     */
    int EXTENDS = 14;
    /**
     * RegularExpression Id.
     */
    int FALSE = 15;
    /**
     * RegularExpression Id.
     */
    int IF = 16;
    /**
     * RegularExpression Id.
     */
    int IMPORT = 17;
    /**
     * RegularExpression Id.
     */
    int INSTANCEOF = 18;
    /**
     * RegularExpression Id.
     */
    int INT = 19;
    /**
     * RegularExpression Id.
     */
    int NEW = 20;
    /**
     * RegularExpression Id.
     */
    int NULL = 21;
    /**
     * RegularExpression Id.
     */
    int PACKAGE = 22;
    /**
     * RegularExpression Id.
     */
    int PRIVATE = 23;
    /**
     * RegularExpression Id.
     */
    int PROTECTED = 24;
    /**
     * RegularExpression Id.
     */
    int PUBLIC = 25;
    /**
     * RegularExpression Id.
     */
    int RETURN = 26;
    /**
     * RegularExpression Id.
     */
    int STATIC = 27;
    /**
     * RegularExpression Id.
     */
    int SUPER = 28;
    /**
     * RegularExpression Id.
     */
    int THIS = 29;
    /**
     * RegularExpression Id.
     */
    int TRUE = 30;
    /**
     * RegularExpression Id.
     */
    int VOID = 31;
    /**
     * RegularExpression Id.
     */
    int WHILE = 32;
    /**
     * RegularExpression Id.
     */
    int COMMA = 33;
    /**
     * RegularExpression Id.
     */
    int DOT = 34;
    /**
     * RegularExpression Id.
     */
    int LBRACK = 35;
    /**
     * RegularExpression Id.
     */
    int LCURLY = 36;
    /**
     * RegularExpression Id.
     */
    int LPAREN = 37;
    /**
     * RegularExpression Id.
     */
    int RPAREN = 38;
    /**
     * RegularExpression Id.
     */
    int RBRACK = 39;
    /**
     * RegularExpression Id.
     */
    int RCURLY = 40;
    /**
     * RegularExpression Id.
     */
    int SEMI = 41;
    /**
     * RegularExpression Id.
     */
    int ASSIGN = 42;
    /**
     * RegularExpression Id.
     */
    int DEC = 43;
    /**
     * RegularExpression Id.
     */
    int EQUAL = 44;
    /**
     * RegularExpression Id.
     */
    int GT = 45;
    /**
     * RegularExpression Id.
     */
    int INC = 46;
    /**
     * RegularExpression Id.
     */
    int LAND = 47;
    /**
     * RegularExpression Id.
     */
    int LE = 48;
    /**
     * RegularExpression Id.
     */
    int LNOT = 49;
    /**
     * RegularExpression Id.
     */
    int MINUS = 50;
    /**
     * RegularExpression Id.
     */
    int PLUS = 51;
    /**
     * RegularExpression Id.
     */
    int PLUS_ASSIGN = 52;
    /**
     * RegularExpression Id.
     */
    int STAR = 53;
    /**
     * RegularExpression Id.
     */
    int IDENTIFIER = 54;
    /**
     * RegularExpression Id.
     */
    int LETTER = 55;
    /**
     * RegularExpression Id.
     */
    int DIGIT = 56;
    /**
     * RegularExpression Id.
     */
    int INT_LITERAL = 57;
    /**
     * RegularExpression Id.
     */
    int CHAR_LITERAL = 58;
    /**
     * RegularExpression Id.
     */
    int STRING_LITERAL = 59;
    /**
     * RegularExpression Id.
     */
    int ESC = 60;
    /**
     * RegularExpression Id.
     */
    int ERROR = 61;
    /**
     * RegularExpression Id.
     */
    int DOUBLE_LITERAL = 62;
    /**
     * RegularExpression Id.
     */
    int FLOAT_LITERAL = 63;
    /**
     * RegularExpression Id.
     */
    int LONG_LITERAL = 64;
    /**
     * RegularExpression Id
     */
    int HEX_LONG_LITERAL = 65;
    /**
     * RegularExpression Id
     */
    int BINARY_LONG_LITERAL = 66;
    /**
     * RegularExpression Id
     */
    int OCTAL_LONG_LITERAL = 67;
    /**
     * RegularExpression Id
     */
    int HEX_INT_LITERAL = 68;
    /**
     * RegularExpression Id
     */
    int BINARY_INT_LITERAL = 69;
    /**
     * RegularExpression Id
     */
    int OCTAL_INT_LITERAL = 70;

    /**
     * Lexical state.
     */
    int DEFAULT = 0;
    /**
     * Lexical state.
     */
    int IN_SINGLE_LINE_COMMENT = 1;

    /**
     * Literal token values.
     */
    String[] tokenImage = {
            "<EOF>",
            "\" \"",
            "\"\\t\"",
            "\"\\n\"",
            "\"\\r\"",
            "\"\\f\"",
            "\"//\"",
            "<END_COMMENT>",
            "<COMMENT>",
            "\"abstract\"",
            "\"boolean\"",
            "\"char\"",
            "\"class\"",
            "\"else\"",
            "\"extends\"",
            "\"false\"",
            "\"if\"",
            "\"import\"",
            "\"instanceof\"",
            "\"int\"",
            "\"new\"",
            "\"null\"",
            "\"package\"",
            "\"private\"",
            "\"protected\"",
            "\"public\"",
            "\"return\"",
            "\"static\"",
            "\"super\"",
            "\"this\"",
            "\"true\"",
            "\"void\"",
            "\"while\"",
            "\",\"",
            "\".\"",
            "\"[\"",
            "\"{\"",
            "\"(\"",
            "\")\"",
            "\"]\"",
            "\"}\"",
            "\";\"",
            "\"=\"",
            "\"--\"",
            "\"==\"",
            "\">\"",
            "\"++\"",
            "\"&&\"",
            "\"<=\"",
            "\"!\"",
            "\"-\"",
            "\"+\"",
            "\"+=\"",
            "\"*\"",
            "<IDENTIFIER>",
            "<LETTER>",
            "<DIGIT>",
            "<INT_LITERAL>",
            "<CHAR_LITERAL>",
            "<STRING_LITERAL>",
            "<DOUBLE_LITERAL>",
            "<FLOAT_LITERAL>",
            "<LONG_LITERAL>",
            "<HEX_LONG_LITERAL>",
            "<BINARY_INT_LITERAL>",
            "<OCTAL_LONG_LITERAL>",
            "<HEX_INT_LITERAL>",
            "<BINARY_INT_LITERAL>",
            "<OCTAL_INT_LITERAL>",
            "<ESC>",
            "<ERROR>",
    };

}
