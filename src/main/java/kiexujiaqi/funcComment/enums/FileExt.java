package kiexujiaqi.funcComment.enums;

public enum FileExt {

    GO("go");

    private String extName;

    FileExt(String extName) {
        this.extName = extName;
    }

    public String getExtName() {
        return extName;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }
}
