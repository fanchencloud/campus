package cn.fanchencloud.campus.model;

import java.io.InputStream;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/3/30
 * Time: 1:48
 * Description: 封装文件信息
 *
 * @author chen
 */
public class FileContainer {

    /**
     * 文件流
     */
    private InputStream fileInputStream;
    /**
     * 文件名
     */
    private String fileName;

    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(InputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
