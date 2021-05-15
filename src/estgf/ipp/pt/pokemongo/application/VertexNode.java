package estgf.ipp.pt.pokemongo.application;

/**
 * The VertexNode Created for the Problem of Pokemon GO That Contains All
 * Possiblities For It With A Different Number Of Pokestops
 */
public class VertexNode {

    private VertexPathNode[] vertexPathNodes;

    /**
     * Constructor Method For The VertexNode That Creates The Structures For The
     * Different Number Of Pokestops
     *
     * @param node The Vertex To Create
     * @param pokestops Number Maximum Of Pokestops (Possibilities)
     * @throws NegativeArraySizeException Throws This Exception If The Number Of
     * Pokestops If Negative
     */
    public VertexNode(Vertex node, int pokestops) throws NegativeArraySizeException {
        if (pokestops < 0) {
            throw new NegativeArraySizeException("You Have a Negative Number of Pokestops!!!");
        }
        this.vertexPathNodes = new VertexPathNode[pokestops + 1];
        for (int i = 0; i <= pokestops; i++) {
            this.vertexPathNodes[i] = new VertexPathNode(node, i);
        }
    }

    /**
     * Get All VertexPathsNodes (Possibilities Of With All The Numbers Of
     * Pokestops)
     *
     * @return VertexPathsNodes
     */
    public VertexPathNode[] getVertexPathNodes() {
        return vertexPathNodes;
    }
}
