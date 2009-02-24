package edu.mgupi.pass;

/**
 * Primitive for time counting.
 * 
 * @author raidan
 * 
 */
public class Secundomer {

	private boolean started = false;

	private long time;
	private long total;
	private int cnt;

	public Secundomer() {
		//
	}

	private String name;

	public Secundomer(String name) {
		this.name = name;
	}

	/**
	 * Start
	 */
	public void start() {
		this.time = System.currentTimeMillis();
		started = true;
	}

	/**
	 * Stop
	 */
	public void stop() {
		if (started) {
			this.total += System.currentTimeMillis() - time;
			this.time = 0;
			cnt++;
			started = false;
		}
	}

	public void reset() {
		if (!started) {
			this.total = 0;
			this.cnt = 0;
		} else {
			stop();
			reset();
		}
	}

	/**
	 * Return time between all start and stop calls in msec
	 * 
	 * @return time between all start and stops
	 */
	public int getTotalTime() {
		return (int) this.total;
	}

	public int getTotalCalls() {
		return this.cnt;
	}

	public String toString() {
		return (name == null ? "" : name + " ") + "Total: " + this.getTotalTime() + " msec (" + this.getTotalCalls()
				+ "), avg = " + ((float) this.getTotalTime() / (float) this.getTotalCalls()) + " msec/call";
	}
}
