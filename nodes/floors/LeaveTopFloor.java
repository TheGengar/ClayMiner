package scripts.clayminer.nodes.floors;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSObject;
import scripts.clayminer.nodes.Floor;
import scripts.clayminer.data.Vars;

// GOT STUCK HERE
public class LeaveTopFloor extends Floor {
    @Override
    public void execute() {
        System.out.println("The LeaveTopFloor Node has been Validated! Executing...");
        RSObject[] ladders = Objects.find(5, Vars.TOP_LADDER_ID);

        if (ladders.length > 0) {
            climbDoubleLadder(ladders, 3);
        } else if (ladders.length <= 0 && WebWalking.walkTo(Vars.TOP_LADDER_TILE)) {

            if (Timing.waitCondition(new Condition() {
                @Override
                public boolean active() {
                    General.sleep(100);
                    return Objects.find(5, Vars.TOP_LADDER_ID).length > 0;
                }
            }, General.random(4500,5000))) {
                ladders = Objects.find(5, Vars.TOP_LADDER_ID);

                if (!ladders[0].isOnScreen()) {
                    Camera.turnToTile(ladders[0].getPosition());
                }
                climbDoubleLadder(ladders, 3);
            }
        }
    }

    @Override
    public boolean validate() {
        return (Player.getPosition().getPlane() == 1 && !Inventory.isFull());
    }
}
