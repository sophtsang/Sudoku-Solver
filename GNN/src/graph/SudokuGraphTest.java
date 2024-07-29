package graph;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Set;

public class SudokuGraphTest {

    @DisplayName("WHEN a grid position is added onto the Sudoku board, positions that are in the"
            + "same row, column, and 3-by-3 neighborhood as the position should be updated.")
    @Test
    void testAddPosition() {
        SudokuVertex s1 = new SudokuVertex(25, 0);
        assertEquals(List.of(18, 19, 20, 21, 22, 23, 24, 25, 26), s1.row);
        assertEquals(List.of(7, 16, 25, 34, 43, 52, 61, 70, 79), s1.col);
        assertEquals(List.of(6, 7, 8, 15, 16, 17, 24, 25, 26), s1.box);

        SudokuVertex s2 = new SudokuVertex(0, 0);
        assertEquals(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8), s2.row);
        assertEquals(List.of(0, 9, 18, 27, 36, 45, 54, 63, 72), s2.col);
        assertEquals(List.of(0, 1, 2, 9, 10, 11, 18, 19, 20), s2.box);

        SudokuVertex s3 = new SudokuVertex(80, 0);
        assertEquals(List.of(72, 73, 74, 75, 76, 77, 78, 79, 80), s3.row);
        assertEquals(List.of(8, 17, 26, 35, 44, 53, 62, 71, 80), s3.col);
        assertEquals(List.of(60, 61, 62, 69, 70, 71, 78, 79, 80), s3.box);

        SudokuVertex s4 = new SudokuVertex(48, 0);
        assertEquals(List.of(45, 46, 47, 48, 49, 50, 51, 52, 53), s4.row);
        assertEquals(List.of(3, 12, 21, 30, 39, 48, 57, 66, 75), s4.col);
        assertEquals(List.of(30, 31, 32, 39, 40, 41, 48, 49, 50), s4.box);
    }

    @DisplayName("WHEN a grid position with a value is added to the board, that value should not be"
            + "found in 'possibilities' of positions that are in the same row, column, or 3-by-3"
            + "neighborhood as the position.")
    @Test
    void testUpdatePossibilities() {
        SudokuGraph s = new SudokuGraph();
        s.addVertex(new SudokuVertex(0, 5));
        s.addVertex(new SudokuVertex(1, 3));
        s.addVertex(new SudokuVertex(4, 7));
        s.addVertex(new SudokuVertex(9, 6));
        s.addVertex(new SudokuVertex(12, 1));
        s.addVertex(new SudokuVertex(13, 9));
        s.addVertex(new SudokuVertex(14, 5));
        s.addVertex(new SudokuVertex(19, 9));
        s.addVertex(new SudokuVertex(20, 8));
        s.addVertex(new SudokuVertex(25, 6));
        s.addVertex(new SudokuVertex(27, 8));
        s.addVertex(new SudokuVertex(31, 6));
        s.addVertex(new SudokuVertex(35, 3));
        s.addVertex(new SudokuVertex(36, 4));
        s.addVertex(new SudokuVertex(39, 8));
        s.addVertex(new SudokuVertex(41, 3));
        s.addVertex(new SudokuVertex(44, 1));
        s.addVertex(new SudokuVertex(45, 7));
        s.addVertex(new SudokuVertex(49, 2));
        s.addVertex(new SudokuVertex(53, 6));
        s.addVertex(new SudokuVertex(55, 6));
        s.addVertex(new SudokuVertex(60, 2));
        s.addVertex(new SudokuVertex(61, 8));
        s.addVertex(new SudokuVertex(66, 4));
        s.addVertex(new SudokuVertex(67, 1));
        s.addVertex(new SudokuVertex(68, 9));
        s.addVertex(new SudokuVertex(71, 5));
        s.addVertex(new SudokuVertex(76, 8));
        s.addVertex(new SudokuVertex(79, 7));
        s.addVertex(new SudokuVertex(80, 9));

        s.fillNotDone();
        SudokuVertex start = s.getVertex(s.notDone.remove());
        s.sudokuSolverB(start);

        String answer = "";
        for (int position : s.index.keySet()) {
            if (position % 9 == 8) {
                System.out.println(answer + " " + s.getVertex(position).value());
                answer = "";
            } else {
                answer += " " + s.getVertex(position).value();
            }
        }
    }

    @DisplayName("NYT 6/8/24 Sudoku.")
    @Test
    void testSukoduB() {
        SudokuGraph s = new SudokuGraph();
        s.addVertex(new SudokuVertex(1, 5));
        s.addVertex(new SudokuVertex(3, 8));
        s.addVertex(new SudokuVertex(8, 6));
        s.addVertex(new SudokuVertex(9, 4));
        s.addVertex(new SudokuVertex(15, 2));
        s.addVertex(new SudokuVertex(17, 5));
        s.addVertex(new SudokuVertex(18, 6));
        s.addVertex(new SudokuVertex(29, 2));
        s.addVertex(new SudokuVertex(33, 1));
        s.addVertex(new SudokuVertex(36, 7));
        s.addVertex(new SudokuVertex(39, 9));
        s.addVertex(new SudokuVertex(41, 6));
        s.addVertex(new SudokuVertex(42, 4));
        s.addVertex(new SudokuVertex(52, 5));
        s.addVertex(new SudokuVertex(54, 2));
        s.addVertex(new SudokuVertex(56, 9));
        s.addVertex(new SudokuVertex(57, 5));
        s.addVertex(new SudokuVertex(60, 7));
        s.addVertex(new SudokuVertex(61, 3));
        s.addVertex(new SudokuVertex(67, 1));
        s.addVertex(new SudokuVertex(71, 4));
        s.addVertex(new SudokuVertex(73, 3));
        s.addVertex(new SudokuVertex(76, 8));

        s.fillNotDone();
        SudokuVertex start = s.getVertex(s.notDone.remove());
        s.sudokuSolverB(start);

        String answer = "";
        for (int position : s.index.keySet()) {
            if (position % 9 == 8) {
                System.out.println(answer + " " + s.getVertex(position).value());
                answer = "";
            } else {
                answer += " " + s.getVertex(position).value();
            }
        }
    }

    @DisplayName("NYT 6/8/24 Sudoku.")
    @Test
    void testSukoduC() {
        SudokuGraph s = new SudokuGraph();
        s.addVertex(new SudokuVertex(0, 8));
        s.addVertex(new SudokuVertex(1, 9));
        s.addVertex(new SudokuVertex(8, 6));
        s.addVertex(new SudokuVertex(10, 7));
        s.addVertex(new SudokuVertex(13, 6));
        s.addVertex(new SudokuVertex(15, 2));
        s.addVertex(new SudokuVertex(18, 3));
        s.addVertex(new SudokuVertex(21, 8));
        s.addVertex(new SudokuVertex(25, 7));
        s.addVertex(new SudokuVertex(26, 9));
        s.addVertex(new SudokuVertex(28, 5));
        s.addVertex(new SudokuVertex(29, 2));
        s.addVertex(new SudokuVertex(35, 7));
        s.addVertex(new SudokuVertex(36, 4));
        s.addVertex(new SudokuVertex(39, 1));
        s.addVertex(new SudokuVertex(41, 9));
        s.addVertex(new SudokuVertex(42, 3));
        s.addVertex(new SudokuVertex(50, 8));
        s.addVertex(new SudokuVertex(54, 5));
        s.addVertex(new SudokuVertex(55, 1));
        s.addVertex(new SudokuVertex(57, 3));
        s.addVertex(new SudokuVertex(68, 6));
        s.addVertex(new SudokuVertex(71, 5));

        s.fillNotDone();
        SudokuVertex start = s.getVertex(s.notDone.remove());
        s.sudokuSolverB(start);

        String answer = "";
        for (int position : s.index.keySet()) {
            if (position % 9 == 8) {
                System.out.println(answer + " " + s.getVertex(position).value());
                answer = "";
            } else {
                answer += " " + s.getVertex(position).value();
            }
        }
    }

    @DisplayName("NYT 6/8/24 Sudoku.")
    @Test
    void testSukoduD() {
        SudokuGraph s = new SudokuGraph();
        s.addVertex(new SudokuVertex(0, 4));
        s.addVertex(new SudokuVertex(4, 3));
        s.addVertex(new SudokuVertex(6, 1));
        s.addVertex(new SudokuVertex(7, 9));
        s.addVertex(new SudokuVertex(11, 3));
        s.addVertex(new SudokuVertex(17, 2));
        s.addVertex(new SudokuVertex(20, 8));
        s.addVertex(new SudokuVertex(28, 6));
        s.addVertex(new SudokuVertex(30, 1));
        s.addVertex(new SudokuVertex(34, 8));
        s.addVertex(new SudokuVertex(39, 9));
        s.addVertex(new SudokuVertex(44, 5));
        s.addVertex(new SudokuVertex(45, 7));
        s.addVertex(new SudokuVertex(47, 2));
        s.addVertex(new SudokuVertex(49, 8));
        s.addVertex(new SudokuVertex(55, 2));
        s.addVertex(new SudokuVertex(56, 1));
        s.addVertex(new SudokuVertex(58, 4));
        s.addVertex(new SudokuVertex(61, 7));
        s.addVertex(new SudokuVertex(67, 1));
        s.addVertex(new SudokuVertex(73, 5));
        s.addVertex(new SudokuVertex(75, 8));
        s.addVertex(new SudokuVertex(78, 6));
        s.addVertex(new SudokuVertex(80, 1));


        s.fillNotDone();
        SudokuVertex start = s.getVertex(s.notDone.remove());
        s.sudokuSolverB(start);


        String answer = "";
        for (int position : s.index.keySet()) {
            if (position % 9 == 8) {
                System.out.println(answer + " " + s.getVertex(position).value());
                answer = "";
            } else {
                answer += " " + s.getVertex(position).value();
            }
        }
    }
}

