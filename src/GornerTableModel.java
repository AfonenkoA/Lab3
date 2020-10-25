import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class GornerTableModel extends AbstractTableModel
{
    private final Double[] coefficients;
    private final Double from;
    private final Double to;
    private final Double step;

    public GornerTableModel(Double from, Double to, Double step, Double[] coefficients)
    {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
    }

    public Double getFrom()
    {
        return from;
    }

    public Double getTo()
    {
        return to;
    }

    public Double getStep()
    {
        return step;
    }

    public int getColumnCount()
    {
        // В данной модели два столбца
        return 2;
    }

    public int getRowCount()
    {
        // Вычислить количество точек между началом и концом отрезка
        // исходя из шага табулирования
        return ((Double) Math.ceil((to - from) / step)).intValue() + 1;
    }

    public Object getValueAt(int row, int col)
    {
        // Вычислить значение X как НАЧАЛО_ОТРЕЗКА + ШАГ*НОМЕР_СТРОКИ
        double x = from + step * row;
        if (col == 0)
        {
            // Если запрашивается значение 1-го столбца, то это X
            return x;
        }
        else
        {
            // Если запрашивается значение 2-го столбца, то это значение многочлена
            // Вычисление значения в точке по схеме Горнера.
            // Вспомнить 1-ый курс и реализовать
            // ...
            Double result = coefficients[0];
            for(int i = 1; i < coefficients.length; i++)
            {
                result *= x;
                result += coefficients[i];
            }
            return result;
        }
    }

    public String getColumnName(int col)
    {
        switch (col)
        {
            case 0:
                // Название 1-го столбца
                return "Значение X";
            default:
                // Название 2-го столбца
                return "Значение многочлена";
        }
    }

    public Class<?> getColumnClass(int col)
    {
        // И в 1-ом и во 2-ом столбце находятся значения типа Double
        return Double.class;
    }
}
