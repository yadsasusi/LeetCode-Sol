class T {
  public int u;
  public int d;
  public T(int u, int d) {
    this.u = u;
    this.d = d;
  }
}

class Solution {
  public int reachableNodes(int[][] edges, int maxMoves, int n) {
    List<Pair<Integer, Integer>>[] graph = new List[n];
    PriorityQueue<T> minHeap = new PriorityQueue<>((a, b) -> a.d - b.d);
    int[] dist = new int[n];
    Arrays.fill(dist, maxMoves + 1);

    for (int i = 0; i < n; ++i)
      graph[i] = new ArrayList<>();

    for (int[] e : edges) {
      final int u = e[0];
      final int v = e[1];
      final int cnt = e[2];
      graph[u].add(new Pair<>(v, cnt));
      graph[v].add(new Pair<>(u, cnt));
    }

    minHeap.offer(new T(0, 0));
    dist[0] = 0;

    while (!minHeap.isEmpty()) {
      final int u = minHeap.peek().u;
      final int d = minHeap.poll().d;
      // already takes maxMoves to reach u, can't explore anymore
      if (d >= maxMoves)
        break;
      for (var node : graph[u]) {
        final int v = node.getKey();
        final int w = node.getValue();
        if (d + w + 1 < dist[v]) {
          dist[v] = d + w + 1;
          minHeap.offer(new T(v, dist[v]));
        }
      }
    }

    final int reachableNodes = (int) Arrays.stream(dist).filter(d -> d <= maxMoves).count();
    int reachableSubnodes = 0;

    for (int[] e : edges) {
      final int u = e[0];
      final int v = e[1];
      final int cnt = e[2];
      // reachable nodes of e from u
      final int a = dist[u] > maxMoves ? 0 : Math.min(maxMoves - dist[u], cnt);
      // reachable nodes of e from v
      final int b = dist[v] > maxMoves ? 0 : Math.min(maxMoves - dist[v], cnt);
      reachableSubnodes += Math.min(a + b, cnt);
    }

    return reachableNodes + reachableSubnodes;
  }
}