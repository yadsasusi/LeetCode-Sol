class T {
  public int u;
  public int d;
  public int stops;
  public T(int u, int d, int stops) {
    this.u = u;
    this.d = d;
    this.stops = stops;
  }
}

class Solution {
  public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
    List<Pair<Integer, Integer>>[] graph = new List[n];
    PriorityQueue<T> minHeap = new PriorityQueue<>((a, b) -> a.d - b.d);
    int[][] dist = new int[n][k + 2];
    Arrays.stream(dist).forEach(A -> Arrays.fill(A, Integer.MAX_VALUE));

    for (int i = 0; i < n; ++i)
      graph[i] = new ArrayList<>();

    for (int[] f : flights) {
      final int u = f[0];
      final int v = f[1];
      final int w = f[2];
      graph[u].add(new Pair<>(v, w));
    }

    minHeap.offer(new T(src, 0, k + 1)); // start with node src with d == 0
    dist[src][k + 1] = 0;

    while (!minHeap.isEmpty()) {
      final int u = minHeap.peek().u;
      final int d = minHeap.peek().d;
      final int stops = minHeap.poll().stops;
      if (u == dst)
        return d;
      if (stops > 0)
        for (var node : graph[u]) {
          final int v = node.getKey();
          final int w = node.getValue();
          final int newDist = d + w;
          if (newDist < dist[v][stops - 1]) {
            dist[v][stops - 1] = newDist;
            minHeap.offer(new T(v, d + w, stops - 1));
          }
        }
    }

    return -1;
  }
}
