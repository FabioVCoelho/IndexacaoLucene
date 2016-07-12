package br.ufsc.visualizao;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.lucene.queryparser.classic.ParseException;

import br.ufsc.IndexacaoLucene.SearcherComFiltroDeDocumentos;

public class Painel extends JPanel {

	private static final long serialVersionUID = 1L;
	Graphics g;
	private List<String> pathsSeparados;
	private List<JButton> vertices;

	public Painel() {
		vertices();
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
		this.setVisible(true);
		this.setSize(500, 500);
	}

	private void vertices() {
		/*
		 * Pesquisa utilizando o método de indexação
		 */
		List<String> camposRetornaveis = null;
		try {
			camposRetornaveis = new SearcherComFiltroDeDocumentos().pesquisar();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		pathsSeparados = new ArrayList<String>();
		vertices = new ArrayList<JButton>();
		pathsSeparados.addAll(new SepararString().camposDaPesquisa(camposRetornaveis));

		// Cria vertices para cada valor de campo diferente entre si.
		for (String string : pathsSeparados)
			if (!vertices.toString().contains(string))
				vertices.add(new Retangulo(string, 10, 10, 20, 20));

		// Cria os botões no painel.
		for (JButton jButton : vertices)
			this.add(jButton);

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);

		/*
		 * Cria as linhas entre os vértices correspondentes à seu caminho.
		 * Cria um mouseListener para ao entrar no vértice, mostrar todos os caminhos
		 * que o mesmo seguiu.
		 * Algum bug no i = 9 que o vertice2 está sendo o CURRICULO-VITAE, e não tem nenhum
		 * vértice que deveria se ligar ao mesmo.
		 */
		for (int i = 0; i < pathsSeparados.size() - 1; i++) {
			if (pathsSeparados.get(i + 1).equals(pathsSeparados.get(0))) {
			} else {
				JButton vertice1 = vertices.get(acharIndiceDeVerticeIgualAoStringDoPath(pathsSeparados.get(i)));
				JButton vertice2 = vertices.get(acharIndiceDeVerticeIgualAoStringDoPath(pathsSeparados.get(i + 1)));
				desenharLinhaEntreVertices(vertice1, vertice2, g2);
				((Retangulo) vertice1).setToolTip(vertice1.toString() + " -> " + vertice2.toString());
				vertice1.addMouseListener(new ToolTipDoVertice(vertice1));
			}
		}
	}

	/*
	 * Compara o nome do pathsSeparados com o valor dos componentes de vertices.
	 * Utilizado para saber para qual o vertice que deve ser conectado.
	 */
	private int acharIndiceDeVerticeIgualAoStringDoPath(String string) {
		for (int i = 0; i < vertices.size(); i++) {
			if (vertices.get(i).toString().equals(string))
				return i;
		}
		return 0;
	}

	/*
	 * Desenha uma linha entre os dois vértices passados como parametro
	 */
	private void desenharLinhaEntreVertices(JButton jButton, JButton jButton2, Graphics2D g2) {
		g2.setStroke(new BasicStroke(2F));
		g2.drawLine(jButton.getBounds().x, (int) jButton.getBounds().getCenterY(), jButton2.getBounds().x,
				(int) jButton2.getBounds().getCenterY());
	}
}
