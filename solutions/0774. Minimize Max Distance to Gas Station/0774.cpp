class Solution {
 public:
  double minmaxGasDist(vector<int>& stations, int k) {
    constexpr double kErr = 1e-6;
    double l = 0;
    double r = stations.back() - stations[0];

    // true if can use k or less gas stations to ensure
    // each adjacent distance between gas stations is at most m
    auto check = [&](int k, double m) -> bool {
      for (int i = 1; i < stations.size(); ++i) {
        const int diff = stations[i] - stations[i - 1];
        if (diff > m) {
          k -= ceil(diff / m) - 1;
          if (k < 0)
            return false;
        }
      }
      return true;
    };

    while (r - l > kErr) {
      const double m = (l + r) / 2;
      if (check(k, m))
        r = m;
      else
        l = m;
    }

    return l;
  }
};