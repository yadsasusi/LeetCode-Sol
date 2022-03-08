class Solution {
 public:
  double findMaxAverage(vector<int>& nums, int k) {
    constexpr double kErr = 1e-5;
    double l = *min_element(begin(nums), end(nums));
    double r = *max_element(begin(nums), end(nums));

    // true if there's a subarray with length >= k and average sum >= m
    auto check = [&](double m) -> bool {
      double sum = 0;
      double prevSum = 0;
      double minPrevSum = 0;

      for (int i = 0; i < nums.size(); ++i) {
        // trick: -m for each num so that we can check if the sum of the
        // subarray >= 0
        sum += nums[i] - m;
        if (i >= k) {
          prevSum += nums[i - k] - m;
          minPrevSum = min(minPrevSum, prevSum);
        }
        // if sum - minPrevSum >= 0,
        // we know there's a subarray with length >= k and average sum >= m
        if (i + 1 >= k && sum >= minPrevSum)
          return true;
      }

      return false;
    };

    while (r - l > kErr) {
      const double m = (l + r) / 2;
      if (check(m))
        l = m;
      else
        r = m;
    }

    return l;
  }
};
