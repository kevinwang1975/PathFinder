package view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JOptionPane;

import matrix.AStarCostEvaluator;
import matrix.MatrixNode;
import model.XAStarPathAlgorithm;
import model.XMatrix;
import util.AWTUtil;

public class Coordinator {

	Map<Object,PropertyChangeSupport> supports = new HashMap<Object,PropertyChangeSupport>();

	public void addPropertyChangeListener(Object source, PropertyChangeListener listener) {
		PropertyChangeSupport support = supports.get(source);
		if (support == null) {
			support = new PropertyChangeSupport(source);
			supports.put(source, support);
		}
		support.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(Object source, PropertyChangeListener listener) {
		PropertyChangeSupport support = supports.get(source);
		if (support != null) {
			support.removePropertyChangeListener(listener);
		}
	}
	
	protected void firePropertyChange(Object source, String propertyName, Object oldValue, Object newValue) {
		PropertyChangeSupport support = supports.get(source);
		if (support != null) {
			support.firePropertyChange(propertyName, oldValue, newValue);
		}
	}

	static class Pack {

		Canvas canvas;
		XAStarPathAlgorithm algorithm;
		AStarCostEvaluator evaluator;

	}

	Collection<Pack> packs = new ArrayList<Pack>();

	ControlPanel controlPanel;

	PropertyChangeListener startRequestedListener = new PropertyChangeListener() {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			for (Pack pack : packs) {
				if (pack.algorithm.isSearching()) {
					return;
				}
				if (pack.canvas.getMatrix().getEnd() == null) {
					JOptionPane.showMessageDialog(AWTUtil.findFrame(controlPanel), "Destination not set.");
					return;
				}
			}
			final MatrixNode start = ((Canvas)evt.getSource()).getMatrix().getStart();
			for (Pack pack : packs) {
				final Canvas canvas = pack.canvas;
				final XAStarPathAlgorithm algorithm = pack.algorithm;
				final AStarCostEvaluator evaluator = pack.evaluator;
				final XMatrix matrix = canvas.getMatrix();
				Runnable runnable = new Runnable() {
					@Override
					public void run() {
						firePropertyChange(canvas, AppConstant.SearchStarted, null, null);
						canvas.setEditable(false);
						evaluator.setEnabled(true);
						matrix.setStart(matrix.getValue(start.getRow(), start.getCol()));
						matrix.reset();
						matrix.evaluateHeuristic();
						algorithm.searchPath(matrix.getStart(), matrix.getEnd());
						canvas.repaint();
						canvas.setEditable(true);
						firePropertyChange(canvas, AppConstant.SearchCompleted, null, null);
					}
				};
				Thread thread = new Thread(runnable);
				thread.start();
			}
		}
	};

	PropertyChangeListener matrixEditedListener = new PropertyChangeListener() {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			Canvas src = (Canvas)evt.getSource();
			for (Pack pack : packs) {
				if (pack.canvas == src) {
					continue;
				}
				if (copyMatrix(src.getMatrix(), pack.canvas.getMatrix())) {
					pack.canvas.repaint();
				}
			}
		}

	};

	boolean copyMatrix(XMatrix src, XMatrix dest) {
		int srcRows = src.getRows();
		int srcCols = src.getColumns();
		int destRows = dest.getRows();
		int destCols = dest.getColumns();
		if (srcRows != destRows || srcCols != destCols) {
			System.err.println("matrix size not match");
			return false;
		}
		for (int i = 0; i < srcRows; i++) {
			for (int j = 0; j < srcCols; j++) {
				MatrixNode srcNode = src.getValue(i, j);
				MatrixNode destNode = dest.getValue(i, j);
				destNode.setEnabled(srcNode.isEnabled());
			}
		}
		MatrixNode srcStart = src.getStart();
		if (srcStart != null) {
			dest.setStart(dest.getValue(srcStart.getRow(), srcStart.getCol()));
		}
		else {
			dest.setStart(null);
		}
		MatrixNode srcEnd = src.getEnd();
		if (srcEnd != null) {
			dest.setEnd(dest.getValue(srcEnd.getRow(), srcEnd.getCol()));
		}
		else {
			dest.setEnd(null);
		}
		dest.buildGraph();
		return true;
	}

	void generateMatrix(XMatrix matrix, int obstaclePercent) {
		int rows = matrix.getRows();
		int cols = matrix.getColumns();
		matrix.setStart(null);
		matrix.setEnd(null);
		Random random = new Random();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				matrix.getValue(i, j).setEnabled(random.nextInt(100) >= obstaclePercent);
			}
		}
	}

	PropertyChangeListener controlPanelListener = new PropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if (AppConstant.CellSizeChanged.equals(evt.getPropertyName())) {
				for (Pack pack : packs) {
					pack.canvas.updateMatrixIfDimensionChanged();
				}
			}
			else if (AppConstant.ClearMapRequested.equals(evt.getPropertyName()) ) {
				for (Pack pack : packs) {
					generateMatrix(pack.canvas.getMatrix(), 0);
					pack.canvas.repaint();
				}
			}
			else if (AppConstant.GenerateMapRequested.equals(evt.getPropertyName())) {
				XMatrix src = null;
				for (Pack pack : packs) {
					if (src == null) {
						src = pack.canvas.getMatrix();
						generateMatrix(src, controlPanel.getParameters().getObstaclePercent());
						pack.canvas.repaint();
					}
					else if (copyMatrix(src, pack.canvas.getMatrix())) {
						pack.canvas.repaint();
					}
				}
			}
			else if (AppConstant.StopRequested.equals(evt.getPropertyName())) {
				for (Pack pack : packs) {
					pack.evaluator.setEnabled(false);
				}
			}
			else if (AppConstant.ModeChanged.equals(evt.getPropertyName())) {
				switch (controlPanel.getParameters().getMode()) {
				case MAP_EDITING_MODE:
					for (Pack pack : packs) {
						pack.canvas.getMatrix().reset();
						pack.canvas.repaint();
					}
					break;
				default:
					break;
				}
			}
		}
	};

	public void add(Canvas canvas, XAStarPathAlgorithm algorithm, AStarCostEvaluator evaluator) {
		Pack pack = new Pack();
		pack.canvas = canvas;
		pack.algorithm = algorithm;
		pack.evaluator = evaluator;
		canvas.addPropertyChangeListener(AppConstant.StartRequested, startRequestedListener);
		canvas.addPropertyChangeListener(AppConstant.MapEdited, matrixEditedListener);
		packs.add(pack);
	}

	public void setControlPanel(ControlPanel controlPanel) {
		this.controlPanel = controlPanel;
		this.controlPanel.addPropertyChangeListener(controlPanelListener);
	}

}
