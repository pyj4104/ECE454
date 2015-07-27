import java.util.ArrayList;
import java.io.IOException;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.DataBag;
import org.apache.pig.impl.util.WrappedIOException;

public class Part3calc extends EvalFunc<String>
{
	@SuppressWarnings("deprecation")
	public String exec(Tuple input) throws IOException
	{
		if(input==null || input.size() == 0)
		{
			return null;
		}
		ArrayList<Double> sample1 = new ArrayList<Double>();
		ArrayList<Double> sample2 = new ArrayList<Double>();
		boolean firstSample = true;
		String s1 = "";
		String s2 = "";
		try
		{
			s1 = (String)input.get(0);
			for(int i = 1; i < input.size(); i++){
				try{
					if(firstSample){
						sample1.add(Double.parseDouble((String)input.get(i)));
					}else{
						sample2.add(Double.parseDouble((String)input.get(i)));
					}
				}catch(Exception e){
					s2 = (String)input.get(i);
					if(s1.equals(s2)){
						return null;
					}
					firstSample = false;
				}
			}
			double result = 0.0;
			for(int i = 0; i < sample1.size(); i++){
				result += sample1.get(i) * sample2.get(i);
			}
			
			return String.format("%s,%s,%.2f", s1,s2,result);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			throw WrappedIOException.wrap(e);
		}
	}
}

