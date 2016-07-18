package rates.batch;

import org.springframework.batch.core.ItemReadListener;

public class CustomItemReaderListener implements ItemReadListener<RateLine> {

	@Override
	public void beforeRead() {
		//System.out.println("ItemReadListener - beforeRead");
	}

	@Override
	public void afterRead(RateLine item) {
		//System.out.println("ItemReadListener - afterRead");
	}

	@Override
	public void onReadError(Exception ex) {
		//System.out.println("ItemReadListener - onReadError " + ex);
	}

}
