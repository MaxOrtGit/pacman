import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Random;

public class View extends JPanel implements KeyListener, MouseListener {

    public static JFrame TScreen;
    public static JPanel[] gridCell;
    public static boolean colorPicked;
    public static int sizeOfGrid = 11;
    public static int sizeofSquare = 50;
    public static int chosenCell;
    public static int walls = sizeOfGrid * 2 + 4;
    public static Color[] normColor;
    public static Color[] effectColor;
    public static boolean[] isWall;
    public static boolean[] isEffectWall;
    public static boolean[] hasEffect;
    public static int[] AVal;
    public static int playerLocation = sizeOfGrid*2 +2;
    public static boolean createWall = true;
    public static boolean fov = false;
    public static boolean[] canSee = new boolean[sizeOfGrid*sizeOfGrid];
    public static boolean[] updated;
    public static boolean moveUp = false;
    public static boolean moveDown = false;
    public static boolean moveLeft = false;
    public static boolean moveRight = false;
    public static boolean clickedV = false;
    public static boolean clickedC = false;
    public static boolean clickedM = false;
    public static boolean clickedUp = false;
    public static boolean clickedDown = false;
    public static boolean clickedLeft = false;
    public static boolean clickedRight = false;
    public static Enemy[] enemies = new Enemy[0];
    public View() {
        GridLoader();
    }

    public void GridLoader() {

        TScreen = new JFrame("grid");
        TScreen.setLocation(100, 100);
        TScreen.setSize(new Dimension(sizeOfGrid * sizeofSquare, sizeOfGrid * sizeofSquare));
        TScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TScreen.addKeyListener(this);

        GridLayout layout = new GridLayout(sizeOfGrid, sizeOfGrid);

        TScreen.setLayout(layout);
        gridCell = GridControler.gridCreator(sizeOfGrid);
        for (int i = 0; i < sizeOfGrid*sizeOfGrid; i++) {
            TScreen.add(gridCell[i]);
            normColor[i] = gridCell[i].getBackground();
            effectColor[i] = gridCell[i].getBackground();
        }

        TScreen.addMouseListener(this);
    }

    public static void createEnemy(){
        Enemy[] tempEnemies = new Enemy[enemies.length + 1];
        for (int i = 0; i < enemies.length; i++) {
            tempEnemies[i] = enemies[i];
        }
        tempEnemies[enemies.length] = new Enemy();
        enemies = new Enemy[tempEnemies.length];
        System.arraycopy(tempEnemies, 0, enemies,0, tempEnemies.length);
    }

    public static int[] getEnemyLocations(){
        if (enemies.length != 0) {
            int[] toReturn = new int[enemies.length];
            for (int i = 0; i < enemies.length; i++) {
                toReturn[i] = enemies[i].location;
            }
            return toReturn;
        }
        return new int[0];
    }

    public static Color getNormCellColor(int cell){
        if(cell == playerLocation){
            return Color.YELLOW;
        }
        int[] locations = getEnemyLocations();
        for (int i = 0; i < locations.length; i++) {
            if (cell == locations[i]) {
                return enemies[i].color;
            }
        }
        return normColor[cell];
    }

    public static int selectedCellFinder(Point selectedPointOnScreen){
        for (int i = 0; i < sizeOfGrid*sizeOfGrid; i++) {
            if (selectedPointOnScreen.x > gridCell[i].getLocationOnScreen().x && selectedPointOnScreen.x < (gridCell[i].getLocationOnScreen().x + sizeofSquare) && selectedPointOnScreen.y > gridCell[i].getLocationOnScreen().y && selectedPointOnScreen.y < (gridCell[i].getLocationOnScreen().y + sizeofSquare)){
                return(i);
            }
        }
        return(-1);
    }

    public static void resetAll(){
        for (int i = 0; i < sizeOfGrid*sizeOfGrid; i++) {
            gridCell[i].setBackground(effectColor[i]);
        }
    }

    public static void resetOptimized(){
        for (int i = 0; i < sizeOfGrid*sizeOfGrid; i++) {
            if (updated[i]) {
                gridCell[i].setBackground(effectColor[i]);
            }
        }
    }

    public static void movePlayer(int oPlayerLocation, int nPlayerLocation, int toSide) {
        if (GridControler.notCrossingAEdge(oPlayerLocation, nPlayerLocation, toSide) && isWall[nPlayerLocation] != true && isEffectWall[nPlayerLocation] != true) {
            playerLocation = nPlayerLocation;
            paintPlayer(oPlayerLocation, nPlayerLocation);
        }
    }

    public static void paintPlayer(int oPlayerLocation, int nPlayerLocation) {
        effectColor[oPlayerLocation] = (getNormCellColor(oPlayerLocation));
        updated[oPlayerLocation] = true;
        effectColor[nPlayerLocation] = (Color.YELLOW);
        updated[nPlayerLocation] = true;
    }

    public static void paintEnemy(int oEnemyLocation, int nEnemyLocation, Color color) {
        effectColor[oEnemyLocation] = (getNormCellColor(oEnemyLocation));
        updated[oEnemyLocation] = true;
        effectColor[nEnemyLocation] = color;
        updated[nEnemyLocation] = true;
        if (nEnemyLocation == playerLocation){
            effectColor[playerLocation] = (Color.YELLOW);
            updated[playerLocation] = true;
        }
    }

    public static void paintWall( int nWallLocation) {
        if(spaceOpen(nWallLocation)) {
            normColor[nWallLocation] = Color.darkGray;
            updateCell(nWallLocation,Color.darkGray);
        }
    }

    public static void paintFloor(int nWallLocation) {
        if(spaceOpen(nWallLocation)) {
            if (nWallLocation % 2 == 0) {
                normColor[nWallLocation] = new Color(190, 190, 190);
                updateCell(nWallLocation, new Color(190, 190, 190));
            } else {
                normColor[nWallLocation] = new Color(200, 200, 200);
                updateCell(nWallLocation, new Color(200, 200, 200));
            }
        }
    }

    private static void updateCell(int nWallLocation, Color cl) {
        if(!hasEffect[nWallLocation]){
            effectColor[nWallLocation] = cl;
            updated[nWallLocation] = true;
        }
    }

    public static boolean spaceEmpty(int cell, int oCell, int dir){
        int[] locations = getEnemyLocations();
        for (int i = 0; i < locations.length; i++) {
            if (locations[i] == cell){
                return false;
            }
        }
        return (GridControler.notCrossingAEdge(oCell,cell,dir) && playerLocation != cell & !isWall[cell] && !isEffectWall[cell]);
    }

    public static boolean spaceNotBlocked(int cell, int oCell, int dir){
        return (GridControler.notCrossingAEdge(oCell,cell,dir) && (!isWall[cell] && !isEffectWall[cell]));
    }
    public static boolean spaceOpen(int cell){
        int[] locations = getEnemyLocations();
        for (int i = 0; i < locations.length; i++) {
            if (locations[i] == cell){
                return false;
            }
        }
        return playerLocation != cell;
    }


    static void DTSC(int cell, int choice) {
        switch (choice) {
        }
    }

    @Override
    public void keyTyped(KeyEvent k) {
    }

    @Override
    public void keyPressed(KeyEvent k) {
        if (k.getKeyCode() == KeyEvent.VK_W) {
            moveUp = true;
        }
        if (k.getKeyCode() == KeyEvent.VK_S) {
            moveDown = true;
        }
        if (k.getKeyCode() == KeyEvent.VK_A) {
            moveLeft = true;
        }
        if (k.getKeyCode() == KeyEvent.VK_D) {
            moveRight = true;
        }

        if (k.getKeyCode() == KeyEvent.VK_ENTER) {
            for (int i = 0; i < enemies.length; i++) {
                enemies[i].runASharp();
            }
        }

        if (k.getKeyCode() == KeyEvent.VK_SHIFT) {
            resetAll();
            Enemy.paint = !Enemy.paint;
        }

        if (k.getKeyCode() == KeyEvent.VK_BACK_SLASH) {
            createWall = !createWall;
        }


        if (k.getKeyCode() == KeyEvent.VK_UP) {
            clickedUp = true;
        }
        if (k.getKeyCode() == KeyEvent.VK_RIGHT) {
            clickedRight = true;
        }
        if (k.getKeyCode() == KeyEvent.VK_LEFT) {
            clickedLeft = true;
        }
        if (k.getKeyCode() == KeyEvent.VK_DOWN) {
            clickedDown = true;
        }

        if (k.getKeyCode() == KeyEvent.VK_M) {
            clickedM = true;
        }

        if (k.getKeyCode() == KeyEvent.VK_V) {
            clickedV = true;
        }

        if (k.getKeyCode() == KeyEvent.VK_C) {
            clickedC = true;
        }

        if (k.getKeyCode() == KeyEvent.VK_F) {
            fov = !fov;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent m) {
    }

    @Override
    public void mousePressed(MouseEvent m) {
        chosenCell = selectedCellFinder(m.getLocationOnScreen());
    }

    @Override
    public void mouseReleased(MouseEvent m) {
        int selectedCell = selectedCellFinder(m.getLocationOnScreen());
        if (selectedCell == chosenCell) {
            if (createWall){
                if(spaceOpen(selectedCell)) {
                    if (isWall[selectedCell] == true) {
                        isWall[selectedCell] = false;
                        paintFloor(selectedCell);
                    } else {
                        isWall[selectedCell] = true;
                        paintWall(selectedCell);
                    }
                }
            } else {
                System.out.println("cell: " + selectedCell + " normColor: " + normColor[selectedCell] + " effectColor: " + effectColor[selectedCell]);
            }
        } else {
            int[] locations = getEnemyLocations();
            for (int i = 0; i < locations.length; i++) {
                if (chosenCell == locations[i]){
                    locations[i] = selectedCell;
                    paintEnemy(chosenCell, locations[i], enemies[i].color);
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent m) {

    }

    @Override
    public void mouseExited(MouseEvent m) {

    }


    public static void main(String[] args) {
        colorPicked = false;
        effectColor = new Color[sizeOfGrid * sizeOfGrid];
        normColor = new Color[sizeOfGrid * sizeOfGrid];
        new View();
        isWall = new boolean[sizeOfGrid * sizeOfGrid];
        Arrays.fill(isWall, false);
        isEffectWall = new boolean[sizeOfGrid * sizeOfGrid];
        Arrays.fill(isEffectWall, false);
        hasEffect = new boolean[sizeOfGrid * sizeOfGrid];
        Arrays.fill(hasEffect, false);
        AVal = new int[sizeOfGrid * sizeOfGrid];
        Arrays.fill(AVal, 0);
        updated = new boolean[sizeOfGrid * sizeOfGrid];
        Arrays.fill(updated, false);

        Random rand = new Random();

        for (int i = 0; i < walls; i++) {
            int toBeWall = rand.nextInt(sizeOfGrid*sizeOfGrid);
            if (isWall[toBeWall] != true && toBeWall != playerLocation){
                isWall[toBeWall] = true;
                effectColor[toBeWall] = (Color.DARK_GRAY);
                normColor[toBeWall] = (Color.DARK_GRAY);
            } else {
                i--;
            }
        }



        effectColor[playerLocation] = (Color.YELLOW);

        TScreen.setVisible(true);

        TimerRunner.firstRun();
    }
}
