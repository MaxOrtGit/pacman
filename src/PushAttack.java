
import java.awt.*;

public class PushAttack {


    public static int sizeOfGrid = View.sizeOfGrid;
    public static int UATime = 0;
    public static int RATime = 0;
    public static int LATime = 0;
    public static int DATime = 0;
    public static boolean UA = false;
    public static boolean RA = false;
    public static boolean LA = false;
    public static boolean DA = false;
    public static boolean UAReset = false;
    public static boolean RAReset = false;
    public static boolean LAReset = false;
    public static boolean DAReset = false;

    public static int ULocation = -1;
    public static int ULocationC1 = -1;
    public static int ULocationC2 = -1;
    public static int ULocationC3 = -1;
    public static int ULocationF1 = -1;
    public static int ULocationF2 = -1;
    public static int ULocationF3 = -1;

    public static int RLocation = -1;
    public static int RLocationC1 = -1;
    public static int RLocationC2 = -1;
    public static int RLocationC3 = -1;
    public static int RLocationF1 = -1;
    public static int RLocationF2 = -1;
    public static int RLocationF3 = -1;

    public static int LLocation = -1;
    public static int LLocationC1 = -1;
    public static int LLocationC2 = -1;
    public static int LLocationC3 = -1;
    public static int LLocationF1 = -1;
    public static int LLocationF2 = -1;
    public static int LLocationF3 = -1;

    public static int DLocation = -1;
    public static int DLocationC1 = -1;
    public static int DLocationC2 = -1;
    public static int DLocationC3 = -1;
    public static int DLocationF1 = -1;
    public static int DLocationF2 = -1;
    public static int DLocationF3 = -1;

    public static Enemy[] UPushed;
    public static Enemy[] RPushed;
    public static Enemy[] LPushed;
    public static Enemy[] DPushed;

    public static int[] hasEffect;
    public static Color[] saveColor = new Color[sizeOfGrid * sizeOfGrid];;

    public static void main(String[] args) {
    }

    public static Color getGradient(Color col1, Color col2, double per){
        //per of cal1
        return new Color((int)(col1.getRed()*per + col2.getRed()*(1-per)), (int)(col1.getGreen()*per + col2.getGreen()*(1-per)), (int)(col1.getBlue()*per + col2.getBlue()*(1-per)));
    }
    public static int overFlow(int num){
        if (num > 255){
            return 255;
        } else {
            if (num < 0) {
                return 0;
            } else {
                return num;
            }
        }
    }
    public static Color addColors(Color col1, Color col2, Color org) {
        //Color grad = getGradient(org, new Color(overFlow(col1.getRed() + col2.getRed() - orGridControler.getRed()), overFlow(col1.getGreen() + col2.getGreen() - orGridControler.getGreen()), overFlow(col1.getBlue() + col2.getBlue() - orGridControler.getBlue())), .95);
        return new Color(overFlow(col1.getRed() + col2.getRed() - org.getRed()), overFlow(col1.getGreen() + col2.getGreen() - org.getGreen()), overFlow(col1.getBlue() + col2.getBlue() - org.getBlue()));
    }

    public static void createFadeEffect(int pos1, int pos2, int pos3, double grad, int from){
        if(pos1 != -1){
            Color grad1;
            grad1 = getGradient(Color.RED, View.getNormCellColor(pos1), grad);
            if (true) {
                switch (from) {
                    case 1:
                        if (hasEffect[pos1] % 3 != 0 && hasEffect[pos1] % 5 != 0 && hasEffect[pos1] % 7 != 0) {
                            View.effectColor[pos1] = (addColors(grad1,saveColor[pos1],View.getNormCellColor(pos1)));
                            View.updated[pos1] = true;
                        } else {
                            saveColor[pos1] = addColors(grad1,saveColor[pos1],View.getNormCellColor(pos1));
                        }
                        break;
                    case 2:
                        if (hasEffect[pos1] % 5 != 0 && hasEffect[pos1] % 7 != 0) {
                            View.effectColor[pos1] = (addColors(grad1,saveColor[pos1],View.getNormCellColor(pos1)));
                            View.updated[pos1] = true;
                        } else {
                            saveColor[pos1] = addColors(grad1,saveColor[pos1],View.getNormCellColor(pos1));
                        }
                        break;
                    case 3:
                        if (hasEffect[pos1] % 7 != 0) {
                            View.effectColor[pos1] = (addColors(grad1,saveColor[pos1],View.getNormCellColor(pos1)));
                            View.updated[pos1] = true;
                        } else {
                            saveColor[pos1] = addColors(grad1,saveColor[pos1],View.getNormCellColor(pos1));
                        }
                        break;
                    case 4:
                        View.effectColor[pos1] = (addColors(grad1,saveColor[pos1],View.getNormCellColor(pos1)));
                        View.updated[pos1] = true;
                        break;
                }
            }
        }
        if(pos2 != -1){
            Color grad2;
            grad2 = getGradient(Color.RED, View.getNormCellColor(pos2), grad);
            if (true) {
                switch (from){
                    case 1:
                        if (hasEffect[pos2] % 3 != 0 && hasEffect[pos2] % 5 != 0 && hasEffect[pos2] % 7 != 0) {
                            View.effectColor[pos2] = (addColors(grad2,saveColor[pos2],View.getNormCellColor(pos2)));
                            View.updated[pos2] = true;
                        } else {
                            saveColor[pos2] = addColors(grad2,saveColor[pos2],View.getNormCellColor(pos2));
                        }
                        break;
                    case 2:
                        if (hasEffect[pos2] % 5 != 0 && hasEffect[pos2] % 7 != 0) {
                            View.effectColor[pos2] = (addColors(grad2,saveColor[pos2],View.getNormCellColor(pos2)));
                            View.updated[pos2] = true;
                        } else {
                            saveColor[pos2] = addColors(grad2,saveColor[pos2],View.getNormCellColor(pos2));
                        }
                        break;
                    case 3:
                        if (hasEffect[pos2] % 7 != 0) {
                            View.effectColor[pos2] = (addColors(grad2,saveColor[pos2],View.getNormCellColor(pos2)));
                            View.updated[pos2] = true;
                        } else {
                            saveColor[pos2] = addColors(grad2,saveColor[pos2],View.getNormCellColor(pos2));
                        }
                        break;
                    case 4:
                        View.effectColor[pos2] = (addColors(grad2,saveColor[pos2],View.getNormCellColor(pos2)));
                        View.updated[pos2] = true;
                        break;
                }
            }
        }
        if(pos3 != -1){
            Color grad3;
            grad3 = getGradient(Color.RED, View.getNormCellColor(pos3), grad);
            if (true) {
                switch (from){
                    case 1:
                        if (hasEffect[pos3] % 3 != 0 && hasEffect[pos3] % 5 != 0 && hasEffect[pos3] % 7 != 0) {
                            View.effectColor[pos3] = (addColors(grad3,saveColor[pos3],View.getNormCellColor(pos3)));
                            View.updated[pos3] = true;
                        } else {
                            saveColor[pos3] = addColors(grad3,saveColor[pos3],View.getNormCellColor(pos3));
                        }
                        break;
                    case 2:
                        if (hasEffect[pos3] % 5 != 0 && hasEffect[pos3] % 7 != 0) {
                            View.effectColor[pos3] = (addColors(grad3,saveColor[pos3],View.getNormCellColor(pos3)));
                            View.updated[pos3] = true;
                        } else {
                            saveColor[pos3] = addColors(grad3,saveColor[pos3],View.getNormCellColor(pos3));
                        }
                        break;
                    case 3:
                        if (hasEffect[pos3] % 7 != 0) {
                            View.effectColor[pos3] = (addColors(grad3,saveColor[pos3],View.getNormCellColor(pos3)));
                            View.updated[pos3] = true;
                        } else {
                            saveColor[pos3] = addColors(grad3,saveColor[pos3],View.getNormCellColor(pos3));
                        }
                        break;
                    case 4:
                        View.effectColor[pos3] = (addColors(grad3,saveColor[pos3],View.getNormCellColor(pos3)));
                        View.updated[pos3] = true;
                        break;
                }
            }
        }
    }

    public static void createWall(int pos1, int pos2, int pos3, Color color){
        if(pos1 != -1){
            View.effectColor[pos1] = color;
            View.updated[pos1] = true;
        }
        if(pos2 != -1){
            View.effectColor[pos2] = color;
            View.updated[pos2] = true;
        }
        if(pos3 != -1){
            View.effectColor[pos3] = color;
            View.updated[pos3] = true;
        }
    }

    public static void createPWall(int pos1, int pos2, int pos3) {
        if(pos1 != -1){
            View.isEffectWall[pos1] = true;
            View.hasEffect[pos1] = true;
        }
        if(pos2 != -1){
            View.isEffectWall[pos2] = true;
            View.hasEffect[pos2] = true;
        }
        if(pos3 != -1){
            View.isEffectWall[pos3] = true;
            View.hasEffect[pos3] = true;
        }
    }

    public static void removeEffect(int pos1, int pos2, int pos3,int from){
        if(pos1 != -1 && !View.isEffectWall[pos1]){
            switch (from){
                case 1:
                    if (hasEffect[pos1] % 3 != 0 && hasEffect[pos1] % 5 != 0 && hasEffect[pos1] % 7 != 0) {
                        View.effectColor[pos1] = View.getNormCellColor(pos1);
                        View.updated[pos1] = true;
                    }
                    break;
                case 2:
                    if (hasEffect[pos1] % 5 != 0 && hasEffect[pos1] % 7 != 0) {
                        View.effectColor[pos1] = View.getNormCellColor(pos1);
                        View.updated[pos1] = true;
                    }
                    break;
                case 3:
                    if (hasEffect[pos1] % 7 != 0) {
                        View.effectColor[pos1] = View.getNormCellColor(pos1);
                        View.updated[pos1] = true;
                    }
                    break;
                case 4:
                    View.effectColor[pos1] = View.getNormCellColor(pos1);
                    View.updated[pos1] = true;
                    break;
            }
        }
        if(pos2 != -1 && !View.isEffectWall[pos2]){
            switch (from){
                case 1:
                    if (hasEffect[pos2] % 3 != 0 && hasEffect[pos2] % 5 != 0 && hasEffect[pos2] % 7 != 0) {
                        View.effectColor[pos2] = View.getNormCellColor(pos2);
                        View.updated[pos2] = true;
                    }
                    break;
                case 2:
                    if (hasEffect[pos2] % 5 != 0 && hasEffect[pos2] % 7 != 0) {
                        View.effectColor[pos2] = View.getNormCellColor(pos2);
                        View.updated[pos2] = true;
                    }
                    break;
                case 3:
                    if (hasEffect[pos2] % 7 != 0) {
                        View.effectColor[pos2] = View.getNormCellColor(pos2);
                        View.updated[pos2] = true;
                    }
                    break;
                case 4:
                    View.effectColor[pos2] = View.getNormCellColor(pos2);
                    View.updated[pos2] = true;
                    break;
            }
        }
        if(pos3 != -1 && !View.isEffectWall[pos3]){
            switch (from){
                case 1:
                    if (hasEffect[pos3] % 3 != 0 && hasEffect[pos3] % 5 != 0 && hasEffect[pos3] % 7 != 0) {
                        View.effectColor[pos3] = View.getNormCellColor(pos3);
                        View.updated[pos3] = true;
                    }
                    break;
                case 2:
                    if (hasEffect[pos3] % 5 != 0 && hasEffect[pos3] % 7 != 0) {
                        View.effectColor[pos3] = View.getNormCellColor(pos3);
                        View.updated[pos3] = true;
                    }
                    break;
                case 3:
                    if (hasEffect[pos3] % 7 != 0) {
                        View.effectColor[pos3] = View.getNormCellColor(pos3);
                        View.updated[pos3] = true;
                    }
                    break;
                case 4:
                    View.effectColor[pos3] = View.getNormCellColor(pos3);
                    View.updated[pos3] = true;
                    break;
            }
        }
    }

    public static void removePWall(int pos1, int pos2, int pos3){
        if(pos1 != -1){
            View.isEffectWall[pos1] = false;
            View.hasEffect[pos1] = false;
        }
        if(pos2 != -1){
            View.isEffectWall[pos2] = false;
            View.hasEffect[pos2] = false;
        }
        if(pos3 != -1){
            View.isEffectWall[pos3] = false;
            View.hasEffect[pos3] = false;
        }
    }

    public static void startUpAttack(){
        UAReset = true;
        UA = true;
    }

    public static void startRightAttack(){
        RAReset = true;
        RA = true;
    }

    public static void startLeftAttack(){
        LAReset = true;
        LA = true;
    }

    public static void startDownAttack(){
        DAReset = true;
        DA = true;
    }

    public static void attack(int time, int locationC1, int locationC2, int locationC3, int locationF1, int locationF2, int locationF3, int dir, int dirSide, int from, int mod){
        if (time == 399){
            if (locationC1 != -1){
                hasEffect[locationC1] *= mod;
            }
            if (locationC2 != -1){
                hasEffect[locationC2] *= mod;
            }
            if (locationC3 != -1){
                hasEffect[locationC3] *= mod;
            }
            for (int i = 0; i < View.enemies.length; i++) {
                Enemy e = View.enemies[i];
                if (e.location == locationC1 && locationF1 != -1 && !View.isWall[locationF1] && !View.isEffectWall[locationF1]) {
                    e.ableToRun = false;
                    e.location = locationF1;
                    View.paintEnemy(locationC1, locationF1, View.enemies[i].color);
                } else if (e.location == locationC2 && locationF2 != -1 && !View.isWall[locationF2] && !View.isEffectWall[locationF2]) {
                    e.ableToRun = false;
                    e.location = locationF2;
                    View.paintEnemy(locationC2, locationF2, View.enemies[i].color);
                } else if (e.location == locationC3 && locationF3 != -1 && !View.isWall[locationF3] && !View.isEffectWall[locationF3]) {
                    e.ableToRun = false;
                    e.location = locationF3;
                    View.paintEnemy(locationC3, locationF3, View.enemies[i].color);
                }
            }
            int locationA1 = -1;
            int locationA2 = -1;
            int locationA3 = -1;
            if (GridControler.notCrossingAEdge(locationF1, locationF1+dir, dirSide)) {
                locationA1 = locationF1+dir;
            }
            if (GridControler.notCrossingAEdge(locationF2, locationF2+dir, dirSide)) {
                locationA2 = locationF2+dir;
            }
            if (GridControler.notCrossingAEdge(locationF3, locationF3+dir, dirSide)) {
                locationA3 = locationF3+dir;
            }
            for (int i = 0; i < View.enemies.length; i++) {
                Enemy e = View.enemies[i];
                if (e.location == locationF1 && locationA1 != -1 && !View.isWall[locationA1] && !View.isEffectWall[locationA1]) {
                    e.ableToRun = false;
                } else if (e.location == locationF2 && locationA2 != -1 && !View.isWall[locationA2] && !View.isEffectWall[locationA2]) {
                    e.ableToRun = false;
                } else if (e.location == locationF3 && locationA3 != -1 && !View.isWall[locationA3] && !View.isEffectWall[locationA3]) {
                    e.ableToRun = false;
                }
            }
        }
        if (time <= 398 && time > 350){
            createFadeEffect(locationC1,locationC2,locationC3,((50-(time-350))/100.0)/4, from);
        }
        if (time == 379){
            if (locationF1 != -1){
                hasEffect[locationF1] *= mod;
            }
            if (locationF2 != -1){
                hasEffect[locationF2] *= mod;
            }
            if (locationF3 != -1){
                hasEffect[locationF3] *= mod;
            }
        }
        if (time <= 380 && time > 290){
            createFadeEffect(locationF1,locationF2,locationF3,((100-(time-290))/100.0), from);
        }
        if (time <= 349 && time > 250){
            createFadeEffect(locationC1,locationC2,locationC3,((time-250)/100.0)/8, from);
        }
        if (time == 249){
            if (locationC1 != -1){
                hasEffect[locationC1] /= mod;
            }
            if (locationC2 != -1){
                hasEffect[locationC2] /= mod;
            }
            if (locationC3 != -1){
                hasEffect[locationC3] /= mod;
            }
        }
        if (time == 366){
            int locationA1 = -1;
            int locationA2 = -1;
            int locationA3 = -1;
            if (GridControler.notCrossingAEdge(locationF1, locationF1+dir, dirSide)) {
                locationA1 = locationF1+dir;
            }
            if (GridControler.notCrossingAEdge(locationF2, locationF2+dir, dirSide)) {
                locationA2 = locationF2+dir;
            }
            if (GridControler.notCrossingAEdge(locationF3, locationF3+dir, dirSide)) {
                locationA3 = locationF3+dir;
            }
            for (int i = 0; i < View.enemies.length; i++) {
                Enemy e = View.enemies[i];
                if (e.location == locationF1 && locationA1 != -1 && !View.isWall[locationA1] && !View.isEffectWall[locationA1]) {
                    e.ableToRun = true;

                    e.location = locationA1;
                    View.paintEnemy(locationF1, locationA1, View.enemies[i].color);
                } else if (e.location == locationF2 && locationA2 != -1 && !View.isWall[locationA2] && !View.isEffectWall[locationA2]) {
                    e.ableToRun = true;
                    e.location = locationA2;
                    View.paintEnemy(locationF2, locationA2, View.enemies[i].color);
                } else if (e.location == locationF3 && locationA3 != -1 && !View.isWall[locationA3] && !View.isEffectWall[locationA3]) {
                    e.ableToRun = true;
                    e.location = locationA3;
                    View.paintEnemy(locationF3, locationA3, View.enemies[i].color);

                }
            }
        }
        if (time <= 365 && time > 363){
            createPWall(locationF1,locationF2,locationF3);
        }
        if (time <= 291 && time > 102) {
            createWall(locationF1,locationF2,locationF3,Color.RED);
        }
        if (time <= 291 && time > 101) {
            createWall(locationF1,locationF2,locationF3,new Color(Color.RED.getRed()-1,Color.RED.getGreen(),Color.RED.getBlue()));
        }
        if (time == 249) {
            removeEffect(locationC1,locationC2,locationC3,from);
        }
        if (time <= 101 && time > 2){
            createFadeEffect(locationF1,locationF2,locationF3,((time-1)/100.0), from);
        }
        if (time == 25) {
            removePWall(locationF1,locationF2,locationF3);
        }
    }

    public static void upAttack(){
        UATime-=1;

        if (UATime >= 1) {
            attack(UATime, ULocationC1, ULocationC2, ULocationC3, ULocationF1, ULocationF2, ULocationF3, -sizeOfGrid, 0, 1,2);
        }

        if (UAReset || UATime == 1){
            removePWall(ULocationF1, ULocationF2, ULocationF3);
            removeEffect(ULocationC1, ULocationC2, ULocationC3,1);
            removeEffect(ULocationF1, ULocationF2, ULocationF3,1);

            if (ULocationC1 != -1 && hasEffect[ULocationC1] % 2 == 0){
                hasEffect[ULocationC1] /= 2;
            }
            if (ULocationC2 != -1 && hasEffect[ULocationC2] % 2 == 0){
                hasEffect[ULocationC2] /= 2;
            }
            if (ULocationC3 != -1 && hasEffect[ULocationC3] % 2 == 0){
                hasEffect[ULocationC3] /= 2;
            }
            if (ULocationF1 != -1 && hasEffect[ULocationF1] % 2 == 0){
                hasEffect[ULocationF1] /= 2;
            }
            if (ULocationF2 != -1 && hasEffect[ULocationF2] % 2 == 0){
                hasEffect[ULocationF2] /= 2;
            }
            if (ULocationF3 != -1 && hasEffect[ULocationF3] % 2 == 0){
                hasEffect[ULocationF3] /= 2;
            }
            UA = false;
        }

        if(UAReset){
            UAReset = false;
            UATime = 400;
            ULocation = View.playerLocation;
            if (GridControler.notCrossingAEdge(ULocation, ULocation-1,1)) {
                if (GridControler.notCrossingAEdge(ULocation-1, ULocation-sizeOfGrid-1,0)) {
                    ULocationC1 = ULocation-sizeOfGrid-1;
                    if (GridControler.notCrossingAEdge(ULocationC1, ULocationC1-sizeOfGrid,0)) {
                        ULocationF1 = ULocationC1-sizeOfGrid;
                    } else {
                        ULocationF1 = -1;
                    }
                } else {
                    ULocationC1 = -1;
                    ULocationF1 = -1;
                }
            } else {
                ULocationC1 = -1;
                ULocationF1 = -1;
            }
            if (GridControler.notCrossingAEdge(ULocation, ULocation-sizeOfGrid,0)) {
                ULocationC2 = ULocation-sizeOfGrid;
                if (GridControler.notCrossingAEdge(ULocationC2, ULocationC2-sizeOfGrid,0)) {
                    ULocationF2 = ULocationC2-sizeOfGrid;
                } else {
                    ULocationF2 = -1;
                }
            } else {
                ULocationC2 = -1;
                ULocationF2 = -1;
            }
            if (GridControler.notCrossingAEdge(ULocation, ULocation+1,2)) {
                if (GridControler.notCrossingAEdge(ULocation+1, ULocation-sizeOfGrid+1,0)) {
                    ULocationC3 = ULocation-sizeOfGrid+1;
                    if (GridControler.notCrossingAEdge(ULocationC3, ULocationC3-sizeOfGrid,0)) {
                        ULocationF3 = ULocationC3-sizeOfGrid;
                    } else {
                        ULocationF3 = -1;
                    }
                } else {
                    ULocationC3 = -1;
                    ULocationF3 = -1;
                }
            } else {
                ULocationC3 = -1;
                ULocationF3 = -1;
            }
            UA = true;
        }
    }

    public static void rightAttack(){
        RATime-=1;

        if (RATime >= 1) {
            attack(RATime, RLocationC1, RLocationC2, RLocationC3, RLocationF1, RLocationF2, RLocationF3, 1, 2, 2,3);
        }

        if (RAReset || RATime == 1){
            removePWall(RLocationF1, RLocationF2, RLocationF3);
            removeEffect(RLocationC1, RLocationC2, RLocationC3,2);
            removeEffect(RLocationF1, RLocationF2, RLocationF3,2);

            if (RLocationC1 != -1 && hasEffect[RLocationC1] % 3 == 0){
                hasEffect[RLocationC1] /= 3;
            }
            if (RLocationC2 != -1 && hasEffect[RLocationC2] % 3 == 0){
                hasEffect[RLocationC2] /= 3;
            }
            if (RLocationC3 != -1 && hasEffect[RLocationC3] % 3 == 0){
                hasEffect[RLocationC3] /= 3;
            }
            if (RLocationF1 != -1 && hasEffect[RLocationF1] % 3 == 0){
                hasEffect[RLocationF1] /= 3;
            }
            if (RLocationF2 != -1 && hasEffect[RLocationF2] % 3 == 0){
                hasEffect[RLocationF2] /= 3;
            }
            if (RLocationF3 != -1 && hasEffect[RLocationF3] % 3 == 0){
                hasEffect[RLocationF3] /= 3;
            }

            RA = false;
        }

        if(RAReset){
            RAReset = false;
            RATime = 400;
            RLocation = View.playerLocation;
            if (GridControler.notCrossingAEdge(RLocation, RLocation-sizeOfGrid,0)) {
                if (GridControler.notCrossingAEdge(RLocation-sizeOfGrid, RLocation-sizeOfGrid+1,2)) {
                    RLocationC1 = RLocation-sizeOfGrid+1;
                    if (GridControler.notCrossingAEdge(RLocationC1, RLocationC1+1,2)) {
                        RLocationF1 = RLocationC1+1;
                    } else {
                        RLocationF1 = -1;
                    }
                } else {
                    RLocationC1 = -1;
                    RLocationF1 = -1;
                }
            } else {
                RLocationC1 = -1;
                RLocationF1 = -1;
            }
            if (GridControler.notCrossingAEdge(RLocation, RLocation+1,2)) {
                RLocationC2 = RLocation+1;
                if (GridControler.notCrossingAEdge(RLocationC2, RLocationC2+1,2)) {
                    RLocationF2 = RLocationC2+1;
                } else {
                    RLocationF2 = -1;
                }
            } else {
                RLocationC2 = -1;
                RLocationF2 = -1;
            }
            if (GridControler.notCrossingAEdge(RLocation, RLocation+sizeOfGrid,0)) {
                if (GridControler.notCrossingAEdge(RLocation+sizeOfGrid, RLocation+sizeOfGrid+1,2)) {
                    RLocationC3 = RLocation+sizeOfGrid+1;
                    if (GridControler.notCrossingAEdge(RLocationC3, RLocationC3+1,2)) {
                        RLocationF3 = RLocationC3+1;
                    } else {
                        RLocationF3 = -1;
                    }
                } else {
                    RLocationC3 = -1;
                    RLocationF3 = -1;
                }
            } else {
                RLocationC3 = -1;
                RLocationF3 = -1;
            }
            RA = true;
        }
    }

    public static void leftAttack(){
        LATime-=1;

        if (LATime >= 1 && !LAReset) {
            attack(LATime, LLocationC1, LLocationC2, LLocationC3, LLocationF1, LLocationF2, LLocationF3, -1, 1, 3, 5);
        }
        if (LAReset || LATime == 1){
            removePWall(LLocationF1, LLocationF2, LLocationF3);
            removeEffect(LLocationC1, LLocationC2, LLocationC3,3);
            removeEffect(LLocationF1, LLocationF2, LLocationF3,3);

            if (LLocationC1 != -1 && hasEffect[LLocationC1] % 5 == 0){
                hasEffect[LLocationC1] /= 5;
            }
            if (LLocationC2 != -1 && hasEffect[LLocationC2] % 5 == 0){
                hasEffect[LLocationC2] /= 5;
            }
            if (LLocationC3 != -1 && hasEffect[LLocationC3] % 5 == 0){
                hasEffect[LLocationC3] /= 5;
            }
            if (LLocationF1 != -1 && hasEffect[LLocationF1] % 5 == 0){
                hasEffect[LLocationF1] /= 5;
            }
            if (LLocationF2 != -1 && hasEffect[LLocationF2] % 5 == 0){
                hasEffect[LLocationF2] /= 5;
            }
            if (LLocationF3 != -1 && hasEffect[LLocationF3] % 5 == 0){
                hasEffect[LLocationF3] /= 5;
            }

            LA = false;
        }

        if(LAReset){
            LAReset = false;
            LATime = 400;
            LLocation = View.playerLocation;
            if (GridControler.notCrossingAEdge(LLocation, LLocation-sizeOfGrid,0)) {
                if (GridControler.notCrossingAEdge(LLocation-sizeOfGrid, LLocation-sizeOfGrid-1,1)) {
                    LLocationC1 = LLocation-sizeOfGrid-1;
                    if (GridControler.notCrossingAEdge(LLocationC1, LLocationC1-1,1)) {
                        LLocationF1 = LLocationC1-1;
                    } else {
                        LLocationF1 = -1;
                    }
                } else {
                    LLocationC1 = -1;
                    LLocationF1 = -1;
                }
            } else {
                LLocationC1 = -1;
                LLocationF1 = -1;
            }
            if (GridControler.notCrossingAEdge(LLocation, LLocation-1,1)) {
                LLocationC2 = LLocation-1;
                if (GridControler.notCrossingAEdge(LLocationC2, LLocationC2-1,1)) {
                    LLocationF2 = LLocationC2-1;
                } else {
                    LLocationF2 = -1;
                }
            } else {
                LLocationC2 = -1;
                LLocationF2 = -1;
            }
            if (GridControler.notCrossingAEdge(LLocation, LLocation+sizeOfGrid,0)) {
                if (GridControler.notCrossingAEdge(LLocation+sizeOfGrid, LLocation+sizeOfGrid-1,1)) {
                    LLocationC3 = LLocation+sizeOfGrid-1;
                    if (GridControler.notCrossingAEdge(LLocationC3, LLocationC3-1,1)) {
                        LLocationF3 = LLocationC3-1;
                    } else {
                        LLocationF3 = -1;
                    }
                } else {
                    LLocationC3 = -1;
                    LLocationF3 = -1;
                }
            } else {
                LLocationC3 = -1;
                LLocationF3 = -1;
            }
            LA = true;
        }
    }

    public static void downAttack(){
        DATime-=1;

        if (DATime >= 1) {
            attack(DATime, DLocationC1, DLocationC2, DLocationC3, DLocationF1, DLocationF2, DLocationF3, sizeOfGrid, 0, 4,7);
        }

        if (DAReset || DATime == 1){
            removePWall(DLocationF1, DLocationF2, DLocationF3);
            removeEffect(DLocationC1, DLocationC2, DLocationC3,4);
            removeEffect(DLocationF1, DLocationF2, DLocationF3,4);

            if (DLocationC1 != -1 && hasEffect[DLocationC1] % 7 == 0){
                hasEffect[DLocationC1] /= 7;
            }
            if (DLocationC2 != -1 && hasEffect[DLocationC2] % 7 == 0){
                hasEffect[DLocationC2] /= 7;
            }
            if (DLocationC3 != -1 && hasEffect[DLocationC3] % 7 == 0){
                hasEffect[DLocationC3] /= 7;
            }
            if (DLocationF1 != -1 && hasEffect[DLocationF1] % 7 == 0){
                hasEffect[DLocationF1] /= 7;
            }
            if (DLocationF2 != -1 && hasEffect[DLocationF2] % 7 == 0){
                hasEffect[DLocationF2] /= 7;
            }
            if (DLocationF3 != -1 && hasEffect[DLocationF3] % 7 == 0){
                hasEffect[DLocationF3] /= 7;
            }

            DA = false;
        }

        if(DAReset){
            DAReset = false;
            DATime = 400;
            DLocation = View.playerLocation;
            if (GridControler.notCrossingAEdge(DLocation, DLocation-1,1)) {
                if (GridControler.notCrossingAEdge(DLocation-1, DLocation+sizeOfGrid-1,0)) {
                    DLocationC1 = DLocation+sizeOfGrid-1;
                    if (GridControler.notCrossingAEdge(DLocationC1, DLocationC1+sizeOfGrid,0)) {
                        DLocationF1 = DLocationC1+sizeOfGrid;
                    } else {
                        DLocationF1 = -1;
                    }
                } else {
                    DLocationC1 = -1;
                    DLocationF1 = -1;
                }
            } else {
                DLocationC1 = -1;
                DLocationF1 = -1;
            }

            if (GridControler.notCrossingAEdge(DLocation, DLocation+sizeOfGrid,0)) {
                DLocationC2 = DLocation+sizeOfGrid;
                if (GridControler.notCrossingAEdge(DLocationC2, DLocationC2+sizeOfGrid,0)) {
                    DLocationF2 = DLocationC2+sizeOfGrid;
                } else {
                    DLocationF2 = -1;
                }
            } else {
                DLocationC2 = -1;
                DLocationF2 = -1;
            }

            if (GridControler.notCrossingAEdge(DLocation, DLocation+1,2)) {
                if (GridControler.notCrossingAEdge(DLocation+1, DLocation+sizeOfGrid+1,0)) {
                    DLocationC3 = DLocation+sizeOfGrid+1;
                    if (GridControler.notCrossingAEdge(DLocationC3, DLocationC3+sizeOfGrid,0)) {
                        DLocationF3 = DLocationC3+sizeOfGrid;
                    } else {
                        DLocationF3 = -1;
                    }
                } else {
                    DLocationC3 = -1;
                    DLocationF3 = -1;
                }
            } else {
                DLocationC3 = -1;
                DLocationF3 = -1;
            }
            DA = true;
        }
    }

    public static void DTSC(int cell, int choice, String direction) {
    }
}