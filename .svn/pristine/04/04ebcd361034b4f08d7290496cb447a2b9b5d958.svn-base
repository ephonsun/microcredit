package banger.test;

import org.junit.Test;

import banger.common.tests.BaseServiceTestCase;
import banger.moduleIntf.IVideoModule;

public class VideoIntfTest extends BaseServiceTestCase{

	@Test
	public void testGetVideoRecord() {
		System.out.println(videoModule.getVideoEsb().getVideoRecord("121").getRecordNo());
	}
	
	@Test
	public void testInformVideoRecordDone(){
		videoModule.getVideoEsb().informVideoRecordDone("121");
	}
	private IVideoModule videoModule;
	
	
	public IVideoModule getVideoModule() {
		return videoModule;
	}
	
	public void setVideoModule(IVideoModule videoModule) {
		this.videoModule = videoModule;
	}
}
