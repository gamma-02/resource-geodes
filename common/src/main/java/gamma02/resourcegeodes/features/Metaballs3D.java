package gamma02.resourcegeodes.features;

import net.minecraft.util.RandomSource;

/**
 * ALL CREDIT FOR THIS CODE GOES TO THE TFC TEAM
 */
public class Metaballs3D
{
    public static Metaballs3D simple(RandomSource random, int size)
    {
        return new Metaballs3D(random, 5, 7, 0.1f * size, 0.3f * size, 0.5f * size);
    }

    private final Ball[] balls;

    public Metaballs3D(RandomSource random, int minBalls, int maxBalls, float minSize, float maxSize, float radius)
    {
        final int ballCount = uniform(random, minBalls, maxBalls);
        final int negativeBallCount = minSize < 0 ? (int) (ballCount * (-minSize / (maxSize - minSize))) : 0;
        balls = new Ball[ballCount];
        for (int i = 0; i < balls.length; i++)
        {
            balls[i] = new Ball(
                    triangle(random, radius),
                    triangle(random, radius),
                    triangle(random, radius),
                    i < negativeBallCount ? uniform(random, minSize, 0) : uniform(random, 0, maxSize)
            );
        }
    }

    public boolean inside(float x, float y, float z)
    {
        float f = 0;
        for (Ball ball : balls)
        {
            f += ball.weight * Math.abs(ball.weight) / ((x - ball.x) * (x - ball.x) + (y - ball.y) * (y - ball.y) + (z - ball.z) * (z - ball.z));
            if (f > 1)
            {
                return true;
            }
        }
        return false;
    }

    record Ball(float x, float y, float z, float weight) {}

    public static int uniform(RandomSource random, int min, int max)
    {
        return min == max ? min : min + random.nextInt(max - min);
    }
    public static float uniform(RandomSource random, float min, float max)
    {
        return random.nextFloat() * (max - min) + min;
    }

    public static float triangle(RandomSource random, float delta)
    {
        return (random.nextFloat() - random.nextFloat()) * delta;
    }

}
