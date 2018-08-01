package scripts.clayminer.nodes.floors;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import scripts.clayminer.nodes.Floor;
import scripts.clayminer.data.Vars;


public class LeaveBottomFloor extends Floor {
    @Override
    public void execute() {
        System.out.println("The LeaveBottomFloor Node has been Validated! Executing...");
        if (!(Objects.find(5, Vars.BOT_LADDER_ID).length > 0)) {
            WebWalking.walkTo(Vars.BOTTOM_LADDER_TILE);
        }

        Timing.waitCondition(new Condition() {
            @Override
            public boolean active() {
                General.sleep(100);
                return !Player.isMoving();
            }
        }, General.random(3500,4000));
        climbLadder(Objects.find(5, Vars.BOT_LADDER_ID), 1);
    }

    @Override
    public boolean validate() {
        return (isInBottomFloor() && !Vars.isInMine() && Inventory.isFull());
    }
}
