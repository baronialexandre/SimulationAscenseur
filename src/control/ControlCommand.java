package control;

import utils.Direction;
import utils.ControllerState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public ControlCommand(int floorNumber) {
        this.callsUp = new ArrayList<>();
        this.callsDown = new ArrayList<>();
        this.state = ControllerState.WAIT;
        this.direction = Direction.NONE;
        this.currentFloor = 0;
        this.emergency = false;
        this.elevatorSimulator = new ElevatorSimulator(floorNumber);
    }

    public void addCallUp(int floorNb){
        callsUp.add(floorNb);
        Collections.sort(callsUp);
    }

    public void addCallDown(int floorNb){
        callsDown.add(floorNb);
        Collections.sort(callsDown,Collections.reverseOrder());
    }

    public void addCall(int aimedFloorNb){
        if (aimedFloorNb > currentFloor && !callsUp.contains(aimedFloorNb))
            addCallUp(aimedFloorNb);
        else if (aimedFloorNb < currentFloor && !callsDown.contains(aimedFloorNb))
            addCallDown(aimedFloorNb);
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

    public void emergency(){
        emergency = true;
        callsUp.clear();
        callsDown.clear();
    }

    public void restart(){
        emergency = false;
    }



}
