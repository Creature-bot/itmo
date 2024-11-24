import json
import yaml

with open('json_file.json') as json_file:
  data = json.load(json_file)

with open('schedule_yaml_library.yaml', 'w') as yaml_file:
  yaml.dump(data, yaml_file, allow_unicode=True, sort_keys=False)
