'''
Created on Oct 5, 2010
 
@author: nomemory
'''
 
#Associativity constants for operators
LEFT_ASSOC = 0
RIGHT_ASSOC = 1
 
#Supported operators
OPERATORS = {
    '+' : (0, LEFT_ASSOC),
    '-' : (0, LEFT_ASSOC),
    '*' : (5, LEFT_ASSOC),
    '/' : (5, LEFT_ASSOC),
    '%' : (5, LEFT_ASSOC),
    '^' : (10, RIGHT_ASSOC)
}
 
#Test if a certain token is operator
def isOperator(token):
    return token in OPERATORS.keys()
 
#Test the associativity type of a certain token
def isAssociative(token, assoc):
    if not isOperator(token):
        raise ValueError('Invalid token: %s' % token)
    return OPERATORS[token][1] == assoc
 
#Compare the precedence of two tokens
def cmpPrecedence(token1, token2):
    if not isOperator(token1) or not isOperator(token2):
        raise ValueError('Invalid tokens: %s %s' % (token1, token2))
    return OPERATORS[token1][0] - OPERATORS[token2][0]
 
#Transforms an infix expression to RPN
def infixToRPN(tokens):
    out = []
    stack = []
    #For all the input tokens [S1] read the next token [S2]
    for token in tokens:
        if isOperator(token):
            # If token is an operator (x) [S3]
            while len(stack) != 0 and isOperator(stack[-1]):
                # [S4]
                if (isAssociative(token, LEFT_ASSOC)
                    and cmpPrecedence(token, stack[-1]) <= 0) or \
                    (isAssociative(token, RIGHT_ASSOC)
                    and cmpPrecedence(token, stack[-1]) < 0):
                    # [S5] [S6]
                    out.append(stack.pop())
                    continue
                break
            # [S7]
            stack.append(token)
        elif token == '(':
            stack.append(token) # [S8]
        elif token == ')':
            # [S9]
            while len(stack) != 0 and stack[-1] != '(':
                out.append(stack.pop()) # [S10]
            stack.pop() # [S11]
        else:
            out.append(token) # [S12]
    while len(stack) != 0:
        # [S13]
        out.append(stack.pop())
    return out
 
if __name__ == '__main__':
    input = "( 1 + 2 ) * ( 3 / 4 ) ^ ( 5 + 6 )".split(" ")
    output = infixToRPN(input)
    print output
