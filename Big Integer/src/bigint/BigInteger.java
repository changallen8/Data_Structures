package bigint;

/**
 * This class encapsulates a BigInteger, i.e. a positive or negative integer
 * with any number of digits, which overcomes the computer storage length
 * limitation of an integer.
 * 
 */
public class BigInteger {

	/**
	 * True if this is a negative integer
	 */
	boolean negative;

	/**
	 * Number of digits in this integer
	 */
	int numDigits;

	/**
	 * Reference to the first node of this integer's linked list representation
	 * NOTE: The linked list stores the Least Significant Digit in the FIRST node.
	 * For instance, the integer 235 would be stored as: 5 --> 3 --> 2
	 * 
	 * Insignificant digits are not stored. So the integer 00235 will be stored as:
	 * 5 --> 3 --> 2 (No zeros after the last 2)
	 */
	DigitNode front;

	/**
	 * Initializes this integer to a positive number with zero digits, in other
	 * words this is the 0 (zero) valued integer.
	 */
	public BigInteger() {
		negative = false;
		numDigits = 0;
		front = null;
	}

	/**
	 * Parses an input integer string into a corresponding BigInteger instance. A
	 * correctly formatted integer would have an optional sign as the first
	 * character (no sign means positive), and at least one digit character
	 * (including zero). Examples of correct format, with corresponding values
	 * Format Value +0 0 -0 0 +123 123 1023 1023 0012 12 0 0 -123 -123 -001 -1 +000
	 * 0
	 * 
	 * Leading and trailing spaces are ignored. So " +123 " will still parse
	 * correctly, as +123, after ignoring leading and trailing spaces in the input
	 * string.
	 * 
	 * Spaces between digits are not ignored. So "12 345" will not parse as an
	 * integer - the input is incorrectly formatted.
	 * 
	 * An integer with value 0 will correspond to a null (empty) list - see the
	 * BigInteger constructor
	 * 
	 * @param integer Integer string that is to be parsed
	 * @return BigInteger instance that stores the input integer.
	 * @throws IllegalArgumentException If input is incorrectly formatted
	 */
	public static BigInteger parse(String integer) throws IllegalArgumentException {

		BigInteger bigNumber = new BigInteger();

		integer = integer.trim();

		bigNumber.negative = false;
		

		int i = 0;
		if (integer.charAt(0) == '-') {
			bigNumber.negative = true;
			i = 1;
		} else if (integer.charAt(0) == '+') {
			bigNumber.negative = false;
			i = 1;
		}
		
	
			
		// CHECK POSSIBLE ERRORSf
		// CHECK FOR LEADING SPACES
		// CHECK FOR LEADING ZEROES
		// CHECK IF NUM IS ZERO
		while (i < integer.length()) {
			char x = integer.charAt(i);
			if (Character.isDigit(x) == false) {
				throw new IllegalArgumentException("Incorrect Format");
			}
			int y = Character.getNumericValue(x);
			if (x == '0' && bigNumber.front == null) {
				i++;
			} else {
				bigNumber.front = new DigitNode(y, bigNumber.front);
				i++;
				bigNumber.numDigits++;
			}
		}
		if(bigNumber.negative == false) {
		System.out.println(bigNumber.numDigits + "not negative");
		}
		else System.out.println(bigNumber.numDigits + "negative");
		return bigNumber;
	}

	/**
	 * Adds the first and second big integers, and returns the result in a NEW
	 * BigInteger object. DOES NOT MODIFY the input big integers.
	 * 
	 * NOTE that either or both of the input big integers could be negative. (Which
	 * means this method can effectively subtract as well.)
	 * 
	 * @param first  First big integer
	 * @param second Second big integer
	 * @return Result big integer
	 */

	public static BigInteger add(BigInteger first, BigInteger second) {

		BigInteger tmp = new BigInteger();

		if (first.front == null) {
			tmp = second;
			return tmp;
		} else if (second.front == null) {
			tmp = first;
			return tmp;
		}

		int carry = 0;
		int sum = 0;
		int count = 0;

		if ((first.negative == false && second.negative == false) || (first.negative && second.negative)) {
			DigitNode ptr1 = first.front;
			DigitNode ptr2 = second.front;
			DigitNode tmpPtr = null;
			DigitNode tmpPtr2 = new DigitNode(0, null);
			if (first.negative)
				tmp.negative = true;
			while (ptr1 != null || ptr2 != null) {

				sum = carry;
				if (ptr1 != null) {
					sum = ptr1.digit + sum;
					ptr1 = ptr1.next;

				}
				if (ptr2 != null) {
					sum = ptr2.digit + sum;
					ptr2 = ptr2.next;
				}

				if (sum > 9)
					carry = 1;
				else
					carry = 0;

				sum = sum % 10;

				tmpPtr = new DigitNode(sum, null);
				tmpPtr2.next = tmpPtr;
				tmpPtr2 = tmpPtr;
				tmp.numDigits++;
				if (count == 0) {
					tmp.front = tmpPtr2;
					count++;
				}

				if (carry == 1 && ptr1 == null && ptr2 == null) {
					tmpPtr = new DigitNode(1, null);
					tmpPtr2.next = tmpPtr;
					tmp.numDigits++;
				}

			}
		} else {

			BigInteger first1 = new BigInteger();
			BigInteger second2 = new BigInteger();
			second2 = second;
			first1 = first;
			if (first.numDigits < second.numDigits) {
				second2 = first;
				first1 = second;
			}
			int x = first.numDigits;
			if (first.numDigits == second.numDigits) {
				DigitNode j = first.front;
				DigitNode k = second.front;
				for (int i = 1; i <= x; i++) {
					if (i == x) {
						if (j.digit == k.digit) {
							if (x == 1) {
								tmp.front = null;
								return tmp;
							}
							i = 0;
							x--;
							j = first.front;
							k = second.front;
						} else if (j.digit > k.digit) {
						} else {
							second2 = first;
							first1 = second;
						}
					}
					if (i != 0) {
						j = j.next;
						k = k.next;
					}
				}
			}

			if (second2.negative)
				tmp.negative = false;
			else
				tmp.negative = true;

			DigitNode ptr1 = first1.front;
			DigitNode ptr2 = second2.front;
			DigitNode tmpPtr = null;
			DigitNode tmpPtr2 = new DigitNode(0, null);

			while (ptr1 != null || ptr2 != null) {

				if (ptr2 != null) {
					if ((ptr1.digit < ptr2.digit) || ((ptr1.digit == ptr2.digit) && carry == -1)) {

						sum = ptr1.digit - ptr2.digit + 10 + carry;
						ptr1 = ptr1.next;
						ptr2 = ptr2.next;
						carry = -1;
					} else {

						sum = ptr1.digit - ptr2.digit + carry;
						ptr1 = ptr1.next;
						ptr2 = ptr2.next;
						carry = 0;
					}
				} else {
					if (ptr1.digit == 0 && carry == -1) {
						sum = 9;
						carry = -1;
						ptr1 = ptr1.next;
					} else {
						sum = ptr1.digit + carry;
						ptr1 = ptr1.next;
						carry = 0;
					}
				}

				sum = sum % 10;

				tmpPtr = new DigitNode(sum, null);
				tmpPtr2.next = tmpPtr;
				tmpPtr2 = tmpPtr;
				tmp.numDigits++;

				if (count == 0) {
					tmp.front = tmpPtr2;
					count++;
				}
			}

		}

		/* IMPLEMENT THIS METHOD */

		// following line is a placeholder for compilation
		DigitNode tmpPtr3 = tmp.front;
		DigitNode lag3 = null;
		while (tmpPtr3 != null) {
			if (tmpPtr3.next == null) {
				if (tmpPtr3.digit == 0) {
					lag3.next = null;
					tmpPtr3 = tmp.front;
					tmp.numDigits--;
				} else
					break;
			} else {
				lag3 = tmpPtr3;
				tmpPtr3 = tmpPtr3.next;
			}
		}

		return tmp;

	}

	/**
	 * Returns the BigInteger obtained by multiplying the first big integer with the
	 * second big integer
	 * 
	 * This method DOES NOT MODIFY either of the input big integers
	 * 
	 * @param first  First big integer
	 * @param second Second big integer
	 * @return A new BigInteger which is the product of the first and second big
	 *         integers
	 */
	public static BigInteger multiply(BigInteger first, BigInteger second) {

		BigInteger mult = new BigInteger();
		BigInteger first1 = first;
		BigInteger second2 = second;

		if (first.front == null || second.front == null) {
			mult.front = null;
			return mult;
		}

		/* IMPLEMENT THIS METHOD */
		if (first.numDigits < second.numDigits) {
			second2 = first;
			first1 = second;
		}

		DigitNode ptr1 = first1.front;
		DigitNode ptr2 = second2.front;

		int m = (ptr1.digit * ptr2.digit) % 10;
		int carry = (ptr1.digit * ptr2.digit) / 10;
		mult.front = new DigitNode(m, null);
		mult.numDigits++;
		ptr1 = ptr1.next;

		DigitNode tmpPtr = mult.front;
		DigitNode tmpPtr2 = mult.front;

		if (first1.negative != second2.negative)
			mult.negative = true;
		else
			mult.negative = false;

		while (ptr2 != null) {
			while (ptr1 != null) {

				m = ptr1.digit * ptr2.digit + carry;
				carry = m / 10;
				m = m % 10;

				if (tmpPtr.next == null) {
					tmpPtr.next = new DigitNode(m, null);
					mult.numDigits++;
				} else {
					carry += (tmpPtr.next.digit + m) / 10;
					tmpPtr.next.digit = (tmpPtr.next.digit + m) % 10;
				}
				tmpPtr = tmpPtr.next;
				ptr1 = ptr1.next;
			}
			if (carry != 0) {
				tmpPtr.next = new DigitNode(carry, null);
				mult.numDigits++;
				carry = 0;
			}
			ptr1 = first1.front;
			ptr2 = ptr2.next;
			tmpPtr = tmpPtr2;
			tmpPtr2 = tmpPtr2.next;

		}
		// following line is a placeholder for compilation
		
		

		return mult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (front == null) {
			return "0";
		}
		String retval = front.digit + "";
		for (DigitNode curr = front.next; curr != null; curr = curr.next) {
			retval = curr.digit + retval;
		}

		if (negative) {
			retval = '-' + retval;
		}
		return retval;
	}
}
