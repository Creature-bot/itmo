import time

start_time = time.perf_counter()
for i in range(100):
    import lab4_osnov
end_time = time.perf_counter()
print(f"Основа - {end_time - start_time:.8f} сек")

start_time = time.perf_counter()
for i in range(100):
    import lab4_dop1
end_time = time.perf_counter()
print(f"Доп 1  - {end_time - start_time:.8f} сек")

start_time = time.perf_counter()
for i in range(100):
    import lab4_dop2
end_time = time.perf_counter()
print(f"Доп 2  - {end_time - start_time:.8f} сек")

start_time = time.perf_counter()
for i in range(100):
    import lab4_dop3
end_time = time.perf_counter()
print(f"Доп 3  - {end_time - start_time:.8f} сек")
