import javafx.util.Pair;

public abstract class Species {
    protected Integer id;
    protected Position pos;
    protected Position target;
    protected Integer typeOfTarget;
    protected Integer energy;
    protected Integer criticalEnergy;
    protected Integer amountOfEnergyForReproduction;
    protected Integer lifetimeTacts;
    protected Integer state;
    protected Integer currentAge;
    protected Integer  maxEnergy;
    protected Boolean causeOfdeath; // cause of death: 0 - from age, 1 - from hunger

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public Position getTarget() {
        return target;
    }

    public void setTarget(Position target) {
        this.target = target;
    }

    public Integer getTypeOfTarget() {
        return typeOfTarget;
    }

    public void setTypeOfTarget(Integer typeOfTarget) {
        this.typeOfTarget = typeOfTarget;
    }

    public Integer getEnergy() {
        return energy;
    }

    public void setEnergy(Integer energy) {
        this.energy = energy;
    }

    public Integer getCriticalEnergy() {
        return criticalEnergy;
    }

    public void setCriticalEnergy(Integer criticalEnergy) {
        this.criticalEnergy = criticalEnergy;
    }

    public Integer getAmountOfEnergyForReproduction() {
        return amountOfEnergyForReproduction;
    }

    public void setAmountOfEnergyForReproduction(Integer amountOfEnergyForReproduction) {
        this.amountOfEnergyForReproduction = amountOfEnergyForReproduction;
    }

    public Integer getLifetimeTacts() {
        return lifetimeTacts;
    }

    public void setLifetimeTacts(Integer lifetimeTacts) {
        this.lifetimeTacts = lifetimeTacts;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getCurrentAge() {
        return currentAge;
    }

    public void setCurrentAge(Integer currentAge) {
        this.currentAge = currentAge;
    }

    public Integer getMaxEnergy() {
        return maxEnergy;
    }

    public void setMaxEnergy(Integer maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public void act() {
        if (this.currentAge >= this.lifetimeTacts) {
            this.dieFrom(false);
        }
        else if (this.energy < this.criticalEnergy) {
            this.dieFrom(true);
        }
        else if (this.energy < this.amountOfEnergyForReproduction) {
            this.typeOfTarget = 0;
        }
        else if (this.energy < this.maxEnergy) {
            this.typeOfTarget = 1;
        }
        else {
            this.typeOfTarget = 2;
        }
        this.searchTarget(typeOfTarget);
    }

    public abstract void move();

    public abstract void doStep();

    public abstract void searchTarget(Integer targetType);

    public void createOffspring() {

    }

    public void dieFrom(boolean death) {
        this.causeOfdeath = death;
        this.state = 0;
        this.currentAge = 0;
        this.energy = 0;
        this.pos.setX(0);
        this.pos.setY(0);
    }
}
