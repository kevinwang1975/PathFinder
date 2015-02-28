package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.LayoutFocusTraversalPolicy;

import util.InputValidationDocumentListener;
import util.IntegerStringValidator;
import view.AppConstant.Mode;
import view.AppConstant.Painter;


@SuppressWarnings("serial")
public class ControlPanel extends JPanel {

	private Parameters parameters;

	private final JTextField 	cellSizeTextField     		= new JTextField();
	private final JTextField 	animationMsTextField  		= new JTextField();
	private final JTextField 	obstaclePercentTextField  	= new JTextField();
	private final JButton    	stopButton            		= new JButton("Stop");
	private final JButton    	clearMapButton        		= new JButton("Clear Map");
	private final JButton    	generateMapButton      		= new JButton("Generate Map");
	private final JButton    	applyCellSizeButton    		= new JButton("Apply");
	private final JButton    	applyAnimationMsButton 		= new JButton("Apply");
	private final ButtonGroup 	modeButtonGroup      		= new ButtonGroup();
	private final JPanel        modesPanel                  = new JPanel();
	private final JPanel 		mapGeneratingPanel 			= new JPanel();
	private final JPanel        mapSettingPanel             = new JPanel();
	private final JPanel        pathSearchPanel             = new JPanel();

	static class TextFiledDocumentListener extends InputValidationDocumentListener {

		JTextField textField;
		
		@Override
		protected void onValidInput(String text) {
			textField.setForeground(Color.black);
		}

		@Override
		protected void onInvalidInput(String text) {
			textField.setForeground(Color.red);
		}

		public JTextField getTextField() {
			return textField;
		}

		public void setTextField(JTextField textField) {
			this.textField = textField;
		}
		
	}
	
	public ControlPanel(Parameters aParameters) {
		parameters = aParameters;

		/*-------------------------------------------*
		 *                modes                  *
		 *-------------------------------------------*/
		modesPanel.setLayout(new GridLayout(0, 1, 2, 2));
		modesPanel.setBorder(BorderFactory.createTitledBorder("Mode"));

		ActionListener radioButtonListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				Mode oldMode = parameters.getMode();
				Mode newMode = Mode.getValue(((JRadioButton)evt.getSource()).getText());
				parameters.setMode(newMode);
				updateVisibility();
				firePropertyChange(AppConstant.StopRequested, null, null);
				firePropertyChange(AppConstant.ModeChanged, oldMode, newMode);
			}
		};

		Mode[] modes = Mode.values();
		for (Mode mode : modes) {
			JRadioButton button = new JRadioButton(mode.toString());
			button.setFocusPainted(false);
			button.addActionListener(radioButtonListener);
			button.setSelected(parameters.getMode() == mode);
			modeButtonGroup.add(button);
			modesPanel.add(button);
		}

		/*-------------------------------------------*
		 *               map editing                 *
		 *-------------------------------------------*/
		obstaclePercentTextField.setText(Integer.toString(parameters.getObstaclePercent()));
		TextFiledDocumentListener percentTextFieldDocListener = new TextFiledDocumentListener() {

			@Override
			protected void onValidInput(String text) {
				super.onValidInput(text);
				generateMapButton.setEnabled(true);
			}
			
			@Override
			protected void onInvalidInput(String text) {
				super.onInvalidInput(text);
				generateMapButton.setEnabled(false);
			}
		};
		percentTextFieldDocListener.setValidator(new IntegerStringValidator());
		percentTextFieldDocListener.setTextField(obstaclePercentTextField);
		obstaclePercentTextField.getDocument().addDocumentListener(percentTextFieldDocListener);

		generateMapButton.setFocusPainted(false);
		generateMapButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = obstaclePercentTextField.getText();
				parameters.setObstaclePercent(Integer.parseInt(text));
				firePropertyChange(AppConstant.GenerateMapRequested, null, null); 
			}
		});
		
		clearMapButton.setFocusPainted(false);
		clearMapButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				firePropertyChange(AppConstant.ClearMapRequested, null, null); 
			}
		});
		
		final JComboBox<Painter> painterComboBox = new JComboBox<Painter>();
		Painter[] painters = Painter.values();
		for (Painter painter : painters) {
			painterComboBox.addItem(painter);
		}
		painterComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				parameters.setPainter((Painter) painterComboBox.getSelectedItem());
			}
		});
		
		mapGeneratingPanel.setLayout(new GridLayout(0, 1, 2, 2));
		mapGeneratingPanel.add(new JLabel("Obstacle [%]:"));
		mapGeneratingPanel.add(obstaclePercentTextField);
		mapGeneratingPanel.add(generateMapButton);
		mapGeneratingPanel.add(clearMapButton);
		mapGeneratingPanel.add(new JLabel("Toolbox:"));
		mapGeneratingPanel.add(painterComboBox);
		
		/*-------------------------------------------*
		 *                path search                *
		 *-------------------------------------------*/
		animationMsTextField.setText(Integer.toString(parameters.getAnimationMs()));
		TextFiledDocumentListener animationMsTextFieldDocListener = new TextFiledDocumentListener() {

			@Override
			protected void onValidInput(String text) {
				super.onValidInput(text);
				applyAnimationMsButton.setEnabled(true);
			}
			
			@Override
			protected void onInvalidInput(String text) {
				super.onInvalidInput(text);
				applyAnimationMsButton.setEnabled(false);
			}
		};
		animationMsTextFieldDocListener.setValidator(new IntegerStringValidator());
		animationMsTextFieldDocListener.setTextField(animationMsTextField);
		animationMsTextField.getDocument().addDocumentListener(animationMsTextFieldDocListener);
		
		applyAnimationMsButton.setFocusPainted(false);
		applyAnimationMsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = animationMsTextField.getText();
				parameters.setAnimationMs(Integer.parseInt(text));
			}
		});
		
		stopButton.setFocusPainted(false);
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				firePropertyChange(AppConstant.StopRequested, null, null); 
			}
		});

		JPanel animationCtrlPanel = new JPanel();
		animationCtrlPanel.setLayout(new GridLayout(0, 1, 2, 2));
		animationCtrlPanel.add(new JLabel("Animation (ms):"));
		animationCtrlPanel.add(animationMsTextField);
		animationCtrlPanel.add(applyAnimationMsButton);
		
		JPanel runCtrlPanel = new JPanel();
		runCtrlPanel.setLayout(new GridLayout(0, 1, 2, 2));
		runCtrlPanel.add(stopButton);
		
		pathSearchPanel.setLayout(new BoxLayout(pathSearchPanel, BoxLayout.Y_AXIS));
		pathSearchPanel.add(animationCtrlPanel);
		pathSearchPanel.add(Box.createVerticalStrut(8));
		pathSearchPanel.add(runCtrlPanel);
		
		/*-------------------------------------------*
		 *                map setting                *
		 *-------------------------------------------*/
		cellSizeTextField.setText(Integer.toString(parameters.getCellSize()));
		TextFiledDocumentListener cellSizeTextFieldDocListener = new TextFiledDocumentListener() {

			@Override
			protected void onValidInput(String text) {
				super.onValidInput(text);
				applyCellSizeButton.setEnabled(true);
			}
			
			@Override
			protected void onInvalidInput(String text) {
				super.onInvalidInput(text);
				applyCellSizeButton.setEnabled(false);
			}
		};
		cellSizeTextFieldDocListener.setValidator(new IntegerStringValidator());
		cellSizeTextFieldDocListener.setTextField(cellSizeTextField);
		cellSizeTextField.getDocument().addDocumentListener(cellSizeTextFieldDocListener);
		
		applyCellSizeButton.setFocusPainted(false);
		applyCellSizeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = cellSizeTextField.getText();
				int oldValue = parameters.getCellSize();
				int newValue = Integer.parseInt(text);
				if (oldValue != newValue) {
					parameters.setCellSize(newValue);
					firePropertyChange(AppConstant.CellSizeChanged, oldValue, newValue); 
				}
			}
		});
		
		mapSettingPanel.setLayout(new GridLayout(0, 1, 2, 2));
		mapSettingPanel.add(new JLabel( "Cell Size (pxl):"));
		mapSettingPanel.add(cellSizeTextField);
		mapSettingPanel.add(applyCellSizeButton);

		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setFocusTraversalPolicy(new LayoutFocusTraversalPolicy());
		add(modesPanel);
		add(Box.createVerticalStrut(8));
		add(mapGeneratingPanel);
		add(pathSearchPanel);
		add(mapSettingPanel);
		add(Box.createVerticalStrut(2000));
		
		int preferedWidth = modesPanel.getPreferredSize().width;
		preferedWidth = Math.max(preferedWidth, mapGeneratingPanel.getPreferredSize().width);
		preferedWidth = Math.max(preferedWidth, pathSearchPanel.getPreferredSize().width);
		preferedWidth = Math.max(preferedWidth, mapSettingPanel.getPreferredSize().width);
		modesPanel.setPreferredSize(new Dimension(preferedWidth, modesPanel.getPreferredSize().height));
		mapGeneratingPanel.setPreferredSize(new Dimension(preferedWidth, mapGeneratingPanel.getPreferredSize().height));
		pathSearchPanel.setPreferredSize(new Dimension(preferedWidth, pathSearchPanel.getPreferredSize().height));
		mapSettingPanel.setPreferredSize(new Dimension(preferedWidth, mapSettingPanel.getPreferredSize().height));
		
		updateVisibility();
	}
	
	void updateVisibility() {
		mapGeneratingPanel.setVisible(parameters.getMode() == Mode.MAP_EDITING_MODE);
		pathSearchPanel.setVisible(parameters.getMode() == Mode.PATH_SEARCH_MODE);
		mapSettingPanel.setVisible(parameters.getMode() == Mode.MAP_SETTING_MODE);
	}

	public Parameters getParameters() {
		return parameters;
	}

}
