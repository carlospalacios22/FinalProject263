public class Player {
    private String name;
    private int money;

    public Player(String name) {
        this.name = name;
        this.money = 1000;
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int amount) {
        this.money += amount;
    }
}
