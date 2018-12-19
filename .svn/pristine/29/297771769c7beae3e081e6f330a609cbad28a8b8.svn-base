package banger.test;

import org.junit.Test;

import banger.common.tests.BaseServiceTestCase;
import banger.moduleIntf.ITalkModule;

public class TalkIntfTest extends BaseServiceTestCase{

	@Test
	public void testGetTalkRecord() {
		System.out.println(talkModule.getTalkEsb().getTalkRecord("201501221009416666666666653FF6F064985545108321387").getBeginTime());
	}
	@Test
	public void testInformTalkRecordDone(){
		talkModule.getTalkEsb().informTalkRecordDone("201501221009416666666666653FF6F064985545108321387");
	}
	
	@Test
	public void testGetOfflineTalkRecord(){
		System.out.println(talkModule.getTalkEsb().getOfflineTalkRecord("zs", "xg").get(0).getBeginTime());
		System.out.println(talkModule.getTalkEsb().getOfflineTalkRecord("zs", "xg").get(1).getBeginTime());
		System.out.println(talkModule.getTalkEsb().getOfflineTalkRecord("zs", "xg").get(2).getBeginTime());
	}
	private ITalkModule talkModule;

	public ITalkModule getTalkModule() {
		return talkModule;
	}

	public void setTalkModule(ITalkModule talkModule) {
		this.talkModule = talkModule;
	}
	

}
