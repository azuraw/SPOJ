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

/* for memset() */
#include <stdio.h>
#include <string.h> // also for segmented sieve

using namespace std;

#define REP(i,n) for((i)=0;(i)<(ll)(n);(i)++)
#define foreach(c,itr) for(__typeof((c).begin()) itr=(c).begin();itr!=(c).end();itr++)
#define CHECK_BIT(var,pos) ((var) & (1<<(pos)))

typedef unsigned long long ll;

#define MAX 46656
#define LMT 216
#define LEN 4830
#define RNG 100032

unsigned base[MAX/64], segment[RNG/64], primes[LEN];

#define sq(x) ((x)*(x))
#define mset(x,v) memset(x,v,sizeof(x))
#define chkC(x,n) (x[n>>6]&(1<<((n>>1)&31)))
#define setC(x,n) (x[n>>6]|=(1<<((n>>1)&31)))

void runEratosthenesSieve(ll lowerBound, ll upperBound) {
      ll upperBoundSquareRoot = (ll)sqrt((double)upperBound);

      ll RANGE = 100001;

      bool *isComposite = new bool[RANGE];
      memset(isComposite, 0, sizeof(bool) * (RANGE));

      for (int m = 2; m <= upperBoundSquareRoot; m++) {
          if (!isComposite[m]) {
              // find first number lowerBound <= n <= upperBound,
              // s.t. (n-m^2) is multiple of m
              
              ll k = lowerBound;
              while (k<=upperBound)
              {
                if (!((k - (m*m)) % m))
                    break;
                k++;
              }
              for (; k <= upperBound; k += m)
                isComposite[k - lowerBound] = true;
            }
      }

      for (ll m=lowerBound; m <= upperBound; m++)
            if (m > 1 && !isComposite[m - lowerBound])
                cout << m << endl;

      delete [] isComposite;
}

/* Generates all the necessary prime numbers and marks them in base[]*/
void sieve()
{
    unsigned i, j, k;
    for(i=3; i<LMT; i+=2)
        if(!chkC(base, i))
            for(j=i*i, k=i<<1; j<MAX; j+=k)
                setC(base, j);
    for(i=3, j=0; i<MAX; i+=2)
        if(!chkC(base, i))
            primes[j++] = i;
}

/* Returns the prime-count within range [a,b] and marks them in segment[] */
int segmented_sieve(int a, int b)
{
    unsigned i, j, k, cnt = (a<=2 && 2<=b)? 1 : 0;

    if(b<2) return 0;
    if(a<3) {
        if (a<=2)
            cout << 2 << endl;
        a = 3;
    }
    if(a%2==0) a++;

    mset(segment,0);

    for(i=0; sq(primes[i])<=b; i++)
    {
        j = primes[i] * ( (a+primes[i]-1) / primes[i] );
        if(j%2==0) j += primes[i];
        for(k=primes[i]<<1; j<=b; j+=k)
            if(j!=primes[i])
                setC(segment, (j-a));
    }

    for(i=0; i<=b-a; i+=2)
        if(!chkC(segment, i))
        {
            cout << (a+i) << endl;
            cnt++;
        }

    return cnt;
}

int main(void){
    sieve();
    ll t, T;
    scanf("%llu",&T);    // num test cases
    REP(t,T){
        ll l, h;
        scanf("%llu %llu",&l,&h);
        segmented_sieve(l, h);
        cout << endl;
    }
    return 0;
}
