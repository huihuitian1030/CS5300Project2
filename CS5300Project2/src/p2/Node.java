package p2;

import java.util.*;


public class Node {
	private int nodeId;
	private List<Integer> edgeList;
	private double pageRank;
	
	public Node(int nodeId){
		this.nodeId = nodeId;
		this.edgeList = new ArrayList<Integer>();
		this.pageRank = 0.0;
	}
	
	public int getNodeId() {
		return this.nodeId;
	}
	public void addEdge(int id) {
		edgeList.add(id);
	}
	
	public List<Integer> getEdgeList() {
		return edgeList;
	}
	
	public int getDegree() {
		return edgeList.size();
	}
	
	public void setPageRank(double pr) {
		this.pageRank = pr;
	}
	
	public double getPageRank() {
		return this.pageRank;
	}
}
