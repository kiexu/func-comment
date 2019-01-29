package kiexujiaqi.funcComment.utils;

import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.TextRange;
import org.jetbrains.annotations.NotNull;

public class DocumentUtil {

    /**
     * 回车事件前触发获取指定行
     *
     * @param editor editor
     * @param offset 当前行=0
     * @return 指定行
     */
    public static String getLineWhilePreprocessEnter(@NotNull Editor editor, int offset) {
        CaretModel caretModel = editor.getCaretModel();
        int targetLine = caretModel.getLogicalPosition().line + offset;
        final Document document = editor.getDocument();
        return getText(document, targetLine);
    }

    /**
     * 获取document的指定行
     *
     * @param document document
     * @param lineIndex lineIndex
     * @return 指定行
     */
    public static String getText(Document document, int lineIndex) {
        if (lineIndex < 0 || lineIndex > document.getLineCount() - 1) {
            return "";
        }
        int startOffset = document.getLineStartOffset(lineIndex);
        int endOffset = document.getLineEndOffset(lineIndex);
        TextRange range = new TextRange(startOffset, endOffset);
        return document.getText(range);
    }
}
