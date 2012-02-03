public class StringArray 
{
	public String[] vals;
	int length = 0;
	int capacity=0;
	
	public StringArray(int c)
	{
		capacity = c ;
		length = 0 ;
		vals = new String[capacity];
	}
	public StringArray()
	{
		//default capacity is 25
		capacity = 25;
		length = 0 ;
		vals = new String[capacity];
	}
	public void add(String item)
	{
		if(length<capacity)
		{
			vals[length]=item;
			length++;
		}
		else
		{
			capacity*=2;
			String[] newArray = new String[capacity];
			System.arraycopy(vals, 0, newArray, 0, vals.length);
			vals=newArray;
			vals[length]=item;
			length++;
		}
	}
	public String getVals(int index)
	{
		return vals[index];
	}
	public int getLength()
	{
		return length;
	}
	public int getCapacity()
	{
		return capacity;
	}
}