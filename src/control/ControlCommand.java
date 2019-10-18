package control;

import control.strategy.ControlStrategy;
import ui.ElevatorPanel;
import utils.Direction;
import utils.ControllerState;
import utils.ElevatorState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ControlCommand
{

    private static final long sleepTime = 1000;
    private final int floorNumber;
    private ElevatorSimulator elevatorSimulator;
    private ControlStrategy controlStrategy;
    private List<Integer> callsUp, callsDown;
    private ControllerState state;
    private Direction direction;
    private int currentFloor;
    private int aimedFloor;
    private boolean emergency;

    public ControlCommand(int floorNumber, ElevatorPanel elevatorPanel, ControlStrategy controlStrategy) {
        this.callsUp = new ArrayList<>();
        this.callsDown = new ArrayList<>();
        this.state = ControllerState.WAIT;
        this.direction = Direction.UP;
        this.currentFloor = 0;
        this.emergency = false;
        this.floorNumber = floorNumber;
        this.controlStrategy = controlStrategy;
        this.elevatorSimulator = new ElevatorSimulator();
        elevatorSimulator.start();

        ElevatorListener elevatorListener = new ElevatorListener(elevatorSimulator, this, elevatorPanel);
        elevatorListener.start();
    }

    public void addCallUp(int floorNb){
        if (emergency || floorNb == currentFloor && state == ControllerState.WAIT)
            return;
        if(!callsUp.contains(floorNb)){
            callsUp.add(floorNb);
            Collections.sort(callsUp);
            controlStrategy.updateAimedFloor(this);
        }
    }

    public void addCallDown(int floorNb){
        if (emergency || floorNb == currentFloor && state == ControllerState.WAIT)
            return;
        if(!callsDown.contains(floorNb)){
            callsDown.add(floorNb);
            callsDown.sort(Collections.reverseOrder());
            controlStrategy.updateAimedFloor(this);
        }
    }

    public void addCall(int aimedFloorNb){
        if (emergency || aimedFloorNb == currentFloor && state == ControllerState.WAIT)
            return;
        if (aimedFloorNb > currentFloor && !callsUp.contains(aimedFloorNb))
            addCallUp(aimedFloorNb);
        else if (aimedFloorNb < currentFloor && !callsDown.contains(aimedFloorNb))
            addCallDown(aimedFloorNb);
        else if (aimedFloorNb == currentFloor){
            if(direction.equals(Direction.UP))
                addCallDown(aimedFloorNb);
            else
                addCallUp(aimedFloorNb);
        }
    }

    void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
        controlStrategy.updateElevatorState(this);
    }


    public void emergency(){
        emergency = true;
        callsUp.clear();
        callsDown.clear();
        elevatorSimulator.setState(ElevatorState.STOPPED);
    }

    public void restart(){
        emergency = false;
        if(state.equals(ControllerState.MOVEMENT)) {
            if (direction.equals(Direction.UP))
                aimedFloor = currentFloor + 1;
            else
                aimedFloor = currentFloor - 1;
        }
        controlStrategy.updateElevatorState(this);
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public List<Integer> getCallsUp() {
        return callsUp;
    }

    public List<Integer> getCallsDown() { return callsDown; }

    public ControllerState getState() {
        return state;
    }

    public void setState(ControllerState state) {
        this.state = state;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getAimedFloor() {
        return aimedFloor;
    }

    public void setAimedFloor(int aimedFloor) {
        this.aimedFloor = aimedFloor;
    }

    public boolean isEmergency() {
        return emergency;
    }

    public ElevatorSimulator getElevatorSimulator() {
        return elevatorSimulator;
    }

    public static long getSleepTime() {
        return sleepTime;
    }

    public String toString() {
        return "ControlCommand{" +
                "floorNumber=" + floorNumber +
                ", callsUp=" + callsUp +
                ", callsDown=" + callsDown +
                ", state=" + state +
                ", direction=" + direction +
                ", currentFloor=" + currentFloor +
                ", aimedFloor=" + aimedFloor +
                ", emergency=" + emergency +
                ", elevatorSimulator=" + elevatorSimulator +
                '}';
    }
}
