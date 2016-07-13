package br.ufsc.visualizao;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

import javax.swing.JButton;

public class DesenharLinhaEntreVertices {

	private JButton vertice1;
	private JButton vertice2;
	private Graphics2D g2;

	/*
	 * Desenha uma linha entre os dois vértices passados como parametro E cria
	 * uma seta indicando para qual o vertice 1 está conectando.
	 */
	public DesenharLinhaEntreVertices(JButton vertice1, JButton vertice2, Graphics2D g2) {
		this.vertice1 = vertice1;
		this.vertice2 = vertice2;
		this.g2 = g2;
		g2.setStroke(new BasicStroke(2F));
		linhaRetaNaHorizontal();
		linhaRetaNaVertical();
		linhaDiagonal();
	}

	public void linhaRetaNaHorizontal() {
		if (vertice1.getBounds().getCenterY() == vertice2.getBounds().getCenterY()) {
			if (vertice1.getBounds().getCenterX() < vertice2.getBounds().getCenterX()) {
				g2.drawLine((int) vertice1.getBounds().getCenterX(), (int) vertice1.getBounds().getCenterY(),
						vertice2.getBounds().x, (int) vertice2.getBounds().getCenterY());
				g2.drawLine(vertice2.getBounds().x, (int) vertice2.getBounds().getCenterY(), vertice2.getBounds().x - 5,
						(int) vertice2.getBounds().getCenterY() - 5);
				g2.drawLine(vertice2.getBounds().x, (int) vertice2.getBounds().getCenterY(), vertice2.getBounds().x - 5,
						(int) vertice2.getBounds().getCenterY() + 5);
			}
			if (vertice1.getBounds().getCenterX() > vertice2.getBounds().getCenterX()) {
				g2.drawLine((int) vertice1.getBounds().getCenterX(), (int) vertice1.getBounds().getCenterY(),
						vertice2.getBounds().x, (int) vertice2.getBounds().getCenterY());
				g2.drawLine(vertice1.getBounds().x, (int) vertice1.getBounds().getCenterY(), vertice1.getBounds().x + 5,
						(int) vertice1.getBounds().getCenterY() - 5);
				g2.drawLine(vertice1.getBounds().x, (int) vertice1.getBounds().getCenterY(), vertice1.getBounds().x + 5,
						(int) vertice1.getBounds().getCenterY() + 5);
			}
		}
	}

	public void linhaRetaNaVertical() {
		if (vertice1.getBounds().getCenterX() == vertice2.getBounds().getCenterX()) {
			if (vertice1.getBounds().getCenterY() < vertice2.getBounds().getCenterY()) {
				g2.drawLine((int) vertice1.getBounds().getCenterX(), (int) vertice1.getBounds().getMinY(),
						(int) vertice2.getBounds().getCenterX(), (int) vertice2.getBounds().getMaxY());
				g2.drawLine((int) vertice1.getBounds().getCenterX(), (int) vertice1.getBounds().getMinY(),
						(int) vertice1.getBounds().getCenterX() - 5, (int) vertice1.getBounds().getMinY() + 5);
				g2.drawLine((int) vertice1.getBounds().getCenterX(), (int) vertice1.getBounds().getMinY(),
						(int) vertice1.getBounds().getCenterX() + 5, (int) vertice1.getBounds().getMinY() + 5);
			}
			if (vertice1.getBounds().getCenterY() > vertice2.getBounds().getCenterY()) {
				g2.drawLine((int) vertice1.getBounds().getCenterX(), (int) vertice1.getBounds().getMinY(),
						(int) vertice2.getBounds().getCenterX(), (int) vertice2.getBounds().getMaxY());
				g2.drawLine((int) vertice2.getBounds().getCenterX(), (int) vertice2.getBounds().getMaxY(),
						(int) vertice2.getBounds().getCenterX() - 5, (int) vertice2.getBounds().getMaxY() + 5);
				g2.drawLine((int) vertice2.getBounds().getCenterX(), (int) vertice2.getBounds().getMaxY(),
						(int) vertice2.getBounds().getCenterX() + 5, (int) vertice2.getBounds().getMaxY() + 5);
			}
		}
	}

	/*
	 * Melhorar o local aonde irá ficar a flecha, utilizar angulação.
	 */
	public void linhaDiagonal() {
		int vertice1minX = (int) vertice1.getBounds().getMinX();
		int vertice1maxX = (int) vertice1.getBounds().getMaxX();
		int vertice1maxY = (int) vertice1.getBounds().getMaxY();
		int vertice1minY = (int) vertice1.getBounds().getMinY();
		int vertice2minX = (int) vertice2.getBounds().getMinX();
		int vertice2maxX = (int) vertice2.getBounds().getMaxX();
		int vertice2maxY = (int) vertice2.getBounds().getMaxY();
		int vertice2minY = (int) vertice2.getBounds().getMinY();
		if (vertice1.getBounds().getCenterX() > vertice2.getBounds().getCenterX()
				&& vertice1.getBounds().getCenterY() < vertice2.getBounds().getCenterY()) {
			g2.drawLine(vertice2maxX, vertice2minY, vertice1minX, vertice1maxY);
			g2.drawLine(vertice2maxX, vertice2minY, vertice2maxX + 5, vertice2minY + 3);
			g2.drawLine(vertice2maxX, vertice2minY, vertice2maxX, vertice2minY - 5);
		}
		if (vertice1.getBounds().getCenterX() < vertice2.getBounds().getCenterX()
				&& vertice1.getBounds().getCenterY() > vertice2.getBounds().getCenterY()) {
			g2.drawLine(vertice1maxX, vertice1minY, vertice2minX, vertice2maxY);
			g2.drawLine(vertice2minX, vertice2maxY, vertice2minX - 5, vertice2maxY - 2);
			g2.drawLine(vertice2minX, vertice2maxY, vertice2minX, vertice2maxY + 5);
		}
		if (vertice1.getBounds().getCenterX() > vertice2.getBounds().getCenterX()
				&& vertice1.getBounds().getCenterY() > vertice2.getBounds().getCenterY()) {
			g2.drawLine(vertice1minX, vertice1minY, vertice2maxX, vertice2maxY);
			g2.drawLine(vertice2maxX, vertice2maxY, vertice2maxX + 5, vertice2maxY + 2);
			g2.drawLine(vertice2maxX, vertice2maxY, vertice2maxX, vertice2maxY - 5);
		}
		if (vertice1.getBounds().getCenterX() < vertice2.getBounds().getCenterX()
				&& vertice1.getBounds().getCenterY() < vertice2.getBounds().getCenterY()) {
			g2.drawLine(vertice1maxX, vertice1maxY, vertice2minX, vertice2minY);
			g2.drawLine(vertice2minX, vertice2minY, vertice2minX - 5, vertice2minY - 2);
			g2.drawLine(vertice2minX, vertice2minY, vertice2minX + 2, vertice2minY - 5);
		}
	}
}
