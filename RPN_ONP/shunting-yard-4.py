'''Hey, it's public domain :)'''

class MismatchedParenthesesException(Exception):
 '''Raised when Djikstrer detects that there is something wrong with separators or parentheses'''
 pass
 
class Djikstrer:
 '''This class performs conversion from infix expressions to Reverse Polish Notation using Djikstra's shunting yard
 algorithm. Function run() accepts a list of tokens(yes, the expression needs to be tokenized first) and after run() finishes
 there is a list of tokens in class' attribute 'output' in RPN order. New operators can be added below. Smaller precedence
 means the operator will get executed later, ie. multiplication has greater precedence than addition. Obviously, precedence is set in 
 op_prec dictionary. assoc, lassor and rassoc dictionaries specify whether the operators are associative, left-associative or 
 right-associative. Note: one class can perform many conversions, as 'run' method cleans the stack and output before analysis.
 Another note: watch out for unary minus(ie. -5) passed as two tokens, as it will certainly screw the stuff up.
 Same with unary plus(but who would type something like that?'''
 operators = ('&&','||','+','-','*','/','^')
 op_prec = {'+':3,'-':3,'*':4,'/':4,'^':5,'&&':6,'||':6}
 assoc = {'+':True,'-':False,'*':True,'/':False,'^':False,'||':True,'&&':True}
 lassoc = {'+':True,'-':True,'*':True,'/':True,'^':False,'||':True,'&&':True}
 rassoc = {'+':True,'-':False,'*':True,'/':False,'^':True,'||':True,'&&':True}
 
 def _isparenthesis(self, token):
  return (token in ('(',')'))
 def _isfunction(self, token):
  return not (self._isoperator(token) or self._isparenthesis(token) or self._isnumber(token) or self._isseparator(token))
 def _isseparator(self, token):
  return token== ','
 def _isoperator(self, token):
  return token in self.operators
 def _isnumber(self,x):
  try:
   float(x)
  except:
   return False
  return True
 def areThereOperatorsOnStack(self):
  for x in self.stack:
   if self._isoperator(x):
    return True
  return False
 def sTo(self):
  self.output.append(self.stack.pop())
 def spush(self, token):
  self.stack.append(token)
 def opush(self, token):
  self.output.append(token)
 
 def run(self, tokenlist):
  self.stack = []
  self.output = []
  try:
   for token in tokenlist:
    self.token(token) 
   while self.areThereOperatorsOnStack():
    if self._isparenthesis(self.stack[-1]):
     raise MismatchedParenthesesException, 'Parentheses'
    self.sTo()
  except:
   raise MismatchedParenthesesException, 'Parentheses or separators'
   
 def token(self, token):
  if self._isnumber(token):
   self.opush(token)
   return
  if self._isfunction(token):
   self.spush(token)
   return
  if self._isseparator(token):
   while not self.stack[-1]=='(':
    if len(self.stack)==0:
     raise MismatchedParenthesesException, 'Parentheses or separators'
    self.sTo()
    if self.stack[-1]=='(':
     break
   return
  if self._isoperator(token):
   if len(self.stack)>0:
    while self._isoperator(self.stack[-1]) and (((self.assoc[token] or self.lassoc[token]) and (self.op_prec[token]<=self.op_prec[self.stack[-1]])) or (self.rassoc[token] and (self.op_prec[token]<self.op_prec[self.stack[-1]]))):
     self.sTo()
   self.spush(token)
   return
  if token=='(':
   self.spush(token)
   return
  if token==')':
   while not self.stack[-1]=='(':
    if len(self.stack)==0:
     raise MismatchedParenthesesException('Parentheses')
    self.sTo()
    if self.stack[-1]=='(':
     break
   self.stack.pop()
   if len(self.stack)==0:
    return
   if self._isfunction(self.stack[-1]):
    self.sTo()
    
if __name__ == "__main__":
 a = Djikstrer()
 a.run(['2','+','2']);
 print a.output
    