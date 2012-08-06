#!/usr/bin/env python

# Usage: python shunting-yard.py '1+2*3'

import re, sys
try:
    expr = sys.argv[1]
except:
    print "Usage %s expression" % sys.argv[0]
    sys.exit(1)
tokens = re.split(' *([+\-*/()]|\d+\.\d+|\d+) *', expr)
prec = {'-u': 5, '*': 4, '/': 3, '+': 2, '-': 1, '(': 0, '': 9}
right = {'-u': 1}
def getprec(op):
    return prec.get(op, -1)
# Parsing
op_stack = []
rpn = []
last = ''
for token in tokens:
    if not token:
        continue
    if token == '-' and getprec(last) >= 0:
        token = '-u'
    if re.match('^\d+$|^\d+\.\d+$', token):
        if re.match('^\d+$|^\d+\.\d+$', last) or last == ')':
            raise Exception, "Value tokens must be separated by an operator"
        rpn.append(token)
    elif token == '(':
        op_stack.append(token)
    elif token == ')':
        while op_stack[-1] != '(':
            t = op_stack.pop()
            rpn.append(t)
        if op_stack.pop() != '(':
            raise Exception, "No matching ("
    elif getprec(token) > 0:
        pr = getprec(token)
        if token in right:
            while op_stack and pr < getprec(op_stack[-1]):
                rpn.append(op_stack.pop())
        else:
            while op_stack and pr <= getprec(op_stack[-1]):
               rpn.append(op_stack.pop())
        op_stack.append(token)
    else:
        raise Exception, "Unknown token: \"%s\"" % token
    last = token
while op_stack:
    rpn.append(op_stack.pop())
for token in rpn:
    if token == '-u':
        print '_1*',
    else:
        print token,
#print 'p'
