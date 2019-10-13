package control;

import ui.ElevatorViewPanel;
import utils.Direction;
import utils.ControllerState;
import utils.ElevatorState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static utils.ElevatorState.*;

public class ControlCommand
{
    private static final double time = 1000;
    private List<Integer> callsUp, callsDown;
    private ControllerState state;
    private Direction direction;
    private int currentFloor;
    private int aimedFloor;
    private boolean emergency;
    private ElevatorSimulator elevatorSimulator;

    public ControlCommand(int floorNumber, ElevatorViewPanel elevatorViewPanel) {
        this.callsUp = new ArrayList<>();
        this.callsDown = new ArrayList<>();
        this.state = ControllerState.WAIT;
        this.direction = Direction.NONE;
        this.currentFloor = 0;
        this.emergency = false;

        this.elevatorSimulator = new ElevatorSimulator(floorNumber);
        elevatorSimulator.start();

        ElevatorListener elevatorListener = new ElevatorListener(elevatorSimulator, this, elevatorViewPanel);
        elevatorListener.start();
    }

    public void addCallUp(int floorNb){
        callsUp.add(floorNb);
        Collections.sort(callsUp);
        modifyStateAfterCall();
    }

    public void addCallDown(int floorNb){
        callsDown.add(floorNb);
        //Collections.sort(callsDown,Collections.reverseOrder());
        callsDown.sort(Collections.reverseOrder()); // Conseillé
        modifyStateAfterCall();
    }

    public void addCall(int aimedFloorNb){
        if (aimedFloorNb > currentFloor && !callsUp.contains(aimedFloorNb))
            addCallUp(aimedFloorNb);
        else if (aimedFloorNb < currentFloor && !callsDown.contains(aimedFloorNb))
            addCallDown(aimedFloorNb);
    }

    public void modifyStateAfterCall()
    {
        //aimedFloor ne change jamais
        if(!callsDown.isEmpty()) {
            aimedFloor = callsDown.get(0);
        }
        if(!callsUp.isEmpty()) {
            aimedFloor = callsUp.get(0);
        }

        if(currentFloor - aimedFloor == 0) {
            //aimedFloorReached
            elevatorSimulator.stopUntilOrder();
        }
        if(currentFloor - aimedFloor == 1) {
            elevatorSimulator.setGoingNextDown();
        }
        else if(currentFloor - aimedFloor == -1) {
            elevatorSimulator.setGoingNextUp();
        }
        else if(currentFloor - aimedFloor > 1) {
            elevatorSimulator.setGoingDown();
        }
        else if(currentFloor - aimedFloor < -1) {
            elevatorSimulator.setGoingUp();
        }
    }

    public ControllerState getState() {
        return state;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean getEmergency() {
        return emergency;
    }

    // quand on set le currentFloor il faut renseigner à elevatorSimulator ce qu'il doit faire par son état
    void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getAimedFloor() {
        return aimedFloor;
    }

    public void emergency(){
        emergency = true;
        callsUp.clear();
        callsDown.clear();
        elevatorSimulator.stopUntilOrder();
    }

    public void restart(){
        emergency = false;
    }

}
