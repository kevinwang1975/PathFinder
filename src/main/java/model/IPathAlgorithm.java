package model;

/**
 * A graph path search algorithm.
 * 
 * @author Kevin Wang
 *
 */
public interface IPathAlgorithm {

	/**
	 * Searches for a path between the origin and the destination.
	 * 
	 * @param start
	 * @param end
	 * @return true if found a path, false otherwise
	 */
	boolean searchPath(INode start, INode end);

}
