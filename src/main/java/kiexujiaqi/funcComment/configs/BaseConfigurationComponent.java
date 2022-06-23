package kiexujiaqi.funcComment.configs;

import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.util.ui.FormBuilder;
import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Data
public class BaseConfigurationComponent {

    private JPanel basePanel;

    // 标题
    private JLabel title = new JLabel(BaseConfigurable.CONFIG_NAME);

    // 选项
    private final JBCheckBox printFuncName = new JBCheckBox("Print function name");
    private final JBCheckBox printParams = new JBCheckBox("Print param");
    private final JBCheckBox printReturns = new JBCheckBox("Print return symbol");

    private final JBCheckBox showNotice = new JBCheckBox("Show config notice on IDE startup");

    // 警告文字
    private final JLabel uncheckAllWarning = new JLabel("Func Comment will not work due to unchecking all");

    // ActionListener
    private final ActionListener allBlockedListener = a -> {
        if (a != null && a.getID() == ActionEvent.ACTION_PERFORMED) {
            uncheckAllWarning.setVisible(!(printFuncName.isSelected() || printParams.isSelected() || printReturns.isSelected()));
        }
    };

    public BaseConfigurationComponent() {
        InitUI();
    }

    private void InitUI() {
        final FormBuilder builder = FormBuilder.createFormBuilder();
        builder.addComponent(createTitlePanel());
        builder.addSeparator();
        builder.addComponent(createPrintPanel());
        builder.addSeparator();
        builder.addComponent(createNoticePanel());
        builder.addVerticalGap(1);
        builder.addComponent(createWarningPanel());
        basePanel = new JPanel(new BorderLayout());
        basePanel.add(builder.getPanel(), BorderLayout.NORTH);
    }

    public void updateUI(final BaseState state) {
        if (state == null) {
            return;
        }
        printFuncName.setSelected(state.isPrintFuncName());
        printParams.setSelected(state.isPrintParams());
        printReturns.setSelected(state.isPrintReturnSymbol());
        showNotice.setSelected(state.isConfigNotice());
        uncheckAllWarning.setVisible(state.allBlocked());
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
        printReturns.setToolTipText("Print the return symbol if checked");

        printFuncName.addActionListener(allBlockedListener);
        printParams.addActionListener(allBlockedListener);
        printReturns.addActionListener(allBlockedListener);

        final JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(printFuncName);
        panel.add(printParams);
        panel.add(printReturns);

        return panel;
    }

    // 提醒重开
    private JPanel createNoticePanel() {

        showNotice.setToolTipText("Show config sticky balloon on startup if checked");

        final JPanel panel = new JPanel(new GridLayout(1, 1));
        panel.add(showNotice);

        return panel;
    }

    // 警告栏
    private JLabel createWarningPanel() {
        uncheckAllWarning.setFont(uncheckAllWarning.getFont().deriveFont(Font.BOLD));
        uncheckAllWarning.setForeground(JBColor.RED);
        return uncheckAllWarning;
    }
}
