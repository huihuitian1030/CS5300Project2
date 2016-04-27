package p2;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class BlockPageRankMapper  extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text>{
	
	public void map(LongWritable key, Text value, OutputCollector<Text,Text> output,Reporter reporter) throws IOException{
		
		String[] token = value.toString().split(Constant.parser);
		int blockID = Integer.parseInt(token[0]);
		int nodeID = Integer.parseInt(token[1]);
		
		Text mappedKey = new Text(String.valueOf(blockID));
		
		float pageRank = Float.parseFloat(token[2]);
		String edges = token[3];
		int degree = Integer.parseInt(token[4]);

		Text newPR = new Text(String.valueOf(pageRank/degree));
		
		Text turpleList = new Text("PR "+ String.valueOf(nodeID) +" "+ String.valueOf(pageRank) + " " + edges);
		output.collect(mappedKey, turpleList);
		
		if(!edges.equals(Constant.emptyEdgeList)){
			String[] outNodes = edges.split(",");
			
			for(String outNode : outNodes){
				int curBlockID = blockIDofNodes(Integer.parseInt(outNode));
				
				if(curBlockID == blockID){
					Text BE = new Text("BE " + String.valueOf(nodeID) +" "+ outNode);
					output.collect(mappedKey, BE);
				}else{
					Text BC = new Text("BC " + String.valueOf(nodeID) +" " + outNode + " " + newPR);
					output.collect(mappedKey, BC);
				}
				
			}
		}
		
		
	}
	
	public int blockIDofNodes(int nodeID){
		int blockID = (int) Math.floor(nodeID / Constant.partitionSize);
		long testBoundry = Constant.blockBoundries[blockID];
		if(testBoundry > nodeID){
			blockID--;
		}
		return blockID;
	}

}