package cpsc_476_Project1;

import java.util.Random;

public class ShortURL {

	public static final String ALPHABET = "23456789bcdfghjkmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ-_";
	public static final int BASE = ALPHABET.length();
	
	public static String generateRandom()
	{
		int number = new Random().nextInt(999999);
		String str="";
		if(number > 100)
		{
			str = encode(number);
		}
		else
			generateRandom();
		return str;
		
	}
	
	
	public static String encode(int num) {
		StringBuilder str = new StringBuilder();
		while (num > 0) {
			str.insert(0, ALPHABET.charAt(num % BASE));
			num = num / BASE;
		}
		return str.toString();
	}
	
	public static int decode(String str) {
		int num = 0;
		for (int i = 0; i < str.length(); i++) {
			num = num * BASE + ALPHABET.indexOf(str.charAt(i));
		}
		return num;
	}

}

