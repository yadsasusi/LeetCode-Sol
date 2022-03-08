struct T {
  int u;
  int d;
  T(int u, int d) : u(u), d(d) {}
};

class Solution {
 public:
  int networkDelayTime(vector<vector<int>>& times, int n, int k) {
    vector<vector<pair<int, int>>> graph(n);
    auto compare = [](const T& a, const T& b) { return a.d > b.d; };
    priority_queue<T, vector<T>, decltype(compare)> minHeap(compare);
    vector<bool> seen(n);

    for (const auto& t : times) {
      const int u = t[0] - 1;
      const int v = t[1] - 1;
      const int w = t[2];
      graph[u].emplace_back(v, w);
    }

    minHeap.emplace(k - 1, 0);

    while (!minHeap.empty()) {
      const auto [u, d] = minHeap.top();
      minHeap.pop();
      if (seen[u])
        continue;
      seen[u] = true;
      if (--n == 0)
        return d;
      for (const auto& [v, w] : graph[u])
        minHeap.emplace(v, d + w);
    }

    return -1;
  }
};
