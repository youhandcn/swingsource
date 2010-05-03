package org.yccheok.blobsallad;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

/**
 */
public class Stick {
	private PointMass pointMassA;
	private PointMass pointMassB;
	private double length;
	private double lengthSquared;
	private Vector delta;

	public Stick(PointMass pointMassA, PointMass pointMassB) {
		this.pointMassA = pointMassA;
		this.pointMassB = pointMassB;
		this.length = this.pointMassDist(pointMassA, pointMassB);
		this.lengthSquared = this.length * this.length;
		this.delta = new Vector(0.0, 0.0);
	}

	private double pointMassDist(PointMass pointMassA, PointMass pointMassB) {
		double aXbX = pointMassA.getXPos() - pointMassB.getXPos();
		double aYbY = pointMassA.getYPos() - pointMassB.getYPos();
		return Math.sqrt(aXbX * aXbX + aYbY * aYbY);
	}

	public PointMass getPointMassA() {
		return this.pointMassA;
	}

	public PointMass getPointMassB() {
		return this.pointMassB;
	}

	public void scale(double scaleFactor) {
		this.length *= scaleFactor;
		this.lengthSquared = this.length * this.length;
	}

	// Key function.
	public void sc(Environment env) {
		double dotProd, scaleFactor;
		Vector pointMassAPos, pointMassBPos;

		pointMassAPos = this.pointMassA.getPos();
		pointMassBPos = this.pointMassB.getPos();

		this.delta.set(pointMassBPos);
		this.delta.sub(pointMassAPos);

		dotProd = this.delta.dotProd(this.delta);

		scaleFactor = this.lengthSquared / (dotProd + this.lengthSquared) - 0.5;
		this.delta.scale(scaleFactor);

		pointMassAPos.sub(this.delta);
		pointMassBPos.add(this.delta);
	}

	public void setForce(Vector force) {
		this.pointMassA.setForce(force);
		this.pointMassB.setForce(force);
	}

	public void addForce(Vector force) {
		this.pointMassA.addForce(force);
		this.pointMassB.addForce(force);
	}

	public void move(double dt) {
		this.pointMassA.move(dt);
		this.pointMassB.move(dt);
	}

	public void draw(Graphics graphics, double scaleFactor) {
		// this.pointMassA.draw(ctx, scaleFactor);
		// this.pointMassB.draw(ctx, scaleFactor);
		//
		// ctx.lineWidth = 3;
		// ctx.fillStyle = '#000000';
		// ctx.strokeStyle = '#000000';
		// ctx.beginPath();
		// ctx.moveTo(this.pointMassA.getXPos() * scaleFactor,
		// this.pointMassA.getYPos() * scaleFactor);
		// ctx.lineTo(this.pointMassB.getXPos() * scaleFactor,
		// this.pointMassB.getYPos() * scaleFactor);
		// ctx.stroke();
		this.pointMassA.draw(graphics, scaleFactor);
		this.pointMassB.draw(graphics, scaleFactor);

		BasicStroke stroke = new BasicStroke(3.0f);
		Graphics2D g2d = (Graphics2D) graphics;

		g2d.setColor(Color.BLACK);
		g2d.setStroke(stroke);

		GeneralPath generalPath = new GeneralPath();
		generalPath.moveTo(this.pointMassA.getXPos() * scaleFactor,
				this.pointMassA.getYPos() * scaleFactor);
		generalPath.lineTo(this.pointMassB.getXPos() * scaleFactor,
				this.pointMassB.getYPos() * scaleFactor);
		g2d.draw(generalPath);
	}
}
