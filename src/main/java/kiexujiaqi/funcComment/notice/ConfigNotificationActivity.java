package kiexujiaqi.funcComment.notice;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import kiexujiaqi.funcComment.configs.BaseConfigurable;
import kiexujiaqi.funcComment.configs.BaseState;
import org.jetbrains.annotations.NotNull;

/**
 * 前往配置提醒
 */
public class ConfigNotificationActivity implements StartupActivity.DumbAware {

    private static final String CONFIG_GROUP_ID = "Func Comment config notice";

    @Override
    public void runActivity(@NotNull Project project) {
        // 全部关闭时为最高优先级提示
        if (BaseState.getInstance().allBlocked()) {
            Notification blockNotice = NotificationGroupManager.getInstance().getNotificationGroup(CONFIG_GROUP_ID)
                    .createNotification("Func Comment will not work due to unchecking all printing parts. " +
                                    "recommend change configuration."
                            , NotificationType.INFORMATION);
            blockNotice.addAction(new DumbAwareAction("Go To Configuration...") {
                        @Override
                        public void actionPerformed(@NotNull final AnActionEvent e) {
                            ShowSettingsUtil.getInstance().showSettingsDialog(project,
                                    BaseConfigurable.class);
                        }
                    })
                    .addAction(new DumbAwareAction("Let It Go", "Close this notification but see you after next startup", null) {
                        @Override
                        public void actionPerformed(@NotNull final AnActionEvent e) {
                            blockNotice.expire();
                        }
                    })
                    .notify(project);
            return;
        }
        if (!BaseState.getInstance().isConfigNotice()) {
            return;
        }
        Notification configNotice = NotificationGroupManager.getInstance().getNotificationGroup(CONFIG_GROUP_ID)
                .createNotification("Welcome to Func Comment. you can separately configure whether " +
                                "func name, params, return symbol will be printed in auto-generated comment."
                        , NotificationType.INFORMATION);
        configNotice.addAction(new DumbAwareAction("Go To Configuration...") {
                    @Override
                    public void actionPerformed(@NotNull final AnActionEvent e) {
                        ShowSettingsUtil.getInstance().showSettingsDialog(project,
                                BaseConfigurable.class);
                    }
                })
                .addAction(new DumbAwareAction("Don't Show Again", "I don't want to see this notification in the future", null) {
                    @Override
                    public void actionPerformed(@NotNull final AnActionEvent e) {
                        BaseState.getInstance().setConfigNotice(false);
                        configNotice.expire();
                    }
                })
                .notify(project);
    }


}
