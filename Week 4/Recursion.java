public class Recursion 
{
	public static boolean isPalindrome(String word)
	{
		// Checks how long the word is, if lenght equals one it is a palindrome. If length is 2 checks if both characters are equal.
		int amountChar = word.length();
		if(amountChar == 1){
			return true;
		}

		if(amountChar == 2){
			if(word.charAt(0) == word.charAt(1)){
				return true;
			}
		}

		// If first and last characters are equal the method calls itself without the first and last character.
		if(word.charAt(0) == word.charAt(amountChar - 1)){
			word = word.substring(1,amountChar-1);
			return isPalindrome(word);
		}

		// If first and last character are not equal returns false.
		return false;
	}
	
	public static double power(double x, int n)
	{
		if(n == 1){
			return x;
		}

		if(n % 2 == 0){
			return power( x, n/2) * power(x,n/2);
		} else {
			return x*power(x,(n-1)/2) * power(x,(n-1)/2);
		}
	}
	
	public static void main(String [] args)
	{
		System.out.println(isPalindrome("racecar"));
		System.out.println(isPalindrome("not a palindrome"));
		
		System.out.println(power(1.33,20));
		System.out.println(power(2,9));
	}
}
