package p2;

import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

/**
 * Reducer: calculate page rank and residual error.
 */
public class SimplePageRankReducer extends MapReduceBase
								   implements Reducer<Text, Text, Text, Text> {

	public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter)
			throws IOException {
		// TODO Auto-generated method stub
		float pageRankSum = (float) 0.0;     // sum of current page rank
		float pageRankStart = (float) 0.0;   // old page rank
		float pageRankEnd = (float) 0.0;     // new page rank
		float residualError = (float) 0.0;   // residual error for convergence
		
		String edges = Constant.emptyEdgeList;
		// for each node, do following
		while (values.hasNext()) {
			Text line = values.next();
			String[] words = line.toString().split(Constant.parser);
			
			// case 1: previous page rank
			if (words[0].equals("PR")) {
				pageRankStart = Float.parseFloat(words[1]);
				if (!words[2].equals(Constant.emptyEdgeList)) { edges = words[2]; }
				else { edges = Constant.emptyEdgeList; }
			}
			// case 2: new page rank 
			else
				pageRankSum += Float.parseFloat(words[0]);
		}
		
		// calculate new page rank
		pageRankEnd = Constant.dampingFactor * pageRankSum + (1 - Constant.dampingFactor) / Constant.numberNodes;
		// calculate residual error
		residualError = Math.abs(pageRankStart - pageRankEnd) / pageRankEnd;
		
		// add residual error to counter
		long residual = (long) Math.floor(residualError * Constant.precision);
		reporter.getCounter(SimplePageRank.Counter.residual).increment(residual);
		
		// set output
		int degree = edges.split(",").length;
		Text out = new Text(pageRankEnd + " " + edges + " " + Integer.toString(degree));
		output.collect(key, out);
	}
}