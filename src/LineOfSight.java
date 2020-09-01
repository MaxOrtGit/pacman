import java.awt.*;

public class LineOfSight {
    public static int sizeOfGrid = View.sizeOfGrid;

    public static int run(int start, int type) {
        int toReturn = -1;
//            makeLine(start,305,type,305>180);
//        makeLine(start,125,type,125>180);

        for (int i = 0; i < 359; i++) {
            makeLine((start % sizeOfGrid), (start / sizeOfGrid), i, type, i > 180, start);
        }
        double offset = 0;
        switch (type){
            case (1):
                offset = 0.1;
                break;

            case (2):
                offset = 0.35;
                break;

            case (3):
                offset = 0.35;
                break;

            case (4):

                break;

            default:
                break;
        }
            for (double i = 0; i < 1439; i++) {
                double j = i % 360;
                int k = (int) i / 360;
                switch (k) {
                    case (0):
                        makeLine((start % sizeOfGrid) - offset, (start / sizeOfGrid) - offset, j, type, j > 180, start);
                        break;
                    case (1):
                        makeLine((start % sizeOfGrid) - offset, (start / sizeOfGrid) + offset, j, type, j > 180, start);
                        break;
                    case (2):
                        makeLine((start % sizeOfGrid) + offset, (start / sizeOfGrid) - offset, j, type, j > 180, start);
                        break;
                    case (3):
                        makeLine((start % sizeOfGrid) + offset, (start / sizeOfGrid) + offset, j, type, j > 180, start);
                        break;
                    default:
                        System.out.println(i + "  " + k);
                        break;
                }

            }


        return toReturn;
    }


    public static int makeLine(double sx, double sy, double deg, int type, boolean flip, int start) {

        double slope = Math.tan(Math.toRadians(deg));
        if (deg == 45 || deg == 135){
            slope += 0.000001;
        }
        boolean done = false;
        double t = 0;
        int mod = 1;
        if (flip) {
            mod = -1;
        }
        int oCell = start;

        if (slope < 1.0 && slope > -1.0) {
            while (true) {
                int cell = (int) ((double) sx + 0.5 + t) + ((int) (((double) sy + ((t + (0.5 * mod)) * slope)) + 0.5) * sizeOfGrid);
                if (oCell != -1 && !GridControler.notCrossingAEdge(oCell, cell, 0)) {
                    break;
                }
                if (oCell == -1 && !(cell >= 0 && cell <= sizeOfGrid * sizeOfGrid - 1)){
                    break;
                }
                if (View.isWall[cell] || View.isEffectWall[cell]) {
                    if (type == 3) {
                        if (!View.canSee[cell]){
                            View.seenMemory[cell] = View.effectColor[cell];
                        }
                        View.canSee[cell] = true;
                    }
                    break;
                }
                if (cell == View.playerLocation) {
                    if (type == 1) {
                        return cell;
                    }
                }
                if (type == 2) {
                    View.effectColor[cell] = (GridControler.getGradient(Color.CYAN, View.getNormCellColor(cell), .25));
                    View.updated[cell] = true;
                }
                if (type == 3) {
                    if (!View.canSee[cell]){
                        View.seenMemory[cell] = View.effectColor[cell];
                    }
                    View.canSee[cell] = true;
                }
                oCell = cell;
                cell += mod;
                double s = 1.5;
                s += (mod * 0.5);
                if (!GridControler.notCrossingAEdge(oCell, cell, (int) s)) {
                    break;
                }
                if (View.isWall[cell] || View.isEffectWall[cell]) {
                    if (type == 3) {
                        if (!View.canSee[cell]){
                            View.seenMemory[cell] = View.effectColor[cell];
                        }
                        View.canSee[cell] = true;
                    }
                    break;
                }
                if (cell == View.playerLocation) {
                    if (type == 1) {
                        return cell;
                    }
                }
                if (type == 2) {
                    View.effectColor[cell] = (GridControler.getGradient(Color.CYAN, View.getNormCellColor(cell), .25));
                    View.updated[cell] = true;
                }
                if (type == 3) {
                    if (!View.canSee[cell]){
                        View.seenMemory[cell] = View.effectColor[cell];
                    }
                    View.canSee[cell] = true;
                }
                oCell = cell;
                t += mod;
            }
        } else {
            slope = Math.tan(Math.toRadians(deg - 90));
            while (true) {
                int cell = (int) (((double) sx + ((t + (0.5 * mod)) * slope)) + 0.5) + ((int) (sy + t) * sizeOfGrid);
                double s = 1.5;
                s += (mod * 0.5);
                if (oCell != -1 && !GridControler.notCrossingAEdge(oCell, cell, (int) s)) {
                    break;
                }
                if (View.isWall[cell] || View.isEffectWall[cell]) {
                    done = true;
                    if (type == 3) {
                        if (!View.canSee[cell]){
                            View.seenMemory[cell] = View.effectColor[cell];
                        }
                        View.canSee[cell] = true;
                    }
                    break;
                }
                if (cell == View.playerLocation) {
                    if (type == 1) {
                        return cell;
                    }
                }
                if (type == 2) {
                    View.effectColor[cell] = (GridControler.getGradient(Color.CYAN, View.getNormCellColor(cell), .25));
                    View.updated[cell] = true;
                }
                if (type == 3) {
                    if (!View.canSee[cell]){
                        View.seenMemory[cell] = View.effectColor[cell];
                    }
                    View.canSee[cell] = true;
                }
                oCell = cell;
                cell += mod * 11;
                if (!GridControler.notCrossingAEdge(oCell, cell, 0)) {
                    break;
                }
                if (View.isWall[cell] || View.isEffectWall[cell]) {
                    if (type == 3) {
                        if (!View.canSee[cell]){
                            View.seenMemory[cell] = View.effectColor[cell];
                        }
                        View.canSee[cell] = true;
                    }
                    break;
                }
                if (cell == View.playerLocation) {
                    if (type == 1) {
                        return cell;
                    }
                }
                if (type == 2) {
                    View.effectColor[cell] = (GridControler.getGradient(Color.CYAN, View.getNormCellColor(cell), .25));
                    View.updated[cell] = true;
                }
                if (type == 3) {
                    if (!View.canSee[cell]){
                        View.seenMemory[cell] = View.effectColor[cell];
                    }
                    View.canSee[cell] = true;
                }
                oCell = cell;
                t += mod;
            }
        }
        return -1;
    }

}