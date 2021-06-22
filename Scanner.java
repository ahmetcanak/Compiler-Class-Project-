package edu.aydin.sda.frontent;

public class Scanner {
	private Token token;
	private ProgramText source;

	Scanner(ProgramText source) {
		this.source = source;
	}

	Token nextToken() {
		Token token = null;
		char ch = source.curChar();

		while (Character.isWhitespace(ch))
			ch = source.nextChar();

		if (ch == ProgramText.EOF) {
			return new EOFToken(null);
		} else if (Character.isDigit(ch)) {
			token = new NumberToken(source);
		} else if (Character.isLetter(ch)) {
			token = new IdentifierToken(source);
		} else if (Character.isLetter(ch)) {
			token = new SpecialToken(source);
		} else if (TokenType.SPECIAL_SYMBOLS.containsKey(Character.toString(ch))) {
			token = new SpecialToken(source);
		}
		return token;
	}

}
