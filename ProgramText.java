package edu.aydin.sda.frontent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ProgramText {

	private String progText;
	private int curPos;
	public static char EOF = 0;

	ProgramText() {
		curPos = -1;

		try {
			progText = readWholeProgram();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String readWholeProgram() throws IOException {
		return new String(Files.readAllBytes(Paths.get("program1.txt")));
	}

	char curChar() {
		if (curPos == -1)
			curPos++;
		if (curPos == progText.length())
			return EOF;
		return progText.charAt(curPos);
	}

	char nextChar() {
		curPos++;
		if (curPos == progText.length())
			return EOF;

		return progText.charAt(curPos);
	}

}
