package com.cangcui.gametest.engine;

public class Timer {

    private double lastLoopTime;
    
    /**
     * Initializes this timer.
     */
    public void init() {
        lastLoopTime = getTime();
    }

    /**
     * Gets the current time.
     * @return the current time
     */
    public double getTime() {
        return System.nanoTime() / 1000_000_000.0;
    }

    /**
     * Gets the elapsed time since the last call of this method.
     * @return the elapsed time
     */
    public float getElapsedTime() {
        double time = getTime();
        float elapsedTime = (float) (time - lastLoopTime);
        lastLoopTime = time;
        return elapsedTime;
    }

    /**
     * Gets the last time {@link #getElapsedTime()} was called.
     * @return the last time {@link #getElapsedTime()} was called
     */
    public double getLastLoopTime() {
        return lastLoopTime;
    }
}