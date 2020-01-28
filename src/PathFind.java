import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PathFind {

    public static Map<Integer, Integer> gValue = new HashMap<>();
    public static Map<Integer, Integer> sValue = new HashMap<>();
    public static Map<Integer, Integer> tValue = new HashMap<>();
    public static Map<Integer, Integer> from = new HashMap<>();
    public static Map<Integer, Integer> order = new HashMap<>();
    public static Map<Integer, Integer> gaValue = new HashMap<>();
    public static Map<Integer, Integer> saValue = new HashMap<>();
    public static Map<Integer, Integer> taValue = new HashMap<>();
    public static int DTSCLocation;
    public static int orderV = 0;
    public static boolean[] isWall;
    public static boolean[] isEffectWall;
    public static int color;
    public static int scolor;
    public static int lowestS = 0;
    public static int lowestC = 0;
    public static int newLocation = -1;
    public static int pLocation;
    public static int sizeOfGrid = View.sizeOfGrid;

    public static int[] startPathfindAI(boolean paint, boolean roaming, int playerLocation, int location){
        int[] toReturn = new int[2];
        pLocation = playerLocation;
        if (pLocation != location && pLocation != -1) {
            DTSCLocation = location;
            gValue.clear();
            tValue.clear();
            sValue.clear();
            gaValue.clear();
            taValue.clear();
            saValue.clear();
            order.clear();
            orderV = 0;
            from.clear();
            from.put(location, -1);
            order.put(location, orderV);
            orderV += 1;
            isWall = View.isWall;
            isEffectWall = View.isEffectWall;
            int toSearch = location;
            sValue.put(toSearch, 0);
            saValue.put(toSearch, 0);
            color = 255;
            scolor = 255;
            int done = 1;
            while (done == 1) {
                GridControler.doToSurroundCells(toSearch, 1, 2, true);
                toSearch = lowestFound();
                order.put(toSearch, orderV);
                orderV += 1;
                if (toSearch != -2) {
                    if (toSearch != -1) {
                        int searched = from.get(toSearch);
                        if (paint == true && color > 0) {
                            View.effectColor[toSearch] = (PushAttack.getGradient(new Color(color, color, 150), View.getNormCellColor(toSearch), .5));
                            View.updated[toSearch] = true;
                            color -= 3;
                        }
                        gValue.remove(toSearch);
                        tValue.remove(toSearch);
                        if (toSearch == pLocation) {
                            done = 2;
                        }
                    } else {
                        done = 3;
                    }
                } else {
                    done = 2;
                }
            }
            if (done == 2 && newLocation == -1) {
                toReturn[0] = -2;
                toReturn[1] = move(location, paint);
                return toReturn;
            }
        }
        toReturn[0] = newLocation;
        return toReturn;
    }

    public static int move(int location, boolean paint){
        Integer[] iPathKeys = (from.keySet().toArray(new Integer[from.keySet().size()]));
        int[] pathKeys = new int[iPathKeys.length];
        for (int j = 0; j <= iPathKeys.length-1; j++) {
            pathKeys[j] = iPathKeys[j];
        }

        boolean done = true;
        int toFind = pLocation;
        int found = 0;
        while (done){
            for (int i = 0; i <= pathKeys.length-1; i++) {
                if (toFind == location){
                    return found;
                }
                if (pathKeys[i] == toFind) {
                    found = toFind;
                    toFind = from.get(pathKeys[i]);
                    if (paint == true && scolor > 0) {
                        View.effectColor[toFind] = (PushAttack.getGradient(new Color(150, scolor, 150), View.getNormCellColor(toFind), .5));
                        View.updated[toFind] = true;
                        scolor -= 3;
                    }
                }
            }
            if (toFind == location){
                return found;
            }
        }
        return -1;
    }


    public static int lowestFound() {
        Integer[] iTValueKeys = (tValue.keySet().toArray(new Integer[tValue.keySet().size()]));
        int[] TValueKeys  = new int[iTValueKeys.length];
        for (int j = 1; j <= iTValueKeys.length; j++) {
            TValueKeys [j-1] = iTValueKeys[j - 1];
        }
        if (TValueKeys.length != 0) {
            int tLowest = tValue.get(TValueKeys[0]);
            int gLowest = gValue.get(TValueKeys[0]);
            int lowestFound = TValueKeys[0];
            for (int i = 0; i < tValue.size(); i++) {
                if (tValue.get(TValueKeys[i]) < tLowest) {
                    tLowest = tValue.get(TValueKeys[i]);
                    gLowest = gValue.get(TValueKeys[i]);
                    lowestFound = TValueKeys[i];
                } else if (tValue.get(TValueKeys[i]) == tLowest) {
                    if (gValue.get(TValueKeys[i]) < gLowest){
                        tLowest = tValue.get(TValueKeys[i]);
                        gLowest = gValue.get(TValueKeys[i]);
                        lowestFound = TValueKeys[i];
                    }
                }
            }
            if (lowestFound == pLocation){
                return -2;
            }
            return lowestFound;
        }
        return -1;
    }

    public static int distanceFrom(int start, int goal){
        return (Math.abs((start % sizeOfGrid) - (goal % sizeOfGrid)) + Math.abs((start / sizeOfGrid) - (goal / sizeOfGrid)));
    }

    public static void DTSC(int cell, int choice, int oCell) {
        switch (choice){
            case 1:
                if (isWall[cell] != true && isEffectWall[cell] != true && !from.containsKey(cell)) {
                    gValue.put(cell, distanceFrom(cell, pLocation));
                    gaValue.put(cell, distanceFrom(cell, pLocation));
                    int tg = (gValue.get(cell) != null ? gValue.get(cell) : 0);
                    lowestS = -1;
                    lowestC = -1;
                    GridControler.doToSurroundCells(cell, 2, 2, true);
                    from.put(cell, lowestC);
                    sValue.put(cell, lowestS + 1);
                    saValue.put(cell, lowestS + 1);
                    tValue.put(cell, tg + lowestS + 1);
                    taValue.put(cell, tg + lowestS + 1);
                }
            case 2:
                if (from.containsKey(cell)) {
                    if (lowestS != -1) {
                        if ((sValue.get(cell) != null ? sValue.get(cell) : 0) < lowestS) {
                            int ts = (sValue.get(cell) != null ? sValue.get(cell) : 0);
                            lowestS = ts;
                            lowestC = cell;
                        }
                    } else {
                        int ts = (sValue.get(cell) != null ? sValue.get(cell) : 0);
                        lowestS = ts;
                        lowestC = cell;
                    }
                    if (cell == DTSCLocation){
                        lowestS = 0;
                        lowestC = DTSCLocation;
                    }
                }

        }
    }
}
