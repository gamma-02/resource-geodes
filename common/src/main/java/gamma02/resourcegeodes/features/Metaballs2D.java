package gamma02.resourcegeodes.features;

import net.minecraft.util.RandomSource;

/**
 * AGAIN, CREDIT TO TFC
 * A 2D Implementation of <a href="https://en.wikipedia.org/wiki/Metaballs">Metaballs</a>, primarily using the techniques outlined in <a href="http://jamie-wong.com/2014/08/19/metaballs-and-marching-squares/">this blog</a>
 */
public class Metaballs2D
{
    public static Metaballs2D simple(RandomSource random, int size)
    {
        return new Metaballs2D(random, 3, 8, 0.1f * size, 0.3f * size, 0.5f * size);
    }

    private final Ball[] balls; // x, y, weight

    public Metaballs2D(RandomSource random, int minBalls, int maxBalls, float minSize, float maxSize, float radius)
    {
        final int ballCount = Metaballs3D.uniform(random, minBalls, maxBalls);

        balls = new Ball[ballCount];
        for (int i = 0; i < balls.length; i++)
        {
            balls[i] = new Ball(
                    Metaballs3D.triangle(random, radius),
                    Metaballs3D.triangle(random, radius),
                    Metaballs3D.uniform(random, minSize, maxSize)
            );
        }
    }

    public boolean inside(float x, float z)
    {
        float f = 0;
        for (Ball ball : balls)
        {
            f += ball.weight * Math.abs(ball.weight) / ((x - ball.x) * (x - ball.x) + (z - ball.z) * (z - ball.z));
            if (f > 1)
            {
                return true;
            }
        }
        return false;
    }

    record Ball(float x, float z, float weight) {}
}