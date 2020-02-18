import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class TimerRunner extends TimerTask {

    public static Timer timer = new Timer();
    public static boolean firstRun = true;

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
        View.resetOptimized();
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
        if (View.clickedC){
            View.resetAll();
            View.clickedC = false;
        }
        if(View.clickedM){
            View.createEnemy();
            View.clickedM = false;
        }

        if (View.clickedUp) {
            PushAttack.startUpAttack();
            View.clickedUp = false;
        }
        if (View.clickedRight) {
            PushAttack.startRightAttack();
            View.clickedRight = false;
        }
        if (View.clickedLeft) {
            PushAttack.startLeftAttack();
            View.clickedLeft = false;
        }
        if (View.clickedDown) {
            PushAttack.startDownAttack();
            View.clickedDown = false;
        }
    }
}
