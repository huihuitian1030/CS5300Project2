package p2;

import java.io.*;

public class EdgesFilter {
	
	 
	public static void main(String []args){
		selectEdges();
	}
	
	protected static void selectEdges() {
		try {
			BufferedReader buffer = new BufferedReader(new FileReader(Constant.inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(Constant.selectedFile, false));
			String line = "";
			String prevId = "";
			int count = 0;
			while(true) {
				line = buffer.readLine();
				if (line == null || line.isEmpty()) {
					break;
				}
				line = line.trim();
				String[] info = line.split(Constant.parser);
				String src = info[0];
				String dest = info[1];
				double randomNum = Double.parseDouble(info[2]);
				if (randomNum>=Constant.rejectMin && randomNum <Constant.rejectLimit) {
					continue;
				}
				if (src.equals(prevId)){
					writer.write(Constant.connection);
					writer.write(dest);
					count++;
				}else {
					if (prevId!=""){
						writer.write(Constant.space);
						writer.write(String.valueOf(count));
						writer.newLine();
					}
					while (prevId!="" && Integer.parseInt(src)-Integer.parseInt(prevId)>1){
						prevId = String.valueOf(Integer.parseInt(prevId) + 1);
						writer.write(prevId);
						writer.write(Constant.space);
						writer.write(String.valueOf(Constant.defaultPageRank));
						writer.write(Constant.space);
						writer.write(Constant.emptyEdgeList);
						writer.write(Constant.space);
						writer.write(String.valueOf(0));
						writer.newLine();
					}
					prevId = src;
					writer.write(src);
					writer.write(Constant.space);
					writer.write(String.valueOf(Constant.defaultPageRank));
					writer.write(Constant.space);
					writer.write(dest);
					count = 1;
				}
			}
			if (prevId!=""){
				writer.write(Constant.space);
				writer.write(String.valueOf(count));
			}
			buffer.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
