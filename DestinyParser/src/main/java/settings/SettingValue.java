package settings;

/**
 * @author by Ilin_ai on 07.10.2017.
 */
public class SettingValue {
    private String russianName;
    private Datatype datatype;
    private AggregateOperation aggregateOperation;

    public String getRussianName() {
        return russianName;
    }

    public void setRussianName(String russianName) {
        this.russianName = russianName;
    }

    public Datatype getDatatype() {
        return datatype;
    }

    public void setDatatype(Datatype datatype) {
        this.datatype = datatype;
    }

    public AggregateOperation getAggregateOperation() {
        return aggregateOperation;
    }

    public void setAggregateOperation(AggregateOperation aggregateOperation) {
        this.aggregateOperation = aggregateOperation;
    }
}