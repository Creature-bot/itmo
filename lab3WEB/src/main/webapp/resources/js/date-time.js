
Number.prototype.twoDigits = function() {
  const string = this.toString();
  if (string.length === 1) {
    return `0${string}`;
  }
  return string;
};

export  class DateTime {
  #months = [
    'Январь',
    'Февраль',
    'Март',
    'Апрель',
    'Май',
    'Июнь',
    'Июль',
    'Август',
    'Сентябрь',
    'Октябрь',
    'Ноябрь',
    'Декабрь',
  ];

  #days = [
    'Воскресенье',
    'Понедельник',
    'Вторник',
    'Среда',
    'Четверг',
    'Пятница',
    'Суббота',
  ];

  #$dayName;
  #$month;
  #$dayNumber;
  #$year;
  #$hour;
  #$minutes;
  #$seconds;
  #$period;


  constructor({$dayName, $month, $dayNumber, $year, $hour, $minutes, $seconds, $period}) {
    this.#$dayName = $dayName;
    this.#$month = $month;
    this.#$dayNumber = $dayNumber;
    this.#$year = $year;
    this.#$hour = $hour;
    this.#$minutes = $minutes;
    this.#$seconds = $seconds;
    this.#$period = $period;
  }


    show = () => {
        const date = new Date();

        const hours = date.getHours();

        this.#$dayName.text(this.#days[date.getDay()]);
        this.#$month.text(this.#months[date.getMonth()]);
        this.#$dayNumber.text(date.getDate().twoDigits());
        this.#$year.text(date.getFullYear());

        this.#$hour.text(hours.twoDigits());

        this.#$minutes.text(date.getMinutes().twoDigits());
        this.#$seconds.text(date.getSeconds().twoDigits());

    };
}