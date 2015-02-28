package view;


public class AppConstant {

	public final static String ModeChanged 				= "Mode Changed";

	public final static String EndNodeChanged 			= "End Node Changed";
	public final static String NodeOpenStateChanged 	= "Node Open State Changed";
	public final static String NodeVisitedStateChange	= "Node Visited State Changed";
	public final static String NodeSelectedStateChanged	= "Node Selected State Changed";
	public final static String NodePredecessorChanged	= "Node Predecessor Changed";
	public final static String NodeCostChanged	        = "Node Cost Changed";

	public final static String StartRequested 	        = "Start Requested";
	public final static String StopRequested 			= "Stop Requested";
	
	public final static String SearchStarted 			= "Search Started";
	public final static String SearchCompleted 			= "Search Completed";

	public final static String ClearMapRequested 		= "Clear Map Requested";
	public final static String GenerateMapRequested		= "Generate Map Requested";
	public final static String MapEdited                = "Map Edited";
	public final static String CellSizeChanged 			= "Cell Size Changed";

	public static enum Painter {

		DESTINATION   ("Destination", 		"Select a cell as the destination for path search"),
		CELL_OBSTACLE ("Cell Obstacle",		"Select cells as obstacles"),
		BLOCK_OBSTACLE("Block Obstacle",	"Select a block of cells as obstacles"),
		CELL_ERASER   ("Cell Eraser", 	    "Clear cell obstacles"),
		BLOCK_ERASER  ("Block Eraser", 		"Clear a block of cell obstacles");

		String name;
		String description;

		Painter(String name, String description) {
			this.name = name;
			this.description = description;
		}

		public static Painter getValue(String name) {
			for (Painter p : values()) {
				if (p.name.equals(name)) {
					return p;
				}
			}
			return null;
		}

		@Override
		public String toString() {
			return this.name;
		}

	}

	public static enum Mode {

		MAP_EDITING_MODE("Map Editing"),
		PATH_SEARCH_MODE("Path Search"),
		MAP_SETTING_MODE("Map Setting");

		String name;

		Mode(String name) {
			this.name = name;
		}

		public static Mode getValue(String name) {
			for (Mode m : values()) {
				if (m.name.equals(name)) {
					return m;
				}
			}
			return null;
		}

		@Override
		public String toString() {
			return this.name;
		}

	}

}
