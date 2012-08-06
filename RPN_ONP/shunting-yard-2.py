class Infix2Postfix(object):
    """This class implements a parser for a query in infix
    notation, and it uses the Shunting-yard algorithm to
    parse and reorder the query into postfix notation for
    processing."""
    def __init__(self):
        self.stack = []
        self.tokens = []
        self.postfix = []

    def tokenize(self, input):
        self.tokens = shlex.split(input)
        # Add whitespace around parens.
        newtokens = []
        for token in self.tokens:
            rightparen = False
            if token[0] == "(":
                newtokens.append("(")
                token = token[1:]
            if len(token) > 0 and token[-1] == ")":
                token = token[:-1]
                rightparen = True
            newtokens.append(token)
            if rightparen:
                newtokens.append(")")

        self.tokens = newtokens

    def is_operator(self, token):
        if token == "and" or token == "or" or token == "not":
            return True

    def manage_precedence(self, token):
        if token != 'not':
            while len(self.stack) > 0:
                op = self.stack.pop()
                if op == 'not':
                    self.postfix.append(op)
                else:
                    self.stack.append(op)
                    break

        self.stack.append(token)

    def right_paren(self):
        found_left = False
        while len(self.stack) > 0:
            top_op = self.stack.pop()
            if top_op != "(":
                self.postfix.append(top_op)
            else:
                found_left = True
                break
        if not found_left:
            raise ParseError, "Parse error: Mismatched parens"

        if len(self.stack) > 0:
            top_op = self.stack.pop()
            if self.is_operator(top_op):
                self.postfix.append(top_op)
            else:
                self.stack.append(top_op)

    def parse(self, input):
        if len(self.tokens) > 0:
            self.__init__()
        factory = PostfixTokenFactory()
        self.tokenize(input)
        for token in self.tokens:
            if self.is_operator(token):
                self.manage_precedence(token)
            else:
                # Look for parens.
                if token == "(":
                    self.stack.append(token)
                elif token == ")":
                    self.right_paren()
                else:
                    self.postfix.append(token)
        while len(self.stack) > 0:
            operator = self.stack.pop()
            if operator == "(" or operator == ")":
                raise ParseError, "Parse error: mismatched parens"
            self.postfix.append(operator)
        return self.postfix

if __name__ == "__main__":
    parser = Infix2Postfix()
    pf = parser.parse("(foo and not bar) or bash")
