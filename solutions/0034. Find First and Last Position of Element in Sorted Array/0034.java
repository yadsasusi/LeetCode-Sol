class Solution {
  public int[] searchRange(int[] nums, int target) {
    final int l = firstGreaterEqual(nums, target);
    if (l == nums.length || nums[l] != target)
      return new int[] {-1, -1};
    final int r = firstGreaterEqual(nums, target + 1) - 1;
    return new int[] {l, r};
  }

  // find the first index l s.t A[l] >= target
  // return A.length if can't find
  private int firstGreaterEqual(int[] A, int target) {
    int l = 0;
    int r = A.length;
    while (l < r) {
      final int m = l + (r - l) / 2;
      if (A[m] >= target)
        r = m;
      else
        l = m + 1;
    }
    return l;
  }
}
