package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// classe para testar expressões regulares
public class ERPlay {

	public static void main(String[] args) {

		Pattern pattern = Pattern.compile("fala[r!]?");
		String text = "o rato que falava queria falar que a vaca fala a palavra fala!";
		Matcher matcher = pattern.matcher(text);
		System.out.println("###");
		while (matcher.find())
			System.out.print(matcher.group() + "|");
		System.out.println();
		
		pattern = Pattern.compile("fala[^r!]"); 
		matcher = pattern.matcher(text);
		System.out.println("###");
		while (matcher.find())
			System.out.print(matcher.group() + "|");
		System.out.println();

		pattern = Pattern.compile("fala[^r!]?"); // e agora? 
		matcher = pattern.matcher(text);
		System.out.println("###");
		while (matcher.find())
			System.out.print(matcher.group() + "|"); // falav|fala|fala |fala|
		System.out.println();
		
		pattern = Pattern.compile("aaa.*bbb"); // gulodice do *
		System.out.println("###");
		matcher = pattern.matcher("aaa123123123bbb");
		matcher.find();
		System.out.print(matcher.group() + " ");
		matcher = pattern.matcher("aaa123123123bbbbbb");
		matcher.find();
		System.out.print(matcher.group() + " ");
		matcher = pattern.matcher("aaa123123123bbba");
		matcher.find();
		System.out.print(matcher.group() + " ");
		

	}

}
