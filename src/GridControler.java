import javax.swing.*;
import java.awt.*;

public class GridControler {

    public static int sizeOfGrid = View.sizeOfGrid;


    public static JPanel[] gridCreator(int gridSize) {
        JPanel[] grid;
        grid = new JPanel[gridSize * gridSize];

        for (int i = 0; i < gridSize * gridSize; i++) {
            JPanel cell = new JPanel(new BorderLayout());
            if (i % 2 == 0) {
                cell.setBackground(new Color(190, 190, 190));
            } else {
                cell.setBackground(new Color(200, 200, 200));
            }
            grid[i] = cell;
        }
        return grid;
    }

    public static Color findBackground(int cell){
        if (cell % 2 == 0) {
           return new Color(190, 190, 190);
        } else {
            return new Color(200, 200, 200);
        }

    }

    public static boolean notCrossingAEdge(int before, int after, int toside) {
        // 0 is up/down. 1 is going left. 2 is going right.

        if ((before < 0 || before > sizeOfGrid * sizeOfGrid - 1) && toside != 0){
            return false;
        }
        if (after >= 0 && after <= sizeOfGrid * sizeOfGrid - 1) {
            for (int i = 0; i < sizeOfGrid; i++) {
                int leftSide = sizeOfGrid * i;
                int rightSide = (sizeOfGrid * i) + sizeOfGrid - 1;
                if (before >= leftSide && before <= rightSide){
                    if (toside == 1) {
                        if (after == rightSide - sizeOfGrid){
                            return false;
                        }
                    } else if (toside == 2){
                        if (after == leftSide + sizeOfGrid){
                            return false;
                        }
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }

    public static void doToSurroundCells(int cell, int choice, int from, boolean corners){
        int oporator = cell - sizeOfGrid;
        if (notCrossingAEdge(cell, (oporator),0)) {
            sendBack(oporator, choice, from, cell, "u",0);
        }
        oporator = cell - 1;
        if (notCrossingAEdge(cell, (oporator),1)) {
            sendBack(oporator, choice, from, cell, "l",1);
        }
        oporator = cell + 1;
        if (notCrossingAEdge(cell, (oporator),2)) {
            sendBack(oporator, choice, from, cell, "r",2);
        }
        oporator = cell + sizeOfGrid;
        if (notCrossingAEdge(cell, (oporator),0)) {
            sendBack(oporator, choice, from, cell, "d",3);
        }
        if (corners) {
            oporator = cell - sizeOfGrid - 1;
            if (notCrossingAEdge(cell, (oporator), 1)) {
                sendBack(oporator, choice, from, cell, "ul",4);
            }
            oporator = cell - sizeOfGrid + 1;
            if (notCrossingAEdge(cell, (oporator),2)) {
                sendBack(oporator, choice, from, cell, "ur",5);
            }
            oporator = cell + sizeOfGrid - 1;
            if (notCrossingAEdge(cell, (oporator),1)) {
                sendBack(oporator, choice, from, cell, "dl",6);
            }
            oporator = cell + sizeOfGrid + 1;
            if (notCrossingAEdge(cell, (oporator),2)) {
                sendBack(oporator, choice, from, cell, "dr",7);
            }
        }
    }

    private static void sendBack(int cell, int choice, int from, int oCell, String direction, int directionForLoop) {
        switch (from){
            case 1:
                View.DTSC(cell, choice);
                break;
            case 2:
                PathFind.DTSC(cell, choice, oCell, directionForLoop);
                break;
            case 3:
                PushAttack.DTSC(cell, choice, direction);
                break;
        }
    }

}
