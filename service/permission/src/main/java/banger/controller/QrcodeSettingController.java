package banger.controller;

import banger.common.BaseController;
import banger.domain.enumerate.LoanAddedFileType;
import banger.domain.html5.IntoFileInfo;
import banger.domain.permission.IntoQrcodeSave;
import banger.domain.permission.IntoQrcodeSetting;
import banger.framework.util.FileUtil;
import banger.moduleIntf.ILoanModule;
import banger.service.intf.IQrcodeSaveService;
import banger.service.intf.IQrcodeSettingService;
import banger.util.MultipartImageUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by panl on 2017/12/1
 * 进件用户二维码配置、生成
 */
@Controller
@RequestMapping("/qrcodeSetting")
public class QrcodeSettingController  extends BaseController {

	@Autowired
	IQrcodeSettingService iQrcodeSettingService;

	@Autowired
	IQrcodeSaveService iQrcodeSaveService;

	@Resource
	private ILoanModule loanModule;

	private static final int QRCOLOR = 0xFF000000;   //默认是黑色
	private static final int BGWHITE = 0xFFFFFFFF;   //背景颜色

	@RequestMapping("/queryQrcodeSetting")
	public String queryQrcodeSetting(){
		IntoQrcodeSetting intoQrcodeSetting = iQrcodeSettingService.queryQrcodeSetting();
		if (intoQrcodeSetting != null){
			setAttribute("accessPath",intoQrcodeSetting.getAccessPath());
			setAttribute("imaPath",intoQrcodeSetting.getImaPath());
			setAttribute("imaName",intoQrcodeSetting.getImaName());
			setAttribute("qrId",intoQrcodeSetting.getQrId());
			if(intoQrcodeSetting.getImaPath() != null && intoQrcodeSetting.getImaName() != null){
				String imageBase64 ="data:image/jpg;base64," + imgToByte(intoQrcodeSetting.getImaPath() + intoQrcodeSetting.getImaName());
				setAttribute("imageBase64",imageBase64);
			}
		}
		return "permission/vm/qrCodeSetting";
	}
	/**
	 * @Author panl
	 * @Date 2017/12/1 16:53
	 * 更改二维码配置
	 */
	@RequestMapping("/updateqrcodeSetting")
	public void updateqrcodeSetting(@RequestParam("imageInput") MultipartFile imageInput, HttpServletRequest request, HttpServletResponse response){
		File image1 = null;
		String accessPath = this.getParameter("accessPath");
		try {
			if (request instanceof MultipartRequest) {
				MultipartRequest multRequest = (MultipartRequest) request;
				Map<String, MultipartFile> fileMap = multRequest.getFileMap();
				if (fileMap.containsKey("imageInput")) {
					image1 = MultipartImageUtil.createImageFile(this.getRootPath(),imageInput);
			       uploadImg(fileMap.get("imageInput"),accessPath);
				}
			}
		}catch (IOException e){
				System.out.println(e.getMessage());
		}
	}

	/**
	 * @Author panl
	 * @Date 2017/12/11 16:16
	 * 生成二维码
	 */
	@RequestMapping("/createQrCode")
	public String  createQrCode(){
		IntoQrcodeSave intoQrcodeSave = new IntoQrcodeSave();
		String path = loanModule.getLoanAddedProvider().getSavePath(
				LoanAddedFileType.QRIMAGE.savePath);
		IntoQrcodeSetting intoQrcodeSetting = new IntoQrcodeSetting();
		intoQrcodeSetting = iQrcodeSettingService.queryQrcodeSetting();
		String filePath = intoQrcodeSetting.getImaPath()+intoQrcodeSetting.getImaName();
		String content = intoQrcodeSetting.getAccessPath();
		try
		{
			QrcodeSettingController zp = new QrcodeSettingController();
			BufferedImage bim = zp.getQR_CODEBufferedImage(content, BarcodeFormat.QR_CODE, 400, 400, zp.getDecodeHintType());
			String qrName = zp.addLogo_QRCode(bim, new File(filePath), new LogoConfig(), path);
			intoQrcodeSave.setQrName(qrName);
			intoQrcodeSave.setQrPath(path);
			iQrcodeSaveService.insertQrcodeSave(intoQrcodeSave);
			String base64 ="data:image/jpg;base64," + imgToByte(path + qrName);
			setAttribute("base64",base64);
			return "/permission/vm/createQrCode";
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private void	uploadImg(MultipartFile image,String accessPath){
		Integer id = null;
		// 上传位置
		String path = loanModule.getLoanAddedProvider().getSavePath(
				LoanAddedFileType.QRIMAGE.savePath);
		// 缩略图
		String thumbPath = loanModule.getLoanAddedProvider()
				.getImageThumbPath();

		FileUtil.mkdirs(new String[]{path, thumbPath});
// 获得原始文件名
		String srcName = image.getOriginalFilename();
// 新文件名
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(new Date());

		String newFileName = dateString+srcName;
		String newFullFilename = path + newFileName;
		String fix = FileUtil.getFileFix(srcName);

		if (!image.isEmpty()) {
			String errorMessage = "";
			try {
				FileOutputStream fos = new FileOutputStream(newFullFilename);
				InputStream in = image.getInputStream();
				int b = 0;
				while ((b = in.read()) != -1) {
					fos.write(b);
				}
				fos.close();
				in.close();
			} catch (Exception e) {
				log.error("保存上传图片异常 error:" + e.getMessage(), e);
				errorMessage = e.getMessage();
			}
//			String thumbFilename = thumbPath + newFileName;
//
//			try {
//				ThumbUtil.zoomImageScale(new File(newFullFilename),
//						thumbFilename, 400);
//			} catch (IOException e) {
//				log.error("生成缩略图异常 error:" + e.getMessage(), e);
//				errorMessage = e.getMessage();
//			}
		}

		IntoQrcodeSetting intoQrcodeSetting = new IntoQrcodeSetting();
			IntoFileInfo intoFileInfo = new IntoFileInfo();
			if (new File(newFullFilename).exists()) {
				Long fileSize = new File(newFullFilename).length();
					intoQrcodeSetting.setImaPath(path);
					intoQrcodeSetting.setImaName(newFileName);
					intoQrcodeSetting.setImaSize(fileSize.intValue());
			}
			intoQrcodeSetting.setQrId(1);
			intoQrcodeSetting.setAccessPath(accessPath);
			iQrcodeSettingService.updateQrcodeSetting(intoQrcodeSetting);
			iQrcodeSaveService.deleteQrcodeSave();
	}
	/**
	 * 给二维码图片添加Logo
	 *

	 * @param logoPic
	 */
	public String addLogo_QRCode(BufferedImage bim, File logoPic, LogoConfig logoConfig, String path)
	{
		try
		{
			/**
			 * 读取二维码图片，并构建绘图对象
			 */
			BufferedImage image = bim;
			Graphics2D g = image.createGraphics();

			if(logoPic.length()==0){

			}else{
				/**
				 * 读取Logo图片
				 */
				BufferedImage logo = ImageIO.read(logoPic);
				/**
				 * 设置logo的大小,本人设置为二维码图片的20%,因为过大会盖掉二维码
				 */
				int widthLogo = logo.getWidth(null)>image.getWidth()*3/10?(image.getWidth()*3/10):logo.getWidth(null),
						heightLogo = logo.getHeight(null)>image.getHeight()*3/10?(image.getHeight()*3/10):logo.getWidth(null);

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
			ImageIO.write(image, "png", new File( path + qrName));

			String imageBase64QRCode =  Base64.encodeBase64URLSafeString(baos.toByteArray());

			baos.close();
			return qrName;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
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