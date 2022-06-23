package kiexujiaqi.funcComment.configs;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Data
@State(
        name = "kiexujiaqi.funcComment.configs.BaseState",
        storages = {@Storage("FuncComment.xml")}
)
public class BaseState implements PersistentStateComponent<BaseState> {

    // 打印方法名
    private boolean printFuncName = true;

    // 打印参数列表
    private boolean printParams = true;

    // 打印返回标记
    private boolean printReturnSymbol = true;

    public static BaseState getInstance() {
        return ApplicationManager.getApplication().getService(BaseState.class);
    }

    @Override
    public @Nullable
    BaseState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull BaseState baseState) {
        XmlSerializerUtil.copyBean(baseState, this);
    }

    public boolean allBlocked() {
        return !(printFuncName || printParams || printReturnSymbol);
    }
}
