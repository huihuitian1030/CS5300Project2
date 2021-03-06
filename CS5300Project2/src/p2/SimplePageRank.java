package p2;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RunningJob;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

/**
 * This is the main class of simple computation of page rank. It will does 5 MapReduce passes. For each pass, it
 * create a new running job, set mapper and reducer class, set Text as output key and value classes. On the initial pass
 * use the pre-filtered file as input file, other pass use the output of the last pass as input. At the end of each pass, 
 * compute the average nodes residual error and write into the simpelPageRankOutput.txt.
 */

public class SimplePageRank {
	
	// use a hadoop counter to record the total residual error and compute the average later. 
	protected static enum Counter {
        residual;
	}
	public static void main (String [] args) throws IOException{
		if (args.length!= 2){
			System.out.println("invalid input");
			System.exit(1);
		}
		String inputPath = args[0];
		String outputPath =args[1];
		float[] residual = new float[Constant.iteration];
		BufferedWriter out = new BufferedWriter
		         (new FileWriter(Constant.SimpleOuput, false));
		for (int i = 0; i< Constant.iteration; i++) {
			JobConf conf = new JobConf(SimplePageRank.class);
			conf.setOutputKeyClass(Text.class);
	        conf.setOutputValueClass(Text.class);
	        // Set Mapper and Reducer class
	        conf.setMapperClass(SimplePageRankMapper.class);
	        conf.setReducerClass(SimplePageRankReducer.class);
	        
	        conf.setInputFormat(TextInputFormat.class);
	        conf.setOutputFormat(TextOutputFormat.class);
	        if (i>0) {
				inputPath = outputPath + "/output" + i;
			}
	        FileInputFormat.setInputPaths(conf, new Path(inputPath));
	        FileOutputFormat.setOutputPath(conf, new Path(outputPath + "/output" + (i + 1)));
	        RunningJob job = JobClient.runJob(conf);
	        long curr_residual = job.getCounters()
					.findCounter(Counter.residual).getValue();
	        
	        //compute average nodes residual error
	        residual[i] = (float)curr_residual/ Constant.precision/Constant.numberNodes;
		}
		for (int i = 0; i<Constant.iteration; i++){
			out.write("Iteration " + i + " avg error "
					+ residual[i]);
			out.newLine();
		}
		out.close();
	}
}
