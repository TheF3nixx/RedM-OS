package traces;

public abstract class TracePattern {
    protected String patternName;

    public TracePattern(String patternName) {
        this.patternName = patternName;
    }

    public String getPatternName() {
        return patternName;
    }

    @Override
    public String toString() {
        return patternName;
    }

}

