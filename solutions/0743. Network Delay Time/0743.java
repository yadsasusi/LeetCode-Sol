class T {
  public int u;
  public int d;
  public T(int u, int d) {
    this.u = u;
    this.d = d;
  }
}

class Solution {
  public int networkDelayTime(int[][] times, int n, int k) {
    List<Pair<Integer, Integer>>[] graph = new List[n];
    PriorityQueue<T> minHeap = new PriorityQueue<>((a, b) -> a.d - b.d);
    boolean[] seen = new boolean[n];

    for (int i = 0; i < n; ++i)
      graph[i] = new ArrayList<>();

    for (int[] t : times) {
      final int u = t[0] - 1;
      final int v = t[1] - 1;
      final int w = t[2];
      graph[u].add(new Pair<>(v, w));
    }

    minHeap.offer(new T(k - 1, 0));

    while (!minHeap.isEmpty()) {
      final int u = minHeap.peek().u;
      final int d = minHeap.poll().d;
      if (seen[u])
        continue;
      seen[u] = true;
      if (--n == 0)
        return d;
      for (var node : graph[u]) {
        final int v = node.getKey();
        final int w = node.getValue();
        minHeap.offer(new T(v, d + w));
      }
    }

    return -1;
  }
}
