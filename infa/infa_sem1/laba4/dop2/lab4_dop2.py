import re

stdin = open('json_file.json', 'r')
stdout = open('schedule_yaml_regex.yaml', 'w')

def json_to_yaml(file):
    text = file.read()
    ans_yaml = ""
    key_value_pair = re.compile(r'"(\w+)":\s*"?([^"\n}]+)"?,*')

    for line in text.splitlines():

        counter = line.count('\t')
        line = line.replace('{', '')
        line = line.replace('[', '')
        match = key_value_pair.search(line)

        if match:
            key, value = match.groups()
            if key == "_teacher" or key == "_place" or key == "_lesson" or key == "_tipe":
                ans_yaml += '  ' * (counter - 3) + f"{key}: {value}\n"
            elif key == "_time":
                ans_yaml += '  ' * (counter - 4) + '- ' + f"{key}: {value}\n"
            elif 'lesson_' in key:
                ans_yaml += '  ' * (counter - 3) + '- ' + f"{key}: {value}\n"
            else:
                ans_yaml += '  ' * (counter - 1) + f"{key}: {value}\n"

    return ans_yaml

yaml_content = json_to_yaml(stdin)
stdout.write(yaml_content)

stdin.close()
stdout.close()
