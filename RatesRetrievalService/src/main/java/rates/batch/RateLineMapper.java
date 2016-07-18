package rates.batch;

import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.core.io.Resource;

public class RateLineMapper extends DefaultLineMapper<RateLine> {

	private Resource currentFile;

	public RateLineMapper() {}
	
	public void setCurrentFile(Resource currentFile) {
		this.currentFile = currentFile;
		setMapper();
	}
	
	public Resource getCurrentFile() {
		return currentFile;
	}

	private void setMapper() {
		Range[] range = new Range[4];
		range[0] = new Range(1, 8);
		range[1] = new Range(9, 16);
		range[2] = new Range(17, 19);
		range[3] = new Range(20, 22);
		setLineTokenizer(new FixedLengthTokenizer() {
			{
				
				setNames(new String[] { "date", "rate", "buy", "sell" });

				setColumns(range);
			}
		});
		setFieldSetMapper(new BeanWrapperFieldSetMapper<RateLine>() {
			{
				setTargetType(RateLine.class);
			}
		});
	}
}
