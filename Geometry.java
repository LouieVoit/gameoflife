package gameoflife;

import java.util.Arrays;
import java.util.Iterator;

/**
 *
 * @author Loux
 */
public abstract class Geometry implements Iterable<Cell> {

    protected Cell[][][] cells_;

    public static class Rectangle extends Geometry {

        private final int nbRows_, nbColumns_;

        public Rectangle(int nbRows, int nbColumns) {
            nbRows_ = nbRows;
            nbColumns_ = nbColumns;
            cells_ = constructCells(nbRows, nbColumns);
        }

        private Cell[][][] constructCells(int nbRows, int nbColumns) {
            Cell[][] cells2D = new Cell[nbRows][nbRows];
            for (int row = 0; row < nbRows; row++) {
                cells2D[row][0] = new Cell();
                cells2D[row][nbColumns - 1] = new Cell();
            }
            for (int column = 0; column < nbColumns; column++) {
                cells2D[0][column] = new Cell();
                cells2D[nbRows - 1][column] = new Cell();
            }
            for (int i = 1; i < nbRows - 1; i++) {
                for (int j = 1; j < nbColumns - 1; j++) {
                    Cell cell = new Cell();
                    cells2D[i][j] = cell;
                }
            }
            for (int i = 1; i < nbRows - 1; i++) {
                for (int j = 1; j < nbColumns - 1; j++) {
                    Cell cell = cells2D[i][j];
                    cell.addNeighbour(cells2D[i - 1][j - 1]);
                    cell.addNeighbour(cells2D[i - 1][j]);
                    cell.addNeighbour(cells2D[i - 1][j + 1]);
                    cell.addNeighbour(cells2D[i][j + 1]);
                    cell.addNeighbour(cells2D[i + 1][j + 1]);
                    cell.addNeighbour(cells2D[i + 1][j]);
                    cell.addNeighbour(cells2D[i + 1][j - 1]);
                    cell.addNeighbour(cells2D[i][j - 1]);
                }
            }
            for (int row = 1; row < nbRows - 1; row++) {
                cells2D[row][0].addNeighbour(cells2D[row][1]);
                cells2D[row][0].addNeighbour(cells2D[row - 1][0]);
                cells2D[row][0].addNeighbour(cells2D[row - 1][1]);
                cells2D[row][0].addNeighbour(cells2D[row + 1][1]);
                cells2D[row][0].addNeighbour(cells2D[row + 1][0]);
                //
                cells2D[row][nbColumns - 1].addNeighbour(cells2D[row][nbColumns - 2]);
                cells2D[row][nbColumns - 1].addNeighbour(cells2D[row - 1][nbColumns - 1]);
                cells2D[row][nbColumns - 1].addNeighbour(cells2D[row - 1][nbColumns - 2]);
                cells2D[row][nbColumns - 1].addNeighbour(cells2D[row + 1][nbColumns - 1]);
                cells2D[row][nbColumns - 1].addNeighbour(cells2D[row + 1][nbColumns - 2]);
            }
            for (int column = 1; column < nbColumns - 1; column++) {
                cells2D[0][column].addNeighbour(cells2D[1][column]);
                cells2D[0][column].addNeighbour(cells2D[0][column - 1]);
                cells2D[0][column].addNeighbour(cells2D[1][column - 1]);
                cells2D[0][column].addNeighbour(cells2D[1][column + 1]);
                cells2D[0][column].addNeighbour(cells2D[0][column + 1]);
                //
                cells2D[nbRows - 1][column].addNeighbour(cells2D[nbRows - 2][column]);
                cells2D[nbRows - 1][column].addNeighbour(cells2D[nbRows - 1][column - 1]);
                cells2D[nbRows - 1][column].addNeighbour(cells2D[nbRows - 2][column - 1]);
                cells2D[nbRows - 1][column].addNeighbour(cells2D[nbRows - 2][column + 1]);
                cells2D[nbRows - 1][column].addNeighbour(cells2D[nbRows - 1][column + 1]);
            }
            cells2D[0][0].addNeighbour(cells2D[0][1]);
            cells2D[0][0].addNeighbour(cells2D[1][1]);
            cells2D[0][0].addNeighbour(cells2D[1][0]);
            //
            cells2D[0][nbColumns - 1].addNeighbour(cells2D[0][nbColumns - 2]);
            cells2D[0][nbColumns - 1].addNeighbour(cells2D[1][nbColumns - 2]);
            cells2D[0][nbColumns - 1].addNeighbour(cells2D[1][nbColumns - 1]);
            //
            cells2D[nbRows - 1][0].addNeighbour(cells2D[nbRows - 2][0]);
            cells2D[nbRows - 1][0].addNeighbour(cells2D[nbRows - 2][1]);
            cells2D[nbRows - 1][0].addNeighbour(cells2D[nbRows - 1][1]);
            //
            cells2D[nbRows - 1][nbColumns - 1].addNeighbour(cells2D[nbRows - 2][nbColumns - 2]);
            cells2D[nbRows - 1][nbColumns - 1].addNeighbour(cells2D[nbRows - 2][nbColumns - 1]);
            cells2D[nbRows - 1][nbColumns - 1].addNeighbour(cells2D[nbRows - 1][nbColumns - 2]);
            //
            Cell[][][] cells = new Cell[][][]{cells2D};
            return cells;
        }

        @Override
        public int getWidth() {
            return nbRows_;
        }

        @Override
        public int getHeight() {
            return nbColumns_;
        }

        @Override
        public int getDepth() {
            return 1;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int layer = 0; layer < getDepth(); layer++) {
            for (int row = 0; row < getHeight(); row++) {
                builder.append(Arrays.toString(cells_[layer][row]));
                builder.append("\n");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public Cell[][][] getCells() {
        return cells_.clone();
    }

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract int getDepth();

    @Override
    public Iterator<Cell> iterator() {
        Iterator<Cell> iterator = new Iterator<Cell>() {
            private int layer_ = 0, row_ = 0, column_ = 0;

            @Override
            public boolean hasNext() {
                return !(layer_ == getDepth() - 1 && row_ == getHeight() - 1 && column_ == getWidth() - 1);
            }

            @Override
            public Cell next() {
                Cell next = cells_[layer_][row_][column_];
                column_ = (column_ + 1) % getWidth();
                if (column_ == 0) {
                    row_ = (row_ + 1) % getHeight();
                }
                if (row_ == 0) {
                    layer_ = (layer_ + 1) % getDepth();
                }
                return (next);
            }
        };
        return iterator;
    }

}
