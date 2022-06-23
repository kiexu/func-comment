package kiexujiaqi.funcComment.configs;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Referenced 'Pokemon Progress',my favorite plugin.
 */
public class BaseConfigurable implements Configurable {

    public static final String CONFIG_NAME = "Func Comment Plugin";

    private BaseConfigurationComponent comp;

    @Nls
    @Override
    public String getDisplayName() {
        return CONFIG_NAME;
    }

    @Override
    public @Nullable
    JComponent createComponent() {
        comp = new BaseConfigurationComponent();
        return comp.getPanel();
    }

    @Override
    public boolean isModified() {
        if (comp == null) {
            return false;
        }
        final BaseState state = BaseState.getInstance();
        return comp.getPrintFuncName().isSelected() != state.isPrintFuncName()
                || comp.getPrintParams().isSelected() != state.isPrintParams()
                || comp.getPrintReturns().isSelected() != state.isPrintReturnSymbol()
                || comp.getShowNotice().isSelected() != state.isConfigNotice();
    }

    @Override
    public void apply() throws ConfigurationException {
        if (comp == null) {
            return;
        }
        final BaseState state = BaseState.getInstance();
        state.setPrintFuncName(comp.getPrintFuncName().isSelected());
        state.setPrintParams(comp.getPrintParams().isSelected());
        state.setPrintReturnSymbol(comp.getPrintReturns().isSelected());
        state.setConfigNotice(comp.getShowNotice().isSelected());
    }

    @Override
    public void reset() {
        if (comp == null) {
            return;
        }
        final BaseState state = BaseState.getInstance();
        comp.updateUI(state);
    }

    @Override
    public void disposeUIResources() {
        comp = null;
    }
}
