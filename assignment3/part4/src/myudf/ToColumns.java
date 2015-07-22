import java.util.ArrayList;
import java.io.IOException;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.apache.pig.data.DataBag;
import org.apache.pig.data.BagFactory;
import org.apache.pig.impl.util.WrappedIOException;

public class ToColumns extends EvalFunc<String>
{
	BagFactory mBag = BagFactory.getInstance();
	TupleFactory mTuple = TupleFactory.getInstance();

	@SuppressWarnings("deprecation")
	public String exec(Tuple input) throws IOException
	{
		if(input==null || input.size() == 0)
		{
			return null;
		}
		try
		{
			Integer index = 1;
			String name = "gene_";
			Double max = 0.0;
			String inputString = input.get(0).toString();
			String formattedInput = inputString.replace("{", "").replace("}", "").replace("(", "").replace(")", "");
			ArrayList<Double> genes = new ArrayList<Double>();
			for(String expVal : formattedInput.split(","))
			{
				genes.add(Double.parseDouble(expVal));
			}
			DataBag output = mBag.newDefaultBag();
			for(Double gene:genes)
			{
				if(max <= gene)
				{
					if(max < gene)
					{
						output.clear();
						max = gene;
					}
					Tuple element = mTuple.newTuple(1);
					element.set(0, name+(index.toString()));
					output.add(element);
				}
				index++;
			}
			return output.toString().replace("{", "").replace("}", "").replace("(", "").replace(")", "");
		}
		catch(Exception e)
		{
			throw WrappedIOException.wrap(e);
		}
	}
}

