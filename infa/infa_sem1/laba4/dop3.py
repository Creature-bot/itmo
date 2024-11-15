from lark import Lark, Transformer
import yaml

json_grammar = """
    ?start: value
    ?value: object
          | array
          | string
          | SIGNED_NUMBER      -> number
          | "true"             -> true
          | "false"            -> false
          | "null"             -> null

    array  : "[" [value ("," value)*] "]"
    object : "{" [pair ("," pair)*] "}"
    pair   : string ":" value
    string : ESCAPED_STRING

    %import common.ESCAPED_STRING
    %import common.SIGNED_NUMBER
    %import common.WS
    %ignore WS
"""

json_parser = Lark(json_grammar, start='value', parser='lalr')

class JSONTransformer(Transformer):
    def object(self, items):
        return dict(items)

    def array(self, items):
        return items

    def string(self, s):
        return s[0][1:-1]

    def number(self, n):
        if '.' in n[0]:
            return float(n[0])
        else:
            return int(n[0])

    def true(self, _):
        return True

    def false(self, _):
        return False

    def null(self, _):
        return None

    def pair(self, items):
        key = items[0][0:].strip()
        value = items[1]
        return key, value

def json_to_yaml(json_data, yaml_filename):
    tree = json_parser.parse(json_data)
    transformer = JSONTransformer()
    data = transformer.transform(tree)

    with open(yaml_filename, 'w') as yaml_file:
        yaml.dump(data, yaml_file, allow_unicode=True, sort_keys=False)

with open('json_file.json') as file:
    json_content = file.read()
    json_to_yaml(json_content, 'schedule_yaml_grammar.yaml')
