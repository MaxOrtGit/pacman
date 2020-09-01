import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PathFind {

    public static Map<Integer, Integer> distancesFromGoal = new HashMap<>();
    public static Map<Integer, Integer> distancesFromStart = new HashMap<>();
    public static Map<Integer, Double> realDistancesFromGoal = new HashMap<>();
    public static Map<Integer, Double> realDistancesFromStart = new HashMap<>();
    public static Map<Integer, Integer> favorability = new HashMap<>();
    public static Map<Integer, Integer> stepsFromStart = new HashMap<>();
    public static Map<Integer, Integer> from = new HashMap<>();
    public static Map<Integer, Integer> order = new HashMap<>();
    public static Map<Integer, Integer> distancesFromGoalArchive = new HashMap<>();
    public static Map<Integer, Integer> stepsFromStartArchive = new HashMap<>();
    public static Map<Integer, Integer> distancesFromStartArchive = new HashMap<>();
    public static int startLocation;
    public static int orderV = 0;
    public static boolean[] isOpen;
    public static int color;
    public static int scolor;
    public static int lowestStepsFromStart = 0;
    public static int lowestStepsFromStartCell = 0;
    public static int lowestStepsFromStartCellFavorability = 0;
    public static double lowestStepsFromStartCellDistance = 0;
    public static int newLocation = -1;
    public static int goal;
    public static int sizeOfGrid = View.sizeOfGrid;
    public static double[] smartLookAround = {-1,-1,-1,-1};
    public static int[] smartLookAroundCell = {-1,-1,-1,-1};

    public static int[] startPathfindAI(boolean paint, int playerLocation, int location){
        int[] toReturn = new int[2];
        goal = playerLocation;
        if (goal != location && goal != -1) {
            startLocation = location;
            distancesFromGoal.clear();
            distancesFromStart.clear();
            realDistancesFromGoal.clear();
            realDistancesFromStart.clear();
            favorability.clear();
            stepsFromStart.clear();
            distancesFromGoalArchive.clear();
            distancesFromStartArchive.clear();
            stepsFromStartArchive.clear();
            order.clear();
            orderV = 0;
            from.clear();
            from.put(location, -1);
            order.put(location, orderV);
            orderV += 1;
            isOpen = new boolean[sizeOfGrid*sizeOfGrid];
            for(int i = 0; i < sizeOfGrid*sizeOfGrid; i++){
                isOpen[i] = View.pathfindThrogh(i);
            }
            GridControler.doToSurroundCells(location, 3, 2, false);
            int toSearch = location;
            stepsFromStart.put(toSearch, 0);
            stepsFromStartArchive.put(toSearch, 0);
            color = 255;
            scolor = 255;
            int done = 1;
            if(paint){
                View.resetAll();
            }
            while (done == 1) {
                GridControler.doToSurroundCells(toSearch, 1, 2, false);
                toSearch = lowestFound();
                order.put(toSearch, orderV);
                orderV += 1;
                if (toSearch != -2) {
                    if (toSearch != -1) {
                        if (paint == true && color > 0) {
                            View.effectColor[toSearch] = (GridControler.getGradient(new Color(color, color, 150), View.getNormCellColor(toSearch), .5));
                            View.updated[toSearch] = true;
                            color -= 3;
                        }
                        distancesFromGoal.remove(toSearch);
                        distancesFromStart.remove(toSearch);
                        realDistancesFromGoal.remove(toSearch);
                        realDistancesFromStart.remove(toSearch);
                        favorability.remove(toSearch);
                        if (toSearch == goal) {
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
        int toFind = goal;
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
                        View.effectColor[toFind] = (GridControler.getGradient(new Color(150, scolor, 150), View.getNormCellColor(toFind), .5));
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
        Integer[] iTValueKeys = (distancesFromStart.keySet().toArray(new Integer[distancesFromStart.keySet().size()]));
        int[] TValueKeys  = new int[iTValueKeys.length];
        for (int j = 0; j < iTValueKeys.length; j++) {
            TValueKeys [j] = iTValueKeys[j];
        }
        if (TValueKeys.length != 0) {
            int tLowest = distancesFromStart.get(TValueKeys[0]);
            int gLowest = distancesFromGoal.get(TValueKeys[0]);
            double realTLowest = distancesFromStart.get(TValueKeys[0]);
            double realGLowest = distancesFromGoal.get(TValueKeys[0]);
            int favorabilityLowest = favorability.get(TValueKeys[0]);
            int lowestFound = TValueKeys[0];
            for (int i = 0; i < distancesFromStart.size(); i++) {
                if (distancesFromStart.get(TValueKeys[i]) < tLowest) {
                    tLowest = distancesFromStart.get(TValueKeys[i]);
                    gLowest = distancesFromGoal.get(TValueKeys[i]);
                    realTLowest = realDistancesFromStart.get(TValueKeys[i]);
                    realGLowest = realDistancesFromGoal.get(TValueKeys[i]);
                    favorabilityLowest = favorability.get(TValueKeys[i]);
                    lowestFound = TValueKeys[i];
                } else if (distancesFromStart.get(TValueKeys[i]) == tLowest) {
                    if (distancesFromGoal.get(TValueKeys[i]) < gLowest){
                        tLowest = distancesFromStart.get(TValueKeys[i]);
                        gLowest = distancesFromGoal.get(TValueKeys[i]);
                        realTLowest = realDistancesFromStart.get(TValueKeys[i]);
                        realGLowest = realDistancesFromGoal.get(TValueKeys[i]);
                        favorabilityLowest = favorability.get(TValueKeys[i]);
                        lowestFound = TValueKeys[i];
                    } else if (distancesFromGoal.get(TValueKeys[i]) == gLowest){
                        if (favorability.get(TValueKeys[i]) < favorabilityLowest) {
                            tLowest = distancesFromStart.get(TValueKeys[i]);
                            gLowest = distancesFromGoal.get(TValueKeys[i]);
                            realTLowest = realDistancesFromStart.get(TValueKeys[i]);
                            realGLowest = realDistancesFromGoal.get(TValueKeys[i]);
                            favorabilityLowest = favorability.get(TValueKeys[i]);
                            lowestFound = TValueKeys[i];
                        } else if (favorability.get(TValueKeys[i]) == favorabilityLowest) {
                            if (realDistancesFromStart.get(TValueKeys[i]) < realTLowest) {
                                tLowest = distancesFromStart.get(TValueKeys[i]);
                                gLowest = distancesFromGoal.get(TValueKeys[i]);
                                realTLowest = realDistancesFromStart.get(TValueKeys[i]);
                                realGLowest = realDistancesFromGoal.get(TValueKeys[i]);
                                favorabilityLowest = favorability.get(TValueKeys[i]);
                                lowestFound = TValueKeys[i];
                            } else if (realDistancesFromStart.get(TValueKeys[i]) == realTLowest) {
                                if (distancesFromGoal.get(TValueKeys[i]) < realGLowest) {
                                    tLowest = distancesFromStart.get(TValueKeys[i]);
                                    gLowest = distancesFromGoal.get(TValueKeys[i]);
                                    realTLowest = realDistancesFromStart.get(TValueKeys[i]);
                                    realGLowest = realDistancesFromGoal.get(TValueKeys[i]);
                                    favorabilityLowest = favorability.get(TValueKeys[i]);
                                    lowestFound = TValueKeys[i];
                                }
                            }
                        }
                    }
                }
            }
            if (lowestFound == goal){
                return -2;
            }
            return lowestFound;
        }
        return -1;
    }

    public static int distanceFrom(int start, int end){
        return (Math.abs((start % sizeOfGrid) - (end % sizeOfGrid)) + Math.abs((start / sizeOfGrid) - (end / sizeOfGrid)));
    }
    public static double realDistanceFrom(int start, int end){
        int x1 = (start % sizeOfGrid);
        int x2 = (end % sizeOfGrid);
        int y1 = (start / sizeOfGrid);
        int y2 = (end / sizeOfGrid);
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    public static void DTSC(int cell, int choice, int oCell, int dir) {
        switch (choice){
            case 1:
                if ((isOpen[cell] || goal == cell) && !from.containsKey(cell)) {
                    distancesFromGoal.put(cell, distanceFrom(cell, goal));
                    realDistancesFromGoal.put(cell, realDistanceFrom(cell, goal));
                    distancesFromGoalArchive.put(cell, distanceFrom(cell, goal));
                    int tg = (distancesFromGoal.get(cell) != null ? distancesFromGoal.get(cell) : 0);
                    lowestStepsFromStart = -1;
                    lowestStepsFromStartCell = -1;
                    lowestStepsFromStartCellFavorability = -1;
                    lowestStepsFromStartCellDistance = -1;
                    GridControler.doToSurroundCells(cell, 2, 2, false);
                    from.put(cell, lowestStepsFromStartCell);
                    stepsFromStart.put(cell, lowestStepsFromStart + 1);
                    stepsFromStartArchive.put(cell, lowestStepsFromStart + 1);
                    distancesFromStart.put(cell, tg + lowestStepsFromStart + 1);
                    favorability.put(cell, View.getFavorablity(cell));
                    realDistancesFromStart.put(cell, realDistanceFrom(cell, startLocation));
                    distancesFromStartArchive.put(cell, tg + lowestStepsFromStart + 1);
                }
                break;
            case 2:
                if (from.containsKey(cell)) {
                    if (lowestStepsFromStart != -1) {
                        if ((stepsFromStart.get(cell) != null ? stepsFromStart.get(cell) : 0) < lowestStepsFromStart) {
                            int ts = (stepsFromStart.get(cell) != null ? stepsFromStart.get(cell) : 0);
                            lowestStepsFromStart = ts;
                            lowestStepsFromStartCell = cell;
                            lowestStepsFromStartCellFavorability = View.getFavorablity(cell);
                            lowestStepsFromStartCellDistance = realDistanceFrom(cell, goal);
                        } else if ((stepsFromStart.get(cell) != null ? stepsFromStart.get(cell) : 0) == lowestStepsFromStart){
                            if (View.getFavorablity(cell) < lowestStepsFromStartCellFavorability){
                                int ts = (stepsFromStart.get(cell) != null ? stepsFromStart.get(cell) : 0);
                                lowestStepsFromStart = ts;
                                lowestStepsFromStartCell = cell;
                                lowestStepsFromStartCellFavorability = View.getFavorablity(cell);
                                lowestStepsFromStartCellDistance = realDistanceFrom(cell, goal);
                            } else if (View.getFavorablity(cell) == lowestStepsFromStartCellFavorability){
                                if (lowestStepsFromStartCellDistance > realDistanceFrom(cell, goal)){
                                    int ts = (stepsFromStart.get(cell) != null ? stepsFromStart.get(cell) : 0);
                                    lowestStepsFromStart = ts;
                                    lowestStepsFromStartCell = cell;
                                    lowestStepsFromStartCellFavorability = View.getFavorablity(cell);
                                    lowestStepsFromStartCellDistance = realDistanceFrom(cell, goal);
                                }
                            }
                        }
                    } else {
                        int ts = (stepsFromStart.get(cell) != null ? stepsFromStart.get(cell) : 0);
                        lowestStepsFromStart = ts;
                        lowestStepsFromStartCell = cell;
                        lowestStepsFromStartCellFavorability = View.getFavorablity(cell);
                        lowestStepsFromStartCellDistance = realDistanceFrom(cell, goal);
                    }
                    if (cell == startLocation){
                        lowestStepsFromStart = 0;
                        lowestStepsFromStartCell = cell;
                        lowestStepsFromStartCellFavorability = View.getFavorablity(cell);
                        lowestStepsFromStartCellDistance = realDistanceFrom(cell, goal);
                    }
                }
                break;
            case 3:
                int[] locations = View.getEnemyLocations();
                for (int i = 0; i < locations.length; i++) {
                    if (locations[i] == cell){
                        isOpen[locations[i]] = false;
                    }
                }
        }
    }
}
