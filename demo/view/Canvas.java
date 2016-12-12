package view;



import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.BevelBorder;

import model.XMatrix;
import model.XNode;
import util.MathUtil;

@SuppressWarnings("serial")
public class Canvas extends JComponent {

	private final static Color LINE_COLOR           = new Color(200, 200, 250);
	private final static Color START_NODE_COLOR     = new Color(0, 255, 0);
	private final static Color END_NODE_COLOR       = new Color(255, 0, 0);
	private final static Color DISABLED_NODE_COLOR  = new Color(0, 0, 0);
	private final static Color VISITED_NODE_COLOR   = new Color(155, 150, 250);
	private final static Color SELECTED_NODE_COLOR  = new Color(255, 255, 0);

	private final static Stroke STROKE = new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 1.0f, new float[]{1}, 0);

	private final static int MARGIN = 10;

	private Parameters parameters;
	private XMatrix matrix;

	private volatile boolean editable = true;

	private int left = 0;
	private int top = 0;
	private int right = 0;
	private int bottom = 0;

	private Point clickPoint;
	private Point cursorPoint;

	public Canvas() {
		addMouseListener(mouseListener);
		addMouseMotionListener(mouseListener);
		addComponentListener(componentListener);
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		paintMatrix(g);
		paintPainterMarkedArea(g);
	}

	protected void paintPainterMarkedArea(Graphics g) {
		switch (parameters.getMode()) {
		case MAP_EDITING_MODE:
			switch (parameters.getPainter()) {
			case BLOCK_OBSTACLE:
			case BLOCK_ERASER:
				if (clickPoint != null && cursorPoint != null) {
					Point pt1 = clickPoint;
					Point pt2 = cursorPoint;
					int x = (pt1.x < pt2.x) ? pt1.x : pt2.x;
					int y = (pt1.y < pt2.y) ? pt1.y : pt2.y;
					int width = Math.abs(pt1.x - pt2.x);
					int height = Math.abs(pt1.y - pt2.y);

					g.setColor(Color.blue);
					((Graphics2D)g).setStroke(STROKE);
					g.drawRect(x, y, width, height);
				}
				break;
			default:
				break;
			}
		default:
			break;
		}
	}
	
	public Dimension calculateDimension(int width, int height, int cellSize) {
		int rows = (height - MARGIN*2)/cellSize;
		int cols = (width - MARGIN*2)/cellSize;
		return new Dimension(rows, cols);
	}
	
	public void updateMatrixIfDimensionChanged() {
		Dimension dimension = calculateDimension(getWidth(), getHeight(), parameters.getCellSize());
		if (dimension.getWidth() != matrix.getRows() || dimension.getHeight() != matrix.getColumns()) {
			updateMatrix();
		}
	}

	public void updateMatrix() {
		int panelWidth = getWidth();
		int panelHeight = getHeight();
		int cellsize = parameters.getCellSize();
		int rows = (panelHeight - MARGIN*2)/cellsize;
		int cols = (panelWidth - MARGIN*2)/cellsize;
		left = (panelWidth - cellsize*cols)/2;
		top = (panelHeight - cellsize*rows)/2;
		right = left + cellsize*cols;
		bottom = top + cellsize*rows;
		matrix.setDimension(rows, cols);
		matrix.setStart(null);
		matrix.setEnd(null);
		repaint();
	}

	protected void paintMatrix(Graphics g) {
		int rows = matrix.getRows();
		int cols = matrix.getColumns();
		int cellsize = parameters.getCellSize();

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				XNode node = matrix.getValue(row, col);
				if (node == matrix.getStart()) {
					g.setColor(START_NODE_COLOR);
				}
				else if (node == matrix.getEnd()) {
					g.setColor(END_NODE_COLOR);
				}
				else if (!node.isEnabled()) {
					g.setColor(DISABLED_NODE_COLOR);
				}
				else if (node.isSelected()) {
					g.setColor(SELECTED_NODE_COLOR);
				}
				else if (node.isVisited()) {
					g.setColor(VISITED_NODE_COLOR);
				}
				else {
					continue;
				}
				int x = left + cellsize*col;
				int y = top + cellsize*row;
				g.fillRect(x, y, cellsize, cellsize);
			}
		}

		g.setColor(LINE_COLOR);
		for (int row = 0; row <= rows; row++) {
			int y = top + cellsize*row;
			g.drawLine(left, y, right, y);
		}
		for (int col = 0; col <= cols; col++) {
			int x = left + cellsize*col;
			g.drawLine(x, top, x, bottom);
		}
	}

	ComponentAdapter componentListener = new ComponentAdapter() {

		@Override
		public void componentResized(ComponentEvent aEvent) {
			updateMatrixIfDimensionChanged();
		}

	};

	XNode toNode(int x, int y) {
		int cellsize = parameters.getCellSize();
		if (x > left && x < right && y > top && y < bottom) {
			int row = (y - top)/cellsize;
			int col = (x - left)/cellsize;
			return matrix.getValue(row, col);
		}
		return null;
	}

	void setEnabled(List<Point> points, boolean enabled) {
		int cellsize = parameters.getCellSize();
		int rows = matrix.getRows();
		int cols = matrix.getColumns();
		double step = cellsize*0.3;

		// take each time one line segment, start from one end, toward the other end,
		// take a step of half the cell side length, the square where the step
		// falls in is not walkable.
		double x1, y1, x2, y2;
		x1 = points.get(0).getX();
		y1 = points.get(0).getY();
		for (int i = 1; i < points.size(); i++) {
			x2 = points.get(i).getX();
			y2 = points.get(i).getY();

			double angle = MathUtil.direction(x1, y1, x2, y2);
			double dist  = MathUtil.distance(x1, y1, x2, y2);
			int steps = (int)(dist/step + 0.5);

			for (int j = 0; j < steps; j++) {
				double disp[];
				if (j < steps - 1) {
					disp = MathUtil.coordinate(x1, y1, angle, step*j);
				}
				else {
					disp = new double[2];
					disp[0] = x2;
					disp[1] = y2;
				}
				int row = (int)((disp[1]-top)/cellsize);
				int col = (int)((disp[0]-left)/cellsize);
				if (row >= 0 && row < rows && col >= 0	&& col < cols) {
					XNode node = matrix.getValue(row, col);
					node.setEnabled(enabled);
				}
			}

			x1 = x2;
			y1 = y2;
		}
	}

	void setEnabled(int x, int y, int width, int height, boolean enabled) {
		int cellsize = parameters.getCellSize();
		int rows = matrix.getRows();
		int cols = matrix.getColumns();

		int row1 = y/cellsize;
		int col1 = x/cellsize;
		int row2 = (y + height)/cellsize;
		int col2 = (x + width)/cellsize;

		if (row1 < 0) row1 = 0;
		if (col1 < 0) col1 = 0;
		if (row2 >= rows) row2 = rows - 1;
		if (col2 >= cols) col2 = cols - 1;

		for (int row = row1; row <= row2; row++) {
			for (int col = col1; col <= col2; col++) {
				XNode node = matrix.getValue(row, col);
				node.setEnabled(enabled);
			}
		}
	}

	MouseAdapter mouseListener = new MouseAdapter() {


		@Override
		public void mousePressed(MouseEvent aEvent) {
			if (!isEditable()) {
				return;
			}
			clickPoint = cursorPoint = aEvent.getPoint();
		}

		@Override
		public void mouseDragged(MouseEvent aEvent) {
			if (!isEditable()) {
				return;
			}
			Point lastPos = cursorPoint;
			cursorPoint = aEvent.getPoint();

			switch (parameters.getMode()) {
			case MAP_EDITING_MODE:
				List<Point> points = new ArrayList<Point>();
				points.add(lastPos);
				points.add(cursorPoint);

				switch (parameters.getPainter()) {
				case CELL_OBSTACLE:
					setEnabled(points, false);
					break;

				case CELL_ERASER:
					setEnabled(points, true);
					break;
				default:
					break;
				}
				repaint();
				break;
			default:
				break;
			}
		}

		@Override
		public void mouseReleased(MouseEvent aEvent) {
			if (!isEditable()) {
				return;
			}
			cursorPoint = aEvent.getPoint();

			XNode node = toNode(cursorPoint.x, cursorPoint.y);

			Point pt1, pt2;
			int x, y, width, height;

			switch (parameters.getMode()) {
			case PATH_SEARCH_MODE:
				if (node != null) {
					matrix.setStart(node);
					firePropertyChange(AppConstant.StartRequested, null, null);
				}
				break;

			case MAP_EDITING_MODE:
				switch (parameters.getPainter()) {
				case CELL_OBSTACLE:
					if (node != null) {
						node.setEnabled(false);
					}
					break;

				case CELL_ERASER:
					if (node != null) {
						node.setEnabled(true);
					}
					break;

				case BLOCK_OBSTACLE:
					pt1 = clickPoint;
					pt2 = cursorPoint;
					x = (pt1.x < pt2.x) ? pt1.x : pt2.x;
					y = (pt1.y < pt2.y) ? pt1.y : pt2.y;
					width = Math.abs(pt1.x - pt2.x);
					height = Math.abs(pt1.y - pt2.y);
					List<Point> points = new ArrayList<Point>();
					points.add(new Point(x, y));
					points.add(new Point(x, y + height));
					points.add(new Point(x + width, y + height));
					points.add(new Point(x + width, y));
					points.add(new Point(x, y));
					setEnabled(points, false);
					break;

				case BLOCK_ERASER:
					pt1 = clickPoint;
					pt2 = cursorPoint;
					x = (pt1.x < pt2.x) ? pt1.x : pt2.x;
					y = (pt1.y < pt2.y) ? pt1.y : pt2.y;
					width = Math.abs(pt1.x - pt2.x);
					height = Math.abs(pt1.y - pt2.y);
					setEnabled(x, y, width, height, true);
					break;

				case DESTINATION:
					if (node != null && node.isEnabled()) {
						matrix.setEnd(node);
					}
					break;
				}
				if (matrix.getStart() != null && !matrix.getStart().isEnabled()) {
					matrix.setStart(null);
				}
				if (matrix.getEnd() != null && !matrix.getEnd().isEnabled()) {
					matrix.setEnd(null);
				}
				matrix.buildGraph();
				firePropertyChange(AppConstant.MapEdited, null, null);
				break;
				
			default:
				break;
			}
			clickPoint = cursorPoint = null;
			repaint();
		}
	};

	public XMatrix getMatrix() {
		return matrix;
	}

	public void setMatrix(XMatrix matrix) {
		this.matrix = matrix;
	}

	public Parameters getParameters() {
		return parameters;
	}

	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

}
