package banger.controller;

import banger.common.BaseController;
import banger.common.annotation.NoTokenAnnotation;
import banger.common.interceptor.NoLoginInterceptor;
import banger.constant.AppParamsConst;
import banger.domain.enumerate.LoanAddedFileType;
import banger.domain.html5.IntoCustApplyInfoQuery;
import banger.domain.html5.IntoFileInfo;
import banger.domain.permission.IntoQrcodeSave;
import banger.domain.permission.IntoQrcodeSetting;
import banger.framework.pagesize.IPageList;
import banger.framework.pagesize.IPageSize;
import banger.framework.util.FileUtil;
import banger.framework.util.StringUtil;
import banger.moduleIntf.IIntoCustomersProvider;
import banger.moduleIntf.IIntoQrCodeProvider;
import banger.moduleIntf.IIntoQrCodeSettingProvider;
import banger.moduleIntf.ILoanModule;
import banger.response.AppJsonResponse;
import banger.response.CodeEnum;
import banger.util.AppJsonUtil;
import banger.util.HttpParseUtil;
import banger.util.PageSizeUtil;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by panl on 2017/11/23
 * 进件用户
 */
@Controller
@RequestMapping("/api")
public class AppIntoCustomerController  extends BaseController {
	@Autowired
	private IIntoCustomersProvider iIntoCustomersProvider;

	@Autowired
	private IIntoQrCodeProvider iIntoQrCodeProvider;

	@Autowired
	private IIntoQrCodeSettingProvider iIntoQrCodeSettingProvider;

	@Resource
	private ILoanModule loanModule;

	private static final int QRCOLOR = 0xFF000000;   //默认是黑色
	private static final int BGWHITE = 0xFFFFFFFF;   //背景颜色

	/**
	 * @Author panl
	 * @Date 2017/11/23 11:16
	 * 进件用户列表
	 */
	@NoLoginInterceptor
	@RequestMapping(value = "/v1/AppqueryIntoCustomerList", method = RequestMethod.POST)
	public ResponseEntity<String> queryIntoCustomerList(HttpServletRequest request, HttpServletResponse response) {
		//首先判断传递的值是否为空
		String reqJson= HttpParseUtil.getJsonStr(request);
		if(StringUtils.isEmpty(reqJson)){
			log.error("进件客户列表，参数为空");
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
		}
		//获取传递的值
		JSONObject jsonObject = new JSONObject().fromObject(reqJson);
		//获取参数
		String userId = jsonObject.containsKey("userId")?jsonObject.getString("userId"):"";
		String userName = jsonObject.containsKey("userName")?jsonObject.getString("userName"):"";
		String createDate = jsonObject.containsKey("createDate")?jsonObject.getString("createDate"):"";
		String createDateEnd = jsonObject.containsKey("createDateEnd")?jsonObject.getString("createDateEnd"):"";

		Map<String,Object> condition = new HashMap<String,Object>();
		IPageList<IntoCustApplyInfoQuery> list = null;
		//如果userid不为空
		if (StringUtils.isNotBlank(userId)) {
			condition.put("userId",userId);
			if (StringUtils.isNotBlank(userName))
			{
				condition.put("custName",userName);
			}
//			//时间填入
//			if(StringUtils.isNotBlank(createDate))
//			{
//				condition.put("startDate",createDate);
//			}
//			if(StringUtils.isNotBlank(createDateEnd))
//			{
//				condition.put("endDate",createDateEnd);
//			}
			IPageSize page = PageSizeUtil.getPageLimit(jsonObject, AppParamsConst.PAGE_SIZE_CUSTOMER_LIST);
			list = iIntoCustomersProvider.IntoAppCustApplyInfoMemberList(condition,page);
		}
		else{
			log.error("进件客户列表，参数不正确");
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
		}
		JSONArray resArray = AppJsonUtil.toJsonArray(list, "applyId,custName,sexCN,custAge,idCard,cardTypeName,custPhone,applyAmount,loanMoneyFormat,loanUserOptionName,signDates" );
		return new ResponseEntity<String>(AppJsonResponse.success(resArray), HttpStatus.OK);
	}

	/**
	 * @Author panl
	 * @Date 2017/11/23 16:52
	 * 进件客户详细信息
	 */
	@NoLoginInterceptor
	@RequestMapping(value = "/v1/getIntoCustomerById", method = RequestMethod.POST)
	public ResponseEntity<String> getIntoCustomerInfo(HttpServletRequest request, HttpServletResponse response){
		String reqJson= HttpParseUtil.getJsonStr(request);
		if(StringUtils.isEmpty(reqJson)){
			log.error("进件用户信息，参数为空");
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
		}

		JSONObject jsonObject = new JSONObject().fromObject(reqJson);
		Integer applyId = jsonObject.containsKey("applyId")?jsonObject.getInt("applyId"):null;
		Integer userId = jsonObject.containsKey("userId")?jsonObject.getInt("userId"):null;

		JSONObject jo = new JSONObject();
		IntoCustApplyInfoQuery intoCustApplyInfoQuery = new IntoCustApplyInfoQuery();
		if(null!=applyId && null!=userId) {
			intoCustApplyInfoQuery = iIntoCustomersProvider.getAppCustApplyInfoById(applyId);
			if (intoCustApplyInfoQuery == null){
				log.error("未找到进件用户信息");
				return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_130), HttpStatus.OK);
			}
			jo.put("custName",intoCustApplyInfoQuery.getCustName());
			jo.put("idType","身份证");
			jo.put("cardType","1");
			jo.put("idCard",intoCustApplyInfoQuery.getIdCard());
			//jo.put("custSex",intoCustApplyInfoQuery.getCustSex());
			jo.put("custPhone",intoCustApplyInfoQuery.getCustPhone());
			//jo.put("homeAddress",intoCustApplyInfoQuery.getHomeAddress());
//			if (null!=intoCustApplyInfoQuery.getUseId()){
//				IntoLoanUse intoLoanUse =  iIntoCustomersProvider.getLoanUseById(intoCustApplyInfoQuery.getUseId());
//				jo.put("intoLoanUse",intoLoanUse.getUseSelect());
//			}
			jo.put("loanUserOptionName",intoCustApplyInfoQuery.getLoanUserOptionName());
			jo.put("applyAmount",intoCustApplyInfoQuery.getApplyAmount());

			SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
			if (intoCustApplyInfoQuery.getSignDates()!=null) {
				String s = formatter.format(intoCustApplyInfoQuery.getSignDates());
				jo.put("signDates", formatter.format(intoCustApplyInfoQuery.getSignDates()));
			}else {
				jo.put("createDate", "");
			}
			Map<String,Object> condition = new HashMap();
			condition.put("applyId",applyId);
			List<IntoFileInfo> intoFileInfoList =    iIntoCustomersProvider.queryFileInfoList(condition);
			//判断 图片是否 有路径 路径下是否有文件 文件中是否有内容

			for (IntoFileInfo intoFileInfo : intoFileInfoList){
				if (intoFileInfo.getImageType()==1){
					if(intoFileInfo.getFilePath().isEmpty()||intoFileInfo.getFileName().isEmpty()){

						jo.put("imageId1",0);
					}else if(imageIsNoNull(intoFileInfo.getFilePath()+intoFileInfo.getFileName())){
						jo.put("imageId1",intoFileInfo.getId());
						jo.put("imageName1",intoFileInfo.getFileSrcName());
					}else{
						jo.put("imageId1",0);
					}
				}else if (intoFileInfo.getImageType()==2){
					if(intoFileInfo.getFilePath().isEmpty()||intoFileInfo.getFileName().isEmpty()){
						jo.put("imageId2",0);
					}else if(imageIsNoNull(intoFileInfo.getFilePath()+intoFileInfo.getFileName())){
						jo.put("imageId2",intoFileInfo.getId());
						jo.put("imageName2",intoFileInfo.getFileSrcName());
					}else{
						jo.put("imageId2",0);
					}
				}else  if (intoFileInfo.getImageType()==3){
					if(intoFileInfo.getFilePath().isEmpty()||intoFileInfo.getFileName().isEmpty()){
						jo.put("imageId3",0);
					}else if(imageIsNoNull(intoFileInfo.getFilePath()+intoFileInfo.getFileName())){
						jo.put("imageId3",intoFileInfo.getId());
						jo.put("imageName3",intoFileInfo.getFileSrcName());
					}else{
						jo.put("imageId3",0);
					}
				}
			}
		}else{
			jo = null;
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_120), HttpStatus.OK);
		}
		return new ResponseEntity<String>(AppJsonResponse.success(jo), HttpStatus.OK);

	}



	/**
	 * 客户经理对进件分配的用户进行转申请、作废操作
	 * @Author panl
	 * @Date 2017/11/24 11:01
	 */
	@NoLoginInterceptor
	@RequestMapping(value = "/v1/dealIntoCustomer", method = RequestMethod.POST)
	public ResponseEntity<String> dealIntoCustomer(HttpServletRequest request,HttpServletResponse response){
		String reqJson= HttpParseUtil.getJsonStr(request);
		if(StringUtils.isEmpty(reqJson)){
			log.error("进件用户信息，参数为空");
			return new ResponseEntity<String>(AppJsonResponse.fail(CodeEnum.CODE_102), HttpStatus.OK);
		}
		JSONObject reqObj = JSONObject.fromObject(reqJson);
		Integer applyId = reqObj.getInt("applyId");
		Integer operator = reqObj.getInt("operator");
		if(operator == 1){
			iIntoCustomersProvider.deleteIntoCustomerById(applyId);
		}else{ //转申请

		}
		return new ResponseEntity<String>(AppJsonResponse.success(), HttpStatus.OK);
		//return new ResponseEntity<String>(AppJsonResponse.success(reqObj), HttpStatus.OK);
	}

	/**
	 * @Author panl
	 * @Date 2017/12/11 23:35
	 * 生成二维码
	 */
	@NoLoginInterceptor
	@RequestMapping(value = "/v1/createQr", method = RequestMethod.GET)
	public String createQr(HttpServletRequest request,HttpServletResponse response) {
		String userAccount = this.getParameter("userAccount");
//		setAttribute("userAccount",userAccount);
//		JSONObject reqObj = JSONObject.fromObject(reqJson);
////		Integer applyId = reqObj.getInt("applyId");
//		String userAccount = reqObj.getString("userAccount");
//		JSONObject jo = new JSONObject();
//		Map<String, Object> condition = new HashMap<String, Object>();
		if(userAccount != null ){
			IntoQrcodeSave intoQrcodeSave = iIntoQrCodeProvider.queryQrcode(userAccount);
			if (intoQrcodeSave==null||StringUtils.isNotBlank(intoQrcodeSave.getQrPath())||StringUtils.isNotBlank(intoQrcodeSave.getQrName())
					|| !FileUtil.isExistFile(intoQrcodeSave.getQrPath()+intoQrcodeSave.getQrName())){
				IntoQrcodeSave intoQrcode = new IntoQrcodeSave();
				String path = loanModule.getLoanAddedProvider().getSavePath(
						LoanAddedFileType.QRIMAGE.savePath);
				IntoQrcodeSetting intoQrcodeSetting = new IntoQrcodeSetting();
				intoQrcodeSetting = iIntoQrCodeSettingProvider.queryQrcodeSetting();
				String filePath = intoQrcodeSetting.getImaPath()+intoQrcodeSetting.getImaName();
				String content = intoQrcodeSetting.getAccessPath()+"?userAccount=" + userAccount;
				try
				{
					AppIntoCustomerController zp = new AppIntoCustomerController();
					BufferedImage bim = zp.getQR_CODEBufferedImage(content, BarcodeFormat.QR_CODE, 400, 400, zp.getDecodeHintType());
					String qrName = zp.addLogo_QRCode(bim, new File(filePath), new LogoConfig(), path);
					intoQrcode.setQrName(qrName);
					intoQrcode.setQrPath(path);
					intoQrcode.setUserAccount(userAccount);
					iIntoQrCodeProvider.insertQrcodeSave(intoQrcode);
					intoQrcodeSave = intoQrcode;
				}
				catch (Exception e)
				{
					e.printStackTrace();
					return "/html5/vm/qrShow";
				}
			}
			String base64 ="data:image/jpg;base64," + imgToByte(intoQrcodeSave.getQrPath()
					+ intoQrcodeSave.getQrName());
			setAttribute("base64",base64);

		}
		return "/html5/vm/qrShow";
	}
	/**
		 * 下载进件图片
		 * @param request
		 * @param response
		 */
	@NoLoginInterceptor
	@NoTokenAnnotation
	@RequestMapping("/v1/downloadIntoImage")
	public void downloadIntoImage(HttpServletRequest request,HttpServletResponse response){
		String id = this.getParameter("intoFileId");
		if(StringUtil.isNotEmpty(id)){
			IntoFileInfo file=iIntoCustomersProvider.getFileInfoById(Integer.valueOf(id));
			if(file!=null){
				String path = file.getFilePath();
				String filename = file.getFileName();
				String filenameOld = file.getFileSrcName();
				downloadImageFile(id,path,filename,filenameOld,response);
			}
		}
	}

	/**
	 * 下载缩略进件图片
	 * @param request
	 * @param response
	 */
	@NoLoginInterceptor
	@RequestMapping("/v1/downloadIntoImageThumb")
	public void downloadIntoImageThumb(HttpServletRequest request,HttpServletResponse response){
		String id = this.getParameter("intoFileId");
		if(StringUtil.isNotEmpty(id)){
			IntoFileInfo file=iIntoCustomersProvider.getFileInfoById(Integer.valueOf(id));
			if(file!=null){
				String path = file.getFileThumbImagePath();
				String filename = file.getFileThumbImageName();
				String filenameOld = file.getFileSrcName();
				downloadImageFile(id,path,filename,filenameOld,response);
			}
		}
	}

	private void downloadImageFile(String id,String path,String filename,String filenameOld,HttpServletResponse response) {
		if (StringUtil.isNotEmpty(path) && StringUtil.isNotEmpty(filename)) {
			String imageFilename = FileUtil.contact(path, filename);
			File file = new File(imageFilename);
			if (file.exists()) {
				FileInputStream stream = null;
				OutputStream output = null;
				try {
					long fileSize = file.length();
					response.setCharacterEncoding("utf-8");
					response.setContentType("application/force-download");// 设置强制下载不打开
					String imageName = URLEncoder.encode(filenameOld, "UTF-8");
					response.addHeader("Content-Disposition", "attachment;fileName=" + imageName);// 设置文件名
					response.addHeader("Content-Length", fileSize + "");

					stream = new FileInputStream(file);
					output = response.getOutputStream();
					byte[] bytes = new byte[1024];
					int len = 0;
					while ((len = stream.read(bytes)) != -1) {
						output.write(bytes, 0, len);
						output.flush();
					}
				} catch (IOException e) {
					log.error("下截进件图片异常 id:" + id, e);
				} finally {
					if (stream != null) {
						try {
							stream.close();
						} catch (Exception e) {
						}
					}
					if (output != null) {
						try {
							output.close();
						} catch (Exception e) {
						}
					}
				}
			}
		}
	}


	//判断文件是否存在，文件是否为空
	private boolean imageIsNoNull(String path){
		boolean image = true;
		File file = new File(path);
		if(!file.exists()){
			image = false;
		}else if(file.exists() && file.length() == 0){
			image = false;
		}
		return image;
	}

	/**
	 * 给二维码图片添加Logo
	 *

	 * @param logoPic
	 */
	public String addLogo_QRCode(BufferedImage bim, File logoPic, LogoConfig logoConfig, String path)throws Exception{

		/**
		 * 读取二维码图片，并构建绘图对象
		 */
		BufferedImage image = bim;
		Graphics2D g = image.createGraphics();

		if(logoPic.length()==0){

		}else {
			/**
			 * 读取Logo图片
			 */
			BufferedImage logo = ImageIO.read(logoPic);
			/**
			 * 设置logo的大小,本人设置为二维码图片的20%,因为过大会盖掉二维码
			 */
			int widthLogo = logo.getWidth(null) > image.getWidth() * 3 / 10 ? (image.getWidth() * 3 / 10) : logo.getWidth(null),
					heightLogo = logo.getHeight(null) > image.getHeight() * 3 / 10 ? (image.getHeight() * 3 / 10) : logo.getWidth(null);

			/**
			 * logo放在中心
			 */
			int x = (image.getWidth() - widthLogo) / 2;
			int y = (image.getHeight() - heightLogo) / 2;
			/**
			 * logo放在右下角
			 *  int x = (image.getWidth() - widthLogo);
			 *  int y = (image.getHeight() - heightLogo);
			 */

			//开始绘制图片
			g.drawImage(logo, x, y, widthLogo, heightLogo, null);
//            g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
//            g.setStroke(new BasicStroke(logoConfig.getBorder()));
//            g.setColor(logoConfig.getBorderColor());
//            g.drawRect(x, y, widthLogo, heightLogo);
			g.dispose();

			logo.flush();
		}
		image.flush();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.flush();
		ImageIO.write(image, "png", baos);

		//二维码生成的路径，但是实际项目中，我们是把这生成的二维码显示到界面上的，因此下面的折行代码可以注释掉
		//可以看到这个方法最终返回的是这个二维码的imageBase64字符串
		//前端用 <img src="data:image/png;base64,${imageBase64QRCode}"/>  其中${imageBase64QRCode}对应二维码的imageBase64字符串
		String qrName = new Date().getTime() + "test.png";
		if(!FileUtil.isExistFilePath(path)){
			new File(path).mkdirs();
		}
		ImageIO.write(image, "png", new File( path + qrName));

		String imageBase64QRCode =  Base64.encodeBase64URLSafeString(baos.toByteArray());

		baos.close();
		return qrName;

	}

	/**
	 * 生成二维码bufferedImage图片
	 *
	 * @param content
	 *            编码内容
	 * @param barcodeFormat
	 *            编码类型
	 * @param width
	 *            图片宽度
	 * @param height
	 *            图片高度
	 * @param hints
	 *            设置参数
	 * @return
	 */
	public BufferedImage getQR_CODEBufferedImage(String content, BarcodeFormat barcodeFormat, int width, int height, Map<EncodeHintType, ?> hints)
	{
		MultiFormatWriter multiFormatWriter = null;
		BitMatrix bm = null;
		BufferedImage image = null;
		try
		{
			multiFormatWriter = new MultiFormatWriter();
			// 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
			bm = multiFormatWriter.encode(content, barcodeFormat, width, height, hints);
			int w = bm.getWidth();
			int h = bm.getHeight();
			image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

			// 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
			for (int x = 0; x < w; x++)
			{
				for (int y = 0; y < h; y++)
				{
					image.setRGB(x, y, bm.get(x, y) ? QRCOLOR : BGWHITE);
				}
			}
		}
		catch (WriterException e)
		{
			e.printStackTrace();
		}
		return image;
	}

	/**
	 * 设置二维码的格式参数
	 *
	 * @return
	 */
	public Map<EncodeHintType, Object> getDecodeHintType()
	{
		// 用于设置QR二维码参数
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		// 设置QR二维码的纠错级别（H为最高级别）具体级别信息
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		// 设置编码方式
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.MARGIN, 0);
		hints.put(EncodeHintType.MAX_SIZE, 350);
		hints.put(EncodeHintType.MIN_SIZE, 100);

		return hints;
	}

	private String imgToByte(String path){
		FileInputStream fileInputStream = null;
		byte[] bytes = null;
		try {
			File file = new File(path);
			if(!file.exists()) {

			}else{
				fileInputStream = new FileInputStream(file);
				bytes = new byte[fileInputStream.available()];
				fileInputStream.read(bytes);
				fileInputStream.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BASE64Encoder encoder = new BASE64Encoder();
		if (bytes==null){
			return null;
		}
		return encoder.encode(bytes);//返回Base64编码过的字节数组字符串

		//return bytes;
	}




}
class LogoConfig
{
	// logo默认边框颜色
	public static final Color DEFAULT_BORDERCOLOR = Color.WHITE;
	// logo默认边框宽度
	public static final int DEFAULT_BORDER = 2;
	// logo大小默认为照片的1/5
	public static final int DEFAULT_LOGOPART = 5;

	private final int border = DEFAULT_BORDER;
	private final Color borderColor;
	private final int logoPart;


	public LogoConfig()
	{
		this(DEFAULT_BORDERCOLOR, DEFAULT_LOGOPART);
	}

	public LogoConfig(Color borderColor, int logoPart)
	{
		this.borderColor = borderColor;
		this.logoPart = logoPart;
	}

	public Color getBorderColor()
	{
		return borderColor;
	}

	public int getBorder()
	{
		return border;
	}

	public int getLogoPart()
	{
		return logoPart;
	}
}