package cn.fanchencloud.campus.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 19-1-31
 * Time: 下午1:03
 * Description:
 *
 * @author chen
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JsonResponse {
    /**
     * 定义jackson对象
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 响应业务状态
     */
    private Integer status;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应中的数据
     */
    private Object data;
    /**
     * 不使用
     */
    private String ok;

    public static JsonResponse build(Integer status, String msg, Object data) {
        return new JsonResponse(status, msg, data);
    }

    public static JsonResponse ok(Object data) {
        return new JsonResponse(data);
    }

    public static JsonResponse ok() {
        return new JsonResponse(200, "操作成功！", null);
    }

    public static JsonResponse ok(String message) {
        return new JsonResponse(200, message, null);
    }

    public static JsonResponse ok(String message, Object data) {
        return new JsonResponse(200, message, data);
    }

    public static JsonResponse errorMsg(String msg) {
        return new JsonResponse(500, msg, null);
    }

    public static JsonResponse error(String message, Object data) {
        return new JsonResponse(500, message, data);
    }

    public static JsonResponse errorMap(Object data) {
        return new JsonResponse(501, "error", data);
    }

    public static JsonResponse errorTokenMsg(String msg) {
        return new JsonResponse(502, msg, null);
    }

    public static JsonResponse errorException(String msg) {
        return new JsonResponse(555, msg, null);
    }

    public static JsonResponse success(String msg, Object data) {
        return new JsonResponse(200, msg, data);
    }


    public JsonResponse() {

    }

//    public static LeeJSONResult build(Integer status, String msg) {
//        return new LeeJSONResult(status, msg, null);
//    }

    public JsonResponse(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public JsonResponse(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Boolean isOK() {
        return this.status == 200;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * @param jsonData
     * @param clazz
     * @return
     * @Description: 将json结果集转化为LeeJSONResult对象
     * 需要转换的对象是一个类
     * @author leechenxiang
     * @date 2016年4月22日 下午8:34:58
     */
    public static JsonResponse formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, JsonResponse.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isObject()) {
                obj = MAPPER.readValue(data.traverse(), clazz);
            } else if (data.isTextual()) {
                obj = MAPPER.readValue(data.asText(), clazz);
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param json
     * @return
     * @Description: 没有object对象的转化
     * @author leechenxiang
     * @date 2016年4月22日 下午8:35:21
     */
    public static JsonResponse format(String json) {
        try {
            return MAPPER.readValue(json, JsonResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param jsonData json数据
     * @param clazz    转换的对象
     * @return 返回信息
     * @Description: Object是集合转化
     * 需要转换的对象是一个list
     */
    public static JsonResponse formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }
}
