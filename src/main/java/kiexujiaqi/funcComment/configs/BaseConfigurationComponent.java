package kiexujiaqi.funcComment.configs;

import com.intellij.ui.components.JBCheckBox;
import com.intellij.util.ui.FormBuilder;
import lombok.Data;

import javax.swing.*;
import java.awt.*;

@Data
public class BaseConfigurationComponent {

    private JPanel basePanel;

    // 标题
    private JLabel title = new JLabel(BaseConfigurable.CONFIG_NAME);

    // 选项
    private final JBCheckBox printFuncName = new JBCheckBox("Print function name");
    private final JBCheckBox printParams = new JBCheckBox("Print param");
    private final JBCheckBox printReturns = new JBCheckBox("Print return value");

    public BaseConfigurationComponent() {
        InitUI();
    }

    private void InitUI() {
        final FormBuilder builder = FormBuilder.createFormBuilder();
        // todo
        builder.addComponent(createTitlePanel());
        builder.addVerticalGap(2);
        builder.addSeparator();
        builder.addComponent(createPrintPanel());
        basePanel = builder.getPanel();
    }

    public void updateUI(final BaseState state) {
        if (state == null) {
            return;
        }
        printFuncName.setSelected(state.isPrintFuncName());
        printParams.setSelected(state.isPrintParams());
        printReturns.setSelected(state.isPrintReturns());
    }

    public JComponent getPanel() {
        return basePanel;
    }

    // 标题栏
    private JPanel createTitlePanel() {
        final JPanel panel = new JPanel(new GridLayout(1, 1));
        title.setFont(title.getFont().deriveFont(Font.BOLD));
        panel.add(title);
        return panel;
    }

    // 单选栏
    private JPanel createPrintPanel() {

        printFuncName.setToolTipText("Print the function name if checked");
        printParams.setToolTipText("Print the param(s) if checked");
        printReturns.setToolTipText("Print the return value(s) if checked");

        final JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(printFuncName);
        panel.add(printParams);
        panel.add(printReturns);

        return panel;
    }
}
