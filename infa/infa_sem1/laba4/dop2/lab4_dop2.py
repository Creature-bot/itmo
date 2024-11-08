import re
import time

start = time.perf_counter()

stdin = open('tuesday_json_file.json', 'r')
stdout = open('tuesday_schedule_yaml_regex.yaml', 'w')
stdin_1 = open('friday_json_file.json', 'r')
stdout_1 = open('friday_schedule_yaml_regex.yaml', 'w')

def json_to_yaml(file):
    text = file.read()
    ans_yaml = ""

    key_value_pair = re.compile(r'"(\w+)":\s*"?([^"\n\}]+)"?,*')

    for line in text.splitlines():
        #print(line)
        counter = line.count('\t')
        line = line.replace('{', '')
        line = line.replace('[', '')
        match = key_value_pair.search(line)
        #print(match)
        if match:
            key, value = match.groups()
            #print(key, value)
            if key == "_teacher" or key == "_place" or key == "_lesson" or key == "_tipe":
                ans_yaml += '  ' * (counter - 2) + f"{key}: {value}\n"
            elif key == "_time":
                ans_yaml += '  ' * (counter - 3) + '- ' + f"{key}: {value}\n"
            else:
                ans_yaml += '  ' * (counter - 1) + f"{key}: {value}\n"

    return ans_yaml

yaml_content_tuesday = json_to_yaml(stdin)
yaml_content_friday = json_to_yaml(stdin_1)

#print(yaml_content_friday)

stdout.write(yaml_content_tuesday)
stdout_1.write(yaml_content_friday)

stdin.close()
stdout.close()
stdin_1.close()
stdout_1.close()

print(time.perf_counter() - start)
