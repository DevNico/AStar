package de.devnico.astar;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Node {

	private int f, g, h;
	private List<Node> neighbors;
	private Node previous;

	private int x, y, size;
	private boolean wall;

	public Node(int x, int y, int size) {
		this.f = 0;
		this.g = 0;
		this.h = 0;
		this.neighbors = new ArrayList<>();
		this.previous = null;

		this.x = x;
		this.y = y;
		this.size = size;
		this.wall = false;

		if (new Random().nextDouble() < 0.3) {
			this.wall = true;
		}
	}

	public void draw(Graphics g, Color col) {
		g.setColor(col);
		if (isWall()) {
			g.setColor(Color.BLACK);
			g.fillRect(this.x * size, this.y * size, size, size);
		} else
			g.fillRect(this.x * size, this.y * size, size, size);
	}

	public void addNeighbors(Node[][] grid) {
		int cols = grid.length;
		int rows = grid[0].length;

		if (x < cols - 1)
			this.neighbors.add(grid[x + 1][y]);
		if (x > 0)
			this.neighbors.add(grid[x - 1][y]);
		if (y < rows - 1)
			this.neighbors.add(grid[x][y + 1]);
		if (y > 0)
			this.neighbors.add(grid[x][y - 1]);
		if (x > 0 && y > 0)
			this.neighbors.add(grid[x - 1][y - 1]);
		if (x < cols - 1 && y > 0)
			this.neighbors.add(grid[x + 1][y - 1]);
		if (x > 0 && y < rows - 1)
			this.neighbors.add(grid[x - 1][y + 1]);
		if (x < cols - 1 && y < rows - 1)
			this.neighbors.add(grid[x + 1][y + 1]);
	}

	public int getF() {
		return f;
	}

	public int getG() {
		return g;
	}

	public int getH() {
		return h;
	}

	public List<Node> getNeighbors() {
		return neighbors;
	}

	public Node getPrevious() {
		return previous;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getSize() {
		return size;
	}

	public boolean isWall() {
		return wall;
	}

	public void setF(int f) {
		this.f = f;
	}

	public void setG(int g) {
		this.g = g;
	}

	public void setH(int h) {
		this.h = h;
	}

	public void setNeighbors(List<Node> neighbors) {
		this.neighbors = neighbors;
	}

	public void setPrevious(Node previous) {
		this.previous = previous;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setWall(boolean wall) {
		this.wall = wall;
	}

}
