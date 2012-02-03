import java.util.ArrayList;
import java.util.Iterator;


public class TopKQueue {
	float max;
	float min;
	TopPriorityQueue<Tuple> queue ;
	
	public TopKQueue(int capacity)
	{
		max = 0;
		min = 0;
		queue = new TopPriorityQueue(capacity, new Tuple(), false);
	}
	public float getMax()
	{
		if(queue.isEmpty())
		{
			return 0.0f;
		}
		else
		{
			return queue.peek().score;
		}
	}
	public float getMin()
	{
		if(queue.isEmpty())
		{
			return 0.0f;
		}
		else
		{
			return queue.getTail().score;
		}
	}
	public boolean AddTuple(Tuple t)
	{
		if(t.score>this.getMin())
		{
			queue.add(t);
			return true;
		}
		else
		{
			return false;	
		}
	}
	public void outputAlltuples()
	{
		for (int i = 0 ; i < queue.size(); i++)
		{
			//System.out.println(queue.peek().getTupleCombination()+"__________" + queue.getTail().getTupleCombination());
		}
	}
	public void AddAllTuple(ArrayList<Tuple> list)
	{
		//System.out.println("Adding ArrayList Tuple into topk :" +list.size());
		for(int i = 0; i < list.size(); i++)
		{
			//System.out.println("XXX::"+ list.get(i).relations[0] + " & " + list.get(i).relations[1]);
			this.queue.add(list.get(i));
		}
	}
}
