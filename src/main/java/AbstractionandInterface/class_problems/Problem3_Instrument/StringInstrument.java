public class StringInstrument extends Instrument {
    public StringInstrument() {
        super();
    }

    @Override
    public String play() {
        return super.play() + " the strings";
    }
}
