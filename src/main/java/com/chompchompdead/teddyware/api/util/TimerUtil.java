package com.chompchompdead.teddyware.api.util;

public class TimerUtil implements UtilInterface {

    private long current;
    public long time;

    public TimerUtil(){
        this.current = System.currentTimeMillis();
        this.time = -1L;
    }

    public boolean hasReached(final long delay){
        return System.currentTimeMillis() - this.current >= delay;
    }

    public boolean hasReached(final long delay, boolean reset){
        if (reset)
            reset();
        return System.currentTimeMillis() - this.current >= delay;
    }

    public void reset(){
        this.current = System.currentTimeMillis();
    }

    public long getTimePassed(){
        return System.currentTimeMillis() - this.current;
    }

    public boolean getTimePassed(long time, Format format) {
        switch (format) {
            case System:
                return getMS(System.nanoTime() - this.time) >= time;
            case Ticks:
                return mc.player.ticksExisted % (int) time == 0;
        }

        return true;
    }

    public long getMS(long time) {
        return time / 1000000L;
    }

    public boolean sleep(final long time){
        if (time() >= time){
            reset();
            return true;
        }
        return false;
    }

    public long time() {
        return System.currentTimeMillis() - current;
    }

    public enum Format {
        System,
        Ticks
    }

}
