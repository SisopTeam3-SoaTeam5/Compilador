package lyc.compiler;

import java_cup.runtime.Symbol;
import lyc.compiler.ParserSym;
import lyc.compiler.files.SymbolInfo;import lyc.compiler.model.*;
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


LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
Identation =  [ \t\f]
Comma =  ","
SemiColon = ";"

Plus = "+"
Mult = "*"
Sub = "-"
Div = "/"
Assig = ":"
OpenBracket = "("
CloseBracket = ")"
SquareOpenBracket = "["
SquareCloseBracket = "]"
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
AND = "&&"
OR = "||"

Operator = {GreaterThan} | {LessThan} | {GreaterOrEqual} | {LessOrEqual} | {NotEqual} | {Equal} | {Plus} | {Mult} | {Sub} | {Div} | {Assig} | {Not}

WhiteSpace = {LineTerminator} | {Identation}
Identifier = {Letter} ({Letter}|{Digit})*
IntegerConstant = {Digit}+
FloatConstant = {Digit}+"."{Digit}+
Content =  ({WhiteSpace} | {Letter} | {IntegerConstant} | {FloatConstant} | {Operator} | {OpenBracket} | {CloseBracket} )*
StringConstant = "\"" {Content} "\""
Comment = "/*" {Content} "*/"
%%


<YYINITIAL>{
    /*Keywords*/
    "init"                                  {return symbol(ParserSym.INIT);}
    "Integer"                               {
                                                return symbol(ParserSym.INTEGER);
                                            }
    "Float"                                 {
                                                return symbol(ParserSym.FLOAT);
                                            }
    "String"                                {
                                                return symbol(ParserSym.STRING);
                                            }
    "if"                                    {return symbol(ParserSym.IF);}
    "else"                                  {return symbol(ParserSym.ELSE);}
    "while"                                 {return symbol(ParserSym.WHILE);}
    "write"                                 {return symbol(ParserSym.WRITE);}
    "read"                                  {return symbol(ParserSym.READ);}
    "DO"                                    {return symbol(ParserSym.DO);}
    "ENDDO"                                 {return symbol(ParserSym.ENDDO);}
    "CASE"                                  {return symbol(ParserSym.CASE);}
    "DEFAULT"                               {return symbol(ParserSym.DEFAULT);}
    "AllEqual"                              {return symbol(ParserSym.ALLEQUAL);}

    /* identifiers */
    {Identifier}                             {
                                                        String s = new String(yytext()); //no me dejaba hacer yytext().lenght de una
                                                        if(s.length()<=20){
                                                            return symbol(ParserSym.IDENTIFIER, yytext());
                                                            }
                                                        else throw  new InvalidLengthException("Identificadores deben tener 20 caracteres o menos"); }
    /* Constants */
    {IntegerConstant}                        {
                                                Long i = new Long(yytext());
                                                System.out.println("numero: " + yytext());
                                                if(Long.toBinaryString(i).toString().length() <= 16)
                                                    return symbol(ParserSym.INTEGER_CONSTANT, yytext());
                                                else throw new InvalidIntegerException("Constantes Integer deben tener 16 bits o menos"); }
    {FloatConstant}                          {  int bits = Float.floatToIntBits(new Float(yytext()));
                                                if(Integer.toBinaryString(bits).length() <= 32) //chequear esto, esta medio fafafa
                                                    return symbol(ParserSym.FLOAT_CONSTANT, yytext());
                                                else throw  new RuntimeException("Constantes Float deben tener 32 bits o menos"); }
    {StringConstant}                         {  String s = new String(yytext()); //no me dejaba hacer yytext().lenght de una
                                                if(s.length()<=42) // 42 para no contar ambas comillas ""
                                                    return symbol(ParserSym.STRING_CONSTANT, yytext());
                                                else throw new InvalidLengthException("Constantes String tienen que tener longitud menor o igual a 40 caracteres");
                                             }

    /* operators */
    {Plus}                                    { return symbol(ParserSym.PLUS); }
    {Sub}                                     { System.out.println("entre al menos");return symbol(ParserSym.SUB); }
    {Mult}                                    { return symbol(ParserSym.MULT); }
    {Div}                                     { return symbol(ParserSym.DIV); }
    {Assig}                                   { return symbol(ParserSym.ASSIG); }
    {SemiColon}                               { return symbol(ParserSym.SEMICOLON);}
    {OpenBracket}                             { return symbol(ParserSym.OPEN_BRACKET); }
    {CloseBracket}                            { return symbol(ParserSym.CLOSE_BRACKET); }
    {SquareOpenBracket}                       { return symbol(ParserSym.SQUARE_OPEN_BRACKET); }
    {SquareCloseBracket}                      { return symbol(ParserSym.SQUARE_CLOSE_BRACKET); }
    {GreaterThan}                             { return symbol(ParserSym.GREATER_THAN); }
    {LessThan}                                { return symbol(ParserSym.LESS_THAN); }
    {GreaterOrEqual}                          { return symbol(ParserSym.GREATER_OR_EQUAL); }
    {LessOrEqual}                             { return symbol(ParserSym.LESS_OR_EQUAL); }
    {NotEqual}                                { return symbol(ParserSym.NOT_EQUAL); }
    {Equal}                                   { return symbol(ParserSym.EQUAL); }
    {Not}                                     { return symbol(ParserSym.NOT); }
    {AND}                                     { return symbol(ParserSym.AND); }
    {OR}                                      { return symbol(ParserSym.OR); }
    {OpenBlock}                               { return symbol(ParserSym.OPEN_BLOCK); }
    {CloseBlock}                              { return symbol(ParserSym.CLOSE_BLOCK); }
    {Comma}                                   { return symbol(ParserSym.COMMA); }
    {Comment}                                 { /* ignore comments */ } //Parece como que no funciona ignorar para ninguno de los dos casos
    {WhiteSpace}                              { /* ignore whitespaces */ }
     .                                        {throw new UnknownCharacterException(yytext());}
}