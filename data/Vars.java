package scripts.clayminer.data;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;


public class Vars {

    public static final int PASSING_ROOT_ANIMATION = 81;
    public static final int LADDER_CLIMB_ANIMATION = 828;
    public static final int MINING_ANIMATION = 624;

    public static final RSTile CLAY_TILE = new RSTile(2451, 9907,0);
    public static final RSTile INSIDE_MINE_ROOT_TILE = new RSTile(2467,9906,0);
    public static final RSTile OUTSIDE_MINE_ROOT_TILE = new RSTile(2467,9902,0);
    public static final RSTile MAIN_LEVEL_TILE = new RSTile(2465,3495,0);
    public static final RSTile TOP_LADDER_TILE = new RSTile(2465,3495,1);
    public static final RSTile BANK_TILE = new RSTile(2449, 3482, 1);
    public static final RSTile BOTTOM_LADDER_TILE = new RSTile(2464,9897, 0);

    public static final int[] PICKAXE_IDS = {1265, 1267, 1269, 12297, 1273, 1271, 1275};
    // Bronze -> Rune, including black pick

    public static final int[] CLAY_ROCKS_IDS = {7487, 7454};
    public static final int ROOTS_ID = 2451;
    public static final int BOT_LADDER_ID = 17385;
    public static final int MID_LADDER_ID = 16683;
    public static final int TOP_LADDER_ID = 16684;
    public static final int TRAP_DOOR_ID = 2446;

    public static final RSArea MINE_AREA_1 = new RSArea(new RSTile(2494, 9908, 0), new RSTile(2434, 9918, 0));  // COVERS MOST OF AREA
    public static final RSArea MINE_AREA_2 = new RSArea(new RSTile(2492, 9907, 0), new RSTile(2489, 9907, 0));  // SMALL LINE VERY RIGHT BOTTOM POCKET
    public static final RSArea MINE_AREA_3 = new RSArea(new RSTile(2468, 9905, 0), new RSTile(2434, 9907, 0));  // WEST OF ENTRANCE TO BOTTOM LEFT, RIGHT ABOVE SMALL POCK
    public static final RSArea MINE_AREA_4 = new RSArea(new RSTile(2476, 9907, 0), new RSTile(2469, 9904, 0));  // SMALL POCKET EAST OF ENTRANCE
    public static final RSArea MINE_AREA_5 = new RSArea(new RSTile(2461, 9904, 0), new RSTile(2434, 9899, 0));  // SAME AS 3, BUT A LITTLE LOWER Y AND AVOIDS OUTSIDE MINE
    public static final RSArea MINE_AREA_6 = new RSArea(new RSTile(2440, 9898, 0), new RSTile(2435, 9896, 0));  // SMALL POCKET BOTTOM LEFT


    public static boolean isInMine(){
        return (Vars.MINE_AREA_1.contains(Player.getPosition()) ||
                Vars.MINE_AREA_2.contains(Player.getPosition()) ||
                Vars.MINE_AREA_3.contains(Player.getPosition()) ||
                Vars.MINE_AREA_4.contains(Player.getPosition()) ||
                Vars.MINE_AREA_5.contains(Player.getPosition()) ||
                Vars.MINE_AREA_6.contains(Player.getPosition()));
    }

    public static boolean passRoots(){
        // GOT NULL POINTER EXCEP. Maybe wait till character stops moving before getting objects again?
        RSObject[] roots = Objects.find(4, Vars.ROOTS_ID);

        if (roots.length > 0){
            if (!roots[0].isOnScreen()){
                Camera.turnToTile(OUTSIDE_MINE_ROOT_TILE);
            }

            roots[0].getModel().click();

            if (Timing.waitCondition(new Condition() {          // SUCCESFULLY CLICK THE ROOTS
                    @Override
                    public boolean active() {
                        General.sleep(100); // Add this in to reduce CPU usage
                        return Player.getAnimation() == PASSING_ROOT_ANIMATION;
                    }
                }, General.random(6000, 6500))){

                // NOW WAIT TILL YOU ACTUALLY LEAVE/ENTER MINE
                // Wait till you fully pass the roots.
                System.out.println("succesfully clicked the root now waiting to pass.");

                boolean isInMine = isInMine();

                General.sleep(1200);    // need this shitty sleep for now
                return Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        General.sleep(100);
                        return (isInMine != isInMine() && !Player.isMoving());
                    }
                }, General.random(3500,4000));
            }
        }
        return false;
    }
}
