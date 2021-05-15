package estgf.ipp.pt.pokemongo.application;

/**
 * The Weight Created for the Problem of Pokemon GO For The Edges
 */
public class Weight {

    private double distance;
    private boolean privateTransport;
    private boolean publicTransport;

    /**
     * Default Constructor Method For The Weight
     */
    public Weight() {
        this.distance = Integer.MAX_VALUE;
        this.privateTransport = false;
        this.publicTransport = false;
    }

    /**
     * Constructor Method For The Weight
     *
     * @param distance The Distance To Travelled The Edges
     * @param privateTransport Is Private Transport Available
     * @param publicTransport Is Public Transport Available
     */
    public Weight(double distance, boolean privateTransport, boolean publicTransport) {
        setDistance(distance);
        setPrivateTransport(privateTransport);
        setPublicTransport(publicTransport);
    }

    /**
     * Get Distance Of The Edge
     *
     * @return The Distance To Travel
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Set A New Distance To The Edge
     *
     * @param distance The New Distance To Travel
     */
    public final void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * Is Private Transport Available?
     *
     * @return True If Available Otherwise Returns False
     */
    public boolean isPrivateTransport() {
        return privateTransport;
    }

    /**
     * Set Private Transport For Edge
     *
     * @param privateTransport The New Value For Private Transport
     */
    private void setPrivateTransport(boolean privateTransport) {
        this.privateTransport = privateTransport;
    }

    /**
     * Is Public Transport Available?
     *
     * @return True If Available Otherwise Returns False
     */
    public boolean isPublicTransport() {
        return publicTransport;
    }

    /**
     * Set Public Transport For Edge
     *
     * @param publicTransport The New Value For Public Transport
     */
    private void setPublicTransport(boolean publicTransport) {
        this.publicTransport = publicTransport;
    }
}
