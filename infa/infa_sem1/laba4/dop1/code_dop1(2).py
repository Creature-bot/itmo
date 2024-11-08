import json
import yaml
import time

start = time.perf_counter()

with open('tuesday_json_file.json') as json_file:
  data = json.load(json_file)

with open('friday_json_file.json') as json_file_1:
  data_1 = json.load(json_file_1)


with open('tuesday_schedule_yaml_library.yaml', 'w') as yaml_file:
  yaml.dump(data, yaml_file, allow_unicode=True, sort_keys=False)

with open('friday_schedule_yaml_library.yaml', 'w') as yaml_file_1:
  yaml.dump(data_1, yaml_file_1, allow_unicode=True, sort_keys=False)

print(time.perf_counter() - start)
