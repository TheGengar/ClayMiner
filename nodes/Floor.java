package scripts.clayminer.nodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSObject;
import scripts.clayminer.framework.Node;
import scripts.clayminer.data.Vars;

public abstract class Floor extends Node {

    public boolean climbLadder(RSObject[] ladder, int floorIndex){
        if (ladder.length > 0){
            if (!ladder[0].isOnScreen()){
                Camera.turnToTile(ladder[0].getPosition());
            }
            ladder[0].click();

            // Ensure we actually climbed ladder, don't spam click ladder.
            return waitForFloorChange(floorIndex);
        }
        return false;
    }

    private boolean waitForFloorChange(int floorIndex){
        return (Timing.waitCondition(new Condition() {
            @Override
            public boolean active() {
                General.sleep(100); // Add this in to reduce CPU usage
                return !getFloor(floorIndex);
            }}, General.random(2200, 2500)));
    }

    private boolean getFloor(int floorIndex){
        if (floorIndex == 1){
            return isInBottomFloor();
        } else if (floorIndex == 2){
            return isInMiddleFloor();
        } else if (floorIndex == 3){
            return isInTopFloor();
        } else {
            return false;
        }
    }

    public boolean climbDoubleLadder(RSObject[] ladder, int floorIndex){
        if (ladder.length > 0) {
            if (!ladder[0].isOnScreen()) {
                Camera.turnToTile(ladder[0].getPosition());
            }
        } else {
            System.out.println("climbDoubleLadder: Did not find a ladder, returning False");
            return false;
        }

        // Wait till mouse is over the ladder
        if (!Timing.waitCondition(new Condition() {
            @Override
            public boolean active() {
                General.sleep(100);
                return ladder[0].getModel().hover();
            }
        }, General.random(1200, 1500))){
            // didn't hover ladder, leave
            return false;
        }
        Mouse.click(3);                                 // Right clicks.
        if (ChooseOption.isOpen()){
            ChooseOption.select("Climb-down Ladder");
        }
        return waitForFloorChange(floorIndex);
    }

    public static boolean isInBottomFloor(){
        return (Math.abs(Player.getPosition().getX() - 2467) < 60 &&
                Math.abs(Player.getPosition().getY() - 9893) < 50);
    }

    public boolean isInMiddleFloor() {
        return (Math.abs(Vars.MAIN_LEVEL_TILE.getY() - Player.getPosition().getY()) < 20 &&
                !(Player.getPosition().getPlane() == 1));
    }

    public boolean isInTopFloor() {
        return (Player.getPosition().getPlane() == 1);
    }
}
