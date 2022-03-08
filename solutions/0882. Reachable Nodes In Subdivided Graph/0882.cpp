struct T {
  int u;
  int d;
  T(int u, int d) : u(u), d(d) {}
};

class Solution {
 public:
  int reachableNodes(vector<vector<int>>& edges, int maxMoves, int n) {
    vector<vector<pair<int, int>>> graph(n);
    auto compare = [](const T& a, const T& b) { return a.d > b.d; };
    priority_queue<T, vector<T>, decltype(compare)> minHeap(compare);
    vector<int> dist(n, maxMoves + 1);

    for (const auto& e : edges) {
      const int u = e[0];
      const int v = e[1];
      const int cnt = e[2];
      graph[u].emplace_back(v, cnt);
      graph[v].emplace_back(u, cnt);
    }

    minHeap.emplace(0, 0);
    dist[0] = 0;

    while (!minHeap.empty()) {
      const auto [u, d] = minHeap.top();
      minHeap.pop();
      // already takes maxMoves to reach u, can't explore anymore
      if (dist[u] >= maxMoves)
        break;
      for (const auto& [v, w] : graph[u])
        if (d + w + 1 < dist[v]) {
          dist[v] = d + w + 1;
          minHeap.emplace(v, dist[v]);
        }
    }

    const int reachableNodes =
        count_if(begin(dist), end(dist), [&](int d) { return d <= maxMoves; });
    int reachableSubnodes = 0;

    for (const auto& e : edges) {
      const int u = e[0];
      const int v = e[1];
      const int cnt = e[2];
      // reachable nodes of e from u
      const int a = dist[u] > maxMoves ? 0 : min(maxMoves - dist[u], cnt);
      // reachable nodes of e from v
      const int b = dist[v] > maxMoves ? 0 : min(maxMoves - dist[v], cnt);
      reachableSubnodes += min(a + b, cnt);
    }

    return reachableNodes + reachableSubnodes;
  }
};
