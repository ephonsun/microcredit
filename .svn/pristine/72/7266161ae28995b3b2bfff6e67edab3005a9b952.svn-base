package banger.controller;

import java.awt.Color;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.bingoohuang.patchca.color.SingleColorFactory;
import com.github.bingoohuang.patchca.custom.ConfigurableCaptchaService;
import com.github.bingoohuang.patchca.filter.predefined.CurvesRippleFilterFactory;
import com.github.bingoohuang.patchca.font.RandomFontFactory;
import com.github.bingoohuang.patchca.utils.encoder.EncoderHelper;
import com.github.bingoohuang.patchca.word.RandomWordFactory;

import banger.action.AppBaseController;
import banger.common.annotation.NoTokenAnnotation;
import banger.common.interceptor.NoLoginInterceptor;
import banger.common.session.ValidateCodeManage;

@RequestMapping("/api")
@Controller
public class AppUnsafeController extends AppBaseController {
	private static final long serialVersionUID = -7047129550806326263L;
	
	private final int number = 4;
	private final int height = 40;
	private final int width = 100;
	
	/**
	 * 生成验证码
	 * @param request
	 * @param response
	 * test url http://localhost:8080/api/v1/downloadVerificationCode.html?requestId=test11234567890
	 */
	@NoLoginInterceptor
	@NoTokenAnnotation
	@RequestMapping("/v1/downloadVerificationCode")
	public void downloadVerificationCode(HttpServletRequest request,HttpServletResponse response){
		String requestId = this.getParameter("requestId");
		ServletOutputStream stream = null;
		try{
			ConfigurableCaptchaService cs = new ConfigurableCaptchaService();  
		    cs.setColorFactory(new SingleColorFactory(new Color(25, 60, 170)));  
		    cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));
		    RandomFontFactory ff = new RandomFontFactory();
		    ff.setMinSize(30);  
		    ff.setMaxSize(30);  
		    RandomWordFactory rwf = new RandomWordFactory();
		    rwf.setMinLength(number);
		    rwf.setMaxLength(number);
		    cs.setWordFactory(rwf);
		    cs.setFontFactory(ff);
		    cs.setHeight(height);
		    cs.setWidth(width);
		  
		    response.setContentType("image/png");
		    response.setHeader("Cache-Control", "no-cache, no-store");
		    response.setHeader("Pragma", "no-cache");
		    long time = System.currentTimeMillis();
		    response.setDateHeader("Last-Modified", time);
		    response.setDateHeader("Date", time);
		    response.setDateHeader("Expires", time);
		      
		    stream = response.getOutputStream();
		    String validate_code = EncoderHelper.getChallangeAndWriteImage(cs,  
		            "png", stream);
		    
		    ValidateCodeManage.setValidateCode(requestId, validate_code);
		    
		    stream.flush();
		    
		}catch (Exception e) {
			log.error("生成验证码异常 error:"+e.getMessage(),e);
		}finally{
			if(stream!=null){
				try{stream.close();}catch(Exception e){};
			}
		}
	}
	
}
