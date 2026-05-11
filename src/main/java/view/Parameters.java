package view;

import view.AppConstant.Mode;
import view.AppConstant.Painter;


public class Parameters {

	private int cellSize = 16;
	private int animationMs = 10;
	private int obstaclePercent = 30;
	private Painter painter = Painter.DESTINATION;
	private Mode mode = Mode.MAP_EDITING_MODE;

	public int getCellSize() {
		return cellSize;
	}

	public void setCellSize(int aCellSize) {
		cellSize = aCellSize;
	}

	public int getAnimationMs() {
		return animationMs;
	}

	public void setAnimationMs(int aAnimationMs) {
		animationMs = aAnimationMs;
	}

	public Painter getPainter() {
		return painter;
	}

	public void setPainter(Painter painter) {
		this.painter = painter;
	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public int getObstaclePercent() {
		return obstaclePercent;
	}

	public void setObstaclePercent(int obstaclePercent) {
		this.obstaclePercent = obstaclePercent;
	}

}
