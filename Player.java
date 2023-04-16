



public class Player {
    private String name; // The name of the player
    private int money; // The amount of money the player has

    // Constructor that initializes the player's name and sets their starting money to 1000
    public Player(String name) {
        this.name = name;
        this.money = 1000;
    }

    // Getter method to return the player's name
    public String getName() {
        return name;
    }

    // Getter method to return the player's money
    public int getMoney() {
        return money;
    }

    // Method to add a specified amount of money to the player's current money
    public void addMoney(int amount) {
        this.money += amount;
    }

    // Method to subtract a specified amount of money from the player's current money
    public void subtractMoney(int amount) {
        this.money -= amount;
    }
}
