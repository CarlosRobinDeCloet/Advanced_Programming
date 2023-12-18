public class DynamicArray 
{	
	// The initial capacity of the dynamic array is 16.
	private static final int InitialiCapacity = 16;
	// The size of the dynamic array; the number of elements it contains.
	private int size;
	// The elements in the dynamic array are stored in an array data
	private int[] data;
	
	// Constructors
	public DynamicArray(int capacity)
	{
		data = new int[capacity];
		size = 0;
	}
	public DynamicArray()
	{
		this(InitialiCapacity);
	}
	
	// The size of the dynamic array
	public int size()
	{
		return size;
	}
	
	// A method to add a value at the end of the dynamic array
	public void add(int value)
	{
		if( this.data.length <= this.size){

			// copies the data and makes a new list twice as large.
			int[] copy = this.data;
			this.data = new int[2*this.data.length];

			// copies all elements to the new list.
			int j = 0;
			for(int ele : copy){
				this.set(j, ele);
				j++;
			}
		}

		// adds new value to the list.
		this.set(size, value);
		this.size++;
	}
	
	// A method to insert a value at a given position in the dynamic array
	public void insert(int index, int value)
	{
		// adds last element to the list.
		this.add( this.get(this.size-1));

		// shifts all elements an index to the right.
		for(int m = size-1; m > index; m--){
			this.set(m,this.get(m-1));
		}

		// insert new value at index.
		this.set(index, value);
	}

	public int remove(int index)
	{
		int removedElement = this.get(index);

		// shifts all values past index k to the left and decreases the size of the array.
		for( int k = index; k < this.size-1; k++){
			set(k, this.get(k+1));
		}

		size --;

		return removedElement;
	}
	
	public int set(int index, int value)
	{
		// Check whether the index is well defined
		// !!MODIFIED checkIndex to use this.data.length instead of size, the use of size does not make sense to me by its description
		// and the program works as expected if this.data.length is used.
		checkIndex(index, this.data.length);
		// Determine the value to be overwritten
		int temp = data[index];
		// Set the new value at the given position
		data[index] = value;
		// Return the value that is overwritten
		return temp;
	}
	
	public int get(int index)
	{
		// Check whether the index is well defined
		checkIndex(index, size);
		// Return the value at the given position
		return data[index];
	}
	
	private void checkIndex(int i, int n) throws IndexOutOfBoundsException
	{
		// Check whether the index is well defined
		if(i < 0 || i >= n)
		{
			throw new IndexOutOfBoundsException("Illegal index: " + i);
		}
	}
	
	// Represent the dynamic array as a string
	public String toString()
	{
		StringBuilder sb = new StringBuilder("[");
		for(int i = 0; i < size; i++)
		{
			sb.append(get(i));
			if(i < size - 1)
			{
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}
	

	public static void main(String[] args) 
	{
		try
		{
			DynamicArray da = new DynamicArray();
			for(int i = 0; i < 20; i++)
			{
				da.add(i);
			}
			System.out.println(da);
			da.insert(5, 50);
			System.out.println(da);
			da.remove(8);
			System.out.println(da);	
		}
		catch(IndexOutOfBoundsException e) 
		{
			System.out.println(e.getMessage());
		}
	}
}

