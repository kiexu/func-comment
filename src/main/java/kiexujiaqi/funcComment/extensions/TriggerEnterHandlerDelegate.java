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
import kiexujiaqi.funcComment.extensions.service.TriggerEnterHandlerDelegateService;
import kiexujiaqi.funcComment.utils.DocumentUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TriggerEnterHandlerDelegate extends BaseEnterHandlerDelegate {

    private static final Logger LOG = Logger.getInstance(TriggerEnterHandlerDelegate.class);

    @Override
    public Result preprocessEnter(@NotNull PsiFile file, @NotNull Editor editor, @NotNull Ref<Integer> caretOffset, @NotNull Ref<Integer> caretAdvance, @NotNull DataContext dataContext, EditorActionHandler originalHandler) {

        // 前置检查
        if (!checkContinue(file, editor, FileExt.GO.getExtName())) {
            return Result.Continue;
        }

        // 获取待检查字符
        String suspectText = DocumentUtil.getLineWhilePreprocessEnter(editor, 0);

        // 检查触发字符
        if (!CommentSign.PREFIX.getText().equals(suspectText)) {
            return Result.Continue;
        }

        // 检查是否紧贴func定义, 要求缩进对齐
        String declText = DocumentUtil.getLineWhilePreprocessEnter(editor, 1);
        List<String> commentList = TriggerEnterHandlerDelegateService.buildCommentLines(declText);

        // 若commentList为空, 继续流程
        if (commentList.size() == 0) {
            return Result.Continue;
        }

        // 写入注释块
        Document document = editor.getDocument();
        CaretModel caretModel = editor.getCaretModel();
        int writeOffset = document.getLineStartOffset(caretModel.getLogicalPosition().line + 1);
        Runnable runnable = () -> {
            // 倒序写入
            for (int i = commentList.size() - 1; i >= 0; i -= 1) {
                document.insertString(writeOffset, commentList.get(i) + "\n");
            }
            // 光标移动
            caretModel.moveToOffset(writeOffset + CommentSign.BODY.getText().length());
        };
        WriteCommandAction.runWriteCommandAction(file.getProject(), runnable);
        return Result.Stop;
    }
}
