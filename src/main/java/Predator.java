public class Predator extends Species {
    @Override
    public  void move() {


    }

    @Override
    public void doStep() {
        this.move();

        if (this.pos == this.target) {
            if (this.typeOfTarget == 0) {
                this.eatPrey();
            } else {
                this.createOffspring();
            }
        }
    }

    @Override
    public void searchTarget(Integer targetType) {

    }

    public void eatPrey() {

    }

}
