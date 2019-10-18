package control.strategy;

import control.ControlCommand;
import utils.ControllerState;
import utils.Direction;
import utils.ElevatorState;

import java.util.Collections;

import static java.lang.Thread.sleep;

public class AdvancedControlStrategy implements ControlStrategy {

    public void updateAimedFloor(ControlCommand controlCommand) {
        if (controlCommand.isEmergency() || controlCommand.getCallsUp().isEmpty() && controlCommand.getCallsDown().isEmpty()) {
            return;
        }

        if (controlCommand.getDirection().equals(Direction.UP)) {
            if (!controlCommand.getCallsUp().isEmpty()) {
                for (Integer i : controlCommand.getCallsUp()) {
                    if (controlCommand.getCurrentFloor() < i) {
                        controlCommand.setAimedFloor(i);
                        updateElevatorState(controlCommand);
                        return;
                    }
                }
            }
            if (!controlCommand.getCallsDown().isEmpty()) {
                Collections.reverse(controlCommand.getCallsDown());
                for (Integer i : controlCommand.getCallsDown()) {
                    if (controlCommand.getCurrentFloor() < i) {
                        controlCommand.setAimedFloor(i);
                        updateElevatorState(controlCommand);
                        Collections.reverse(controlCommand.getCallsDown());
                        return;
                    }
                }
                Collections.reverse(controlCommand.getCallsDown());
            }
            if (controlCommand.getState() == ControllerState.WAIT) {
                controlCommand.setDirection(Direction.DOWN);
                updateAimedFloor(controlCommand);
            }
            return;
        }

        if (controlCommand.getDirection().equals(Direction.DOWN)) {
            if (!controlCommand.getCallsDown().isEmpty()) {
                for (Integer i : controlCommand.getCallsDown()) {
                    if (controlCommand.getCurrentFloor() > i) {
                        controlCommand.setAimedFloor(i);
                        updateElevatorState(controlCommand);
                        return;
                    }
                }
            }
            if (!controlCommand.getCallsUp().isEmpty()) {
                Collections.reverse(controlCommand.getCallsUp());
                for (Integer i : controlCommand.getCallsUp()) {
                    if (controlCommand.getCurrentFloor() > i) {
                        controlCommand.setAimedFloor(i);
                        updateElevatorState(controlCommand);
                        Collections.reverse(controlCommand.getCallsUp());
                        return;
                    }
                }
                Collections.reverse(controlCommand.getCallsUp());
            }
            if (controlCommand.getState() == ControllerState.WAIT) {
                controlCommand.setDirection(Direction.UP);
                updateAimedFloor(controlCommand);
            }
        }
    }

    public void updateElevatorState(ControlCommand controlCommand) {

        if (controlCommand.isEmergency())
            return;

        if (controlCommand.getCurrentFloor() == controlCommand.getAimedFloor()) {
            controlCommand.getElevatorSimulator().setState(ElevatorState.STOPPED);
            controlCommand.setState(ControllerState.WAIT);
            try {
                sleep(controlCommand.getSleepTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            controlCommand.getCallsUp().remove(Integer.valueOf(controlCommand.getCurrentFloor()));
            controlCommand.getCallsDown().remove(Integer.valueOf(controlCommand.getCurrentFloor()));
            if (controlCommand.getCurrentFloor() == controlCommand.getFloorNumber() - 1)
                controlCommand.setDirection(Direction.DOWN);
            else if (controlCommand.getCurrentFloor() == 0)
                controlCommand.setDirection(Direction.UP);
            updateAimedFloor(controlCommand);
        }
        if (controlCommand.getCurrentFloor() - controlCommand.getAimedFloor() == 1) {
            controlCommand.getElevatorSimulator().setState(ElevatorState.GOINGNEXTDOWN);
            controlCommand.setDirection(Direction.DOWN);
            controlCommand.setState(ControllerState.MOVEMENT);
        } else if (controlCommand.getCurrentFloor() - controlCommand.getAimedFloor() == -1) {
            controlCommand.getElevatorSimulator().setState(ElevatorState.GOINGNEXTUP);
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
