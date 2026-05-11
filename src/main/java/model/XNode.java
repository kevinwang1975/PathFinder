package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import matrix.MatrixNode;
import view.AppConstant;

public class XNode extends MatrixNode {

	PropertyChangeSupport support = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		support.addPropertyChangeListener(propertyName, listener);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		support.removePropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}

	@Override
	public void setOpen(boolean open) {
		boolean old = isOpen();
		if (old != open) {
			super.setOpen(open);
			support.firePropertyChange(AppConstant.NodeOpenStateChanged, old, open);
		}
	}

	@Override
	public void setVisited(boolean visited) {
		boolean old = isVisited();
		if (old != visited) {
			super.setVisited(visited);
			support.firePropertyChange(AppConstant.NodeVisitedStateChange, old, visited);
		}
	}

	@Override
	public void setSelected(boolean selected) {
		boolean old = isSelected();
		if (old != selected) {
			super.setSelected(selected);
			support.firePropertyChange(AppConstant.NodeSelectedStateChanged, old, selected);
		}
	}

	@Override
	public void setPredecessor(INode node) {
		INode old = getPredecessor();
		if (old != node) {
			support.firePropertyChange(AppConstant.NodePredecessorChanged, old, node);
			super.setPredecessor(node);
		}
	}

	@Override
	public void setCost(int cost) {
		int old = getCost();
		if (old != cost) {
			support.firePropertyChange(AppConstant.NodeCostChanged, old, cost);
			super.setCost(cost);
		}
	}

}
