import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Board {
    private int[][] tiles;
    private int dimension;
    private int hamming;
    private int manhattan;
    private int[] blankPosition = {0, 0};

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = tiles;
        this.dimension = tiles.length;
        int hamming = 0;
        int manhattan = 0;
        for(int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length; col++) {
                // Set the blank node position
                if (tiles[row][col] == 0) {
                    this.blankPosition[0] = row;
                    this.blankPosition[1] = col;
                    continue;
                }

                int rightTile = row * tiles.length + col + 1;
                if (tiles[row][col] == rightTile) {
                    hamming++;
                } else {
                    int diff = Math.abs(tiles[row][col] - rightTile);
                    manhattan += (diff/tiles.length) + (diff%tiles.length);
                }
            }
        }

        this.hamming = hamming;
        this.manhattan = manhattan;
    }

    // string representation of this board
    public String toString() {
        String boardStr = "";
        for(int row = 0; row < this.tiles.length; row++) {
            for (int col = 0; col < this.tiles.length; col++) {
                boardStr += this.tiles[row][col] + " ";
            }
            boardStr += "\n";
        }

        return boardStr;
    }

    // board dimension n
    public int dimension() {
        return this.dimension;
    }

    // number of tiles out of place
    public int hamming() {
        return this.hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return this.manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.manhattan == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        return this == y;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new BoardsIterable();
    }

    private class BoardsIterable implements Iterable<Board> {
        @Override
        public Iterator<Board> iterator() {
            return new BoardsIterator();
        }

        @Override
        public void forEach(Consumer<? super Board> action) {
            Iterable.super.forEach(action);
        }
    }

    private class BoardsIterator implements Iterator<Board> {
        int[][] nextBlankPos;
        int col = 0;
        int row = 0;
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Board next() {
            return null;
        }
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }
}
