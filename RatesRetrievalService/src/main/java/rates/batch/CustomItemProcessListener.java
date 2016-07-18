package rates.batch;

import org.springframework.batch.core.ItemProcessListener;

import rates.entity.Rate;

public class CustomItemProcessListener implements ItemProcessListener<RateLine, Rate> {

	@Override
	public void beforeProcess(RateLine item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterProcess(RateLine item, Rate result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProcessError(RateLine item, Exception e) {
		//System.out.println("error processing " + item + " exception " + e);
		
	}


}
