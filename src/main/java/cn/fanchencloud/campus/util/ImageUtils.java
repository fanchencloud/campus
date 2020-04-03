package cn.fanchencloud.campus.util;

import cn.fanchencloud.campus.model.FileContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Random;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/3/12
 * Time: 17:12
 * Description: 图片工具类
 *
 * @author chen
 */
public class ImageUtils {

    private static final Logger logger = LoggerFactory.getLogger(ImageUtils.class);

    private static final Random RANDOM = new Random();

    private static final String basePath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();

    /**
     * 保存图片的缩略图并返回存储路径
     *
     * @param fileInputStream 上传的文件流
     * @param targetPath      shop图片目录的相对路径
     * @param fileName        上传的文件名字
     * @return 存储真实路径
     * @throws IOException IO异常
     */
    public static String generateThumbnail(InputStream fileInputStream, String targetPath, String fileName) throws IOException {
        FileContainer fileContainer = new FileContainer();
        fileContainer.setFileInputStream(fileInputStream);
        fileContainer.setFileName(fileName);
        return savePicture(fileContainer, targetPath);
    }

    /**
     * 创建目标路径涉及到的目录，即 /home/chen/images/xxx.jpg
     * 那么 home chen images 这几个文件夹都需要自动创建
     *
     * @param targetPath 目标路径
     */
    private static void makeFolder(String targetPath) {
        String realFileParentPath = PathUtils.getImageBasePath() + targetPath;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()) {
            if (!dirPath.mkdirs()) {
                logger.error("目录创建失败！" + targetPath);
            }
        }
    }

    /**
     * inputStream转outputStream
     *
     * @param in 文件树立刘
     * @return 文件输出流
     * @throws IOException IO异常
     */
    public static ByteArrayOutputStream inputStreamToOutputStream(final InputStream in) throws IOException {
        final ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        int ch;
        while ((ch = in.read()) != -1) {
            swapStream.write(ch);
        }
        return swapStream;
    }

    /**
     * 根据商品详情图片路径删除商品详情图片
     *
     * @param path 商品详情图片
     */
    public static boolean deleteProductImage(String path) {
        String filePath = PathUtils.getImageBasePath() + path;
        File file = new File(filePath);
        return file.delete();
    }

    /**
     * 根据商品缩略图片路径删除商品缩略图片
     *
     * @param path 商品缩略图的路径
     */
    public static boolean deleteProductThumbnail(String path) {
        String filePath = PathUtils.getImageBasePath() + path;
        File file = new File(filePath);
        return file.delete();
    }

    /**
     * 获取输入文件名字的拓展名
     *
     * @param fileName 输入的文件名字
     * @return 文件拓展名
     */
    private static String getExtendedName(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成随机文件名，当前的时间戳 + 五位随机数
     *
     * @return 随机文件名
     */
    private static String getRandomFileName() {
        // 获取随机的五位数
        int number = RANDOM.nextInt(89999) + 10000;
        return System.currentTimeMillis() + "" + number;
    }

    /**
     * 删除店铺缩略图图片
     *
     * @param shopImg 图片路径
     */
    public static void deleteShopImage(String shopImg) {
        assert shopImg != null && (!"".equals(shopImg));
        File file = new File(PathUtils.getImageBasePath() + shopImg);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 保存图片信息
     *
     * @param productImage 图片封装
     * @param targetPath   保存路径
     * @return 真实文件路径
     */
    public static String savePicture(FileContainer productImage, String targetPath) throws IOException {
        InputStream fileInputStream = productImage.getFileInputStream();
        String fileName = productImage.getFileName();
        // 图片文件的真实存储名称
        String realFileName = getRandomFileName();
        // 获取文件的拓展名
        String extendedName = getExtendedName(fileName);
        // 创建存储文件夹
        makeFolder(targetPath);
        // 图片真实存储路径
        String relativeAddress = targetPath + realFileName + extendedName;
        File targetFile = new File(PathUtils.getImageBasePath() + relativeAddress);
        FileImageOutputStream fos = new FileImageOutputStream(targetFile);
        byte[] read = new byte[1024];
        int len = 0;
        while ((len = fileInputStream.read(read)) != -1) {
            fos.write(read, 0, len);
        }
        fileInputStream.close();
        fos.flush();
        fos.close();
        return relativeAddress;
    }
}
