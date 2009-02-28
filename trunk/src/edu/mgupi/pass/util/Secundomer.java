package edu.mgupi.pass.util;

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

	private long min = Long.MAX_VALUE;
	private long max = Long.MIN_VALUE;

	// private Secundomer() {
	// //
	// }

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
			long diff = System.currentTimeMillis() - time;
			this.total += diff;
			this.time = 0;

			this.min = Math.min(this.min, diff);
			this.max = Math.max(this.max, diff);

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
		return (name == null ? "" : name + ". ") + "Total: " + this.getTotalTime() + " msec (" + this.getTotalCalls()
				+ "), avg = " + ((float) this.getTotalTime() / (float) this.getTotalCalls()) + " msec/call"
				+ (this.cnt > 1 ? (", min = " + this.min + " msec, max = " + this.max + " msec") : "");
	}
}
