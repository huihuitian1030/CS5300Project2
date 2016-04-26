package p2;

import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class SimplePageRankReducer extends MapReduceBase
								   implements Reducer<Text, Text, Text, Text> {

	public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter)
			throws IOException {
		// TODO Auto-generated method stub
		float pageRankSum = (float) 0.0;
		float pageRankStart = (float) 0.0;
		float pageRankEnd = (float) 0.0;
		float residualError = (float) 0.0;
		
		String edges = Constant.emptyEdgeList;
		while (values.hasNext()) {
			Text line = values.next();
			String[] words = line.toString().split("\\s+");
			
			if (words[0].equals("PageRank")) {
				pageRankStart = Float.parseFloat(words[1]);
				if (!words[2].equals(Constant.emptyEdgeList)) { edges = words[2]; }
				else { edges = Constant.emptyEdgeList; }
			}
			else
				pageRankSum += Float.parseFloat(words[0]);
		}
		
		pageRankEnd = Constant.dampingFactor * pageRankSum + (1 - Constant.dampingFactor) / Constant.numberNodes;
		residualError = Math.abs(pageRankStart - pageRankEnd) / pageRankEnd;
		
		long residual = (long) Math.floor(residualError * Constant.precision);
		reporter.getCounter(SimplePageRank.Counter.residual).increment(residual);
		
		int degree = edges.split(",").length;
		Text out = new Text(pageRankEnd + " " + edges + " " + Integer.toString(degree));
		output.collect(key, out);
	}
}