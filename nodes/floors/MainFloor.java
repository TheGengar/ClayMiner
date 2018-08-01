package scripts.clayminer.nodes.floors;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import scripts.clayminer.nodes.Floor;
import scripts.clayminer.data.Vars;

public class MainFloor extends Floor {
    @Override
    public void execute() {
        System.out.println("The MainFloor Node has been Validated! Executing...");
        if (Inventory.isFull()) {
            climbLadder(Objects.find(6, Vars.MID_LADDER_ID), 2);
        } else {
            climbLadder(Objects.find(6, Vars.TRAP_DOOR_ID), 2);
        }
    }

    @Override
    public boolean validate() {
        return isInMiddleFloor();
    }
}
