package horserace;

/**
 * Destination for the race display. Decouples the race engine from Swing so
 * it can be driven by the GUI, the console or a test.
 *
 * @author Muhammad Hussain
 */
public interface RaceOutput
{
    /** A sink that discards everything; used until a real output is set. */
    RaceOutput NONE = new RaceOutput()
    {
        public void print(String text) { }
        public void clear() { }
    };

    /**
     * Append text to the display.
     * @param text the text to append
     */
    void print(String text);

    /** Clear the display before the next frame is drawn. */
    void clear();
}
