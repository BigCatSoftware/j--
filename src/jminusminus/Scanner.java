// Copyright 2012- Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import javax.swing.plaf.synth.SynthCheckBoxMenuItemUI;
import javax.swing.plaf.synth.SynthEditorPaneUI;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Hashtable;

import static jminusminus.TokenKind.*;

/**
 * A lexical analyzer for j--, that has no backtracking mechanism.
 */
class Scanner {
    // End of file character.
    public final static char EOFCH = CharReader.EOFCH;

    // Keywords in j--.
    private Hashtable<String, TokenKind> reserved;

    // Source characters.
    private CharReader input;

    // Next unscanned character.
    private char ch;

    // Whether a scanner error has been found.
    private boolean isInError;

    // Source file name.
    private String fileName;

    // Line number of current token.
    private int line;

    /**
     * Constructs a Scanner from a file name.
     *
     * @param fileName name of the source file.
     * @throws FileNotFoundException when the named file cannot be found.
     */
    public Scanner(String fileName) throws FileNotFoundException {
        this.input = new CharReader(fileName);
        this.fileName = fileName;
        isInError = false;

        // Keywords in j--
        reserved = new Hashtable<String, TokenKind>();
        reserved.put(ABSTRACT.image(), ABSTRACT);
        reserved.put(BOOLEAN.image(), BOOLEAN);
        reserved.put(CHAR.image(), CHAR);
        reserved.put(CLASS.image(), CLASS);
        reserved.put(ELSE.image(), ELSE);
        reserved.put(EXTENDS.image(), EXTENDS);
        reserved.put(FALSE.image(), FALSE);
        reserved.put(IF.image(), IF);
        reserved.put(IMPORT.image(), IMPORT);
        reserved.put(INSTANCEOF.image(), INSTANCEOF);
        reserved.put(INT.image(), INT);
        reserved.put(NEW.image(), NEW);
        reserved.put(NULL.image(), NULL);
        reserved.put(PACKAGE.image(), PACKAGE);
        reserved.put(PRIVATE.image(), PRIVATE);
        reserved.put(PROTECTED.image(), PROTECTED);
        reserved.put(PUBLIC.image(), PUBLIC);
        reserved.put(RETURN.image(), RETURN);
        reserved.put(STATIC.image(), STATIC);
        reserved.put(SUPER.image(), SUPER);
        reserved.put(THIS.image(), THIS);
        reserved.put(TRUE.image(), TRUE);
        reserved.put(VOID.image(), VOID);
        reserved.put(WHILE.image(), WHILE);

        // keywords I added as per lexical structure section 3.9
        reserved.put(ASSERT.image(), ASSERT);
        reserved.put(BREAK.image(), BREAK);
        reserved.put(BYTE.image(), BYTE);
        reserved.put(CASE.image(), CASE);
        reserved.put(CATCH.image(), CATCH);
        reserved.put(CONST.image(), CONST);
        reserved.put(CONTINUE.image(), CONTINUE);
        reserved.put(DEFAULT.image(), DEFAULT);
        reserved.put(DO.image(), DO);
        reserved.put(DOUBLE.image(), DOUBLE);
        reserved.put(ENUM.image(), ENUM);
        reserved.put(FINAL.image(), FINAL);
        reserved.put(FINALLY.image(), FINALLY);
        reserved.put(FLOAT.image(), FLOAT);
        reserved.put(FOR.image(), FOR);
        reserved.put(GOTO.image(), GOTO);
        reserved.put(IMPLEMENTS.image(), IMPLEMENTS);
        reserved.put(INTERFACE.image(), INTERFACE);
        reserved.put(LONG.image(), LONG);
        reserved.put(NATIVE.image(), NATIVE);
        reserved.put(SHORT.image(), SHORT);
        reserved.put(STRICTFP.image(), STRICTFP);
        reserved.put(SWITCH.image(), SWITCH);
        reserved.put(SYNCHRONIZED.image(), SYNCHRONIZED);
        reserved.put(THROW.image(), THROW);
        reserved.put(THROWS.image(), THROWS);
        reserved.put(TRANSIENT.image(), TRANSIENT);
        reserved.put(TRY.image(), TRY);
        reserved.put(VOLATILE.image(), VOLATILE);

        // Prime the pump.
        nextCh();
    }

    /**
     * Scans and returns the next token from input.
     *
     * @return the next scanned token.
     */
    public TokenInfo getNextToken() {
        StringBuffer buffer;
        boolean moreWhiteSpace = true;
        while (moreWhiteSpace) {
            while (isWhitespace(ch)) {
                nextCh();
            }
            if (ch == '/') {
                nextCh();
                if (ch == '/') {
                    // CharReader maps all new lines to '\n'.
                    while (ch != '\n' && ch != EOFCH) {
                        nextCh();
                    }
                    return getNextToken();
                } else if (ch == '*') {
                    nextCh();
                    while (ch != EOFCH) {
                        if (ch == '*') {
                            nextCh();
                            if (ch == '/') {
                                nextCh();
                                break;
                            }
                        } else {
                            nextCh();
                        }
                    }
                    return getNextToken();
                } else if (ch == '=') {
                    nextCh();
                    return new TokenInfo(DIV_ASSIGN, line);
                } else {
//                    reportScannerError("Operator / is not supported in j--");
                    return new TokenInfo(DIV, line);
                }
            } else {
                moreWhiteSpace = false;
            }
        }
        line = input.line();
        switch (ch) {
            case ',':
                nextCh();
                return new TokenInfo(COMMA, line);
            case '.':
                nextCh();
                return new TokenInfo(DOT, line);
            case '[':
                nextCh();
                return new TokenInfo(LBRACK, line);
            case '{':
                nextCh();
                return new TokenInfo(LCURLY, line);
            case '(':
                nextCh();
                return new TokenInfo(LPAREN, line);
            case ']':
                nextCh();
                return new TokenInfo(RBRACK, line);
            case '}':
                nextCh();
                return new TokenInfo(RCURLY, line);
            case ')':
                nextCh();
                return new TokenInfo(RPAREN, line);
            case ';':
                nextCh();
                return new TokenInfo(SEMI, line);
            case '*':
                nextCh();
                if (ch == '=') {
                    nextCh();
                    return new TokenInfo(STAR_ASSIGN, line);
                } else {
                    return new TokenInfo(STAR, line);
                }
            case '+':
                nextCh();
                if (ch == '=') {
                    nextCh();
                    return new TokenInfo(PLUS_ASSIGN, line);
                } else if (ch == '+') {
                    nextCh();
                    return new TokenInfo(INC, line);
                } else {
                    return new TokenInfo(PLUS, line);
                }
            case '-':
                nextCh();
                if (ch == '-') {
                    nextCh();
                    return new TokenInfo(DEC, line);
                } else if (ch == '=') {
                    nextCh();
                    return new TokenInfo(MIN_ASSIGN, line);
                } else if (ch == '>') {
                    nextCh();
                    return new TokenInfo(LAMBDA, line);
                } else if (isDigit(ch)) {
                    // I just realized you can have a negative first followed by an int, long, float, or double
                    // I will get to this when I can.
                } else {
                    return new TokenInfo(MINUS, line);
                }
            case '=':
                nextCh();
                if (ch == '=') {
                    nextCh();
                    return new TokenInfo(EQUAL, line);
                } else {
                    return new TokenInfo(ASSIGN, line);
                }
            case '>':
                nextCh();
                if (ch == '=') {
                    nextCh();
                    return new TokenInfo(GE, line);
                } else if (ch == '>') {
                    nextCh();
                    if (ch == '>') {
                        nextCh();
                        if (ch == '=') {
                            nextCh();
                            return new TokenInfo(UNSIGNED_RIGHT_SHIFT_ASSIGN, line);
                        } else {
                            return new TokenInfo(UNSIGNED_RIGHT_SHIFT, line);
                        }
                    } else if (ch == '=') {
                        nextCh();
                        return new TokenInfo(RIGHT_SHIFT_ASSIGN, line);
                    } else {
                        return new TokenInfo(RIGHT_SHIFT, line);
                    }
                } else {
                    return new TokenInfo(GT, line);
                }
            case '<':
                nextCh();
                if (ch == '=') {
                    nextCh();
                    return new TokenInfo(LE, line);
                } else if (ch == '<') {
                    nextCh();
                    if (ch == '=') {
                        nextCh();
                        return new TokenInfo(LEFT_SHIFT_ASSIGN, line);
                    } else {
                        return new TokenInfo(LEFT_SHIFT, line);
                    }
                } else {
                    //reportScannerError("Operator < is not supported in j--");
                    //return getNextToken();
                    return new TokenInfo(LESS, line);
                }
            case '!':
                System.out.println(ch);
                nextCh();
                System.out.println(ch);
                if (ch == '=') {
                    nextCh();
                    return new TokenInfo(NOT_EQ, line);
                }
                return new TokenInfo(LNOT, line);
            case '&':
                nextCh();
                if (ch == '&') {
                    nextCh();
                    return new TokenInfo(LAND, line);
                } else if (ch == '=') {
                    nextCh();
                    return new TokenInfo(BIT_AND_EQ, line);
                } else {
//                    reportScannerError("Operator & is not supported in j--");
//                    return getNextToken();
                    return new TokenInfo(BIT_AND, line);
                }
            case '|':
                nextCh();
                if (ch == '|') {
                    nextCh();
                    return new TokenInfo(LOR, line);
                } else if (ch == '=') {
                    nextCh();
                    return new TokenInfo(BIT_OR_EQ, line);
                } else {
                    return new TokenInfo(BIT_OR, line);
                }
            case '~':
                nextCh();
                return new TokenInfo(TILDE, line);
            case '?':
                nextCh();
                return new TokenInfo(TERN, line);
            case ':':
                nextCh();
                return new TokenInfo(COLON, line);
            case '/':
                char prevChar;
                nextCh();
                if (ch == '=') {
                    nextCh();
                    return new TokenInfo(DIV_ASSIGN, line);
                } else if (ch == '*') {
                    nextCh();
                    while (ch != EOFCH) {
                        if (ch == '*') {
                            nextCh();
                            if (ch == '/') {
                                nextCh();
                                break;
                            }
                        } else {
                            nextCh();
                        }
                    }
                } else {
                    return new TokenInfo(DIV, line);
                }
            case '%':
                nextCh();
                if (ch == '=') {
                    nextCh();
                    return new TokenInfo(MOD_ASSIGN, line);
                } else {
                    return new TokenInfo(MOD, line);
                }
            case '^':
                nextCh();
                if (ch == '=') {
                    nextCh();
                    return new TokenInfo(BIT_XOR_ASSIGN, line);
                } else {
                    return new TokenInfo(BIT_XOR, line);
                }
            case '\'':
                buffer = new StringBuffer();
                buffer.append('\'');
                nextCh();
                if (ch == '\\') {
                    nextCh();
                    buffer.append(escape());
                } else {
                    buffer.append(ch);
                    nextCh();
                }
                if (ch == '\'') {
                    buffer.append('\'');
                    nextCh();
                    return new TokenInfo(CHAR_LITERAL, buffer.toString(), line);
                } else {
                    // Expected a ' ; report error and try to recover.
                    reportScannerError(ch + " found by scanner where closing ' was expected");
                    while (ch != '\'' && ch != ';' && ch != '\n') {
                        nextCh();
                    }
                    return new TokenInfo(CHAR_LITERAL, buffer.toString(), line);
                }
            case '"':
                buffer = new StringBuffer();
                buffer.append("\"");
                nextCh();
                while (ch != '"' && ch != '\n' && ch != EOFCH) {
                    if (ch == '\\') {
                        nextCh();
                        buffer.append(escape());
                    } else {
                        buffer.append(ch);
                        nextCh();
                    }
                }
                if (ch == '\n') {
                    reportScannerError("Unexpected end of line found in string");
                } else if (ch == EOFCH) {
                    reportScannerError("Unexpected end of file found in string");
                } else {
                    // Scan the closing "
                    nextCh();
                    buffer.append("\"");
                }
                return new TokenInfo(STRING_LITERAL, buffer.toString(), line);
            case EOFCH:
                return new TokenInfo(EOF, line);
            case '0':
                char prevNumericChar = ' ';
                buffer = new StringBuffer();
                buffer.append(ch);
                nextCh();
                if (ch == 'x' || ch == 'X') {
                    buffer.append(ch);
                    nextCh();
                    if (ch == '_') {
                        throw new RuntimeException("'_' underscores are not valid at the beginning or end of a " +
                                "numeric value.");
                    }
                    while (isHexDigit(ch)) {
                        buffer.append(ch);
                        prevNumericChar = ch;
                        nextCh();
                    }
                    if (prevNumericChar == '_') {
                        throw new RuntimeException("'_' underscores are not valid at the beginning or end of a " +
                                "numeric value.");
                    }
                    if (ch =='l' || ch == 'L') {
                        buffer.append(ch);
                        nextCh();
                        return new TokenInfo(HEX_LONG_LITERAL, buffer.toString(), line);
                    }
                    return new TokenInfo(HEX_INT_LITERAL, buffer.toString(), line);
                } else if (ch == 'b' || ch == 'B') {
                    buffer.append(ch);
                    nextCh();
                    if (ch == '_') {
                        throw new RuntimeException("'_' underscores are not valid at the beginning or end of a " +
                                "numeric value.");
                    }
                    while (isBinaryDigit(ch)) {
                        buffer.append(ch);
                        prevNumericChar = ch;
                        nextCh();
                    }
                    if (prevNumericChar == '_') {
                        throw new RuntimeException("'_' underscores are not valid at the beginning or end of a " +
                                "numeric value.");
                    }
                    if (ch =='l' || ch == 'L') {
                        buffer.append(ch);
                        nextCh();
                        return new TokenInfo(BINARY_LONG_LITERAL, buffer.toString(), line);
                    }
                    return new TokenInfo(BINARY_INT_LITERAL, buffer.toString(), line);
                } else if (isOctalDigit(ch)) {
                    buffer.append(ch);
                    nextCh();
//                    if (ch == 'd' || ch == 'D') {
//                        buffer.append(ch);
//                        nextCh();
//                        return new TokenInfo(DOUBLE_LITERAL, buffer.toString(), line);
//                    }
                    while(isOctalDigit(ch)) {
                        buffer.append(ch);
                        prevNumericChar = ch;
                        nextCh();
                    }
                    if (prevNumericChar == '_') {
                        throw new RuntimeException("'_' underscores are not valid at the beginning or end of a " +
                                "numeric value.");
                    }
                    if (ch =='l' || ch == 'L') {
                        buffer.append(ch);
                        nextCh();
                        return new TokenInfo(OCTAL_LONG_LITERAL, buffer.toString(), line);
                    }
                    return new TokenInfo(OCTAL_INT_LITERAL, buffer.toString(), line);
                } else if (ch == '.') {
                    if (prevNumericChar == '_') {
                        throw new RuntimeException("'_' underscores are not valid at the beginning or end of a " +
                                "decimal point.");
                    }
                    buffer.append(ch);
                    nextCh();
                    if (ch == '_') {
                        throw new RuntimeException("'_' underscores are not valid at the beginning or end of a " +
                                "decimal point.");
                    }
                    while (isDigit(ch)) {
                        buffer.append(ch);
                        prevNumericChar = ch;
                        nextCh();
                    }
                    if (ch == 'e' || ch == 'E') {
                        if (prevNumericChar == '_') {
                            throw new RuntimeException("'_' underscores are not valid at the beginning or end of a " +
                                    "'e' or 'E' when using scientific notation.");
                        }
                        buffer.append(ch);
                        nextCh();
                        if (ch == '_') {
                            throw new RuntimeException("'_' underscores are not valid at the beginning or end of a " +
                                    "'e' or 'E' when using scientific notation.");
                        }
                        if (ch == '-' || ch == '+') {
                            buffer.append(ch);
                            nextCh();
                            prevNumericChar = ch;
                        }
                        if (ch == '_') {
                            throw new RuntimeException("'_' underscores are not valid at the beginning or end of a " +
                                    "'+' or '-' when using scientific notation.");
                        }
                        while (isDigit(ch)) {
                            buffer.append(ch);
                            prevNumericChar = ch;
                            nextCh();
                        }
                        if (ch == '_') {
                            throw new RuntimeException("'_' underscores are not valid at the beginning or end of a " +
                                    "numeric value.");
                        }
                        if (ch == 'f' || ch == 'F') {
                            buffer.append(ch);
                            nextCh();
                            return new TokenInfo(FLOAT_LITERAL, buffer.toString(), line);
                        } else if (ch == 'd' || ch == 'D') {
                            buffer.append(ch);
                            nextCh();
                            return new TokenInfo(DOUBLE_LITERAL, buffer.toString(), line);
                        } else {
                            return new TokenInfo(DOUBLE_LITERAL, buffer.toString(), line);
                        }
                    }
                } else {
                    return new TokenInfo(INT_LITERAL, buffer.toString(), line);
                }
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                buffer = new StringBuffer();
                prevNumericChar = ' ';
                while (isDigit(ch)) {
                    buffer.append(ch);
                    prevNumericChar = ch;
                    nextCh();
                }
                if (ch == '.') {
                    if (prevNumericChar == '_') {
                        throw new RuntimeException("'_' underscores are not valid at the beginning or end of a " +
                                "decimal point.");
                    }
                    buffer.append(ch);
                    nextCh();
                    if (ch == '_') {
                        throw new RuntimeException("'_' underscores are not valid at the beginning or end of a " +
                                "decimal point.");
                    }
                    while (isDigit(ch)) {
                        buffer.append(ch);
                        prevNumericChar = ch;
                        nextCh();
                    }
                    if (ch == 'e' || ch == 'E') {
                        if (prevNumericChar == '_') {
                            throw new RuntimeException("'_' underscores are not valid at the beginning or end of a " +
                                    "'e' or 'E' when using scientific notation.");
                        }
                        buffer.append(ch);
                        nextCh();
                        if (ch == '_') {
                            throw new RuntimeException("'_' underscores are not valid at the beginning or end of a " +
                                    "'e' or 'E' when using scientific notation.");
                        }
                        if (ch == '-' || ch == '+') {
                            buffer.append(ch);
                            nextCh();
                            prevNumericChar = ch;
                        }
                        if (ch == '_') {
                            throw new RuntimeException("'_' underscores are not valid at the beginning or end of a " +
                                    "'+' or '-' when using scientific notation.");
                        }
                        while (isDigit(ch)) {
                            buffer.append(ch);
                            prevNumericChar = ch;
                            nextCh();
                        }
                        if (ch == '_') {
                            throw new RuntimeException("'_' underscores are not valid at the beginning or end of a " +
                                    "numeric value.");
                        }
                        if (ch == 'f' || ch == 'F') {
                            buffer.append(ch);
                            nextCh();
                            return new TokenInfo(FLOAT_LITERAL, buffer.toString(), line);
                        } else if (ch == 'd' || ch == 'D') {
                            buffer.append(ch);
                            nextCh();
                            return new TokenInfo(DOUBLE_LITERAL, buffer.toString(), line);
                        } else {
                            return new TokenInfo(DOUBLE_LITERAL, buffer.toString(), line);
                        }
                    } else if (ch == 'f' || ch == 'F') {
                        buffer.append(ch);
                        nextCh();
                        return new TokenInfo(FLOAT_LITERAL, buffer.toString(), line);
                    } else if (ch == 'd' || ch == 'D') { // check for d?
                        buffer.append(ch);
                        nextCh();
                        return new TokenInfo(DOUBLE_LITERAL, buffer.toString(), line);
                    } else {
                        return new TokenInfo(DOUBLE_LITERAL, buffer.toString(), line);
                    }
                } else if (ch == 'e' || ch == 'E') {
                    if (prevNumericChar == '_') {
                        throw new RuntimeException("'_' underscores are not valid at the beginning or end of a " +
                                "'e' or 'E' when using scientific notation.");
                    }
                    buffer.append(ch);
                    prevNumericChar = ch;
                    nextCh();
                    if (ch == '_') {
                        throw new RuntimeException("'_' underscores are not valid at the beginning or end of a " +
                                "'e' or 'E' when using scientific notation.");
                    }
                    if (ch == '-' || ch == '+') {
                        if (prevNumericChar == '_') {
                            throw new RuntimeException("'_' underscores are not valid at the beginning or end of a " +
                                    "'+' or '-' when using scientific notation.");
                        }
                        buffer.append(ch);
                        prevNumericChar = ch;
                        nextCh();
                        if (ch == '_') {
                            throw new RuntimeException("'_' underscores are not valid at the beginning or end of a " +
                                    "'+' or '-' when using scientific notation.");
                        }
                    }
                    while (isDigit(ch)) {
                        buffer.append(ch);
                        prevNumericChar = ch;
                        nextCh();
                    }
                    if (prevNumericChar == '_') {
                        throw new RuntimeException("'_' underscores are not valid at the beginning or end of a " +
                                "'e' or 'E' when using scientific notation.");
                    }
                    if (ch == 'f' || ch == 'F') {
                        buffer.append(ch);
                        nextCh();
                        return new TokenInfo(FLOAT_LITERAL, buffer.toString(), line);
                    } else if (ch == 'd' || ch == 'D') {
                        buffer.append(ch);
                        nextCh();
                        return new TokenInfo(DOUBLE_LITERAL, buffer.toString(), line);
                    } else {
                        return new TokenInfo(DOUBLE_LITERAL, buffer.toString(), line);
                    }
                } else if (ch == 'f' || ch == 'F') {
                    buffer.append(ch);
                    nextCh();
                    return new TokenInfo(FLOAT_LITERAL, buffer.toString(), line);
                } else if (ch == 'd' || ch == 'D') {
                    buffer.append(ch);
                    nextCh();   
                    return new TokenInfo(DOUBLE_LITERAL, buffer.toString(), line);
                } else if (ch == 'l' || ch == 'L') {
                    buffer.append(ch);
                    nextCh();
                    return new TokenInfo(LONG_LITERAL, buffer.toString(), line);
                } else {
//                    if () {
//
//                    }
                    return new TokenInfo(INT_LITERAL, buffer.toString(), line);
                }
            default:
                if (isIdentifierStart(ch)) {
                    buffer = new StringBuffer();
                    while (isIdentifierPart(ch)) {
                        buffer.append(ch);
                        nextCh();
                    }
                    String identifier = buffer.toString();
                    if (reserved.containsKey(identifier)) {
                        return new TokenInfo(reserved.get(identifier), line);
                    } else {
                        return new TokenInfo(IDENTIFIER, identifier, line);
                    }
                } else {
                    reportScannerError("Unidentified input token: '%c'", ch);
                    nextCh();
                    return getNextToken();
                }
        }
    }

    /**
     * Returns true if an error has occurred, and false otherwise.
     *
     * @return true if an error has occurred, and false otherwise.
     */
    public boolean errorHasOccurred() {
        return isInError;
    }

    /**
     * Returns the name of the source file.
     *
     * @return the name of the source file.
     */
    public String fileName() {
        return fileName;
    }

    // Scans and returns an escaped character.
    private String escape() {
        switch (ch) {
            case 'b':
                nextCh();
                return "\\b";
            case 't':
                nextCh();
                return "\\t";
            case 'n':
                nextCh();
                return "\\n";
            case 'f':
                nextCh();
                return "\\f";
            case 'r':
                nextCh();
                return "\\r";
            case '"':
                nextCh();
                return "\\\"";
            case '\'':
                nextCh();
                return "\\'";
            case '\\':
                nextCh();
                return "\\\\";
            default:
                reportScannerError("Badly formed escape: \\%c", ch);
                nextCh();
                return "";
        }
    }

    // Advances ch to the next character from input, and updates the line number.
    private void nextCh() {
        line = input.line();
        try {
            ch = input.nextChar();
        } catch (Exception e) {
            reportScannerError("Unable to read characters from input");
        }
    }

    // Reports a lexical error and records the fact that an error has occurred. This fact can be
    // ascertained from the Scanner by sending it an errorHasOccurred message.
    private void reportScannerError(String message, Object... args) {
        isInError = true;
//        System.err.printf("%s:%d: error: ", fileName, line);
//        System.err.printf(message, args);
//        System.err.println();
    }

    // Returns true if the specified character is a digit (0-9), and false otherwise.
    private boolean isDigit(char c) {
        return (c >= '0' && c <= '9' || c =='_');
    }

    // Returns true if the specified character is a digit (0-9, a-f, or A-F), and false otherwise.
    private boolean isHexDigit(char c) {
        return (c >= '0' && c <= '9' || c >= 'a' && c <= 'f' || c >= 'A' && c <= 'F' || c == '_');
    }

    // Returns true if the specified character is a digit (0 or 1), and false otherwise.
    private boolean isBinaryDigit(char c) {
        return (c == '0' || c == '1' || c == '_');
    }

    private boolean isOctalDigit(char c) {
        return (c >= '0' && c <= '7' || c == '_');
    }

    // Returns true if the specified character is a whitespace, and false otherwise.
    private boolean isWhitespace(char c) {
        return (c == ' ' || c == '\t' || c == '\n' || c == '\f');
    }

    // Returns true if the specified character can start an identifier name, and false otherwise.
    private boolean isIdentifierStart(char c) {
        return (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_' || c == '$');
    }

    // Returns true if the specified character can be part of an identifier name, and false
    // otherwise.
    private boolean isIdentifierPart(char c) {
        return (isIdentifierStart(c) || isDigit(c));
    }
}

/**
 * A buffered character reader, which abstracts out differences between platforms, mapping all new
 * lines to '\n', and also keeps track of line numbers.
 */
class CharReader {
    // Representation of the end of file as a character.
    public final static char EOFCH = (char) -1;

    // The underlying reader records line numbers.
    private LineNumberReader lineNumberReader;

    // Name of the file that is being read.
    private String fileName;

    /**
     * Constructs a CharReader from a file name.
     *
     * @param fileName the name of the input file.
     * @throws FileNotFoundException if the file is not found.
     */
    public CharReader(String fileName) throws FileNotFoundException {
        lineNumberReader = new LineNumberReader(new FileReader(fileName));
        this.fileName = fileName;
    }

    /**
     * Scans and returns the next character.
     *
     * @return the character scanned.
     * @throws IOException if an I/O error occurs.
     */
    public char nextChar() throws IOException {
        return (char) lineNumberReader.read();
    }

    /**
     * Returns the current line number in the source file.
     *
     * @return the current line number in the source file.
     */
    public int line() {
        return lineNumberReader.getLineNumber() + 1; // LineNumberReader counts lines from 0
    }

    /**
     * Returns the file name.
     *
     * @return the file name.
     */
    public String fileName() {
        return fileName;
    }

    /**
     * Closes the file.
     *
     * @throws IOException if an I/O error occurs.
     */
    public void close() throws IOException {
        lineNumberReader.close();
    }
}
