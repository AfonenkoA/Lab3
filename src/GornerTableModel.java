import javax.swing.table.AbstractTableModel;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("serial")
public class GornerTableModel extends AbstractTableModel
{
    private final Double[] coefficients;
    private final Double[] revcoefficients;
    private final Double from;
    private final Double to;
    private final Double step;
    private Double gornerCalc(Double[] coefficients,Double x)
    {
        Double result = coefficients[0];
        for(int i = 1; i < coefficients.length; i++)
        {
            result *= x;
            result += coefficients[i];
        }
        return result;
    }
    public GornerTableModel(Double from, Double to, Double step, Double[] coefficients)
    {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
        List<Double> revcoef = Arrays.asList(coefficients.clone());
        Collections.reverse(revcoef);
        this.revcoefficients = revcoef.toArray(new Double[revcoef.size()]);
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
        return 4;
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
        switch (col)
        {
            case 0 -> {return x;}
            case 1 -> {return gornerCalc(coefficients,x);}
            case 2 -> {return gornerCalc(revcoefficients,x);}
            case 3 -> {return gornerCalc(coefficients,x) - gornerCalc(revcoefficients,x);}
        }
        return null;
    }

    public String getColumnName(int col)
    {
        switch (col)
        {
            case 0 -> {return "Значение X";}
            case 1 -> {return "Значение многочлена";}
            case 2 -> {return "Обратные коэффиценты";}
            case 3 -> {return "Разность";}
        }
        return null;
    }

    public Class<?> getColumnClass(int col)
    {
        // во всех столбцах находятся значения типа Double
        return Double.class;
    }
}
