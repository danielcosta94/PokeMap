package estgf.ipp.pt.pokemongo.application;

/**
 * The VertexPathNode Created for the Problem of Pokemon GO That Contains One
 * Specific Number Of Pokestops
 */
public class VertexPathNode implements Comparable<VertexPathNode> {

    private final Vertex node;
    private Vertex ancestor;
    private int ancestorPokestops;
    private double distanceTravelled;
    private final int pokestops;

    /**
     * Constructor Method For The VertexPathNode That Creates The Structures For
     * A Specific Number Of Pokestops
     *
     * @param node The Current Vertex
     * @param pokestops The Number Exact Of Pokestops
     */
    public VertexPathNode(Vertex node, int pokestops) {
        this.node = node;
        this.ancestor = null;
        this.ancestorPokestops = -1;
        this.distanceTravelled = Double.MAX_VALUE;
        this.pokestops = pokestops;
    }

    /**
     * Get Current Vertex
     *
     * @return The Current Vertex
     */
    public Vertex getNode() {
        return node;
    }

    /**
     * Get Ancestor Vertex
     *
     * @return The Ancestor Vertex
     */
    public Vertex getAncestor() {
        return ancestor;
    }

    /**
     * Set Ancestor Vertex For Current Vertex With A Certain Number Of Pokestops
     *
     * @param ancestor The New Ancestor Vertex
     */
    public void setAncestor(Vertex ancestor) {
        this.ancestor = ancestor;
    }

    /**
     * Get Ancestor Vertex Pokestops
     *
     * @return The Number Of Ancestor Pokestops
     */
    public int getAncestorPokestops() {
        return ancestorPokestops;
    }

    /**
     * Set Ancestor Vertex Pokestops
     *
     * @param ancestorPokestops The New Ancestor Vertex Pokestops Number
     */
    public void setAncestorPokestops(int ancestorPokestops) {
        this.ancestorPokestops = ancestorPokestops;
    }

    /**
     * Get Distance Travelled For A Certain Number Of Pokestops
     *
     * @return The Distance Travelled
     */
    public double getDistanceTravelled() {
        return distanceTravelled;
    }

    /**
     * Set Distance Travelled For A Certain Number Of Pokestops
     *
     * @param distanceTravelled The New Distance Travelled
     */
    public void setDistanceTravelled(double distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
    }

    /**
     * Get The Number Of Pokestops
     *
     * @return The Number Of Pokestops
     */
    public int getPokestops() {
        return pokestops;
    }

    /**
     * Compare Two VertexPathNodes And Return A Number Of Which One Is Lower
     *
     * @param other The Other VertexPathNode
     * @return Return An Integer Of The Result -1: LOWER, 0: EQUALS, 1:GREATER
     */
    @Override
    public int compareTo(VertexPathNode other) {
        final int LOWER = -1;
        final int EQUALS = 0;
        final int GREATER = 1;

        if (this.pokestops == other.getPokestops()) {
            if (this.distanceTravelled > other.getDistanceTravelled()) {
                return GREATER;
            } else if (this.distanceTravelled < other.getDistanceTravelled()) {
                return LOWER;
            } else {
                return EQUALS;
            }
        } else if (this.pokestops > other.getPokestops()) {
            return LOWER;
        } else {
            return GREATER;
        }
    }
}
