package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import matrix.AStarCostEvaluator;
import matrix.DijkstraCostEvaluator;
import model.ICostEvaluator;
import model.XAStarPathAlgorithm;
import model.XMatrix;




public class PathFinder {

	@SuppressWarnings("serial")
	static class CanvasPanel extends JPanel {

		public CanvasPanel(String name, Canvas canvas) {
			this.setLayout(new BorderLayout());
			JLabel label = new JLabel(name, JLabel.CENTER);
			add(BorderLayout.CENTER, canvas);
			add(BorderLayout.SOUTH, label);
		}

	}

	Canvas createCanvas(final Parameters parameters, final ICostEvaluator evaluator) {
		final XMatrix matrix = new XMatrix();
		matrix.setEvaluator(evaluator);
		final Canvas canvas = new Canvas();
		canvas.setParameters(parameters);
		canvas.setMatrix(matrix);
		matrix.setNodeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (parameters.getAnimationMs() == 0) {
					return;
				}
				try {
					canvas.repaint();
					Thread.sleep(parameters.getAnimationMs());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		return canvas;
	}

	public void demo() {
		final Parameters parameters = new Parameters();
		final ControlPanel controlPanel = new ControlPanel(parameters);

		final Coordinator coordinator = new Coordinator();
		coordinator.setControlPanel(controlPanel);

		final AStarCostEvaluator evaluator1 = new AStarCostEvaluator();
		final XAStarPathAlgorithm pathAlgorithm1 = new XAStarPathAlgorithm();
		pathAlgorithm1.setEvaluator(evaluator1);
		final Canvas canvas1 = createCanvas(parameters, evaluator1);

		final DijkstraCostEvaluator evaluator2 = new DijkstraCostEvaluator();
		final XAStarPathAlgorithm pathAlgorithm2 = new XAStarPathAlgorithm();
		pathAlgorithm2.setEvaluator(evaluator2);
		final Canvas canvas2 = createCanvas(parameters, evaluator2);

		coordinator.add(canvas1, pathAlgorithm1, evaluator1);
		coordinator.add(canvas2, pathAlgorithm2, evaluator2);

		JPanel overallPanel = new JPanel(new BorderLayout());
		overallPanel.add(controlPanel, BorderLayout.WEST);
		JPanel mainPanel = new JPanel(new GridLayout(1,0,10,10));
		mainPanel.add(new CanvasPanel("A*", canvas1));
		mainPanel.add(new CanvasPanel("Dijkstra", canvas2));
		overallPanel.add(mainPanel, BorderLayout.CENTER);

		JFrame frame = new JFrame("Path Finder");
		frame.getContentPane().add(overallPanel);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize( 1200, 600 );
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new PathFinder().demo();
	}

}


