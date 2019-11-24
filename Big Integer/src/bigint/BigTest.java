package bigint;

import java.io.IOException;
import java.util.Scanner;

public class BigTest {

	static Scanner sc;

	public static void parse() throws IOException {
		System.out.print("\tEnter integer => ");
		String integer = sc.nextLine();
		try {
			BigInteger bigInteger = BigInteger.parse(integer);
			System.out.println("\t\tValue = " + bigInteger);
		} catch (IllegalArgumentException e) {
			System.out.println("\t\tIncorrect Format");
		}
	}

	public static void add() throws IOException {
		
		  System.out.print("\tEnter first integer => "); String integer =
		  sc.nextLine(); BigInteger firstBigInteger = BigInteger.parse(integer);
		  
		  System.out.print("\tEnter second integer => "); integer = sc.nextLine();
		  BigInteger secondBigInteger = BigInteger.parse(integer);
		  
		  BigInteger result = BigInteger.add(firstBigInteger,secondBigInteger);
		  System.out.println("\t\tSum: " + result);
		 
/*
		boolean success = true;
		for (int i = -15000; i <= 15000; i++) {
			for (int j = -15000; j <= 15000; j++) {
				int summ = i + j;
				int x = i;
				int y = j;
				BigInteger a = BigInteger.parse(Integer.toString(i));
				BigInteger b = BigInteger.parse(j + "");
				BigInteger result = BigInteger.add(a, b);
				if (result.toString().compareTo(summ + "") != 0) {
					success = false;
					System.out.println("MY compare: " + result.toString().compareTo(summ + ""));
					System.out.println("MY bigint: " + result);
					System.out.println("MY sum: " + summ);
					System.out.println(x + " " + y);
				}
			}
		}
		if (success) {
			System.out.println("All test cases passed");
		} else {
			System.out.println("Failed");
		}
		*/
	}

	public static void multiply() throws IOException {
		/*
		 * System.out.print("\tEnter first integer => "); String integer =
		 * sc.nextLine(); BigInteger firstBigInteger = BigInteger.parse(integer);
		 * 
		 * System.out.print("\tEnter second integer => "); integer = sc.nextLine();
		 * BigInteger secondBigInteger = BigInteger.parse(integer);
		 * 
		 * BigInteger result = BigInteger.multiply(firstBigInteger,secondBigInteger);
		 * System.out.println("\t\tProduct: " + result);
		 */

		boolean success = true;
		for (int i = -10000; i <= 10000; i++) {
			for (int j = -10000; j <= 10000; j++) {
				int summ = i * j;
				int x = i;
				int y = j;
				BigInteger a = BigInteger.parse(Integer.toString(i));
				BigInteger b = BigInteger.parse(j + "");
				BigInteger result = BigInteger.multiply(a, b);
				if (result.toString().compareTo(summ + "") != 0) {
					success = false;
					System.out.println("MY compare: " + result.toString().compareTo(summ + ""));
					System.out.println("MY bigint: " + result);
					System.out.println("MY sum: " + summ);
					System.out.println(x + " " + y);
				}
			}
		}
		if (success) {
			System.out.println("All test cases passed");
		} else {
			System.out.println("Failed");
		}

	}

	public static void main(String[] args) throws IOException {

		// TODO Auto-generated method stub
		sc = new Scanner(System.in);

		char choice;
		while ((choice = getChoice()) != 'q') {
			switch (choice) {
			case 'p':
				parse();
				break;
			case 'a':
				add();
				break;
			case 'm':
				multiply();
				break;
			default:
				System.out.println("Incorrect choice");
			}
		}
	}

	private static char getChoice() {
		System.out.print("\n(p)arse, (a)dd, (m)ultiply, or (q)uit? => ");
		String in = sc.nextLine();
		char choice;
		if (in == null || in.length() == 0) {
			choice = ' ';
		} else {
			choice = in.toLowerCase().charAt(0);
		}
		return choice;
	}

}
