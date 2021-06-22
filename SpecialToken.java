package edu.aydin.sda.frontent;

public class SpecialToken extends Token {

	SpecialToken(ProgramText source) {
		super(source);
		extract();
	}

	
	
	protected void extract() {
		char curChar = curChar();
		
		text = Character.toString(curChar);
		type = null;
		if(curChar == '{' || curChar == '}' || curChar == '+' || curChar == '-' || curChar == '(' || curChar == ')'
				|| curChar == '=' || curChar == '<' || curChar == ';' || curChar == '*' || curChar == '/' || curChar == '>'
				)
			nextChar();
		type = TokenType.SPECIAL_SYMBOLS.get(text);
	}

}
