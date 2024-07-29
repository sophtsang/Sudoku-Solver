package graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;

public class SudokuGraph {
    /**
     * SudokuGraph is a class that implements the board of a Sudoku game and the Graph ADT.
     * The graph contains no duplicate vertices, and only edges between two distinct vertices can
     * exist - no edges that originate and end at the same vertex.
     */

    /**
     * Priority queue that contains all positions of grid spaces in the Sudoku board that have not
     * been filled in yet. Highest priority positions have the smallest length of String
     * possibilities: a position is removed from priority queue when possibilities.length() = 0.
     */
    public PriorityQueue<Integer> notDone = new MinQueue<>();

    // Maps position to its corresponding vertex.
    public final Map<Integer, SudokuVertex> index = new HashMap<>();

    /**
     * Queue of vertex IDs currently known to be reachable from the starting vertex but for whom the
     * shortest possible path has not yet been determined.  Ordered by weight of the shortest known
     * path from the starting vertex.
     */
    private int size;

    /**
     * Initializes a BasicGraph instance that represents an empty graph with no vertices or edges.
     */
    public SudokuGraph() {
    }


    /**
     * Returns vertex with label 'label' if it exists in the graph, else returns null.
     */
    public SudokuVertex getVertex(int position) {
        if (!index.containsKey(position)) {
            return null;
        }
        return index.get(position);
    }

    /**
     *  Adds a new vertex to graph without any incoming or outgoing edges if 'vertex' does not
     *  already exist in the graph.
     */
    public SudokuVertex addVertex(SudokuVertex vertex) {
        if (!index.containsKey(vertex.position())) {
            index.putIfAbsent(vertex.position(), vertex);
        }
        return getVertex(vertex.position());
    }


    // THIS METHOD REQUIRES MORE SPACE COMPLEXITY THAN VALIDVALUE().


    public void fillPossibilities(SudokuVertex vertex) {
        vertex.possibilities = "";
        List<String> notFilled = new ArrayList<>(List.of("1", "2", "3", "4", "5", "6", "7", "8", "9"));
        for (int position : vertex.row) {
            if (!notDone.contains(position) && index.containsKey(position) &&
                    notFilled.contains("" + getVertex(position).value()) &&
                    vertex.position() != position) {
                notFilled.remove("" + getVertex(position).value());
            }
        }
        for (int position : vertex.col) {
            if (!notDone.contains(position) && index.containsKey(position) &&
                    notFilled.contains("" + getVertex(position).value()) &&
                    vertex.position() != position) {
                notFilled.remove("" + getVertex(position).value());
            }
        }
        for (int position : vertex.box) {
            if (!notDone.contains(position) && index.containsKey(position) &&
                    notFilled.contains("" + getVertex(position).value()) &&
                    vertex.position() != position) {
                notFilled.remove("" + getVertex(position).value());
            }
        }
        for (String value : notFilled) {
            vertex.possibilities += value;
        }
    }

    /**
     * Backtracking: if we find that for all values existing in 'possibilities' at a certain grid
     * position, there are no values that do not conflict with existing values in row, col, or box,
     * then recursively return to the previous call frame and fill-in the next possible value in
     * 'possibilities'.
     * There must be a value in 'possibilities' that will work in a certain grid position: increment
     * through 'possibilities' with for (int i = 0; i < possibilities.length(); i++) {} until that
     * value is found.
     *
     * Given the priority queue 'notDone', fill in the next possible value for the grid with least
     * possible values to be filled in.
     * Sudoku is solved when notDone is empty.
     */
    public void sudokuSolverA(SudokuVertex vertex) {
        // Continue recursing until notDone is empty.
        if (vertex != null) {
            // Fill-in the grid position with the least amount of possibilities first.
            fillPossibilities(vertex);
            for (int i = 1; i < 10; i++) {
                if (vertex.possibilities.contains("" + i)) {
                    vertex.setValue(i);
                    // This will try NoSuchElementException when at the last element in notDone.
                    sudokuSolverA(getVertex(notDone.remove()));
                }
            }
            if (!notDone.isEmpty()) {
                notDone.addOrUpdate(vertex.position(), vertex.possibilities.length());
            }
        }
    }


    // LESS SPACE COMPLEXITY IMPLEMENTATION.


    public boolean validValue(SudokuVertex vertex, int value) {
        for (int i = 0; i < 9; i++) {
            if ((i + 9 * (vertex.position() / 9)) != vertex.position() &&
                    getVertex(i + 9 * (vertex.position() / 9)).value() == value) {
                return false;
            }
            if ((9 * i + (vertex.position() % 9)) != vertex.position() &&
                    getVertex(9 * i + (vertex.position() % 9)).value() == value) {
                return false;
            }
            if (i < 3) {
                for (int j = 0 ; j < 3; j++) {
                    if (((((vertex.position() / 27) * 3 + i) * 9 +
                            ((vertex.position() % 9) / 3) * 3 + j)) != vertex.position() &&
                            getVertex((((vertex.position() / 27) * 3 + i) * 9 +
                            ((vertex.position() % 9) / 3) * 3 + j)).value() == value) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public void fillNotDone() {
        for (int i = 0; i < 81; i++) {
            if (!index.containsKey(i)) {
                SudokuVertex vertex = addVertex(new SudokuVertex(i, 0));
                notDone.addOrUpdate(i, i);
            }
        }
    }
    /**
     * Backtracking: if we find that for all values existing in 'possibilities' at a certain grid
     * position, there are no values that do not conflict with existing values in row, col, or box,
     * then recursively return to the previous call frame and fill-in the next possible value in
     * 'possibilities'.
     * There must be a value in 'possibilities' that will work in a certain grid position: increment
     * through 'possibilities' with for (int i = 0; i < possibilities.length(); i++) {} until that
     * value is found.
     *
     * Given the priority queue 'notDone', fill in the next possible value for the grid with least
     * possible values to be filled in.
     * Sudoku is solved when notDone is empty.
     */
    public void sudokuSolverB(SudokuVertex vertex) {
        // Continue recursing until notDone is empty.
        try {
            if (vertex != null) {
                // Fill-in the grid position with the least amount of possibilities first.
                for (int i = 1; i < 10; i++) {
                    // This will try NoSuchElementException when at the last element in notDone.
                    if (validValue(vertex, i)) {
                        vertex.setValue(i);
                        sudokuSolverB(getVertex(notDone.remove()));
                    }
                }
                if (!notDone.isEmpty()) {
                    vertex.setValue(0);
                    notDone.addOrUpdate(vertex.position(), vertex.position());
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("");
        }
    }
}

