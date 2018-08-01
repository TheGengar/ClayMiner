package scripts.clayminer.nodes;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSObject;
import scripts.clayminer.data.Vars;
import scripts.clayminer.framework.Node;

public class EnterMine extends Node {
    @Override
    public void execute() {
        System.out.println("The EnterMine Node has been Validated! Executing...");

        RSObject[] root = Objects.find(4, Vars.ROOTS_ID);
        if (root.length <= 0){
            System.out.println("CANNOT SEE THE ROOTS, WALKING TO ROOTS");
            WebWalking.walkTo(Vars.OUTSIDE_MINE_ROOT_TILE);
        } else {
            System.out.println("CAN SEE THE ROOTS, ENTERING THE MINE...");
        }
        Vars.passRoots();
    }

    @Override
    public boolean validate() {
        return (Floor.isInBottomFloor() && !Vars.isInMine() && !Inventory.isFull());
    }
}
