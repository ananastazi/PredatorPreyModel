import java.util.List;

public class Emulation {
    private List<List<Cell>> playField;
    private List<Species> predators;
    private List<Species> preys;
    private List<Grass> grass;

    public Emulation(List<List<Cell>> playField, List<Species> predators, List<Species> preys, List<Grass> grass) {
        this.playField = playField;
        this.predators = predators;
        this.preys = preys;
        this.grass = grass;
    }

    public List<List<Cell>> getPlayField() {
        return playField;
    }

    public void setPlayField(List<List<Cell>> playField) {
        this.playField = playField;
    }

    public List<Species> getPredators() {
        return predators;
    }

    public void setPredators(List<Species> predators) {
        this.predators = predators;
    }

    public List<Species> getPreys() {
        return preys;
    }

    public void setPreys(List<Species> preys) {
        this.preys = preys;
    }

    public List<Grass> getGrass() {
        return grass;
    }

    public void setGrass(List<Grass> grass) {
        this.grass = grass;
    }

    void individualsFieldPlacing() {

    }

    void performTact() {

    }
}
