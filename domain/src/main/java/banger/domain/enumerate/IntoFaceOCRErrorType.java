package banger.domain.enumerate;

import banger.framework.collection.OptionItem;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 人脸识别ocr错误信息
 * @author zmd
 *
 */

public enum IntoFaceOCRErrorType {

	Request_Entity_Too_Large("request_entity_too_large","客户发送的请求大小超过了2MB限制。该错误的返回格式为纯文本，不是json格式"),
    API_NOT_FOUND("api_not_found","所调用的API不存在。"),
	IMAGE_ERROR_UNSUPPORTED_FORMAT("image_error_unsupported_format","参数<param>对应的图像无法正确解析，有可能不是一个图像文件、或有数据破损。"),


	INVALID_IMAGE_SIZE("invalid_image_size","客户上传的图像太大。具体是指图像存储尺寸超过2MB、或者像素尺寸的长或宽超过4096像素"),
	MULTIPLE_FACES(" multiple_faces","参数<param>对应的图像中发现有多张脸。仅当fail_when_multiple_faces取值为1时，<param>才可能取值为image。"),
	NO_FACE_FOUND("no_face_found","从数据源获得的身份证图像、用户传递的image图像，或是用户传递image_ref图像中没有找到人脸。"),
	LOW_QUALITY("low_quality","参数image对应的图像中的人脸图像质量低于阈值face_quality_threshold。"),


	INVALID_FACE_TOKEN("invalid_face_token","使用face token作为待验证图时，所传的face_token不存在。"),
	NO_SUCH_ID_NUMBER("no_such_id_number","有源比对时，数据源中没有此身份证号码的记录。"),
	ID_NUMBER_NAME_NOT_MATCH("id_number_name_not_match","有源比对时，数据源中存在此身份证号码，但与提供的姓名不匹配"),
	INVALID_NAME_FORMAT("invalid_name_format","有源比对时，idcard_name参数字符数过多（多于32字符）、或者使用错误的编码（姓名要求以UTF-8编码）"),


	INVALID_IDCARD_NUMBER("invalid_idcard_number","有源比对时，idcard_number参数不是正确的身份证号码格式。身份证号码必定为18位数字，且最后一位可以是X（大小写均可）"),
	DATA_VALIDATION_ERROR("data_validation_error","配合MegLive SDK使用时，delta 参数的校验数据与上传的图像无法一一对应，或者图像上传不完整。"),
	DATA_SOURCE_ERROR("data_source_error","有源比对时，verify方法调用数据源发生错误，一般来说是数据源出错。"),
	DELTA_USED("delta_used","配合MegLive SDK使用时，系统检验出delta已被使用过。"),


	MEGLIVE_BIZ_NO_USED("meglive_biz_no_used","配合MegLiveFlash SDK使用时，需要通过一个 meglive_biz_no 进行算法初始化。"),
	AUTHORIZATION_ERROR("authorization_error","api_key被停用、调用次数超限、没有调用此API的权限，或者没有以当前方式调用此API的权限。"),
	CONCURRENCY_LIMIT_EXCEEDED(" concurrency_limit_exceeded","并发数超过限制。"),
	MISSING_ARGUMENTS("missing_arguments","缺少某个必选参数。"),

	IMG_NOT_CLEAR("img_not_clear","未上传正确正面照片"),
	BAD_ARGUMENTS("bad_arguments","某个参数解析出错（比如必须是数字，但是输入的是非数字字符串; 或者长度过长。"),
	INTERNAL_ERROR("internal_error","服务器内部错误，当此类错误发生时请再次请求，如果持续出现此类错误，请及时联系FaceID客服或商务。")
;
	public final String type; // 错误类型
	public final String typeMessage; // 错误信息

	IntoFaceOCRErrorType(String type, String typeMessage) {
		this.type = type;
		this.typeMessage = typeMessage;
	}

}
