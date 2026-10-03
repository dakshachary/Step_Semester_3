public class Pruner extends CuttingTool {
    public Pruner() {
        super();
    }

    @Override
    public String use() {
        return super.use() + ", then trimming branches precisely";
    }
}
