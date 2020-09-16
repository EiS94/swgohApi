package backend;

import java.util.List;

public class Fleet {

    private Ship capitalShip;
    private List<Ship> startingShips;
    private List<Ship> reinforcment;
    private int gp;
    //TODO arena rank

    public Fleet(Ship capitalShip, List<Ship> startingShips, List<Ship> reinforcment) {
        this.capitalShip = capitalShip;
        this.startingShips = startingShips;
        this.reinforcment = reinforcment;
    }

    public Fleet(Ship capitalShip, List<Ship> startingShips, List<Ship> reinforcment, int gp) {
        this.capitalShip = capitalShip;
        this.startingShips = startingShips;
        this.reinforcment = reinforcment;
        this.gp = gp;
    }

    public Ship getCapitalShip() {
        return capitalShip;
    }

    public void setCapitalShip(Ship capitalShip) {
        this.capitalShip = capitalShip;
    }

    public List<Ship> getStartingShips() {
        return startingShips;
    }

    public void setStartingShips(List<Ship> startingShips) {
        this.startingShips = startingShips;
    }

    public List<Ship> getReinforcment() {
        return reinforcment;
    }

    public void setReinforcment(List<Ship> reinforcment) {
        this.reinforcment = reinforcment;
    }

    public int getGp() {
        return gp;
    }

    public void setGp(int gp) {
        this.gp = gp;
    }

    public String getFullInfo() {
        StringBuilder result = new StringBuilder("GP: " + gp + "\nCapital backend.Ship: " + capitalShip + "\nStarting Ships:\n");
        for (Ship s : startingShips) {
            result.append(s).append("\n");
        }
        result.append("Reinforcment:\n");
        for (Ship s : reinforcment) {
            result.append(s).append("\n");
        }
        return result.toString();
    }

    @Override
    public String toString() {
        return "Capital backend.Ship: " + capitalShip + " Reinforcment count: " + reinforcment.size() + "\nGP: " + gp;
    }
}
