import java.util.Random;

public class Ass1Ex1
{
    public static int findLinear(int [] A, int a)
    {
        int index = -1;

        for(int i = 0; i < A.length; i++){

            if( A[i] == a){
                index = i;
            }
        }

        return index;
    }

    public static int findLogarithmic(int [] A, int a)
    {

        int index = -1;

        int minIndex = 0;
        int maxIndex = A.length-1;

        // only keep searching if a valid index can be found
        while(minIndex<=maxIndex){

            // finds middle index
            int i = ((maxIndex + minIndex) / 2);

            // returns index if value is found
            if (A[i] == a){
                index = i;
                return index = i;
            }

            // sets maxIndex lower than middle index since a is in the upper half of the array.
            if (a < A[i]){
                maxIndex = i-1;
            }

            // sets minIndex higher than middle index since a is in the upper half of the array.
            if (a > A[i]){
                minIndex = i+1;
            }
        }

        return index;
    }


    public static void main(String [] args)
    {
        Random random = new Random(1234);


        // n, the number of items in the array
        int lengthA = 10000;
        // We call the function (A, a) several times, to get a measurable computation time
        int nRepititions = 100000;



        for(int i = 0; i <= 4; i++)
        {
            // We generate the array A
            int [] A = generateInput(lengthA);
            int upperBound = A[A.length - 1] + 10;

            // Generate an array of values to find
            int [] values = new int [nRepititions];
            for(int j = 0; j < values.length; j++)
            {
                values[j] = (int) (random.nextDouble() * upperBound);
            }

            // Measure the computation time of the linear algorithm
            long tic = System.currentTimeMillis();
            int nHitsLinear = 0;
            for(int j = 0; j < values.length; j++)
            {
                int index = findLinear(A, values[j]);
                if(index != -1)
                {
                    nHitsLinear++;
                }
            }

            long toc = System.currentTimeMillis();
            long timeLinear = (toc - tic);

            // Measure the computation time of the logarithmic algorithm
            tic = System.currentTimeMillis();
            int nHitsLogarithmic = 0;

            for(int j = 0; j < values.length; j++)
            {

                int index = findLogarithmic(A, values[j]);

                if(index != -1)
                {
                    nHitsLogarithmic++;

                }
            }
            toc = System.currentTimeMillis();
            long timeLogarithmic = (toc - tic);

            if(nHitsLinear == nHitsLogarithmic)
            {
                System.out.println(lengthA + "\t" + nHitsLinear + "\t" + timeLinear +  "\t" + timeLogarithmic);
            }
            else
            {
                System.out.println("The numbers of hits do not match for the two algorithms.");
                System.out.println(nHitsLinear);
                System.out.println(nHitsLogarithmic);
            }
            lengthA *= 2;
        }
    }

    private static int [] generateInput(int nIntegers)
    {
        Random random = new Random(1);

        int current = 0;
        int [] input = new int[nIntegers];
        for(int j = 0; j < nIntegers; j++)
        {
            current += (1 + random.nextInt(9));
            input[j] = current;
        }

        return input;
    }
}
