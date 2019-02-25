package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	 int n; //row or column of the grid;
	 int [] P ;//measure the openness of the grid. null when not open, 1 when open.
	int top, bottom;
	WeightedQuickUnionUF wqu1, wqu2;
	int[]opensite;
	int sizeOfOpenSite;
	public Percolation(int N) { // create N-by-N grid, with all sites initially blocked
		this.n=N;
		wqu1=new WeightedQuickUnionUF((N*N)+2);
		wqu2= new WeightedQuickUnionUF((N*N)+1);
		top = N*N;
		bottom= N*N+1;
		P=new int[N*N];
		sizeOfOpenSite =0;
		for (int i = 0; i < N; i ++) {
            wqu1.union(top, pos(0, i));
            wqu2.union(top, pos(0, i));
            wqu1.union(bottom, pos(N - 1, i));
        }
	}
	private int pos(int row,int col) {
		return (row*n)+col;
	}
	private int size() {
		return n*n;
	}
	public void open(int row, int col) { // open the site (row, col) if it is not open already
		if(P[pos(row,col)] ==0){
			P[pos(row,col)]=1;
		}
		opensite[sizeOfOpenSite]=P[pos(row,col)];
		sizeOfOpenSite+=1;
		for (int i = 0; i <sizeOfOpenSite; i ++) {
            for (int j = i + 1; j < sizeOfOpenSite; j ++) {
                if ((Math.abs(opensite[i] - opensite[j]) == 1 && (Math.max(opensite[i], opensite[j]) % n) != 0) || (Math.abs(opensite[i] - opensite[j]) == n)) {
                    if (!wqu1.connected(opensite[i], opensite[j])) {
                        wqu1.union(opensite[i], opensite[j]);
                        wqu2.union(opensite[i], opensite[j]);
                    }
                }
            }
        }
	}

	public boolean isOpen(int row, int col) { // is the site (row, col) open?
		return P[pos(row,col)]==1;
	}

	public boolean isFull(int row, int col) { // is the site (row, col) full?
		if(P[pos(row,col)]==0) {
			return false;
		}
		else {
			if(wqu1.connected(top, bottom)) {
				return wqu2.connected(top, pos(row,col));
			}
			else {
				return wqu1.connected(top, pos(row,col));
			}
		}
	}

	public int numberOfOpenSites() { // number of open sites
		int k=0;
		for(int i=0;i<size();i++) {
			if(P[i]==1) {
				k++;
			}
		}
		return k;
	}

	public boolean percolates() { // does the system percolate?
		return wqu1.connected(top, bottom);
	}

	public static void main(String[] args) { // use for unit testing (not required)
		Percolation test1=new Percolation(5);
		test1.open(2, 1);
		System.out.print(test1.isOpen(2, 1));
	}
}
