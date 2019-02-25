package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	int n, t;
	double u, sigma;
	double CFlow, CFhigh;
	double[] PerData;
	Percolation Per;
	int[] order;
	int numSite;
	double percentage;

	public PercolationStats(int N, int T) {
		if (N <= 0 || T <= 0)
			throw new java.lang.IllegalArgumentException("N and T should be integers bigger than 0");
		this.n = N;
		this.t = T;
		PerData = new double[T];
		Per = new Percolation(N);
		order = new int[N * N];
		numSite = 0;
		for (int i = 0; i < N * N; i++) {
			order[i] = i;
		}
		for (int i = 0; i < T; i++) {
			StdRandom.shuffle(order);
			numSite = 0;
			Per = new Percolation(N);
			for (int j = 0; j < N * N && !Per.percolates(); j++) {
				Per.open(order[j] / N, order[j] % N);
				numSite++;
			}
			percentage = (double) numSite / (double) (N * N);
			PerData[i] = percentage;
		}
	}

	public double mean() {
		u = StdStats.mean(PerData);
		return u;
	}

	public double stddev() {
		sigma = StdStats.stddev(PerData);
		return sigma;
	}

	public double confidenceLow() {
		CFlow = u - 1.96 * sigma / Math.sqrt(t);
		return CFlow;
	}

	public double confidenceHigh() {
		CFhigh = u + 1.96 * sigma / Math.sqrt(t);
		return CFhigh;
	}

	public static void main(String[] args) {
		PercolationStats test1 = new PercolationStats(10, 50);
		System.out.println(test1.mean());
		System.out.println(test1.stddev());
		System.out.println(test1.confidenceLow());
		System.out.println(test1.confidenceHigh());
	}
}
