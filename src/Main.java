import java.util.concurrent.Semaphore;

public class Main {
    public static final int CARS_COUNT = 4;
    static Semaphore semaphore = new Semaphore(2); // семафор для туннеля
    static Semaphore semaphore1 = new Semaphore(4); // семафор для финишеров

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), semaphore, semaphore1);
        }
        Thread[] myThreads = new Thread[CARS_COUNT];
        for (int i = 0; i < myThreads.length; i++) {
            final int w = i;
            myThreads[w] = new Thread(() -> {
                try {
                    System.out.println(cars[w].getName() + " готовится");
                    Thread.sleep(500 + (int) (Math.random() * 800));
                    System.out.println(cars[w].getName() + " готов");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            myThreads[w].start();
        }
        // Ждем пока все подготовятся
        try {
            for (Thread thread : myThreads) {
                thread.join();
            }
        }
        catch (InterruptedException e){
            e.getStackTrace();
        }

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        Thread[] myThreads1 = new Thread[CARS_COUNT];
        for (int i = 0; i < myThreads1.length; i++) {
            try {
               myThreads1[i] = new Thread(cars[i]);
               myThreads1[i].start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Ждем когда все финишируют
        try {
            for (Thread thread1 : myThreads1) {
                thread1.join();
            }
        }
        catch (InterruptedException e){
            e.getStackTrace();
        }

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}


