package model;

import java.beans.PropertyChangeListener;

import util.Matrix;
import view.AppConstant;

public class XMatrix extends Matrix<XNode> {

	private ICostEvaluator evaluator;

	private XNode start;
	private XNode end;

	private PropertyChangeListener nodeListener;

	@Override
	public void setDimension(int rows, int cols) {
		super.setDimension(rows, cols);
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				XNode node = new XNode();
				node.setRow(row);
				node.setCol(col);
				setValue(row, col, node);
				if (nodeListener != null) {
					node.addPropertyChangeListener(
							AppConstant.NodePredecessorChanged,
							nodeListener);
				}
			}
		}
	}

	public void buildGraph() {
		int rows = getRows();
		int cols = getColumns();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				buildEdges(row, col);
			}
		}
	}

	public void reset() {
		int rows = getRows();
		int cols = getColumns();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				XNode node = getValue(row, col);
				node.reset();
			}
		}
	}

	public void evaluateHeuristic() {
		int rows = getRows();
		int cols = getColumns();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				XNode node = getValue(row, col);
				int heuristic = evaluator.evaluateHeuristic(node, start, end);
				node.setHeuristic(heuristic);
			}
		}
	}

	private void buildEdges(int row, int col) {
		XNode node = getValue(row, col);
		node.getEdges().clear();
		if (!node.isEnabled()) {
			return;
		}
		int rows = getRows();
		int cols = getColumns();
		for (int i = -1; i <= 1; i++) {
			int y = row + i;
			if (y < 0 || y >= rows) {
				continue;
			}
			for (int j = -1; j <= 1; j++) {
				int x = col + j;
				if (x < 0 || x >=cols) {
					continue;
				}
				if (y == row && x == col) {
					continue;
				}
				if (i != 0 && j != 0 && isCrossCutBlocked(row, col, i, j)) {
					continue;
				}
				XNode neighbor = getValue(y, x);
				if (!neighbor.isEnabled()) {
					continue;
				}
				Edge edge = new Edge();
				edge.setNodeA(node);
				edge.setNodeB(neighbor);
				node.addEdge(edge);
				edge.setWeight(evaluator.evaluateWeight(edge));
			}
		}
	}

	private boolean isCrossCutBlocked(int row, int col, int di, int dj) {
		XNode n1 = getValue(row, col + dj);
		XNode n2 = getValue(row + di, col);
		return !n1.isEnabled() && !n2.isEnabled();
	}

	public ICostEvaluator getEvaluator() {
		return evaluator;
	}

	public void setEvaluator(ICostEvaluator evaluator) {
		this.evaluator = evaluator;
	}

	public XNode getStart() {
		return start;
	}

	public void setStart(XNode start) {
		this.start = start;
	}

	public XNode getEnd() {
		return end;
	}

	public void setEnd(XNode end) {
		this.end = end;
	}

	public PropertyChangeListener getNodeListener() {
		return nodeListener;
	}

	public void setNodeListener(PropertyChangeListener nodeListener) {
		this.nodeListener = nodeListener;
	}

}
