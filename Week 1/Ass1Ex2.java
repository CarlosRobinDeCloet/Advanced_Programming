import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Ass1Ex2 
{
	// Find the highest sum using a quadratic implementation
	public static long findHighestSumLinear(int [] A)
	{
		// sum all elements of the array.
		long sumA = Arrays.stream(A).sum();

		int n = A.length;

		// initializes first function value.
		long S0 = 0;
		int i = 0;

		// computes first function value.
		for(int number: A){
			S0 += i * number;
			i++;
		}

		long bestSum = S0;

		// computes all following values of the sums of the shifted array.
		for(int j = 0; j < n; j++){

			// function to compute next value
			long S1 = S0 + sumA - n*A[n-1-j];

			// if function value is higher, update bestSum.
			if(S1>bestSum){
				bestSum = S1;
			}

			// sets computed value as new previous sum value used to compute the next sum.
			S0 = S1;
		}
		
		return bestSum;
	}
	
	// Find the highest sum using a linear implementation
	public static long findHighestSumQuadratic(int [] A)
	{
		long bestSum = -1;

		int k = A.length;

		// computes all sums by shifting elements of array.
		for(int i = 0; i < k; i++){

			// initializes new list
			long[] B = new long[k];
			int indice = 0;

			// shifts and adds values of A that are shifted out of the index of A as first elements in new list B.
			for( int j = k - i; j < k; j++){

				B[indice] = A[j];
				indice ++;
			}

			// shifts and adds remaining values of A to B
			for( int m = 0; m < k - i; m++){
				B[indice] = A[m];
				indice ++;
			}

			long sum = 0;

			// computes function value
			for(int n = 0; n < k; n++){
				sum += n*B[n];
			}

			// if function value is higher, update bestSum.
			if (sum > bestSum){
				bestSum = sum;
			}


		}
		
		return bestSum;
	}
	
	public static void main(String [] args)
	{

		// n, the length of the array A
		int nIntegers = 1000;
		int nRuns = 5;
		
		for(int i = 0; i < nRuns; i++)
		{
			// We randomly generate a sequence of integers
			int [] A = generateInput(nIntegers);
			nIntegers *= 2;
			
			// We apply the quadratic algorithm
			long tic = System.currentTimeMillis();
			long simpleSum = findHighestSumQuadratic(A);
			long toc = System.currentTimeMillis();
			long simpleTime = toc - tic;
			
			// We apply the linear algorithm
			tic = System.currentTimeMillis();
			long advancedSum = findHighestSumLinear(A);
			toc = System.currentTimeMillis();
			long advancedTime = toc - tic;
			
			// We check whether the algorithms give the same result. If so, we provide some output
			if(advancedSum != simpleSum) 
			{
				System.out.println("The sums do not match!");
			}
			else
			{
				System.out.println(nIntegers + "\t" + simpleSum + "\t" + simpleTime + "\t" + advancedTime); 
			}
		}
	}
	
	private static int [] generateInput(int nIntegers)
	{
		Random random = new Random(1); 
		
		int [] input = new int[nIntegers];
		for(int i = 0; i < nIntegers; i++)
		{
			input[i] = random.nextInt(2000);
		}
		return input;
	}
}
