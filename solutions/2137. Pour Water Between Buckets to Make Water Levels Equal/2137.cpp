class Solution {
 public:
  double equalizeWater(vector<int>& buckets, int loss) {
    constexpr double kErr = 1e-5;
    const double kPercentage = (100 - loss) / (double)100;
    double l = 0.0;
    double r = *max_element(begin(buckets), end(buckets));

    auto canFill = [&](double target) -> bool {
      double extra = 0.0;
      double need = 0.0;
      for (const int bucket : buckets)
        if (bucket > target)
          extra += bucket - target;
        else
          need += target - bucket;
      return extra * kPercentage >= need;
    };

    while (r - l > kErr) {
      const double m = (l + r) / 2;
      if (canFill(m))
        l = m;
      else
        r = m;
    }

    return l;
  }
};
