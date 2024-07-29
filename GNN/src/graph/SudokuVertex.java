package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SudokuVertex{

    // Position of the vertex "number" on the Sudoku board: requires 0 <= position <= 80.
    private int position;

    // All possible numbers that vertex at position 'position' could be without overlapping with
    // already existing filled in numbers in the same row, column, or neighborhood as the vertex.
    public String possibilities;

    // Filled in value of the vertex at position 'position': requires 0 <= value <= 9. Value 'value'
    // is only 0 if the grid at position is empty, not filled-in yet.
    private int value;

    // List containing all positions of vertices in the same row as vertex in position 'position'.
    public List<Integer> row = new ArrayList<>();

    // List containing all positions of vertices in the same col as vertex in position 'position'.
    public List<Integer> col = new ArrayList<>();

    // List containing all positions of vertices in the same neighborhood as vertex in position 'position'.
    public List<Integer> box = new ArrayList<>();

    // Use this constructor if incoming and outgoing edges already known.
    public SudokuVertex(int position, int value) {
        this.position = position;
        this.value = value;
        // row number of grid space is 'position' / 9.
        for (int i = 0; i < 9; i++) {
            row.add(i + 9 * (position / 9));
            col.add(9 * i + (position % 9));
            if (i < 3) {
                for (int j = 0 ; j < 3; j++) {
                    box.add(((position / 27) * 3 + i) * 9 + ((position % 9) / 3) * 3 + j);
                }
            }
        }
        this.possibilities = "";
    }

    public int position() {
        return position;
    }

    public int value() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
