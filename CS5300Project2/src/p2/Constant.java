package p2;

public class Constant {
	protected static final String inputFile = "edges.txt";
	protected static final String selectedFile = "selectedEdges.txt";
	
	protected static final String space = " ";
	protected static final String parser= "\\s+";
	protected static final double netId = 0.976;
	protected static double rejectMin = 0.9 * netId;
	protected static double rejectLimit = rejectMin+ 0.01;;
	protected static int numberNodes = 685230;
	protected static float defaultPageRank = 1.0f/numberNodes;
	protected static final String connection = ",";
	protected static final float dampingFactor = 0.85f;
	protected static final int iteration = 5; 
	protected static final int precision = 10000;
	
	protected static final String emptyEdgeList = "none";
}