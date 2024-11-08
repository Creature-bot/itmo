import json
import yaml
import time

start = time.perf_counter()

with open('jsonfile.json') as json_file:
  data = json.load(json_file)

with open('schedule_yaml_library.yaml', 'w') as yaml_file:
  yaml.dump(data, yaml_file, allow_unicode=True, sort_keys=False)

print(time.perf_counter() - start)
