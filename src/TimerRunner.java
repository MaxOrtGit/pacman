import java.awt.*;
import java.awt.event.KeyEvent;
import java.net.http.WebSocket;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TimerRunner extends TimerTask {

    public static Timer timer = new Timer();
    public static boolean firstRun = true;
    public static int sizeOfGrid = View.sizeOfGrid;

    public static void firstRun() {
        PushAttack.hasEffect = new int[View.sizeOfGrid * View.sizeOfGrid];
        Arrays.fill(PushAttack.hasEffect, 1);
        timer.schedule(new TimerRunner(), 0, 16);
        View.resetAll();
    }

    @Override
    public void run() {
        System.arraycopy(View.normColor, 0, View.effectColor,0, View.normColor.length);
        int[] locations = View.getEnemyLocations();
        for (int i = 0; i < locations.length; i++) {
            View.effectColor[locations[i]] = View.enemies[i].color;
            View.updated[locations[i]] = true;
        }
        View.effectColor[View.playerLocation] = Color.YELLOW;
        View.updated[View.playerLocation] = true;
        playerInput();
        pushAttack();
        enemy();
        if (!View.canSeeAll) {
            LineOfSight.run(View.playerLocation, 3);
        }
        View.resetOptimized(View.canSeeAll);
        Arrays.fill(View.canSee, false);
        Arrays.fill(View.updated, false);
    }

    public static void enemy(){
        for (int i = 0; i < View.enemies.length; i++) {
            View.enemies[i].tick();
        }
    }

    public static void pushAttack() {
        System.arraycopy(View.normColor, 0, PushAttack.saveColor,0, View.normColor.length);
        if (PushAttack.UA){
            PushAttack.upAttack();
        }
        if (PushAttack.RA){
            PushAttack.rightAttack();
        }
        if (PushAttack.LA){
            PushAttack.leftAttack();
        }
        if (PushAttack.DA){
            PushAttack.downAttack();
        }
    }

    public static void playerInput(){
        if (View.moveUp && View.spaceNotBlocked(View.playerLocation - View.sizeOfGrid, View.playerLocation, 0)) {
            View.moveUp = false;
            View.movePlayer(View.playerLocation, View.playerLocation - View.sizeOfGrid, 0);
        }
        if (View.moveDown && View.spaceNotBlocked(View.playerLocation + View.sizeOfGrid, View.playerLocation, 0)) {
            View.moveDown = false;
            View.movePlayer(View.playerLocation, View.playerLocation + View.sizeOfGrid, 0);
        }
        if (View.moveLeft && View.spaceNotBlocked(View.playerLocation - 1, View.playerLocation, 1)) {
            View.moveLeft = false;
            View.movePlayer(View.playerLocation, View.playerLocation - 1, 1);
        }
        if (View.moveRight && View.spaceNotBlocked(View.playerLocation + 1, View.playerLocation, 2)) {
            View.moveRight = false;
            View.movePlayer(View.playerLocation, View.playerLocation + 1, 2);
        }
        if (View.moveUp ) {
            View.moveUp = false;
            View.movePlayer(View.playerLocation, View.playerLocation - View.sizeOfGrid, 0);
        }
        if (View.moveDown ) {
            View.moveDown = false;
            View.movePlayer(View.playerLocation, View.playerLocation + View.sizeOfGrid, 0);
        }
        if (View.moveLeft ) {
            View.moveLeft = false;
            View.movePlayer(View.playerLocation, View.playerLocation - 1, 1);
        }
        if (View.moveRight ) {
            View.moveRight = false;
            View.movePlayer(View.playerLocation, View.playerLocation + 1, 2);
        }

        if (View.clickedV){
            int[] locations = View.getEnemyLocations();
            for (int location : locations) {
                LineOfSight.run(location, 2);
            }
            View.clickedV = false;
        }

        if (View.clickedG){
            View.canSeeAll = !View.canSeeAll;
            System.out.println(View.canSeeAll);
            View.clickedG = false;
        }

        if (View.clickedB){
            View.resetAll();
            LineOfSight.run(View.playerLocation, 2);
            View.clickedB = false;
        }

        if (View.clickedC){
            View.resetAll();
            View.clickedC = false;
        }
        if(View.clickedM){
            View.createEnemy();
            View.clickedM = false;
        }

        if (View.clickedU) {
            for (int x = 0; x < sizeOfGrid * sizeOfGrid; x++) {
                View.makeFloor(x);
            }
            for (int x = 0; x < sizeOfGrid; x++) {
                View.makeWall(x);
            }
            for (int x = 0; x < sizeOfGrid; x++) {
                View.makeWall(x * sizeOfGrid);
            }
            for (int x = 1; x < sizeOfGrid; x++) {
                View.makeWall(x * sizeOfGrid - 1);
            }
            for (int x = 0; x < sizeOfGrid; x++) {
                View.makeWall(x + (sizeOfGrid * (sizeOfGrid - 1)));
            }
            View.clickedU = false;
        }

        if (View.clickedI) {
            for (int x = 0; x < sizeOfGrid * sizeOfGrid; x++) {
                View.makeFloor(x);
            }
            View.clickedI = false;
        }

        if (View.clickedO) {
            Random rand = new Random();

            for (int i = 0; i < 5; i++) {
                int toBeWall = rand.nextInt(sizeOfGrid*sizeOfGrid);
                if (!View.isWall[toBeWall] && toBeWall != View.playerLocation){
                    View.makeWall(toBeWall);
                } else {
                    i--;
                }
            }
            View.clickedO = false;
        }

        if (View.clickedUp) {
//            PushAttack.startUpAttack();
            View.clickedUp = false;
        }
        if (View.clickedRight) {
//            PushAttack.startRightAttack();
            View.clickedRight = false;
        }
        if (View.clickedLeft) {
//            PushAttack.startLeftAttack();
            View.clickedLeft = false;
        }
        if (View.clickedDown) {
//            PushAttack.startDownAttack();
            View.clickedDown = false;
        }
    }
}
