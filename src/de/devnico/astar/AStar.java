package de.devnico.astar;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

public class AStar {

	public static void main(String[] args) {
		new AStar();
	}

	public Random random = new Random();

	public int cols, rows;
	public Node[][] grid;

	public List<Node> openSet;
	public List<Node> closedSet;

	public Node start, end;
	public int size, width, height;
	public List<Node> path;

	public Canvas canvas;
	public boolean running = true;

	public AStar() {
		width = height = 800;
		cols = rows = 50;
		size = width / cols;
		System.out.println("A*");

		JFrame frame = new JFrame();
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setFocusable(false);

		frame.add(canvas);
		frame.pack();

		grid = new Node[cols][rows];
		openSet = new ArrayList<>();
		closedSet = new ArrayList<>();
		path = new ArrayList<>();

		for (int x = 0; x < cols; x++) {
			for (int y = 0; y < rows; y++) {
				grid[x][y] = new Node(x, y, size);
			}
		}

		for (int x = 0; x < cols; x++) {
			for (int y = 0; y < rows; y++) {
				grid[x][y].addNeighbors(grid);
			}
		}

		start = grid[0][0];
		end = grid[cols - 1][rows - 1];
		start.setWall(false);
		end.setWall(false);

		openSet.add(start);

		while (running) {
			update();
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void update() {
		BufferStrategy bs = canvas.getBufferStrategy();
		if (bs == null) {
			canvas.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);

		Node current = null;
		if (openSet.size() > 0) {

			int winner = 0;
			for (int i = 0; i < openSet.size(); i++) {
				if (openSet.get(i).getF() < openSet.get(winner).getF()) {
					winner = i;
				}
			}
			current = openSet.get(winner);

			if (current == end) {
				System.out.println("DONE!");
				running = false;
			}

			openSet.remove(current);
			closedSet.add(current);

			List<Node> neighbors = current.getNeighbors();
			for (int i = 0; i < neighbors.size(); i++) {
				Node neighbor = neighbors.get(i);

				if (!closedSet.contains(neighbor) && !neighbor.isWall()) {
					int tempG = current.getG() + heuristic(neighbor, current);

					boolean newPath = false;
					if (openSet.contains(neighbor)) {
						if (tempG < neighbor.getG()) {
							neighbor.setG(tempG);
							newPath = true;
						}
					} else {
						neighbor.setG(tempG);
						newPath = true;
						openSet.add(neighbor);
					}

					if (newPath) {
						neighbor.setH(heuristic(neighbor, end));
						neighbor.setF(neighbor.getG() + neighbor.getH());
						neighbor.setPrevious(current);
					}
				}

			}
		} else {
			System.out.println("Keine Lösung");
			running = false;
			return;
		}

		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				grid[i][j].draw(g, Color.WHITE);
			}
		}

		// Showing open and closed Set
		// for (int i = 0; i < closedSet.size(); i++) {
		// closedSet.get(i).draw(g, new Color(255, 0, 0));
		// }
		//
		// for (int i = 0; i < openSet.size(); i++) {
		// openSet.get(i).draw(g, new Color(0, 255, 0));
		// }

		path = new ArrayList<>();
		Node temp = current;
		path.add(temp);

		while (temp.getPrevious() != null) {
			path.add(temp.getPrevious());
			temp = temp.getPrevious();
		}
		for (int i = 0; i < path.size() - 1; i++) {
			path.get(i).draw(g, new Color(0, 0, 255));
		}

		g.dispose();
		bs.show();
	}

	public int heuristic(Node a, Node b) {
		return (int) Math.sqrt(Math.pow(b.getY() - a.getY(), 2) + Math.pow(b.getX() - a.getX(), 2));
	}

}
