package scripts.clayminer.nodes;

import org.tribot.api2007.*;
import scripts.clayminer.framework.Node;
import scripts.clayminer.data.Vars;

public class WalkMine extends Node {
    @Override
    public void execute() {
        System.out.println("The WalkMine Node has been Validated! Executing...");
        WebWalking.walkTo(Vars.CLAY_TILE);
    }

    @Override
    public boolean validate() {
        return (!Inventory.isFull() && Vars.isInMine() && !Vars.CLAY_TILE.isOnScreen());
    }
}
