def Solve(used, last, cache):
  if (used, last) in cache:
      return cache[(used, last)]
  cur_pos = len(used)
  if cur_pos == len(A):
    return 0
  mn = 1e10
  for idx in range(len(A)):
      if not idx in used:
          next_used = used.union([idx])
          subcost = Solve(next_used, A[idx], cache)
          additional = C[cur_pos] * abs(last - A[idx])
          mn = min(mn, subcost + additional)
  cache[(used, last)] = mn
  return mn

#T = int(raw_input())
#for i in range(T):
#   N = int(raw_input())
#   A = map(int, raw_input().split())
#   C = map(int, raw_input().split())

A = [136, 24, 145, 33, 3, 41, 12]
C = [20, 59, 29, 67, 38, 76, 46]
cache = {}

solutions = [Solve(frozenset([idx]), A[idx], cache) for idx in range(len(A))]
print min(solutions)
