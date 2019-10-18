package control.strategy;

import control.ControlCommand;
import utils.ControllerState;
import utils.Direction;
import utils.ElevatorState;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Thread.sleep;

public class RandomControlStrategy implements ControlStrategy {

    public void updateAimedFloor(ControlCommand controlCommand) {
        if (controlCommand.isEmergency() || controlCommand.getState().equals(ControllerState.MOVEMENT) || controlCommand.getCallsUp().isEmpty() && controlCommand.getCallsDown().isEmpty()){
            return;
        }
        ArrayList<Integer> calls = new ArrayList<>();
        if (!controlCommand.getCallsUp().isEmpty())
            calls.addAll(controlCommand.getCallsUp());
        if (!controlCommand.getCallsDown().isEmpty())
            calls.addAll(controlCommand.getCallsDown());
        Collections.shuffle(calls);
        controlCommand.setAimedFloor(calls.get(0));
        updateElevatorState(controlCommand);
    }

    public void updateElevatorState(ControlCommand controlCommand) {
        if (controlCommand.isEmergency())
            return;

        if(controlCommand.getCurrentFloor() == controlCommand.getAimedFloor()) {
            controlCommand.getElevatorSimulator().setState(ElevatorState.STOPPED);
            controlCommand.setState(ControllerState.WAIT);
            try {
                sleep(controlCommand.getSleepTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            controlCommand.getCallsUp().remove(Integer.valueOf(controlCommand.getCurrentFloor()));
            controlCommand.getCallsDown().remove(Integer.valueOf(controlCommand.getCurrentFloor()));
            if(controlCommand.getCurrentFloor() == controlCommand.getFloorNumber()-1)
                controlCommand.setDirection(Direction.DOWN);
            else if(controlCommand.getCurrentFloor() == 0)
                controlCommand.setDirection(Direction.UP);
            updateAimedFloor(controlCommand);
        }
        if (controlCommand.getCurrentFloor() - controlCommand.getAimedFloor() == 1) {
            controlCommand.getElevatorSimulator().setState(ElevatorState.GOINGNEXTDOWN);
            controlCommand.setDirection(Direction.DOWN);
            controlCommand.setState(ControllerState.MOVEMENT);
        } else if (controlCommand.getCurrentFloor() - controlCommand.getAimedFloor() == -1) {
            controlCommand.getElevatorSimulator().setState(ElevatorState.GOINGNEXTUP);
            controlCommand.setDirection(Direction.UP);
            controlCommand.setState(ControllerState.MOVEMENT);
        } else if (controlCommand.getCurrentFloor() - controlCommand.getAimedFloor() > 1) {
            controlCommand.getElevatorSimulator().setState(ElevatorState.GOINGDOWN);
            controlCommand.setDirection(Direction.DOWN);
            controlCommand.setState(ControllerState.MOVEMENT);
        } else if (controlCommand.getCurrentFloor() - controlCommand.getAimedFloor() < -1) {
            controlCommand.getElevatorSimulator().setState(ElevatorState.GOINGUP);
            controlCommand.setDirection(Direction.UP);
            controlCommand.setState(ControllerState.MOVEMENT);
        }
    }
}
