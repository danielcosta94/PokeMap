package estgf.ipp.pt.pokemongo.application;

/**
 * The Vertex Created for the Problem of Pokemon GO
 */
public final class Vertex {

    private String id;
    private boolean pokestop;

    /**
     * Constructor Method For The Vertex
     *
     * @param id Name Of Vertex
     * @param pokestop Is Pokestop Or Not?
     */
    public Vertex(String id, boolean pokestop) {
        setId(id);
        setPokestop(pokestop);
    }

    /**
     * Get The ID Of The Specified Vertex
     *
     * @return ID Of The Vertex
     */
    public String getId() {
        return id;
    }

    /**
     * Set The ID Of The Specified Vertex
     *
     * @param id The New ID For The Vertex
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * The Specified Vertex Is Or Not Pokestop?
     *
     * @return True If It Is Pokestop Otherwise False
     */
    public boolean isPokestop() {
        return pokestop;
    }

    /**
     * Set If Vertex Is Or Not Pokestop
     *
     * @param pokestop True If It Is Pokestop Otherwise False
     */
    public void setPokestop(boolean pokestop) {
        this.pokestop = pokestop;
    }
}
