package estgf.ipp.pt.pokemongo.management;

import estgf.ipp.pt.pokemongo.application.Network;
import estgf.ipp.pt.pokemongo.application.Vertex;
import estgf.ipp.pt.pokemongo.application.Weight;
import estgf.ipp.pt.pokemongo.exceptions.VertexNotExists;
import java.io.File;
import java.io.IOException;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class InputData {

    public static Network loadData(String relativePath) throws IOException, BiffException {
        Workbook workbook = Workbook.getWorkbook(new File(relativePath));
        Network network = new Network();
        Sheet sheetVertexes;
        try {
            sheetVertexes = workbook.getSheet(0);
        } catch (IndexOutOfBoundsException ioobe) {
            throw new IOException("There is no Vertexes Sheet in the Document");
        }
        int numVertexes = sheetVertexes.getRows();

        String id;
        boolean pokestop;

        for (int i = 1; i < numVertexes; i++) {
            if (!sheetVertexes.getCell(0, i).getContents().isEmpty()) {
                id = sheetVertexes.getCell(0, i).getContents();
            } else {
                throw new IOException("Vertex Name is not Defined in Cell: " + 0 + "." + i);
            }

            if (sheetVertexes.getCell(1, i).getContents().equalsIgnoreCase("true")) {
                pokestop = true;
            } else if (sheetVertexes.getCell(1, i).getContents().equalsIgnoreCase("false")) {
                pokestop = false;
            } else {
                throw new IOException("Vertex Pokestop is not definied in cell: " + 1 + "." + i);
            }
            network.addVertex(new Vertex(id, pokestop));
        }

        Sheet sheetEdges;
        try {
            sheetEdges = workbook.getSheet(1);
        } catch (IndexOutOfBoundsException ioobe) {
            throw new IOException("There is no Edges Sheet in the Document");
        }
        int numEdges = sheetEdges.getRows();

        for (int i = 1; i < numEdges; i++) {

            String startVertexID = sheetEdges.getCell(0, i).getContents();
            Vertex startVertex;
            if (!startVertexID.isEmpty()) {
                try {
                    startVertex = network.getVertexByID(startVertexID);
                } catch (VertexNotExists vne) {
                    throw new IOException(vne.getLocalizedMessage());
                }
            } else {
                throw new IOException("No Start Vertex Path in: " + 0 + "." + i);
            }

            String targetVertexID = sheetEdges.getCell(1, i).getContents();
            Vertex targetVertex;

            if (!targetVertexID.isEmpty()) {
                try {
                    targetVertex = network.getVertexByID(targetVertexID);
                } catch (VertexNotExists vne) {
                    throw new IOException(vne.getLocalizedMessage());
                }
            } else {
                throw new IOException("No Target Vertex Path in: " + 1 + "." + i);
            }

            String distance = sheetEdges.getCell(2, i).getContents();
            double distanceDouble;
            try {
                distanceDouble = Double.parseDouble(distance);
            } catch (NumberFormatException nfe) {
                throw new IOException("No Distance Between: " + startVertexID + " and " + targetVertexID + " in cell" + 2 + "." + i);
            }

            boolean privateTransport;
            if (sheetEdges.getCell(3, i).getContents().equalsIgnoreCase("true")) {
                privateTransport = true;
            } else if (sheetEdges.getCell(3, i).getContents().equalsIgnoreCase("false")) {
                privateTransport = false;
            } else {
                throw new IOException("Private Transport is not Defined Between: " + startVertexID + " and " + targetVertexID + " in cell" + 3 + "." + i);
            }

            boolean publicTransport;
            if (sheetEdges.getCell(4, i).getContents().equalsIgnoreCase("true")) {
                publicTransport = true;
            } else if (sheetEdges.getCell(4, i).getContents().equalsIgnoreCase("false")) {
                publicTransport = false;
            } else {
                throw new IOException("Public Transport is not Defined Between: " + startVertexID + " and " + targetVertexID + " in cell" + 4 + "." + i);
            }
            network.addEdgeUnidirectional(startVertex, targetVertex, new Weight(distanceDouble, privateTransport, publicTransport));
        }
        return network;
    }
}
