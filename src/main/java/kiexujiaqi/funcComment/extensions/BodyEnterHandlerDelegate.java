package kiexujiaqi.funcComment.extensions;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.util.Ref;
import com.intellij.psi.PsiFile;
import kiexujiaqi.funcComment.enums.FileExt;
import kiexujiaqi.funcComment.constants.CommentSign;
import kiexujiaqi.funcComment.extensions.base.BaseEnterHandlerDelegate;
import kiexujiaqi.funcComment.extensions.service.BodyEnterHandlerDelegateService;
import kiexujiaqi.funcComment.utils.DocumentUtil;
import kiexujiaqi.funcComment.utils.StringUtil;
import org.jetbrains.annotations.NotNull;

/**
 * 用于注释内部换行自动添加注释体前缀
 *
 * @deprecated 升级为斜杠注释后无需此功能
 */
public class BodyEnterHandlerDelegate extends BaseEnterHandlerDelegate {

    private static final Logger LOG = Logger.getInstance(BodyEnterHandlerDelegate.class);

    @Override
    public Result preprocessEnter(@NotNull PsiFile file, @NotNull Editor editor, @NotNull Ref<Integer> caretOffset, @NotNull Ref<Integer> caretAdvance, @NotNull DataContext dataContext, EditorActionHandler originalHandler) {

        // 前置检查
        if (!checkContinue(file, editor, FileExt.GO.getExtName())) {
            return Result.Continue;
        }

        // 获取并检查当前行字符
        String suspectText = DocumentUtil.getLineWhilePreprocessEnter(editor, 0);
        if (CommentSign.BOTTOM.getText().equals(suspectText) || !StringUtil.isCommentBody(suspectText)) {
            return Result.Continue;
        }

        // 遍历检查是否在注释块内
        if (!BodyEnterHandlerDelegateService.inCommentBlock(editor)) {
            return Result.Continue;
        }

        // 获取当前行
        // 写入注释块
        Document document = editor.getDocument();
        CaretModel caretModel = editor.getCaretModel();
        int writeOffset = document.getLineStartOffset(caretModel.getLogicalPosition().line + 1);
        Runnable runnable = () -> {
            document.insertString(writeOffset, CommentSign.BODY.getText() + "\n");
            // 光标移动
            caretModel.moveToOffset(writeOffset + CommentSign.BODY.getText().length());
        };
        WriteCommandAction.runWriteCommandAction(file.getProject(), runnable);

        return Result.Stop;
    }
}
