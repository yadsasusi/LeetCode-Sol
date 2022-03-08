class Solution {
 public:
  long long minimumTime(vector<int>& time, int totalTrips) {
    long long l = 1;
    long long r =
        *min_element(begin(time), end(time)) * static_cast<long>(totalTrips);

    auto canComplete = [&](int m) -> bool {
      long long trips = 0;
      for (const int t : time)
        trips += m / t;
      return trips >= totalTrips;
    };

    while (l < r) {
      const long long m = (l + r) / 2;
      if (canComplete(m))
        r = m;
      else
        l = m + 1;
    }

    return l;
  }
};
