import csv
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt

def edit(s):
    return int(s[:s.find('.')])\

with open("data.csv") as r_file:
    file_reader = csv.reader(r_file, delimiter=",")
    data = [[], [], [], []]
    columns = ["Открытие", "Макс", "Мин", "Закрытие"]
    dates = ['27/09/18', '29/10/18', '27/11/18', '20/12/18']
    count = 0
    for row in file_reader:
        date = row[2]
        if date == dates[0]:
            data[0].append([edit(row[4]), edit(row[5]), edit(row[6]), edit(row[7])])
        if date == dates[1]:
            data[1].append([edit(row[4]), edit(row[5]), edit(row[6]), edit(row[7])])
        if date == dates[2]:
            data[2].append([edit(row[4]), edit(row[5]), edit(row[6]), edit(row[7])])
        if date == dates[3]:
            data[3].append([edit(row[4]), edit(row[5]), edit(row[6]), edit(row[7])])
    plt.figure(figsize=(14, 7))
    for i in range(4):
        df = pd.DataFrame(data[i], columns=columns)
        plt.subplot(2, 2, i + 1)
        sns.boxplot(data=df)
        plt.title(f"{dates[i]}")
        plt.legend(df, loc='best')
    plt.subplots_adjust(wspace=0.4, hspace=0.4)
    plt.show()
