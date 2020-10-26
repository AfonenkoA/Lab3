import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class GornerTableCellRenderer implements TableCellRenderer
{
    private final JPanel panel = new JPanel();
    private final JLabel label = new JLabel();
    // Ищем ячейки, строковое представление которых совпадает с needle
    // (иголкой). Применяется аналогия поиска иголки в стоге сена, в роли
    // стога сена - таблица
    private String needle = null;
    private String rangeFrom = null;
    private String rangeTo = null;
    private Double rangeFromD = null;
    private Double rangeToD = null;
    private final DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance();

    public GornerTableCellRenderer()
    {
        // Показывать только 5 знаков после запятой
        formatter.setMaximumFractionDigits(5);
        // Не использовать группировку (т.е. не отделять тысячи
        // ни запятыми, ни пробелами), т.е. показывать число как "1000",
        // а не "1 000" или "1,000"
        formatter.setGroupingUsed(false);
        // Установить в качестве разделителя дробной части точку, а не
        // запятую. По умолчанию, в региональных настройках
        // Россия/Беларусь дробная часть отделяется запятой
        DecimalFormatSymbols dottedDouble = formatter.getDecimalFormatSymbols();
        dottedDouble.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(dottedDouble);
        // Разместить надпись внутри панели
        panel.add(label);
        // Установить выравнивание надписи по левому краю панели
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int col)
    {
        // Преобразовать double в строку с помощью форматировщика
        String formattedDouble = formatter.format(value);
        // Установить текст надписи равным строковому представлению числа
        label.setText(formattedDouble);
        if((col+row)%2==0)
        {
            panel.setBackground(Color.BLACK);
            label.setForeground(Color.WHITE);
        }
        else
        {
            panel.setBackground(Color.WHITE);
            label.setForeground(Color.BLACK);
        }
        if (rangeFromD == null && rangeFrom != null)
        {
            rangeFromD = Double.parseDouble(rangeFrom);
        }
        if (rangeToD == null && rangeTo != null)
        {
            rangeToD = Double.parseDouble(rangeTo);
        }
        if (col == 1 && needle != null && needle.equals(formattedDouble))
        {
            // Номер столбца = 1 (т.е. второй столбец) + иголка не null
            // (значит что-то ищем) +
            // значение иголки совпадает со значением ячейки таблицы -
            // окрасить задний фон панели в красный цвет
            panel.setBackground(Color.RED);
            label.setForeground(Color.WHITE);
        }
        if(col == 1 && rangeFromD!=null && rangeToD!= null)
        {
            Double v = (Double) value;
            if ( rangeFromD <= v && v <= rangeToD)
            {
                panel.setBackground(Color.GREEN);
                label.setForeground(Color.RED);
            }
        }
        return panel;
    }
    public void setRangeFrom(String rangeFrom)
    {
        this.rangeFrom =  rangeFrom;
        this.rangeFromD = null;
    }
    public void setRangeTo(String rangeTo)
    {
        this.rangeTo = rangeTo;
        this.rangeToD = null;
    }
    public void setNeedle(String needle)
    {
        this.needle = needle;
    }
}
