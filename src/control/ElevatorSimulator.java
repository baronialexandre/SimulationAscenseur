package control;

import utils.ElevatorState;

import static utils.ElevatorState.GOINGDOWN;
import static utils.ElevatorState.STOPPED;

public class ElevatorSimulator extends Thread {

    private ElevatorState state;

    private int y = 0;

    private int floorNumber;

    public ElevatorSimulator(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public int getY() {
        return y;
    }

    public void goUp() {
        y++;
    }

    public void goDown() {
        y--;
    }

    public void stopUntilOrder() {
        this.state = STOPPED;
    }

    public void stopAtNextFloor() {
        try {
            if (this.state == GOINGDOWN) {
                goDown();

                Thread.sleep(10);
            } else {
                goDown();
                Thread.sleep(10);
            }

            while (this.y % 100 != 0) {

                if (this.state == GOINGDOWN) {
                    goDown();

                    Thread.sleep(10);
                } else {
                    goDown();
                    Thread.sleep(10);
                }


            }
        } catch (Exception e) {
            System.out.println("Error in elevator motor brake");
        }
        stopUntilOrder();
    }
        public void run () {
            nextStep();
        }

        public void nextStep () {
            while (this.state != STOPPED) {
                try {
                    if (this.state == GOINGDOWN) {
                        goDown();

                        Thread.sleep(10);
                    } else {
                        goDown();
                        Thread.sleep(10);
                    }
                } catch (Exception e) {
                    System.out.println("Error in elevator motor brake");
                }
            }
        }
    }


