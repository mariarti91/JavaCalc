import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainDialog extends JDialog {
    private JPanel contentPane;
    private JTextField tfStatement;
    private JButton bCalc;

    public MainDialog() {
        setContentPane(contentPane);
        setModal(true);

        bCalc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { calculate(); }
        });
    }

    private void calculate()
    {
        String statement = tfStatement.getText();
        try
        {
            double result = MathParser.Calculate(statement);
            tfStatement.setText(String.valueOf(result));
        }
        catch (BaseParser.UnexpectedTokenException ex)
        {
            tfStatement.setText(ex.getMessage());
        }
    }
}
