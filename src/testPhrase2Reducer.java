import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;


public class testPhrase2Reducer extends Reducer<Phrase2Pair,Text, Text, Text> 
{
	public class GroupKeys{
		int  MC, RC;
		int totalCardinality; 
		String M, R;
		public GroupKeys(){
			M = "";
			R = "";
			MC = 0;
			RC = 0;
			totalCardinality =0 ;
		}
		public int getNumCombinations()
		{
			return MC*RC;
		}
		public String getValues()
		{
			return M+","+ R;
		}
	}
	public Text phrase2Val = new Text();
	public Text outputkey = new Text();
	
	public int topk = 2;
	public TopKQueue tokresult = new TopKQueue(topk);
	
	public float       max_possible;
	public final float upperbound_unseen = 1.0f;
	public boolean     isCandidate;
	public ArrayList<metaTuple> M;
	public ArrayList<metaTuple> R;
	public GroupKeys finalkeys; 

	protected void reduce(Phrase2Pair key, Iterable<Text> values, Context context) 
		throws IOException,InterruptedException
	{
		finalkeys = new GroupKeys();
		isCandidate = false;
		int counter = 0 ; 
		max_possible = 0;
		M = new ArrayList<metaTuple>();
		R = new ArrayList<metaTuple>();
		
		for(Text v: values) 
		{
			String s = v.toString();
			//System.out.println("processing::<"+key.Attribute+","+s+">");
			if(counter==0)
			{
				max_possible = key.score + upperbound_unseen ;
				if(max_possible>tokresult.getMin()){
					isCandidate = true;
					counter++;
				}
				else{
					break;
				}
			}
			if (s.substring(s.length()-1).equals("M"))
			{
				//M.add(new metaTuple(key.score,key.Pos_Relation));
				M.add(new metaTuple(key.score,s.substring(s.indexOf('_')+1)));
				finalkeys.MC++;
			}
			else if (s.substring(s.length()-1).equals("R"))
			{
				R.add(new metaTuple(key.score,s.substring(s.indexOf('_')+1)));
				finalkeys.RC++;
			}
			if(finalkeys.getNumCombinations()>=topk)
			{
				break;
			}
		}
		if(isCandidate)
		{
			ArrayList<Tuple> candidate = metaTuple.Join(topk, M, R);
			tokresult.AddAllTuple(candidate);
			//tokresult.outputAlltuples();
		}
	}
	protected void cleanup(Reducer.Context context)throws IOException,InterruptedException
	{
		while(!tokresult.queue.isEmpty())
		{
			Tuple result = tokresult.queue.peek();
			System.out.println("final output ::" + result.score + 
					"->"+result.getTupleCombination());
			context.write(new Text(Float.toString(tokresult.queue.peek().score)),
					new Text(tokresult.queue.poll().getTupleCombination()));
		}
	}

}


/*
import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;


public class testPhrase2Reducer extends Reducer<Phrase2Pair,Text, Text, Text> 
{
	int topk = 2;
	TopKQueue queue = new TopKQueue(topk);
	
	public class GroupKeys{
		int  MC, RC;
		int totalCardinality; 
		String M, R;
		public GroupKeys(){
			M = "";
			R = "";
			MC = 0;
			RC = 0;
			totalCardinality =0 ;
		}
		public int getNumCombinations()
		{
			return MC*RC;
		}
		public String getValues()
		{
			return M+","+ R;
		}
	}
	Text phrase2Val = new Text();
	Text outputkey = new Text();
	float max_possible;
	final float upperbound_unseen=1.0f;
	// 1 bit after dot
	int accurracy = 3;
	boolean isCandidate = false;
	//int counter = 0 ;
	protected void reduce(Phrase2Pair key, Iterable<Text> values, Context context) throws IOException,InterruptedException
	{
		GroupKeys finalkeys = new GroupKeys();
		int counter = 0 ; 
		max_possible = 0;
		
		for(Text v: values) 
		{
			String s = v.toString();
			System.out.println(key.getAttribute()+"->"+s);
			if(counter==0)
			{
				float score = Float.parseFloat(s.substring(0,accurracy));
				max_possible = score + upperbound_unseen ;
				if(max_possible>queue.getMax())
				{
					isCandidate = true;
				}
			}
			if(isCandidate)
			if (s.substring(s.length()-1).equals("X"))
			{
				finalkeys.M+=s.substring(0,s.length()-2) + "|";
				finalkeys.MC++;
			}
			else if (s.substring(s.length()-1).equals("Y"))
			{
				finalkeys.R+=s.substring(0,s.length()-2) + "|";
				finalkeys.RC++;
			}
			if(finalkeys.getNumCombinations()>=topk)
			{
				break;
			}
		}
		phrase2Val.set(finalkeys.getNumCombinations()+","+finalkeys.getValues());
		outputkey.set(key.getAttribute());
		if(finalkeys.MC>0 && finalkeys.RC > 0)
		{
			context.write(outputkey,phrase2Val);
		}		
	}	
}


*/

