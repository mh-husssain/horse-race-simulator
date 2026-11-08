package horserace;

import java.util.Random;

/**
 * A Random whose nextDouble() always returns the same, changeable value.
 *
 * With value v, a horse moves when v < confidence and falls when
 * v < 0.1 * confidence^2. So v = 0.5 means horses above 0.5 confidence move
 * every frame and none fall; v = 0.0 means every horse moves once and falls.
 */
class FixedRandom extends Random
{
    double value;

    FixedRandom(double value)
    {
        this.value = value;
    }

    @Override
    public double nextDouble()
    {
        return value;
    }
}
