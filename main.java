package edu.aydin.sda.frontent;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(new ProgramText());
		Parser p = new Parser(sc);
		

		p.parse();

	}

}
