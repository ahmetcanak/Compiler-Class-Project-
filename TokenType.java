package edu.aydin.sda.frontent;

import java.util.HashMap;
import java.util.HashSet;

public enum TokenType {
	LEFT_CURLY("{"), RIGHT_CURLY("}"), LEFT_PAR("("), RIGHT_PAR(")"), EQUAL("="), PLUS("+"), SEMI_COLON(";"),
	LESS_THAN("<"), MORE_THAN(">"), LESS_THANEQ("<="), MORE_THANEQ(">="), ISEQUAL("=="), NOTEQUAL("!="), MINUS("-"),
	MULTIPLE("*"), DIVIDE("/"),

	WHILE, ELSE, IF, BEGIN, ENDWHILE, ENDIF, THEN, IN, OUT, END,

	IDENTIFIER, END_OF_FILE, INTEGER;

	private String text;

	TokenType(String text) {
		this.text = text;
	}

	TokenType() {
		this.text = this.toString().toLowerCase();
	}

	static final int SPECIAL_SYMBOL_BEG_INDEX = LEFT_CURLY.ordinal();
	static final int SPECIAL_SYMBOL_END_INDEX = DIVIDE.ordinal();

	static final int KEYWORD_BEG_INDEX = WHILE.ordinal();
	static final int KEYWORD_END_INDEX = INTEGER.ordinal();

	public static HashSet<String> KEYWORDS = new HashSet<String>();
	static {
		TokenType[] values = TokenType.values();
		for (int i = KEYWORD_BEG_INDEX; i <= KEYWORD_END_INDEX; i++)
			KEYWORDS.add(values[i].text);
	}

	public static HashMap<String, TokenType> SPECIAL_SYMBOLS = new HashMap<String, TokenType>();
	static {
		TokenType[] values2 = TokenType.values();
		for (int i = SPECIAL_SYMBOL_BEG_INDEX; i <= SPECIAL_SYMBOL_END_INDEX; i++)
			SPECIAL_SYMBOLS.put(values2[i].text, values2[i]);

	}
}
