#!/bin/bash
# Simple script to generate a random number
# written by Dallas Vogels
set -ue

function roll_die() {
  # capture parameter
  declare -i DIE_SIDES=$1
  # check for die sides
  if [ ! $DIE_SIDES -gt 0 ]; then
    # default to 6
    DIE_SIDES=6
  fi
  # echo to screen
  echo $[ ( $RANDOM % $DIE_SIDES )  + 1 ]
}
#roll_die ${1:-0}  # returns 1 to X

NUMCASES=10
echo $NUMCASES
for i in $(seq 1 $NUMCASES); do
    let N=$(roll_die 10)
    echo $N
    for j in $(seq 1 $N); do
        echo -n $(roll_die 150)" "
    done
    echo ""
    for k in $(seq 1 $N); do
        echo -n $(roll_die 150)" "
    done
    echo ""
done
