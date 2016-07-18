package rates.batch;

import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.core.io.Resource;

public class MyMultiResourceItemReader extends MultiResourceItemReader<RateLine> {
	
	
	
	public MyMultiResourceItemReader(Resource[] currentFiles) {
		setResources(currentFiles);
		RateItemReader reader = new RateItemReader();
		setDelegate(reader);
		
	}
	
	
	
 
}
