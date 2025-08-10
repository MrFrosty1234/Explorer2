package SortingPucks;

import Explorer.Explorer;
import SortingPucks.Sorting.SortingAndKeep;

public class SortingAndUnloading {

    Explorer explorer;

    SortingAndKeep sortingAndKeep;


    public SortingAndUnloading(Explorer explorer){
        this.explorer = explorer;
        sortingAndKeep = new SortingAndKeep(explorer);
    }
    double time = System.currentTimeMillis()/1000.0;
    public boolean sorting() {
        double t = System.currentTimeMillis() / 1000.0;
        int color = explorer.colorDetective.puckDetect();
        int field = explorer.colorDetective.fieldDetect();
        if (explorer.colorDetective.statePuck) {
            explorer.colorDetective.statePuck = sortingAndKeep.separatorPosition(0);
            return false;
        }
        if (t - time > 1 && field == 0) {
            t = System.currentTimeMillis() / 1000.0;
            if (color == explorer.colorDetective.ourColor) {
                explorer.colorDetective.statePuck = sortingAndKeep.separatorPosition(1);
                time = t;
                return true;
            }
            if (color == explorer.colorDetective.notOurColor) {
                explorer.colorDetective.statePuck = sortingAndKeep.separatorPosition(-1);
                time = t;
                return true;
            }
        }
        explorer.colorDetective.statePuck = sortingAndKeep.separatorPosition(0);
        return false;

    }

    public boolean getOut() {
        int color = explorer.colorDetective.fieldDetect();
        if (color == 0 || color == explorer.colorDetective.notOurColor) {
            sortingAndKeep.closeServo();
            sortingAndKeep.separatorPosition(0);
            return false;
        }
        if (color == explorer.colorDetective.ourColor) {
            sortingAndKeep.separatorPosition(0);
            return true;
        }
        return false;
    }


}
