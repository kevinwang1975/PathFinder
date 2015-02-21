package util;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public abstract class InputValidationDocumentListener implements DocumentListener {
	
	protected abstract void onValidInput(String text);
	protected abstract void onInvalidInput(String text);
	
	IValidator<String> validator;
	
	void onChange(DocumentEvent evt) {
		Document doc = evt.getDocument();
		try {
			String text = doc.getText(0, doc.getLength());
			if (validator.validates(text)) {
				onValidInput(text);
			}
			else {
				onInvalidInput(text);
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void removeUpdate(DocumentEvent e) {
		onChange(e);
	}
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		onChange(e);
	}
	
	@Override
	public void changedUpdate(DocumentEvent e) {
		onChange(e);
	}

	public IValidator<String> getValidator() {
		return validator;
	}

	public void setValidator(IValidator<String> validator) {
		this.validator = validator;
	}
	
}