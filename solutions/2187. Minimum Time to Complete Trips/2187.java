class Solution {
  public long minimumTime(int[] time, int totalTrips) {
    long l = 1;
    long r = Arrays.stream(time).min().getAsInt() * (long) totalTrips;

    while (l < r) {
      final long m = (l + r) / 2;
      if (canComplete(time, totalTrips, m))
        r = m;
      else
        l = m + 1;
    }

    return l;
  }

  private boolean canComplete(int[] time, int totalTrips, long m) {
    long trips = 0;
    for (final int t : time)
      trips += m / t;
    return trips >= totalTrips;
  }
}
