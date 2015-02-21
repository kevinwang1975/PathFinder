package util;

import java.awt.Component;
import java.awt.Frame;

public class AWTUtil {

	private AWTUtil() {}
	
	public static Frame findFrame(Component c) {
		Frame f = null;
		if (Frame.class.isInstance(c)) {
			f = (Frame) c;
		}
		else if (c != null && c.getParent() != null) {
			f = findFrame(c.getParent());
		}
		return f;
	}
	
}
