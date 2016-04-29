package p2;

/**
 * Node class for each node inside the web graph data set.
 */
public class Node {
	
	private String nodeId;     // ID of each node
	private String outEdges;   // outgoing edges from this node
	private float pageRank;    // page rank of this node
	private int degree;        // degree of this node
	
	/**
	 * Constructor: set default node property
	 * @param nodeId
	 */
	public Node(String nodeId){
		this.nodeId = nodeId;
		this.outEdges = Constant.emptyEdgeList;
		this.pageRank = (float) 0.0;
		this.degree = 0;
	}
	
	/**
	 * Get node ID
	 * @return nodeId
	 */
	public String getNodeId() {
		return this.nodeId;
	}
	
	/**
	 * Set node ID
	 * @param nodeId
	 */
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	
	/**
	 * Get outgoing edges
	 * @return outEdges
	 */
	public String getOutEdges() {
		return this.outEdges;
	}
	
	/**
	 * Set outgoing edges
	 * @param outEdges
	 */
	public void setOutEdges(String outEdges) {
		this.outEdges = outEdges;
	}
	
	/**
	 * Get page rank
	 * @return pageRank
	 */
	public float getPageRank() {
		return this.pageRank;
	}
	
	/**
	 * Set page rank
	 * @param pageRank
	 */
	public void setPageRank(float pageRank) {
		this.pageRank = pageRank;
	}
	
	/**
	 * Get degree
	 * @return degree
	 */
	public int getDegree() {
		return this.degree;
	}
	
	/**
	 * Set degree
	 * @param degree
	 */
	public void setDegree(int degree) {
		this.degree = degree;
	}
}
