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
		// check 8 neighors
		int rows = getRows();
		int cols = getColumns();
		for (int i = -1; i <= 1; i++) {
			int y = row + i;
			if (y < 0 || y >= rows) {
				continue;
			}
			for (int j = -1; j <= 1; j++) {
				int x = col + j;
				// outside the matrix
				if (x < 0 || x >=cols) {
					continue;
				}
				// it is the node itself
				if (y == row && x == col) {
					continue;
				}
				// do not do cross cut
				if (i == -1 && j == -1) {
					XNode neighbor1 = getValue(row, col-1);
					XNode neighbor2 = getValue(row-1, col);
					if (!neighbor1.isEnabled() && !neighbor2.isEnabled()) {
						continue;
					}
				}
				if (i == -1 && j == 1) {
					XNode neighbor1 = getValue(row, col+1);
					XNode neighbor2 = getValue(row-1, col);
					if (!neighbor1.isEnabled() && !neighbor2.isEnabled()) {
						continue;
					}
				}
				if (i == 1 && j == -1) {
					XNode neighbor1 = getValue(row, col-1);
					XNode neighbor2 = getValue(row+1, col);
					if (!neighbor1.isEnabled() && !neighbor2.isEnabled()) {
						continue;
					}
				}
				if (i == 1 && j == 1) {
					XNode neighbor1 = getValue(row, col+1);
					XNode neighbor2 = getValue(row+1, col);
					if (!neighbor1.isEnabled() && !neighbor2.isEnabled()) {
						continue;
					}
				}
				XNode neighbor = getValue(y, x);
				// the disabled node
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
