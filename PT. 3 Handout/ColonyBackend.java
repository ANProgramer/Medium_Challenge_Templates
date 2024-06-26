import java.util.ArrayList;

// * PARTICPIANTS DO NOT EDIT THIS FILE - EDITING WILL RESULT IN DISQUALIFICATION OF ANSWER
public class ColonyBackend {
    public static void main(String[] args) throws Exception {
        int size = (int) (Math.random() * 11) + 5;

        ArrayList<Integer> hashList = new ArrayList<Integer>();

        Tile[] t = new Tile[size * size];
        int j = 0;
        int colonyCount = 0;

        int totalRequired = 0;
        int totalResources = 0;

        for (int x = 0; x < size; x++){
            for (int y = 0; y < size; y++){
                boolean isColony = false;

                if (Math.random() > 0.9) {
                    isColony = true;
                    colonyCount++;    
                }

                int req = isColony ? (int) (Math.random() * 2) + 3 : 0;
                totalRequired += req;

                int nRes = Math.random() > 0.5 ? (int) (Math.random() * 8) : 0;
                totalResources += nRes;

                t[j] = new Tile(nRes, isColony, x, y, req);
                hashList.add(t[j].hashCode());
                j++;
            }
        }

        if (colonyCount == 0) {
            int index = (int) (Math.random() * t.length);
            hashList.remove( (Object)t[index].hashCode());

            int req = (int) (Math.random() * 2) + 3;
            totalRequired += req;

            t[index] = new Tile(Math.random() > 0.5 ? (int) (Math.random() * 8) : 0, true, (int) (Math.random() * (int) Math.sqrt(t.length)), (int) (Math.random() * (int) Math.sqrt(t.length)), req);
            hashList.add(t[index].hashCode());
        }

        if (totalResources < totalRequired){
            int diff = Math.abs(totalResources - totalRequired);
            int g = 0;

            while (g < t.length && t[g].isColony()) {
                g++;
            }

            hashList.remove(t[g].hashCode());
            t[g] = new Tile(t[g].getResources() + diff + 23, false, t[g].getYPosition(), t[g].getXPosition(), 0);
            hashList.add(t[g].hashCode());
        }

        for (int s = 0; s < 1000; s++ ){
            for (int i = 0; i < 80; i++){
                t = Answer.simulationTick(t);
                j = 0;

                //  *Check for tile integrity
                for (Tile tile : t) {
                    if (!hashList.contains(tile.hashCode())){
                        throw new Exception("Adding new tiles in the solution is NOT allowed, make sure you are modifying the existing tiles");
                    }
                }
    
                for (Tile c : t){
                    if (c.isColony() && c.getResources() < c.getResourceRequirement()){
                        System.out.println(c.getResources() + " : " + c.getResourceRequirement());
                        throw new Exception("Completed " + i + " ticks and " + s + " successful simulations before a Colony went below required resources! Check your solution and run again");
                    }
                }
    
                for (int x = 0; x < size; x++){
                    for (int y = 0; y < size; y++){
                        boolean isColony = t[j].isColony();
        
                        hashList.remove( (Object)t[j].hashCode());
                        t[j] = new Tile(Math.random() > 0.5 && !isColony ? t[j].getResources() + (int) (Math.random() * 4) : isColony ? t[j].getResources() - (int) (Math.random() * t[j].getResources() - 1) : t[j].getResources(), isColony, t[j].getXPosition(), t[j].getYPosition(), isColony ? t[j].getResourceRequirement() : 0);
                        hashList.add(t[j].hashCode());
    
                        j++;
                    }
                }
            }
        }
        

       System.out.println("Simulation Succeeded!");


    }
}