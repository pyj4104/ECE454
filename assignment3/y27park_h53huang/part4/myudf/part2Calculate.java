import java.util.ArrayList;
import java.io.IOException;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.apache.pig.data.DataBag;
import org.apache.pig.data.BagFactory;
import org.apache.pig.impl.util.WrappedIOException;

public class part2Calculate extends EvalFunc<String>
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
			int tot = 0;
			int validGenes = 0;
			String inputString = input.get(0).toString();
			String formattedInput = inputString.replace("{", "").replace("}", "").replace("(", "").replace(")", "");
			ArrayList<Double> genes = new ArrayList<Double>();
			for(String expVal : formattedInput.split(","))
			{
				tot++;
				Double expressionValue = Double.parseDouble(expVal);
				if(expressionValue >= 0.5)
				{
					validGenes++;
				}
			}
			return String.format("%.4f", ((double)validGenes/tot));
		}
		catch(Exception e)
		{
			throw WrappedIOException.wrap(e);
		}
	}
}

