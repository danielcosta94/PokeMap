package estgf.ipp.pt.pokemongo.application;

import estgf.ipp.pt.interfaces.HeapADT;
import estgf.ipp.pt.interfaces.StackADT;
import estgf.ipp.pt.interfaces.UnorderedListADT;
import estgf.ipp.pt.pokemongo.exceptions.EmptyNetworkException;
import estgf.ipp.pt.pokemongo.exceptions.InvalidTransportOption;
import estgf.ipp.pt.pokemongo.exceptions.NoSuchPathException;
import estgf.ipp.pt.pokemongo.exceptions.PokemonStopsNumberNotExists;
import estgf.ipp.pt.pokemongo.exceptions.VertexNotExists;
import estgf.ipp.pt.structures.graphs.Graph;
import estgf.ipp.pt.structures.heaps.LinkedHeap;
import estgf.ipp.pt.structures.lists.ArrayUnorderedList;
import estgf.ipp.pt.structures.stacks.LinkedStack;
import java.util.Iterator;

/**
 * The Network Created for the Problem of Pokemon GO
 */
public class Network extends Graph<Vertex> {

    public static final int PRIVATE_TRANSPORT = 1;
    public static final int PUBLIC_TRANSPORT = 2;
    public static final int WALK_TRANSPORT = 3;
    private final Weight[][] wAdjMatrix;
    private int pokestops;

    /**
     * Constructor Method For The Network
     */
    public Network() {
        super();
        this.numVertices = 0;
        this.pokestops = 0;
        this.wAdjMatrix = new Weight[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = new Vertex[DEFAULT_CAPACITY];
    }

    /**
     * Create Unidirectional Edge Between Two Vertices With Weight
     *
     * @param index1 Position Vertex 1
     * @param index2 Position Vertex 2
     * @param weight Weight Of The Edge
     */
    private void addEdgeUnidirectional(int index1, int index2, Weight weight) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            wAdjMatrix[index1][index2] = weight;
        }
    }

    /**
     * Create Unidirectional Edge Between Two Vertices With Weight
     *
     * @param vertex1 Vertex 1
     * @param vertex2 Vertex 2
     * @param weight Weight Of The Edge
     */
    public void addEdgeUnidirectional(Vertex vertex1, Vertex vertex2, Weight weight) throws ArrayIndexOutOfBoundsException {
        super.addEdgeUnidirectional(vertex1, vertex2);
        addEdgeUnidirectional(getIndex(vertex1), getIndex(vertex2), weight);
    }

    /**
     * Create Bidirectional Edge Between Two Vertices With Weight
     *
     * @param index1 Position Vertex 1
     * @param index2 Position Vertex 2
     * @param weight Weight Of The Edge
     */
    private void addEdgeBidirectional(int index1, int index2, Weight weight) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            wAdjMatrix[index1][index2] = weight;
            wAdjMatrix[index2][index1] = weight;
        }
    }

    /**
     * Create Bidirectional Edge Between Two Vertices With Weight
     *
     * @param vertex1 Vertex 1
     * @param vertex2 Vertex 2
     * @param weight Weight Of The Edge
     */
    public void addEdgeBidirectional(Vertex vertex1, Vertex vertex2, Weight weight) throws ArrayIndexOutOfBoundsException {
        super.addEdgeBidirectional(vertex1, vertex2);
        addEdgeBidirectional(getIndex(vertex1), getIndex(vertex2), weight);
    }

    /**
     * Add a new vertex to the network
     *
     * @param vertex, vertex to be added
     */
    @Override
    public void addVertex(Vertex vertex) {
        if (numVertices == vertices.length) {
            expandCapacity();
        }

        vertices[numVertices] = vertex;
        for (int i = 0; i <= numVertices; i++) {
            wAdjMatrix[numVertices][i] = new Weight();
            wAdjMatrix[i][numVertices] = new Weight();
        }
        if (vertex.isPokestop()) {
            this.pokestops++;
        }
        numVertices++;
    }

    /**
     * Removes a vertex from the network
     *
     * @param index, index of the vertex to be removed
     */
    @Override
    public void removeVertex(int index) {
        if (indexIsValid(index)) {
            if (this.vertices[index].isPokestop()) {
                this.pokestops--;
            }
            numVertices--;

            for (int i = index; i < numVertices; i++) {
                vertices[i] = vertices[i + 1];
            }

            for (int i = index; i < numVertices; i++) {
                for (int j = 0; j <= numVertices; j++) {
                    wAdjMatrix[i][j] = wAdjMatrix[i + 1][j];
                }
            }

            for (int i = index; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    wAdjMatrix[j][i] = wAdjMatrix[j][i + 1];
                }
            }
        }
    }

    /**
     * Removes a vertex from the network
     *
     * @param vertex, the vertex to be removed
     */
    @Override
    public void removeVertex(Vertex vertex) {
        int i = 0;

        while (i < this.numVertices && !this.vertices[i].equals(vertex)) {
            i++;
        }
        if (i < this.numVertices) {
            removeVertex(i);
        }
    }

    /**
     * Get Array of Vertices of the Graph
     *
     * @return Array Of Vertices
     */
    public Vertex[] getVertices() {
        return vertices;
    }

    /**
     * Get Matrix of Adjacencies of the Graph
     *
     * @return Array Of Adjacencies
     */
    public boolean[][] getAdjMatrix() {
        return adjMatrix;
    }

    /**
     * Get Matrix of Weight Adjacencies of the Graph
     *
     * @return Array Of Weight Adjacencies
     */
    public Weight[][] getwAdjMatrix() {
        return wAdjMatrix;
    }

    /**
     * Get Vertex By Specific ID
     *
     * @param id The ID To Search
     * @return The Vertex Pretended If Exists
     * @throws VertexNotExists Throws This Exception If Vertex Does Not Exists
     */
    public Vertex getVertexByID(String id) throws VertexNotExists {
        int i = 0;
        while (i < this.numVertices) {
            if (this.vertices[i].getId().equalsIgnoreCase(id)) {
                return this.vertices[i];
            }
            i++;
        }
        throw new VertexNotExists("The Vertex with the id: " + id + " does not exist!!!");
    }

    /**
     * Return A Iterator With The Vertices Of A Specified Path To A Input Of
     * Data
     *
     * @param startIndex Position Of The Start Index
     * @param targetIndex Position Of The Target Index
     * @param transportOpt Selection Of The Pretended Transport To Do The Travel
     * @param minPokestops Number Minimum Of Different Pokestops To Travel
     * @return The Iterator With The All Vertices
     * @throws NoSuchPathException Throws This Exception If There Is No Path
     * Available
     */
    private Iterator<Vertex> iteratorMinCostPathIndexesIds(int startIndex, int targetIndex, int transportOpt, int minPokestops) throws NoSuchPathException {
        try {
            Iterator<Integer> iterator = iteratorMinCostPathIndexes(startIndex, targetIndex, transportOpt, minPokestops);
            UnorderedListADT<Vertex> resultList = new ArrayUnorderedList<>();

            while (iterator.hasNext()) {
                resultList.addToRear(this.vertices[(iterator.next())]);
            }
            return resultList.iterator();
        } catch (NoSuchPathException nspe) {
            throw nspe;
        }
    }

    /**
     * Check If The Vertex Was Visited While Doing The Transversal
     *
     * @param vertexesNode Data That Records All Known Paths Actually
     * @param vertexToSeekIdx Next Position Vertex To Visit If Was Not Already
     * Visited
     * @param currentIndex Position Of the Current Index
     * @param startIndex Position Of the Start Index
     * @param visitedpokestops Number Of Visited Pokestops While Doing The
     * Trasversal
     * @return True If Visited Otherwise False
     */
    private boolean vertexWasVisited(VertexNode[] vertexesNode, int vertexToSeekIdx, int currentIndex, int startIndex, int visitedpokestops) {
        int seekIdx = currentIndex;
        int ancestorIdx = visitedpokestops;
        Vertex ancestor;
        do {
            int seekIdxTemp = seekIdx;
            try {
                ancestor = vertexesNode[seekIdx].getVertexPathNodes()[ancestorIdx].getAncestor();
                seekIdx = getIndex(ancestor);
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                return false;
            }
            try {
                ancestorIdx = vertexesNode[seekIdxTemp].getVertexPathNodes()[ancestorIdx].getAncestorPokestops();
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                return false;
            }
        } while (seekIdx != vertexToSeekIdx);
        return true;
    }

    /**
     * Update Minimum Costs While Doing The Transversal
     *
     * @param vertexAdj The Vertex Adjancent To The Current Vertex
     * @param minHeap The Minimum Heap To Store All Unvisited Vertex And Select
     * The Minimum To The Next Iteration
     * @param vertexesNode Data That Records All Known Paths Actually
     * @param currentIndex Position Of the Current Index
     * @param i Position Of The Adjancent Vertex
     * @param visitedpokestops Number Of Visited Pokestops While Doing The
     * Trasversal
     */
    private void updateMinCosts(Vertex vertexAdj, HeapADT<VertexPathNode> minHeap, VertexNode[] vertexesNode, int currentIndex, int i, int visitedpokestops) {
        if (vertexAdj.isPokestop()) {
            VertexPathNode vertexPathNodeTemp = new VertexPathNode(vertexAdj, visitedpokestops + 1);
            vertexPathNodeTemp.setDistanceTravelled(this.wAdjMatrix[currentIndex][i].getDistance() + vertexesNode[currentIndex].getVertexPathNodes()[visitedpokestops].getDistanceTravelled());
            vertexPathNodeTemp.setAncestor(this.vertices[currentIndex]);
            vertexPathNodeTemp.setAncestorPokestops(visitedpokestops);
            minHeap.addElement(vertexPathNodeTemp);
            if ((vertexesNode[currentIndex].getVertexPathNodes()[visitedpokestops].getDistanceTravelled() + this.wAdjMatrix[currentIndex][i].getDistance()) < vertexesNode[i].getVertexPathNodes()[visitedpokestops + 1].getDistanceTravelled()) {
                vertexesNode[i].getVertexPathNodes()[visitedpokestops + 1] = vertexPathNodeTemp;
            }
        } else {
            VertexPathNode vertexPathNodeTemp = new VertexPathNode(vertexAdj, visitedpokestops);
            vertexPathNodeTemp.setDistanceTravelled(this.wAdjMatrix[currentIndex][i].getDistance() + vertexesNode[currentIndex].getVertexPathNodes()[visitedpokestops].getDistanceTravelled());
            vertexPathNodeTemp.setAncestor(this.vertices[currentIndex]);
            vertexPathNodeTemp.setAncestorPokestops(visitedpokestops);
            minHeap.addElement(vertexPathNodeTemp);
            if ((vertexesNode[currentIndex].getVertexPathNodes()[visitedpokestops].getDistanceTravelled() + this.wAdjMatrix[currentIndex][i].getDistance()) < vertexesNode[i].getVertexPathNodes()[visitedpokestops].getDistanceTravelled()) {
                vertexesNode[i].getVertexPathNodes()[visitedpokestops] = vertexPathNodeTemp;
            }
        }
    }

    /**
     * Select Minimum Cost Path (Distance) Based On The Minimum Different
     * Pokestops To Visit
     *
     * @param vertexesNode Data That Records All Known Paths Actually
     * @param targetIndex Position Of The Target Index
     * @param minPokestops Number Minimum Of Different Pokestops To Travel
     * @return Data With The Minimum Pokestops, Distance And Path
     */
    private VertexPathNode getMinimumCostPathMinPokestops(VertexNode[] vertexesNode, int targetIndex, int minPokestops) {
        int currentMinPokestops = minPokestops;
        double distanceTravelled = vertexesNode[targetIndex].getVertexPathNodes()[minPokestops].getDistanceTravelled();

        for (int i = minPokestops; i <= this.pokestops; i++) {
            if (vertexesNode[targetIndex].getVertexPathNodes()[i].getDistanceTravelled() < distanceTravelled) {
                currentMinPokestops = i;
            }
        }
        return vertexesNode[targetIndex].getVertexPathNodes()[currentMinPokestops];
    }

    /**
     * Algorithm That Calculates All Possible Paths To All Vertices Starting
     * With One Root Vertex
     *
     * @param startIndex Position Of The Start Index
     * @param targetIndex Position Of The Target Index
     * @param transportOpt Selection Of The Pretended Transport To Do The Travel
     * @param minPokestops Number Minimum Of Different Pokestops To Travel
     * @return The Iterator With The All Vertices Positions
     * @throws NoSuchPathException Throws This Exception If There Is No Path
     * Available
     */
    private Iterator<Integer> iteratorMinCostPathIndexes(int startIndex, int targetIndex, int transportOpt, int minPokestops) throws NoSuchPathException {
        /**
         * Create a array of vertexes for the Dijkstra's algorithm
         */
        VertexNode[] vertexesNode = new VertexNode[this.numVertices];
        /**
         * Create the vertex paths for the Dijkstra's algorithm
         */
        for (int i = 0; i < vertexesNode.length; i++) {
            vertexesNode[i] = new VertexNode(this.vertices[i], this.pokestops);
        }
        /**
         * Pokestops visited while doing transversal to the vertexes
         */
        int visitedpokestops = 0;
        /**
         * List of the result to the shortest distance between start index and
         * target index with the minimum pretended pokestops
         */
        UnorderedListADT<Integer> resultList = new ArrayUnorderedList<>();
        /**
         * Stack used for reading from end to the start the cheapest path
         */
        StackADT<Integer> stack = new LinkedStack<>();
        /**
         * Minimum heap used to select the minimum element for the next
         * iteration
         */
        HeapADT<VertexPathNode> minHeap = new LinkedHeap<>();

        int currentIndex = startIndex;
        vertexesNode[currentIndex].getVertexPathNodes()[visitedpokestops].setDistanceTravelled(0);

        minHeap.addElement(vertexesNode[currentIndex].getVertexPathNodes()[0]);

        while (!minHeap.isEmpty()) {
            VertexPathNode pathNode = minHeap.removeMin();

            Vertex currentNode = pathNode.getNode();
            currentIndex = getIndex(currentNode);
            visitedpokestops = pathNode.getPokestops();

            for (int i = 0; i < this.numVertices; i++) {
                if (this.adjMatrix[currentIndex][i]) {
                    if (!vertexWasVisited(vertexesNode, i, currentIndex, startIndex, visitedpokestops)) {
                        Vertex vertexAdj = this.vertices[i];
                        switch (transportOpt) {
                            case PRIVATE_TRANSPORT: {
                                if (this.wAdjMatrix[currentIndex][i].isPrivateTransport()) {
                                    updateMinCosts(vertexAdj, minHeap, vertexesNode, currentIndex, i, visitedpokestops);
                                }
                                break;
                            }
                            case PUBLIC_TRANSPORT: {
                                if (this.wAdjMatrix[currentIndex][i].isPublicTransport()) {
                                    updateMinCosts(vertexAdj, minHeap, vertexesNode, currentIndex, i, visitedpokestops);
                                }
                                break;
                            }
                            case WALK_TRANSPORT: {
                                updateMinCosts(vertexAdj, minHeap, vertexesNode, currentIndex, i, visitedpokestops);
                                break;
                            }
                        }
                    }
                }
            }
        }

        VertexPathNode vertexPathNode = getMinimumCostPathMinPokestops(vertexesNode, targetIndex, minPokestops);
        if (vertexPathNode.getDistanceTravelled() == Double.MAX_VALUE) {
            throw new NoSuchPathException("There is no path between: " + this.vertices[startIndex].getId() + " and " + this.vertices[targetIndex].getId() + " try with " + minPokestops + " pokestops choose another input options");
        }

        currentIndex = targetIndex;
        stack.push(currentIndex);
        Vertex ancestor = vertexPathNode.getAncestor();
        int ancestorPokestops = vertexPathNode.getAncestorPokestops();
        do {
            try {
                currentIndex = getIndex(ancestor);
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                throw new NoSuchPathException("You cannot set one path with zero pokestops from the starting vertex to itself!!!");
            }
            ancestor = vertexesNode[currentIndex].getVertexPathNodes()[ancestorPokestops].getAncestor();
            ancestorPokestops = vertexesNode[currentIndex].getVertexPathNodes()[ancestorPokestops].getAncestorPokestops();
            stack.push(currentIndex);
        } while (ancestor != null);

        while (!stack.isEmpty()) {
            resultList.addToRear((stack.pop()));
        }
        return resultList.iterator();
    }

    /**
     * Return A Iterator With The Vertices Of A Specified Path To A Input Of
     * Data
     *
     * @param startVertex The Start Index
     * @param targetVertex The Start Index
     * @param transportOpt Selection Of The Pretended Transport To Do The Travel
     * @param minPokestops Number Minimum Of Different Pokestops To Travel
     * @return The Iterator With The All Vertices
     * @throws EmptyNetworkException Throws This Exception If There is No
     * Vertices
     * @throws VertexNotExists Throws This Exception If The Specified Vertex
     * Does Not Exists
     * @throws InvalidTransportOption Throws This Exception If The Option Of
     * Transport Is Invalid
     * @throws PokemonStopsNumberNotExists Throws This Exception If The Number
     * Of Pokestops Is Invalid
     * @throws NoSuchPathException Throws This Exception If There Is No Path
     * Available
     */
    public Iterator<Vertex> iteratorMinCostPath(Vertex startVertex, Vertex targetVertex, int transportOpt, int minPokestops) throws EmptyNetworkException, VertexNotExists, InvalidTransportOption, PokemonStopsNumberNotExists, NoSuchPathException {
        if (isEmpty()) {
            throw new EmptyNetworkException("Network Empty!!!");
        }
        if (transportOpt < PRIVATE_TRANSPORT || transportOpt > WALK_TRANSPORT) {
            throw new InvalidTransportOption("Input a valid option of a type of transport!!!");
        }
        int startVertexIdx;
        int targetVertexIdx;

        try {
            startVertexIdx = getIndex(startVertex);
        } catch (ArrayIndexOutOfBoundsException startIndexException) {
            throw new VertexNotExists("Your start vertex" + startVertex.getId() + " does not exists!!!");
        }
        try {
            targetVertexIdx = getIndex(targetVertex);
        } catch (ArrayIndexOutOfBoundsException startIndexException) {
            throw new VertexNotExists("Your target vertex" + startVertex.getId() + " does not exists!!!");
        }
        if (minPokestops < 0 || minPokestops > this.pokestops) {
            throw new PokemonStopsNumberNotExists("Please Input A Number Of Pokestops Between: 0 and " + this.pokestops + " !!!");
        }
        try {
            return iteratorMinCostPathIndexesIds(startVertexIdx, getIndex(targetVertex), transportOpt, minPokestops);
        } catch (NoSuchPathException nspe) {
            throw nspe;
        }
    }
}
