import java.awt.*;

public class LineOfSight {
    public static int sizeOfGrid = View.sizeOfGrid;

    public static int run(int start, int type) {
        int toReturn = -1;
//            makeLine(start,305,type,305>180);
//        makeLine(start,125,type,125>180);
        for (double i = 0; i < 1439; i+=1) {
            int j = (int)i % 360;
            int k = (int)i / 360;
            switch (k){
                case (0):
                    makeLine((start % sizeOfGrid)-0.5,(start / sizeOfGrid)-0.5, j, type, j > 180);
                    break;
                case (1):
                    makeLine((start % sizeOfGrid)-0.5,(start / sizeOfGrid)+0.5, j, type, j > 180);
                    break;
                case (2):
                    makeLine((start % sizeOfGrid)+0.5,(start / sizeOfGrid)-0.5, j, type, j > 180);
                    break;
                case (3):
                    makeLine((start % sizeOfGrid)+0.5,(start / sizeOfGrid)+0.5, j, type, j > 180);
                    break;
                default:
                    System.out.println(i + "  " + k);
                    break;
            }

        }

        return toReturn;
    }


    public static int makeLine(double sx, double sy, double deg, int type, boolean flip) {

        double slope = Math.tan(Math.toRadians(deg));
        boolean done = false;
        double t = 0;
        int mod = 1;
        if (flip) {
            mod = -1;
        }
        int oCell = -1;

        if (slope < 1.0 && slope > -1.0) {
            while (true) {
                int cell = (int) ((double) sx + t) + ((int) (((double) sy + ((t + (0.5 * mod)) * slope)) + 0.5) * sizeOfGrid);
                if (oCell != -1 && !GridControler.notCrossingAEdge(oCell, cell, 0)) {
                    break;
                }
                if (oCell == -1 && !(cell >= 0 && cell <= sizeOfGrid * sizeOfGrid - 1)){
                    System.out.println("break");
                    break;
                }
                if (View.isWall[cell] || View.isEffectWall[cell]) {
                    if (type == 3) {
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
                    View.effectColor[cell] = (PushAttack.getGradient(Color.CYAN, View.getNormCellColor(cell), .25));
                    View.updated[cell] = true;
                }
                if (type == 3) {
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
                    View.effectColor[cell] = (PushAttack.getGradient(Color.CYAN, View.getNormCellColor(cell), .25));
                    View.updated[cell] = true;
                }
                if (type == 3) {
                    View.canSee[cell] = true;
                }
                oCell = cell;
                t += mod;
            }
        } else {
            slope = Math.tan(Math.toRadians(deg - 90));
            while (true) {
                int cell = (int) (((double) sx + ((t + (0.5 * mod)) * slope)) + 0.5) + ((int) (Math.round((double) sy + t)) * sizeOfGrid);
                double s = 1.5;
                s += (mod * 0.5);
                if (oCell != -1 && !GridControler.notCrossingAEdge(oCell, cell, (int) s)) {
                    break;
                }
                if (View.isWall[cell] || View.isEffectWall[cell]) {
                    done = true;
                    if (type == 3) {
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
                    View.effectColor[cell] = (PushAttack.getGradient(Color.CYAN, View.getNormCellColor(cell), .25));
                    View.updated[cell] = true;
                }
                if (type == 3) {
                    View.canSee[cell] = true;
                }
                oCell = cell;
                cell += mod * 11;
                if (!GridControler.notCrossingAEdge(oCell, cell, 0)) {
                    break;
                }
                if (View.isWall[cell] || View.isEffectWall[cell]) {
                    if (type == 3) {
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
                    View.effectColor[cell] = (PushAttack.getGradient(Color.CYAN, View.getNormCellColor(cell), .25));
                    View.updated[cell] = true;
                }
                if (type == 3) {
                    View.canSee[cell] = true;
                }
                oCell = cell;
                t += mod;
            }
        }
        return -1;
    }

}