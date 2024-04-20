import java.util.*;

public class Answer {
    public static Tile[] simulationTick(Tile[] tiles){
        List<Tile> colonyTiles = new ArrayList<>();
        List<Tile> nonColonyTiles = new ArrayList<>();

        // Separate tiles into colony and non-colony lists
        for (Tile tile : tiles) {
            if (tile.isColony()) {
                colonyTiles.add(tile);
            } else {
                nonColonyTiles.add(tile);
            }
        }

        // Balance non-colony tiles to have either 0 or 1 resource
        for (Tile tile : nonColonyTiles) {
            if (tile.getResources() > 1) {
                tile.setResources(1);
            }
        }

        // Calculate the average resources in colony tiles
        int totalResources = 0;
        for (Tile tile : colonyTiles) {
            totalResources += tile.getResources();
        }
        int averageResources = totalResources / colonyTiles.size();

        // Ensure all colony tiles have more than 2 resources and are within 2 of each other
        for (Tile tile : colonyTiles) {
            if (tile.getResources() < averageResources - 2) {
                tile.setResources(averageResources - 2);
            } else if (tile.getResources() > averageResources + 2) {
                tile.setResources(averageResources + 2);
            }
        }

        // Ensure each colony tile has more than 2 resources
        for (Tile tile : colonyTiles) {
            if (tile.getResources() < 3) {
                tile.setResources(3);
            }
        }

        return tiles;
    }
}
