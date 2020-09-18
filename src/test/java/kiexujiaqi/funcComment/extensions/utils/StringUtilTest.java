package kiexujiaqi.funcComment.extensions.utils;

import kiexujiaqi.funcComment.beans.FuncDeclInfo;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import kiexujiaqi.funcComment.constants.RegexSign;
import kiexujiaqi.funcComment.utils.StringUtil;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

public class StringUtilTest {

    @Test
    public void hasSuffix() {
        String suffix = "/**";
        List<String> inputList = Lists.newArrayList(
                "/**",
                "     /**",
                "/   /**",
                "/**     ",
                "  /**",
                "",
                "abc",
                ""
        );
        for (String input : inputList) {
            boolean hasSuffix = StringUtil.hasSuffix(input, suffix);
            System.out.println(String.format("input=%s||res=%b", input, hasSuffix));
        }
    }


    private static List<String> inputList = Lists.newArrayList(
            "func (s *CouponSvr) SendCouponSync(traceInfo coupon.Trace, uid int64, phone string, couponInfo *coupon.CouponInfo) {",
            "func Add(uid int64) {",
            "func Add(uid int64) int {",
            "func Add() int {",
            "func Add() (i int) {",
            "func Add(uid int64) (i int) {",
            "func Add(uid int64) (i int, j string) {",
            "func Add(uid int64) (int, string) {",
            "func Add(    uid     int64     ) {",
            "func Add() {",
            "func Add(   ) {",
            "func Add",
            ""
    );

    @Test
    public void parseFuncDecl() {
        for (String input : inputList) {
            Matcher matcher = StringUtil.matchDeclStr(input);
            String p = "";
            String r = "";
            if (matcher.matches()) {
                p = matcher.group(RegexSign.PARAM_GROUP.getText());
                r = matcher.group(RegexSign.RETURN_GROUP.getText());
            } else {
                p = "nil";
                r = "nil";
            }
            System.out.println(String.format("input=%s||paramStr=%s||returnStr=%s", input, p, r));
        }
    }

    @Test
    public void parseDeclStr() {
        for (String input : inputList) {
            Optional<FuncDeclInfo> opt = StringUtil.parseDeclStr(input);
            if (opt.isPresent()) {
                FuncDeclInfo info = opt.get();
                System.out.println(String.format("input=%s||paramStr=%s||returnStr=%s", input,
                        Joiner.on(",").join(info.getParamInfoList()),
                        Joiner.on(",").join(info.getReturnInfoList())
                ));
            } else {
                System.out.println(String.format("input=%s||paramStr=%s||returnStr=%s", input, "nil", "nil"));
            }

        }
    }
}