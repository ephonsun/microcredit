package banger.service.intf;

import java.io.File;
import java.io.OutputStream;

public interface IAppProductService {

	void downloadProductFile(File file, OutputStream os, long skipLen) throws Exception;

}
