#include <iostream>
#include <limits>
#include <sstream>
#include <string>
#include <vector>
#include <deque>
#include <queue>
#include <set>
#include <map>
#include <algorithm>
#include <functional>
#include <utility>
#include <cmath>
#include <cstdlib>
#include <ctime>
#include <cstdio>

using namespace std;

#define REP(i,n) for((i)=0;(i)<(int)(n);(i)++)
#define foreach(c,itr) for(__typeof((c).begin()) itr=(c).begin();itr!=(c).end();itr++)
#define CHECK_BIT(var,pos) ((var) & (1<<(pos)))

typedef long long ll;

#define MAXN 15

int cache[1<<MAXN][MAXN];
int N;
int *A, *C;

void init(int N){
	int i, j;
	
	REP(i, 1<<MAXN)
	    REP(j, MAXN)
            cache[i][j] = 0;

    A = new int[N];
    C = new int[N];
    REP(i,N) scanf("%d",&A[i]);
    REP(i,N) scanf("%d",&C[i]);
}

int countSetBits(unsigned int v){
    unsigned int c; // c accumulates the total bits set in v
    for (c = 0; v; c++)
    {
        v &= v - 1; // clear the least significant bit set
    }
    return c;
}

/**
    last is the index to last used value in A[],
    ie. range(element in A)<150(8 bits)
*/
int solve(int used, int last, int cache[][MAXN]){
    if (cache[used][last] > 0)
        return cache[used][last];

    int cur_pos = countSetBits(used);
    if (cur_pos == N)
        return 0;

    int i, mn = numeric_limits<int>::max();
    REP(i,N) {
        if (!CHECK_BIT(used, i))
        {
            int next_used = used | (1<<i);
            int subcost = solve(next_used, i, cache);
            int additional = C[cur_pos] * abs(A[last] - A[i]);
            mn = min(mn, subcost + additional);
        }
    }

    cache[used][last] = mn;
    return mn;
}

void run(void){
    int i, answer = numeric_limits<int>::max();
	REP(i,N) {
        answer = min(answer, solve(1<<i, i, cache));
    }
    cout << answer << endl;
}

int main(void){
	int t, T;
	scanf("%d",&T);	// num test cases
	REP(t,T){
	    scanf("%d",&N);
	    init(N);
		run();
	}
	return 0;
}
