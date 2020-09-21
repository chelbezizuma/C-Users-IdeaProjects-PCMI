class Rectangel {

    // задача окон в формате х1 х2 у1 у2
    static double window1[] = {100, 700, 100, 700};
    static double window2[] = {200, 850, 680, 1000};
    static double window3[] = {150, 650, 50, 900};
    static double windowarray[][] = {window1, window2, window3};
    static int windowamount = 3;

    public static void help() {
        System.out.println("Создатель: Кондратьева Дарья 09-952(2)\nВозможные ключи: -h -help -? -x -y\nИспользование: app -x <координата Х> -y <Координата Y>");
    }
    public static void an() {
        System.out.println("Введите координаты X и Y\nИспользование: app -x <координата Х> -y <Координата Y>\nПример: -x 120 -y 250");
    }
    public static void main(String[] args) {
        double x = -1;
        double y = -1;
        if (args.length == 0) {
            help();
            return;
        } else {
            for (int i = 0; i < args.length; i++) {
                if ((args[i].equals("-h")) || (args[i].equals("-help")) || (args[i].equals("-?"))) {
                    an();
                    return;
                } else
                if (args[0].toLowerCase().equals("-x")) {
                    if (args[i].toLowerCase().equals("-x")) {
                        try {
                            x = Double.parseDouble(args[i+1]);
                        } catch (NumberFormatException e) {
                            System.out.println("Неверный аргумент Х " + args[i+1] + "\nДесятичные дроби необходимо вводить через точку");
                            an();
                            return;
                        }
                    }
                    if (args[i].toLowerCase().equals("-y")) {
                        try {
                            y = Double.parseDouble(args[i+1]);
                        } catch (NumberFormatException e) {
                            System.out.println("Неверный аргумент Y: " + args[i+1] + "\nДесятичные дроби необходимо вводить через точку");
                            an();
                            return;
                        }
                    }
                } else {
                    System.out.println("Ошибка: неожиданный аргумент");
                    an();
                    return;
                }
            }

            if ((x > 0) && (y > 0)) {
                if (determineSelectedWindow(x, y) == -1) {
                    System.out.println("Удовлетворяющего введенным координатам окна не существует");
                    an();
                    return;
                } else {
                    System.out.println("Введённые вами координаты : x = "+ x +", y = " + y);
                    System.out.println("Искомое окно - окно номер " + determineSelectedWindow(x, y));
                }
            } else {
                if (x < 0) {
                    System.out.println("Ошибка: Неверно указана координата Х" + args[1] + "\nКоордината либо не указана, либо отрицательна");
                }
                if (y < 0) {
                    System.out.println("Ошибка: Неверно указана координата Y" + args[3] + "\nКоордината либо не указана, либо отрицательна");
                }
                an();
                return;
            }

        }
    }

    public static int determineSelectedWindow(Double x, Double y) {
        int output = -1;
        for (int i = --windowamount; i > -1; i--) {
            if (x >= windowarray[i][0] && (x <= windowarray[i][1]) && (y >= windowarray[i][2]) && (y <= windowarray[i][3])) {
                output = ++i;
                break;
            }
        }
        return output;
    }

}
