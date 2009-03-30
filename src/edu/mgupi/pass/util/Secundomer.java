package edu.mgupi.pass.util;

/**
 * Primitive for time counting. Cycle counting and print total, min, max, speed,
 * etc.
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

	private String name;

	/**
	 * Default constructor.
	 * 
	 * @param name
	 *            is actually title of this counter class
	 */
	protected Secundomer(String name) {
		this.name = name;
	}

	/**
	 * Return given name.
	 * 
	 * @return name of secundomer.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Start new cycle. If counter does not stopped already -- we stop them now
	 * before new cycle.
	 */
	public void start() {

		if (started) {
			this.stop();
			this.start();
		}

		this.time = System.currentTimeMillis();
		started = true;
	}

	/**
	 * Stop current cycle.
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

	/**
	 * Full reset of secundomer class.
	 */
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
	 * Return time between all start and stop calls in msec.
	 * 
	 * @return time between all start and stops.
	 */
	public int getTotalTime() {
		return (int) this.total;
	}

	/**
	 * Return number of total start/stop cycles.
	 * 
	 * @return total number of calls.
	 */
	public int getTotalCalls() {
		return this.cnt;
	}

	public String toString() {
		StringBuilder string = new StringBuilder();
		if (name != null) {
			string.append(name).append(". ");
		}
		string.append("Total: ").append(this.getTotalTime()).append(" msec (");
		string.append(this.getTotalCalls()).append(")");
		if (this.cnt > 1) {
			string.append(", avg = ").append(
					((float) this.getTotalTime() / (float) this.getTotalCalls()));
			string.append(" msec/call").append(", min = ").append(this.min).append(" msec, max = ");
			string.append(this.max).append(" msec");
		}
		return string.toString();
	}
}
