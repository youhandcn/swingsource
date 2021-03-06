/**
 * Copyright (c) 2006, Sun Microsystems, Inc
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above
 *     copyright notice, this list of conditions and the following
 *     disclaimer in the documentation and/or other materials provided
 *     with the distribution.
 *   * Neither the name of the TimingFramework project nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * ButtonAnimation
 *
 * This class demonstrates the functionality of the Timing Framework library.
 * It uses a GUI that allows the user to enter various settings of the
 * animation and then start it.  The animation consists of two elements:
 * a button called "Animating Button" (which demonstrates the property-setting
 * capabilities of the framework) and a string called "fraction" that
 * demonstrates the more basic interaction between the TimingController and
 * TimingTarget interface.
 */

package org.jdesktop.animation.timing.examples;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.interpolation.DiscreteInterpolator;
import org.jdesktop.animation.timing.interpolation.Interpolator;
import org.jdesktop.animation.timing.interpolation.KeyFrames;
import org.jdesktop.animation.timing.interpolation.KeyTimes;
import org.jdesktop.animation.timing.interpolation.KeyValues;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import org.jdesktop.animation.timing.interpolation.SplineInterpolator;

/**
 * This class is the heart of the demo; in its actionPerformed() method it takes
 * the information from the GUI controls, sets up the animation according to
 * those settings, and starts the animation. It also handles the tedious work of
 * setting up the rather involved GUI.
 */
class ControlPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	NumberFormat intFormat;
	NumberFormat doubleFormat;
	JFormattedTextField beginField;
	JFormattedTextField durationField;
	JFormattedTextField resolutionField;
	JFormattedTextField repeatCountField;
	JFormattedTextField initialFractionField;
	JRadioButton loopButton, reverseButton;
	JRadioButton holdButton, resetButton;
	JRadioButton forwardButton, backwardButton;
	JRadioButton linearButton, discreteButton, nonlinearButton;
	JRadioButton twoButton, threeButton, fourButton;
	JButton goButton, stopButton, pauseButton, resumeButton;
	JFormattedTextField time[] = new JFormattedTextField[4];
	JFormattedTextField valueX[] = new JFormattedTextField[4];
	JFormattedTextField valueY[] = new JFormattedTextField[4];
	JFormattedTextField splineX0[] = new JFormattedTextField[4];
	JFormattedTextField splineY0[] = new JFormattedTextField[4];
	JFormattedTextField splineX1[] = new JFormattedTextField[4];
	JFormattedTextField splineY1[] = new JFormattedTextField[4];
	JFormattedTextField accelerationField;
	JFormattedTextField decelerationField;
	Animator animation = null;
	AnimationView animationView; // We will pass this into Animation

	// so that the animation fraction can
	// can be passed into AnimationView
	// during the animation.

	/**
	 * "Go" button pressed: get the values from the controls and
	 * getInstance/start the animation. This is the heart of the whole demo,
	 * where we getInstance and run a Animator based on the current state of the
	 * GUI.
	 */
	public void actionPerformed(ActionEvent ae) {
		// First, check to see whether someone is pausing/resuming/stopping the
		// animation
		if (animation != null) {
			if (ae.getSource().equals(pauseButton)) {
				animation.pause();
				return;
			} else if (ae.getSource().equals(resumeButton)) {
				animation.resume();
				return;
			} else if (ae.getSource().equals(pauseButton)) {
				animation.stop();
				return;
			}
		}

		// If we are already running an animation, stop it pending
		// a restart with the new control values
		if (animation != null && animation.isRunning()) {
			animation.stop();
		}
		// First, distinguish between a toggle of the number of
		// keyframes and a click on the GO button
		if (!ae.getSource().equals(goButton)) {
			enableTextFields(twoButton.isSelected() ? 2 : (threeButton
					.isSelected() ? 3 : 4));
			return;
		}

		// Must be a GO action; setup and start the animation

		// First, set up the Cycle and Envelope needed by
		// Animator
		int begin = getFieldValueAsInt(beginField);
		int duration = getFieldValueAsInt(durationField);
		int resolution = getFieldValueAsInt(resolutionField);
		double repeatCount = getFieldValueAsDouble(repeatCountField);
		double initialFraction = getFieldValueAsDouble(initialFractionField);
		Animator.Direction direction = forwardButton.isSelected() ? Animator.Direction.FORWARD
				: Animator.Direction.BACKWARD;
		Animator.RepeatBehavior repeatBehavior = reverseButton.isSelected() ? Animator.RepeatBehavior.REVERSE
				: Animator.RepeatBehavior.LOOP;
		Animator.EndBehavior behavior = (holdButton.isSelected()) ? Animator.EndBehavior.HOLD
				: Animator.EndBehavior.RESET;

		// Next, set up the property setter information based on the
		// keyframes-related widgets
		JComponent animatingComponent = animationView.getAnimatingComponent();
		int numKeyframes = twoButton.isSelected() ? 2 : (threeButton
				.isSelected() ? 3 : 4);
		float times[] = new float[numKeyframes];
		Point points[] = new Point[numKeyframes];
		Interpolator interpolators[] = null;
		if (nonlinearButton.isSelected()) {
			interpolators = new Interpolator[numKeyframes - 1];
			for (int i = 0; i < numKeyframes - 1; ++i) {
				interpolators[i] = new SplineInterpolator(
						(float) getFieldValueAsDouble(splineX0[i]),
						(float) getFieldValueAsDouble(splineY0[i]),
						(float) getFieldValueAsDouble(splineX1[i]),
						(float) getFieldValueAsDouble(splineY1[i]));
			}
		}
		for (int i = 0; i < numKeyframes; ++i) {
			times[i] = (float) getFieldValueAsDouble(time[i]);
			points[i] = new Point(getFieldValueAsInt(valueX[i]),
					getFieldValueAsInt(valueY[i]));
		}
		KeyTimes keyTimes = new KeyTimes(times);
		KeyValues<Point> keyValues = KeyValues.create(points);
		// KeySplines keySplines = (interpolationType ==
		// InterpolationType.NONLINEAR) ?
		// new KeySplines(splines) : null;
		KeyFrames<Point> keyFrames;
		if (nonlinearButton.isSelected()) {
			keyFrames = new KeyFrames<Point>(keyValues, keyTimes, interpolators);
		} else if (discreteButton.isSelected()) {
			keyFrames = new KeyFrames<Point>(keyValues, keyTimes,
					DiscreteInterpolator.getInstance());
		} else {
			keyFrames = new KeyFrames<Point>(keyValues, keyTimes,
					(Interpolator) null);
		}

		// Create the Animator with a PropertySetter as
		// the TimingTarget; this will do the work of setting the property
		// specified above
		animation = new Animator(duration, repeatCount, repeatBehavior,
				new PropertySetter(animatingComponent, "location", keyFrames));
		animation.setResolution(resolution);
		animation.setStartDelay(begin);
		animation.setEndBehavior(behavior);
		animation.setStartFraction((float) initialFraction);
		animation.setStartDirection(direction);

		// Now add another TimingTarget to the animation; this will track
		// and display the animation fraction
		animation.addTarget(animationView);
		animationView.setTimer(animation);

		// Vary the acceleration/deceleration values appropriately
		float acceleration = (float) getFieldValueAsDouble(accelerationField);
		float deceleration = (float) getFieldValueAsDouble(decelerationField);
		animation.setAcceleration(acceleration);
		animation.setDeceleration(deceleration);

		// Finally: start the animation
		animation.start();
	}

	//
	// GUI Setup
	// The rest of the methods in this class are all about setting up and
	// running the GUI of the control panel
	//

	private void setupCycleGUI() {
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridBagLayout());
		textPanel.add(new JLabel("Duration (ms)"), new GridBagConstraints(0, 0,
				2, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(5, 5, 0, 0), 0, 0));
		durationField = new JFormattedTextField(intFormat);
		durationField.setValue(1000);
		textPanel.add(durationField, new GridBagConstraints(2, 0, 1, 1, .5, 0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 0, 0), 0, 0));
		textPanel.add(new JLabel("Resolution (ms)"), new GridBagConstraints(4,
				0, 2, 1, 0, 0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
		resolutionField = new JFormattedTextField(intFormat);
		resolutionField.setValue(0);
		textPanel.add(resolutionField, new GridBagConstraints(6, 0, 1, 1, .5,
				0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 0, 0), 0, 0));

		textPanel.add(new JLabel("Initial Fraction (0-1)"),
				new GridBagConstraints(0, 1, 2, 1, 0, 0,
						GridBagConstraints.WEST, GridBagConstraints.NONE,
						new Insets(5, 5, 0, 0), 0, 0));
		initialFractionField = new JFormattedTextField(doubleFormat);
		initialFractionField.setValue(0);
		textPanel.add(initialFractionField, new GridBagConstraints(2, 1, 1, 1,
				.5, 0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 0), 0, 0));
		textPanel.add(new JLabel("Direction"), new GridBagConstraints(4, 1, 2,
				1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(5, 5, 0, 0), 0, 0));
		forwardButton = new JRadioButton("Forward", true);
		backwardButton = new JRadioButton("Backward");
		ButtonGroup group = new ButtonGroup();
		group.add(forwardButton);
		group.add(backwardButton);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(forwardButton);
		buttonPanel.add(backwardButton);
		textPanel.add(buttonPanel, new GridBagConstraints(6, 1, 2, 1, .5, 0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 0, 5), 0, 0));

		textPanel.setBorder(new TitledBorder("Cycle"));
		add(textPanel, new GridBagConstraints(0, 0, 8, 1, .5, 0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 0, 5), 0, 0));
	}

	private void setupEnvelopeGUI() {
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridBagLayout());
		textPanel.add(new JLabel("Time to Begin (ms):"),
				new GridBagConstraints(0, 0, 2, 1, 0, 0,
						GridBagConstraints.WEST, GridBagConstraints.NONE,
						new Insets(5, 5, 0, 0), 0, 0));
		beginField = new JFormattedTextField(intFormat);
		beginField.setValue(0);
		textPanel.add(beginField, new GridBagConstraints(2, 0, 1, 1, .5, 0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 0, 0), 0, 0));
		textPanel.add(new JLabel("RepeatCount:"), new GridBagConstraints(4, 0,
				2, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(5, 5, 0, 0), 0, 0));
		repeatCountField = new JFormattedTextField(doubleFormat);
		repeatCountField.setValue(2);
		textPanel.add(repeatCountField, new GridBagConstraints(6, 0, 1, 1, .5,
				0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 0, 0), 0, 0));
		// Direction radio buttons
		loopButton = new JRadioButton("Loop", true);
		reverseButton = new JRadioButton("Reverse");
		ButtonGroup group = new ButtonGroup();
		group.add(loopButton);
		group.add(reverseButton);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(new TitledBorder("Repeat Behavior"));
		buttonPanel.add(loopButton);
		buttonPanel.add(reverseButton);
		textPanel.add(buttonPanel, new GridBagConstraints(0, 1, 4, 1, .5, 0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 0, 0), 0, 0));

		// EndBehavior radio buttons
		holdButton = new JRadioButton("Hold", true);
		resetButton = new JRadioButton("Reset");
		group = new ButtonGroup();
		group.add(holdButton);
		group.add(resetButton);

		buttonPanel = new JPanel();
		buttonPanel.setBorder(new TitledBorder("End Behavior"));
		buttonPanel.add(holdButton);
		buttonPanel.add(resetButton);
		textPanel.add(buttonPanel, new GridBagConstraints(4, 1, 4, 1, .5, 0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 0, 5), 0, 0));
		textPanel.setBorder(new TitledBorder("Envelope"));
		add(textPanel, new GridBagConstraints(0, 1, 8, 1, .5, 0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 0, 5), 0, 0));
	}

	private void setupPropertySetterGUI() {
		// Interpolation radio buttons
		linearButton = new JRadioButton("Linear", true);
		discreteButton = new JRadioButton("Discrete");
		nonlinearButton = new JRadioButton("Nonlinear");
		ButtonGroup group = new ButtonGroup();
		group.add(linearButton);
		group.add(discreteButton);
		group.add(nonlinearButton);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(new TitledBorder("Interpolation"));
		buttonPanel.add(linearButton);
		buttonPanel.add(discreteButton);
		buttonPanel.add(nonlinearButton);
		linearButton.addActionListener(this);
		discreteButton.addActionListener(this);
		nonlinearButton.addActionListener(this);
		add(buttonPanel, new GridBagConstraints(0, 3, 5, 1, .5, 0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 0, 5), 0, 0));

		// Num keyframes radio buttons
		twoButton = new JRadioButton("2", true);
		threeButton = new JRadioButton("3");
		fourButton = new JRadioButton("4");
		group = new ButtonGroup();
		group.add(twoButton);
		group.add(threeButton);
		group.add(fourButton);

		buttonPanel = new JPanel();
		buttonPanel.setBorder(new TitledBorder("Number of key frames"));
		buttonPanel.add(twoButton);
		buttonPanel.add(threeButton);
		buttonPanel.add(fourButton);
		twoButton.addActionListener(this);
		threeButton.addActionListener(this);
		fourButton.addActionListener(this);
		add(buttonPanel, new GridBagConstraints(5, 3, 3, 1, .5, 0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 0, 5), 0, 0));
		setupKeyTimesGUI();
		setupKeyValuesGUI();
		setupKeySplinesGUI();
	}

	private void setupKeyTimesGUI() {
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridBagLayout());
		for (int i = 0; i < 4; ++i) {
			textPanel.add(new JLabel("time" + i + ":"), new GridBagConstraints(
					0, i, 1, 1, 0, 0, GridBagConstraints.EAST,
					GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
			time[i] = new JFormattedTextField(doubleFormat);
			if (i == 0) {
				time[i].setValue(0);
			} else {
				time[i].setValue(1);
			}
			textPanel.add(time[i], new GridBagConstraints(1, i, 1, 1, .5, 0,
					GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
					new Insets(5, 5, 0, 0), 0, 0));
		}
		textPanel.setBorder(new TitledBorder("KeyTimes (0-1)"));
		add(textPanel, new GridBagConstraints(0, 4, 1, 1, .5, 0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 0, 5), 0, 0));
	}

	private void setupKeyValuesGUI() {
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridBagLayout());
		for (int i = 0; i < 4; ++i) {
			textPanel.add(new JLabel("(x, y)" + i + ":"),
					new GridBagConstraints(0, i, 1, 1, 0, 0,
							GridBagConstraints.EAST, GridBagConstraints.NONE,
							new Insets(5, 5, 0, 0), 0, 0));
			// X
			valueX[i] = new JFormattedTextField(intFormat);
			valueX[i].setValue(0);
			textPanel.add(valueX[i], new GridBagConstraints(1, i, 1, 1, .5, 0,
					GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
					new Insets(5, 5, 0, 0), 0, 0));
			// Y
			valueY[i] = new JFormattedTextField(intFormat);
			if (i == 0) {
				valueY[i].setValue(0);
			} else {
				valueY[i].setValue(190);
			}
			textPanel.add(valueY[i], new GridBagConstraints(2, i, 1, 1, .5, 0,
					GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
					new Insets(5, 5, 0, 0), 0, 0));
		}
		textPanel.setBorder(new TitledBorder("KeyValues"));
		add(textPanel, new GridBagConstraints(1, 4, 2, 1, .5, 0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 0, 5), 0, 0));
	}

	private void setupKeySplinesGUI() {
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridBagLayout());
		for (int i = 0; i < 3; ++i) {
			textPanel.add(new JLabel("Spline" + i + ":"),
					new GridBagConstraints(0, i, 1, 1, 0, 0,
							GridBagConstraints.EAST, GridBagConstraints.NONE,
							new Insets(5, 5, 0, 0), 0, 0));
			// X0
			splineX0[i] = new JFormattedTextField(doubleFormat);
			splineX0[i].setValue(0);
			textPanel.add(splineX0[i],
					new GridBagConstraints(1, i, 1, 1, .5, 0,
							GridBagConstraints.CENTER,
							GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0,
									0), 0, 0));
			// Y0
			splineY0[i] = new JFormattedTextField(doubleFormat);
			splineY0[i].setValue(0);
			textPanel.add(splineY0[i],
					new GridBagConstraints(2, i, 1, 1, .5, 0,
							GridBagConstraints.CENTER,
							GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0,
									0), 0, 0));
			// X1
			splineX1[i] = new JFormattedTextField(doubleFormat);
			splineX1[i].setValue(1);
			textPanel.add(splineX1[i],
					new GridBagConstraints(3, i, 1, 1, .5, 0,
							GridBagConstraints.CENTER,
							GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0,
									0), 0, 0));
			// Y1
			splineY1[i] = new JFormattedTextField(doubleFormat);
			splineY1[i].setValue(1);
			textPanel.add(splineY1[i],
					new GridBagConstraints(4, i, 1, 1, .5, 0,
							GridBagConstraints.CENTER,
							GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0,
									0), 0, 0));
		}
		textPanel.setBorder(new TitledBorder("KeySplines (0-1)"));
		add(textPanel, new GridBagConstraints(3, 4, 5, 2, .5, 0,
				GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 0, 5), 0, 0));
	}

	private void setupAccelerationGUI() {
		// Acceleration/Deceleration
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridBagLayout());
		textPanel.add(new JLabel("acceleration"), new GridBagConstraints(0, 0,
				2, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(5, 5, 0, 0), 0, 0));
		accelerationField = new JFormattedTextField(doubleFormat);
		accelerationField.setValue(0);
		textPanel.add(accelerationField, new GridBagConstraints(2, 0, 1, 1, .5,
				0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 0, 0), 0, 0));
		textPanel.add(new JLabel("deceleration"), new GridBagConstraints(3, 0,
				2, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(5, 5, 0, 0), 0, 0));
		decelerationField = new JFormattedTextField(doubleFormat);
		decelerationField.setValue(0);
		textPanel.add(decelerationField, new GridBagConstraints(5, 0, 1, 1, .5,
				0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 0, 0), 0, 0));
		textPanel.setBorder(new TitledBorder("Acceleration (acc + dec <= 1)"));
		add(textPanel, new GridBagConstraints(0, 5, 5, 1, .5, 0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 0, 5), 0, 0));
	}

	/**
	 * Set up the Go/Stop/Pause/Resume buttons
	 */
	private void setupActionsGUI() {
		JPanel buttonPanel = new JPanel();
		add(buttonPanel, new GridBagConstraints(5, 5, 3, 1, .5, 0,
				GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5,
						5, 0, 5), 0, 0));
		buttonPanel.setBorder(new TitledBorder("Actions"));
		buttonPanel.setLayout(new GridBagLayout());
		pauseButton = new JButton("Pause");
		resumeButton = new JButton("Resume");
		stopButton = new JButton("Stop");
		goButton = new JButton("Go");
		pauseButton.addActionListener(this);
		resumeButton.addActionListener(this);
		stopButton.addActionListener(this);
		goButton.addActionListener(this);
		buttonPanel.add(pauseButton, new GridBagConstraints(0, 0, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 0, 0), 0, 0));
		buttonPanel.add(resumeButton, new GridBagConstraints(0, 1, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 0, 0), 0, 0));
		buttonPanel.add(stopButton, new GridBagConstraints(1, 0, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 0, 0), 0, 0));
		buttonPanel.add(goButton, new GridBagConstraints(1, 1, 1, 1, 0, 0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 0, 0), 0, 0));
	}

	/**
	 * Sets up the GUI for all of the text fields and radio buttons that control
	 * the animation. There is some attempt to group the controls logically
	 * (Cycle, Envelope, etc.).
	 */
	public ControlPanel(AnimationView animationView) {
		this.animationView = animationView;

		setLayout(new GridBagLayout());
		intFormat = NumberFormat.getNumberInstance();
		intFormat.setParseIntegerOnly(true);
		doubleFormat = NumberFormat.getNumberInstance();

		setupCycleGUI();

		setupEnvelopeGUI();

		setupPropertySetterGUI();

		setupAccelerationGUI();

		setupActionsGUI();

		// Animation View
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(animationView);
		TitledBorder animationBorder = new TitledBorder("Animation");
		animationBorder.setTitleFont(panel.getFont().deriveFont(18f));
		animationBorder.setTitleJustification(TitledBorder.CENTER);
		panel.setBorder(animationBorder);
		add(panel, new GridBagConstraints(0, 6, 8, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						5, 5, 5, 5), 0, 0));

		// Set text fields to proper editable state
		enableTextFields(2);
	}

	/**
	 * Utility method to retrieve textfield value as integer
	 */
	private int getFieldValueAsInt(JTextField field) {
		String text = field.getText();
		int returnValue = 0;
		try {
			returnValue = NumberFormat.getNumberInstance().parse(text)
					.intValue();
		} catch (Exception e) {
			System.out.println("Number formation exception: " + e);
		}
		return returnValue;
	}

	/**
	 * Utility method to retrieve textfield value as double
	 */
	private double getFieldValueAsDouble(JTextField field) {
		String text = field.getText();
		double returnValue = 0;
		try {
			returnValue = NumberFormat.getNumberInstance().parse(text)
					.doubleValue();
		} catch (Exception e) {
			System.out.println("Number formation exception: " + e);
		}
		return returnValue;
	}

	/**
	 * This method enables/disables the text fields for KeyTimes, KeyValues, and
	 * KeySplines based on the current setting in the "Number of key frames"
	 * panel plus the setting of the Interpolation buttons
	 */
	private void enableTextFields(int numKeyFrames) {
		// Disable appropriate time, value, spline widgets
		// First, set splines to default (non-editable) state
		for (int i = 0; i < 3; ++i) {
			splineX0[i].setEditable(false);
			splineY0[i].setEditable(false);
			splineX1[i].setEditable(false);
			splineY1[i].setEditable(false);
		}
		float timeFraction = 1.0f / (numKeyFrames - 1);
		for (int i = 0; i < numKeyFrames; ++i) {
			time[i].setEditable(true);
			time[i].setValue(timeFraction * i);
			valueX[i].setEditable(true);
			valueY[i].setEditable(true);
			if (i < (numKeyFrames - 1) && nonlinearButton.isSelected()) {
				splineX0[i].setEditable(true);
				splineY0[i].setEditable(true);
				splineX1[i].setEditable(true);
				splineY1[i].setEditable(true);
			}
		}
		for (int i = numKeyFrames; i < 4; ++i) {
			time[i].setEditable(false);
			valueX[i].setEditable(false);
			valueY[i].setEditable(false);
			splineX0[i - 1].setEditable(false);
			splineY0[i - 1].setEditable(false);
			splineX1[i - 1].setEditable(false);
			splineY1[i - 1].setEditable(false);
		}
	}
}

/**
 * The canvas where the animation is drawn. Most of the work is done by the
 * superclass (simply drawing the animating button at its proper location), but
 * we override paintComponent() in order to display the animation fraction
 * amount in the view.
 */
class AnimationView extends JComponent implements TimingTarget {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton button = new JButton("Animating Button");
	float currentFraction;
	Animator timer = null;

	public AnimationView() {
		try {
			add(button);
			button.setBounds(0, 0, 150, 20);
		} catch (Exception e) {
			System.out.println("Problem adding button: " + e);
		}
	}

	public void setTimer(Animator timer) {
		this.timer = timer;
	}

	JComponent getAnimatingComponent() {
		return button;
	}

	//
	// TimingTarget methods
	// We use these just to set the value of currentFraction, which will
	// be displayed in the AnimationView upon repaint
	//

	public void begin() {
		currentFraction = 0;
	}

	public void end() {
		currentFraction = 1;
	}

	public void repeat() {
	}

	public void timingEvent(float fraction) {
		currentFraction = fraction;
		int x = getWidth() - 100;
		int y = 0;
		paintImmediately(x, y, 100, 10);
	}

	/**
	 * Draws the image in the proper location according to where it is in the
	 * animation cycle (given by currentFraction).
	 */
	public void paintComponent(Graphics g) {
		g.drawString("fraction = " + currentFraction, getWidth() - 100, 10);
	}
}

/**
 * Main class that starts it off. This creates the view where the animation is
 * drawn (AnimationView) and the GUI that controls the aimation (ControlPanel).
 */
public class ButtonAnimation {

	/**
	 * Creates the window and sub-panels and makes it all visible
	 */
	private static void createAndShowGUI() {
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		JFrame f = new JFrame("Timing Demo");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		f.setSize(500, 800);
		AnimationView animationView = new AnimationView();
		ControlPanel controlPanel = new ControlPanel(animationView);
		f.add(controlPanel, BorderLayout.CENTER);
		f.setVisible(true);
	}

	public static void main(String[] args) {
		// Need to do GUI stuff like making the JFrame visible on the
		// Event Dispatch Thread; do this via invokeLater()

		Runnable doCreateAndShowGUI = new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		};
		SwingUtilities.invokeLater(doCreateAndShowGUI);
	}
}
