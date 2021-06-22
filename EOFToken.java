package edu.aydin.sda.frontent;

public class EOFToken extends Token{

	EOFToken(ProgramText source) {
		super(source);
		extract();
	}
	
	protected void extract() {
		
		type = TokenType.END_OF_FILE;
	}

}
