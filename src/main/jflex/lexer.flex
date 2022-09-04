package lyc.compiler;

import java_cup.runtime.Symbol;
import lyc.compiler.ParserSym;
import lyc.compiler.model.*;
import static lyc.compiler.constants.Constants.*;

%%

%public
%class Lexer
%unicode
%cup
%line
%column
%throws CompilerException
%eofval{
  return symbol(ParserSym.EOF);
%eofval}


%{
  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }
%}


LineTerminator = \r|\n|\r\n|";"
InputCharacter = [^\r\n]
Identation =  [ \t\f]
Comma =  ","

Plus = "+"
Mult = "*"
Sub = "-"
Div = "/"
Assig = ":"
OpenBracket = "("
CloseBracket = ")"
OpenBlock= "{"
CloseBlock= "}"
Letter = [a-zA-Z]
Digit = [0-9]

GreaterThan = ">"
LessThan = "<"
GreaterOrEqual = ">="
LessOrEqual = "<="
NotEqual = "!="
Equal = "=="
Not = "!"

Operator = {GreaterThan} | {LessThan} | {GreaterOrEqual} | {LessOrEqual} | {NotEqual} | {Equal} | {Plus} | {Mult} | {Sub} | {Div} | {Assig} | {Not}

WhiteSpace = {LineTerminator} | {Identation}
Identifier = {Letter} ({Letter}|{Digit})*
IntegerConstant = {Digit}+
FloatConstant = {Digit}+"."{Digit}+
StringConstant = "\"" ({WhiteSpace} | {Letter} | {IntegerConstant} | {FloatConstant} | {Operator} | {OpenBracket} | {CloseBracket} )* "\""

%%


<YYINITIAL>{
    /*Keywords*/
    "DECVAR"                                {return symbol(ParserSym.DECVAR);}
    "ENDDEC"                                {return symbol(ParserSym.ENDDEC);}
    "Integer"                               {return symbol((ParserSym.INTEGER));}
    "Float"                                 {return symbol((ParserSym.FLOAT));}
    "if"                                    {return symbol(ParserSym.IF);}
    "else"                                  {return symbol(ParserSym.ELSE);}
    "while"                                 {return symbol((ParserSym.WHILE));}
    "write"                                 {return symbol(ParserSym.WRITE);}

    /* identifiers */
    {Identifier}                             { return symbol(ParserSym.IDENTIFIER, yytext()); }
    /* Constants */
    {IntegerConstant}                        { return symbol(ParserSym.INTEGER_CONSTANT, yytext()); }
    {FloatConstant}                          { return symbol(ParserSym.FLOAT_CONSTANT, yytext()); }
    {StringConstant}                         { return symbol(ParserSym.STRING_CONSTANT, yytext()); }

    /* operators */
    {Plus}                                    { return symbol(ParserSym.PLUS); }
    {Sub}                                     { return symbol(ParserSym.SUB); }
    {Mult}                                    { return symbol(ParserSym.MULT); }
    {Div}                                     { return symbol(ParserSym.DIV); }
    {Assig}                                   { return symbol(ParserSym.ASSIG); }
    {OpenBracket}                             { return symbol(ParserSym.OPEN_BRACKET); }
    {CloseBracket}                            { return symbol(ParserSym.CLOSE_BRACKET); }
    {GreaterThan}                             { return symbol(ParserSym.GREATER_THAN); }
    {LessThan}                                { return symbol(ParserSym.LESS_THAN); }
    {GreaterOrEqual}                          { return symbol(ParserSym.GREATER_OR_EQUAL); }
    {LessOrEqual}                             { return symbol(ParserSym.LESS_OR_EQUAL); }
    {NotEqual}                                { return symbol(ParserSym.NOT_EQUAL); }
    {Equal}                                   { return symbol(ParserSym.EQUAL); }
    {Not}                                     { return symbol(ParserSym.NOT); }
    {OpenBlock}                               { return symbol(ParserSym.OPEN_BLOCK); }
    {CloseBlock}                              { return symbol(ParserSym.CLOSE_BLOCK); }
    {LineTerminator}                          { return symbol(ParseSym.LINE_TERMINATOR); }
    {Comma}                                   { return symbol(ParseSym.COMMA); }
}