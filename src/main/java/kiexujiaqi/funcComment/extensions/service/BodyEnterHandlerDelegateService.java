package kiexujiaqi.funcComment.extensions.service;

import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import kiexujiaqi.funcComment.constants.CommentSign;
import kiexujiaqi.funcComment.utils.DocumentUtil;
import kiexujiaqi.funcComment.utils.StringUtil;
import org.jetbrains.annotations.NotNull;

public class BodyEnterHandlerDelegateService {

    /**
     * 检查当前行是否位于注释块内
     *
     * @param editor editor
     * @return 是否位于注释块内
     */
    public static boolean inCommentBlock(@NotNull Editor editor) {

        Document document = editor.getDocument();
        CaretModel caretModel = editor.getCaretModel();
        int maxLineIndex = document.getLineCount() - 1;
        int startLine = caretModel.getLogicalPosition().line;

        // 检查注释块前缀
        int currentLine = startLine;
        while (currentLine > 0) {
            String line = DocumentUtil.getText(document, currentLine);
            if (!StringUtil.isCommentBody(line)) {
                break;
            }
            currentLine -= 1;
        }
        if (!CommentSign.PREFIX.getText().equals(DocumentUtil.getText(document, currentLine))) {
            return false;
        }

        // 检查注释块后缀
        currentLine = startLine;
        while (currentLine < maxLineIndex) {
            String line = DocumentUtil.getText(document, currentLine);
            if (!StringUtil.isCommentBody(line)) {
                break;
            }
            currentLine += 1;
        }
        // 由于" */"同样匹配注释格式, 此时应检查currentLine - 1
        if (!CommentSign.BOTTOM.getText().equals(DocumentUtil.getText(document, currentLine - 1))) {
            return false;
        }

        return true;
    }
}
