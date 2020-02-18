import java.awt.*;

public class LineOfSight {
    public static int sizeOfGrid = View.sizeOfGrid;

    public static int run(int start, int type){
        int toReturn = -1;
        for (int x = 0; x < sizeOfGrid; x++){
            int i = makeLine(start, x, type);
            if (i != -1){
                switch (type) {
                    case (1):
                        return i;

                    case (2):
                        toReturn = i;
                        break;
                }
            }
        }
        for (int x = 0; x < sizeOfGrid; x++){
            int i = makeLine(start, x * sizeOfGrid, type);
            if (i != -1){
                switch (type) {
                    case (1):
                        return i;
                    case (2):
                        toReturn = i;
                        break;
                }
            }
        }
        for (int x = 1; x < sizeOfGrid; x++){
            int i = makeLine(start, x * sizeOfGrid - 1, type);
            if (i != -1){
                switch (type) {
                    case (1):
                        return i;
                    case (2):
                        toReturn = i;
                        break;
                }
            }
        }
        for (int x = 0; x < sizeOfGrid; x++){
            int i = makeLine(start, x + (sizeOfGrid * (sizeOfGrid-1)), type);
            if (i != -1){
                switch (type) {
                    case (1):
                        return i;
                    case (2):
                        toReturn = i;
                        break;
                }
            }
        }
        return toReturn;
    }

    public static int makeLine(int start, int end, int type){
        int sx = start % sizeOfGrid;
        int sy = start / sizeOfGrid;
        int ex = end % sizeOfGrid;
        int ey = end / sizeOfGrid;
        int dx = ex - sx;
        int dy = ey - sy;

        double slope = (double)(ey-sy)/(ex-sx);
        double b = sy;
        boolean done = false;

        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0){
                for(int s = sx; s < ex; s++) {
                    int yFound  = (int)(Math.round(((s - sx + 0.5) * slope) + b));
                    int cell = (int)(Math.round(s-.5)) + ((yFound) * sizeOfGrid);
                    if (View.isWall[cell] != false || View.isEffectWall[cell] != false) {
                        done = true;
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
                    cell = (int)(Math.round(s+.5)) + ((yFound) * sizeOfGrid);
                    if (View.isWall[cell] != false || View.isEffectWall[cell] != false) {
                        done = true;
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
                }
                int yFound  = (int)(Math.round(((ex - sx + 0.5) * slope) + b));
                int cell = (int)(Math.round(ex-.5)) + ((yFound) * sizeOfGrid);
                if (View.isWall[cell] == false && View.isEffectWall[cell] == false && !done) {
                    if (cell == View.playerLocation) {
                        if (type == 1) {
                            return cell;
                        }
                    }
                    if (type == 2) {
                        View.effectColor[cell] = (PushAttack.getGradient(Color.CYAN, View.getNormCellColor(cell), .25));
                        View.updated[cell] = true;
                    }
                }
            } else {
                for(int s = sx; s > ex; s--) {
                    int yFound  = (int)(Math.round(((s - sx - 0.5) * slope) + b));
                    int cell = (int)(Math.round(s+.5)) + -1 + ((yFound) * sizeOfGrid);
                    if (View.isWall[cell] != false || View.isEffectWall[cell] != false) {
                        done = true;
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
                    cell = (int)(Math.round(s-.5)) + -1 + ((yFound) * sizeOfGrid);
                    if (View.isWall[cell] != false || View.isEffectWall[cell] != false) {
                        done = true;
                        break;
                    }
                    if (cell == View.playerLocation) {
                        if (type == 1) {
                            return cell;
                        }
                    }
                    if (type == 2) {
                        View.effectColor[cell] = (PushAttack.getGradient(Color.CYAN, View.getNormCellColor(cell), .25));
                    }
                }
                int yFound  = (int)(Math.round(((ex - sx - 0.5) * slope) + b));
                int cell = (int)(Math.round(ex+.5)) + -1 + ((yFound) * sizeOfGrid);
                if (View.isWall[cell] == false && View.isEffectWall[cell] == false && !done) {
                    if (cell == View.playerLocation) {
                        if (type == 1) {
                            return cell;
                        }
                    }
                    if (type == 2) {
                        View.effectColor[cell] = (PushAttack.getGradient(Color.CYAN, View.getNormCellColor(cell), .25));
                        View.updated[cell] = true;
                    }
                }
            }
        } else {
            if (dy > 0){
                for(int s = sy; s < ey; s++) {
                    int xFound  = (int)(Math.round(((s - sy + 0.49999) * (1/slope))) + sx);
                    int cell = xFound + ((int)(Math.round(s-.5)) * sizeOfGrid);
                    if (View.isWall[cell] != false || View.isEffectWall[cell] != false) {
                        done = true;
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
                    cell = xFound + ((int)(Math.round(s+.5)) * sizeOfGrid);
                    if (View.isWall[cell] != false || View.isEffectWall[cell] != false) {
                        done = true;
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
                }
                int xFound  = (int)(Math.round(((ey - sy +  0.49999) * (1/slope))) + sx);
                int cell = xFound + ((int)(Math.round(ey-.5)) * sizeOfGrid);
                if (View.isWall[cell] == false && View.isEffectWall[cell] == false && !done) {
                    if (cell == View.playerLocation) {
                        if (type == 1) {
                            return cell;
                        }
                    }
                    if (type == 2) {
                        View.effectColor[cell] = (PushAttack.getGradient(Color.CYAN, View.getNormCellColor(cell), .25));
                        View.updated[cell] = true;
                    }
                }
            } else {
                for(int s = sy; s > ey; s--) {
                    int xFound  = (int)(Math.round(((s - sy - 0.5) * (1/slope))) + sx);
                    int cell = xFound + ((int)(Math.round(s+.5)-1) * sizeOfGrid);
                    if (View.isWall[cell] != false || View.isEffectWall[cell] != false) {
                        done = true;
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
                    cell = xFound + ((int)(Math.round(s-.5)-1) * sizeOfGrid);
                    if (View.isWall[cell] != false || View.isEffectWall[cell] != false) {
                        done = true;
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
                }
                int xFound  = (int)(Math.round(((ey - sy - 0.5) * (1/slope))) + sx);
                int cell = xFound + ((int)(Math.round(ey+.5)-1) * sizeOfGrid);
                if (View.isWall[cell] == false && View.isEffectWall[cell] == false && !done) {
                    if (cell == View.playerLocation) {
                        if (type == 1) {
                            return cell;
                        }
                    }
                    if (type == 2) {
                        View.effectColor[cell] = (PushAttack.getGradient(Color.CYAN, View.getNormCellColor(cell), .25));
                        View.updated[cell] = true;
                    }
                }
            }
        }
        return -1;
    }
}
