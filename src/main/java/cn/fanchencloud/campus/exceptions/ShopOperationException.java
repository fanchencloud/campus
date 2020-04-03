package cn.fanchencloud.campus.exceptions;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/3/12
 * Time: 23:31
 * Description: 店铺操作异常类
 *
 * @author chen
 */
public class ShopOperationException extends RuntimeException{

    private static final long serialVersionUID = -507979058305715930L;

    public ShopOperationException(String message) {
        super(message);
    }
}
