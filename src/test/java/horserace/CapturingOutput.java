package horserace;

/** Records what the race prints since the last clear (the current frame). */
class CapturingOutput implements RaceOutput
{
    private final StringBuilder frame = new StringBuilder();

    @Override
    public void print(String text)
    {
        frame.append(text);
    }

    @Override
    public void clear()
    {
        frame.setLength(0);
    }

    String currentFrame()
    {
        return frame.toString();
    }
}
