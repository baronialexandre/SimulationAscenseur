package control;

import ui.ElevatorViewPanel;
import utils.ElevatorState;

import static utils.ElevatorState.*;

public class ElevatorSimulator extends Thread {

    private ElevatorState state=STOPPED;

    private int y = 0;

    private int floorNumber;

    ElevatorViewPanel elevatorViewPanel;

    ElevatorSimulator(int floorNumber, ElevatorViewPanel elevatorViewPanel) { //todo: SALE => elevatorViewPanel est passé en arg pour faire le lien interface utilisateur <=> code control par MainWindows
        this.floorNumber = floorNumber;
        this.elevatorViewPanel = elevatorViewPanel; //todo: SALE => on en a besoin pour goUp et goDown pour faire avancer le curseur
    }

    int getY() {
        return y;
    }

    private void goUp() {
        y++;
        elevatorViewPanel.ascenseur.setValue(y);
    }

    private void goDown() {
        y--;
        elevatorViewPanel.ascenseur.setValue(y);
    }

    void stopUntilOrder() {state = STOPPED; }

    public void run() {
        // doit tourner à l'infini
        for(;;) {
            try {
                nextStep();
                while (state == STOPPED) {
                    sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void setGoingNextDown() {
        state = GOINGNEXTDOWN;
    }
    void setGoingNextUp() {
        state = GOINGNEXTUP;
    }
    void setGoingDown() {
        state = GOINGDOWN;
    }
    void setGoingUp() {
        state = GOINGUP;
    }
    ElevatorState getGoingState() {
        return state;
    }

    private void reachNextFloor() throws InterruptedException {
        if (state == GOINGNEXTDOWN) {
            goDown();
            sleep(10);
        }
        if (state == GOINGNEXTUP) {
            goUp();
            sleep(10);
        }
        while (state == GOINGNEXTDOWN && y%100 != 0) {
            goDown();
            sleep(10);
        }
        while (state == GOINGNEXTUP && y%100 != 0) {
            goUp();
            sleep(10);
        }
    }

    private void nextStep() throws InterruptedException {
        while (state == GOINGDOWN) {
            goDown();
            sleep(10);
        }
        while (state == GOINGUP) {
            goUp();
            sleep(10);
        }
        if(state == GOINGNEXTDOWN || state == GOINGNEXTUP) {
            reachNextFloor();
            stopUntilOrder();
        }
    }
}


