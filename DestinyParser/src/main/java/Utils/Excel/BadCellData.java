package Utils.Excel;

/**
 * @author by Ilin_ai on 02.10.2017.
 */
public class BadCellData extends ExcelBadData {

    public BadCellData(int i){
        super("Указано некорректное значение в ячейке " + i);
    }
}
