class Solution {
  public int largestPathValue(String colors, int[][] edges) {
    final int n = colors.length();
    int ans = 0;
    int processed = 0;
    List<Integer>[] graph = new List[n];
    int[] inDegree = new int[n];
    Queue<Integer> q = new ArrayDeque<>();
    int[][] count = new int[n][26];

    for (int i = 0; i < n; ++i)
      graph[i] = new ArrayList<>();

    // build graph
    for (int[] e : edges) {
      final int u = e[0];
      final int v = e[1];
      graph[u].add(v);
      ++inDegree[v];
    }

    // topology
    for (int i = 0; i < n; ++i)
      if (inDegree[i] == 0)
        q.offer(i);

    while (!q.isEmpty()) {
      final int out = q.poll();
      ++processed;
      ans = Math.max(ans, ++count[out][colors.charAt(out) - 'a']);
      for (final int in : graph[out]) {
        for (int i = 0; i < 26; ++i)
          count[in][i] = Math.max(count[in][i], count[out][i]);
        if (--inDegree[in] == 0)
          q.offer(in);
      }
    }

    return processed == n ? ans : -1;
  }
}
