package kiexujiaqi.funcComment.extensions.base;

import com.intellij.codeInsight.editorActions.enter.EnterHandlerDelegateAdapter;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * 过滤非指定文件
 */
public class BaseEnterHandlerDelegate extends EnterHandlerDelegateAdapter {

    /**
     * 前置检查是否可以继续流程
     *
     * @param file    file
     * @param editor  editor
     * @param extName 指定扩展名
     * @return 是否可继续
     */
    protected boolean checkContinue(@NotNull PsiFile file, @NotNull Editor editor, String extName) {
        if (StringUtils.isBlank(extName)) {
            return false;
        }
        // 只处理指定扩展名的文件
        VirtualFile virtualFile = file.getVirtualFile();
        if (virtualFile == null || !extName.equals(virtualFile.getExtension())) {
            return false;
        }

        // 多光标时直接返回
        CaretModel caretModel = editor.getCaretModel();
        if (caretModel.getCaretCount() > 1) {
            return false;
        }

        return true;
    }
}
