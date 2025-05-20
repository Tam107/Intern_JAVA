package NCC;

import java.util.*;

public class APlusB {
    // Union-Find data structure
    private static int[] parent;
    private static int[] rank;

    // Initialize Union-Find for N nodes
    private static void initialize(int N) {
        parent = new int[N];
        rank = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = i; // Each node is its own parent initially
            rank[i] = 0;
        }
    }

    // Find the root of a node with path compression
    private static int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // Path compression
        }
        return parent[x];
    }

    // Union two nodes by rank
    private static void union(int x, int y) {
        int px = find(x);
        int py = find(y);
        if (px == py) return; // Already in the same component
        if (rank[px] < rank[py]) {
            parent[px] = py;
        } else if (rank[px] > rank[py]) {
            parent[py] = px;
        } else {
            parent[py] = px;
            rank[px]++;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // Read N (number of nodes) and M (number of edges)
        int N = in.nextInt();
        int M = in.nextInt();

        // Read M edges into an array
        int[][] connections = new int[M][2];
        for (int i = 0; i < M; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            connections[i][0] = u;
            connections[i][1] = v;
        }

        // Step 1: Check if we have enough cables
        if (M < N - 1) {
            System.out.println(-1);
            return;
        }

        // Step 2: Initialize Union-Find
        initialize(N);

        // Step 3: Process each edge to build components
        for (int[] edge : connections) {
            int u = edge[0];
            int v = edge[1];
            union(u, v);
        }

        // Step 4: Count the number of components
        int components = 0;
        for (int i = 0; i < N; i++) {
            if (find(i) == i) {
                components++;
            }
        }

        // Step 5: Print the minimum rewirings needed
        System.out.println(components - 1);
    }
}