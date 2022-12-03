import java.util.concurrent.Semaphore;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private Race race;
    private int speed;
    private String name;
    private Semaphore semaphore;
    private Semaphore semaphore1;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, Semaphore semaphore, Semaphore semaphor1) {
        this.race = race;
        this.speed = speed;
        this.semaphore = semaphore;
        this.semaphore1 = semaphor1;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {

        for (int i = 0; i < race.getStages().size(); i++) {

            race.getStages().get(i).go(this, semaphore);

        }
        // Ловим победителя и остальных финишеров
        if (semaphore1.availablePermits() == 4) {
            try {
                semaphore1.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Пришел первым: " + name);
        }
        else {
            System.out.println("Финишировал: " + name);
        }
    }
}
