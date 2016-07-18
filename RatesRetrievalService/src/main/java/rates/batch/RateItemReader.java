package rates.batch;

import org.springframework.batch.item.ResourceAware;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.core.io.Resource;

public class RateItemReader extends FlatFileItemReader<RateLine> implements ResourceAware {

	Resource currentFile = null;
	RateLineMapper lineMapper = null;

	@Override
	public void setResource(Resource resource) {
		super.setResource(resource);
		currentFile = resource;
		lineMapper.setCurrentFile(currentFile);
	};

	public Resource getCurrentFile() {
		return currentFile;
	}

	public RateItemReader() {
		this.lineMapper = new RateLineMapper();
		this.setLineMapper(lineMapper);
	}

	@Override
	public RateLine read(){
		RateLine rateLine = null;
		try {
			rateLine = super.read();
			if (rateLine != null && currentFile != null) {
				rateLine.setFile(currentFile.getFilename());
				rateLine.setFilePath(currentFile.getURI().getPath());
			}
			return rateLine;
		} catch (Exception e) {
			System.out.println("read error " + e);
		}
		return rateLine;
	}

}
