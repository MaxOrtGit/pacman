import java.awt.*;
import java.util.*;

public class Enemy<addToPath> {

    public int location;
    public static int sizeOfGrid = View.sizeOfGrid;
    public boolean ableToRun = true;
    public boolean running = false;
    public boolean ran = false;
    public static boolean paint = false;
    public int target = -1;
    public boolean roaming = false;
    public int untilMove = 0;
    public int moveTick = 30;
    public int lookTick = 2;
    public Color color;

    public Enemy() {
        boolean done = false;
        Random rand = new Random();
        while (done == false) {
            int toBeGhost = rand.nextInt(sizeOfGrid*sizeOfGrid - 1);
            if (View.spaceEmpty(toBeGhost, toBeGhost, 0) && toBeGhost != View.playerLocation){
                done = true;
                location = toBeGhost;
                float r = rand.nextFloat();
                float g = rand.nextFloat();
                float b = rand.nextFloat();
                color = new Color(r, g, b);
                View.effectColor[toBeGhost] = color;

                View.updated[toBeGhost] = true;
            }
        }
    }

    public void runASharp() {
        if (running == true){
            running = false;
        } else {
            running = true;
        }
        ran = true;
    }

    public void tick() {
        if (ran == true && running == true) {
            if (lookTick == 0) {
                lookAI();
                lookTick = 2;
            } else {
                lookTick--;
            }
            if (moveTick == 0) {
                lookAI();
                if (running && ableToRun) {
                    if (!roaming) {
                        pathfind();
                    } else {
                        roamIA();
                    }
                }

                if (paint) {
                    if (roaming) {
                        LineOfSight.run(location, 2);
                    }
                }
                moveTick = 30;
            } else {
                moveTick--;
            }
        }
    }

    public void pathfind() {

        untilMove = 1;
        int[] newLocation = PathFind.startPathfindAI(paint, roaming, target, location);
        if (newLocation[0] == -2) {
            int oEnemyLocation = location;
            int nEnemyLocation = newLocation[1];
            location = nEnemyLocation;
            View.paintEnemy(oEnemyLocation, nEnemyLocation, color);
            if (paint && target != View.playerLocation) {
                View.effectColor[target] = (PushAttack.getGradient(new Color(200, 0, 0), View.getNormCellColor(target), .25));
                View.updated[target] = true;
            }
            if (location == View.playerLocation) {
                View.effectColor[View.playerLocation] = (Color.YELLOW);
                View.updated[View.playerLocation] = true;
            }
        }
        if (newLocation[0] >= 0) {
            System.out.println("hi");
            int oEnemyLocation = location;
            int nEnemyLocation = newLocation[1];
            location = nEnemyLocation;
            View.paintEnemy(oEnemyLocation, nEnemyLocation, color);
        }
    }

    public void roamIA() {
        if (untilMove == 0){
            untilMove = 1;
            Random rand = new Random();
            for (int j = 0; j < 50; j++){
                int toMove = rand.nextInt(4) + 1;
                switch (toMove){
                    case(1):
                        if (View.spaceEmpty(location+sizeOfGrid, location, 0)){
                            int oLocation = location;
                            location = location+sizeOfGrid;
                            View.paintEnemy(oLocation, location, color);
                            j = 50;
                        }
                        break;
                    case(2):
                        if (View.spaceEmpty(location-sizeOfGrid, location, 0)){
                            int oLocation = location;
                            location = location-sizeOfGrid;
                            View.paintEnemy(oLocation, location, color);
                            j = 50;
                        }
                        break;
                    case(3):
                        if (View.spaceEmpty(location+1, location, 2)){
                            int oLocation = location;
                            location = location+1;
                            View.paintEnemy(oLocation, location, color);
                            j = 50;
                        }
                        break;
                    case(4):
                        if (View.spaceEmpty(location-1, location, 1)){
                            int oLocation = location;
                            location = location-1;
                            View.paintEnemy(oLocation, location, color);
                            j = 50;
                        }
                        break;
                }
            }
        } else {
            untilMove -= 1;
        }
    }

    public void lookAI(){

        int LOSLocation = LineOfSight.run(location, 1);
        if (LOSLocation == -1) {
            if (target != location) {
                if (target != -1) {
                    LOSLocation = target;
                } else {
                    if (!roaming) {
                        roaming = true;
                        untilMove = 3;
                    }
                }
            } else {
                if (!roaming) {
                    roaming = true;
                    untilMove = 3;
                }
            }
        } else {
            target = LOSLocation;
            roaming = false;
        }
    }


    public static void DTSC(int cell, int choice, int oCell) {
        switch (choice){
        }
    }
}

