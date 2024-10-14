#print(466380%6) #0
#print(466380%4) #0
#print(466380%8) #4
# 8-\

import re

def count_smile(t):
    pattern = r'8-\\'
    return len(re.findall(pattern, t))

def run_test():
    tests = [
        "Смайлики 8-\ помогают передать эмоции в текстовых сообщениях.",
        "Я люблю использовать 8-\ и 8-), чтобы выразить свою эмоцию 8-\.",
        "Смайлик 8-\ делают общение более ярким и веселым 8-\ 8-\ 8-\.",
        "Иногда один смайлик 8-\ может сказать больше, чем целое предложение с 8-\ 8-\ 8-\.",
        "Смайлика не будет, смайлик обиделся и написал псж."
    ]

    results = [1,2,4,4,0]

    for i, s in enumerate(tests):
        result = count_smile(s)
        if result == results[i]:
            print(f'In test {i+1} found {result} smile')
        else:
            print(f'Test {i+1} is failed, should have been {result}')

run_test()
