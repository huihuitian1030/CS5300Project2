package p2;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;


public class SimplePageRankMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
	
	public void map(LongWritable key, Text value, OutputCollector<Text,Text> output,Reporter reporter) throws IOException{
		
		String[] token = value.toString().split("\\s+");

		Text u = new Text(token[0]);
		float pageRank = Float.parseFloat(token[1]);
		String edges = token[2];
		int degree = Integer.parseInt(token[3]);
		
		float newPageRank = pageRank/degree;
		Text newPR = new Text(String.valueOf(newPageRank));
		
		Text turpleList = new Text("PageRank "+String.valueOf(pageRank) + " " + edges);
		output.collect(u, turpleList);
		if(!edges.equals(Constant.emptyEdgeList)){
			String[] outNodes = edges.split(",");
			
			for(String node : outNodes){
				Text v = new Text(node);
				output.collect(v, newPR);
			}
		}
		
	}
}
