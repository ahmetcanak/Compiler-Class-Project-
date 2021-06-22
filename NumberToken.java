package edu.aydin.sda.frontent;

public class NumberToken extends Token {

	NumberToken(ProgramText source) {
		super(source);
		extract();
	}

	protected void extract() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(curChar());

		char curChar = nextChar();

		while (Character.isDigit(curChar)) {
			buffer.append(curChar);
			curChar = nextChar();
		}
		text = buffer.toString();

		type = TokenType.INTEGER;

	}
}
