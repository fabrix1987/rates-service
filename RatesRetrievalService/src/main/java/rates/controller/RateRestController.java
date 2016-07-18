package rates.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rates.entity.Rate;
import rates.entity.RateList;
import rates.service.RateService;

@RestController
public class RateRestController {

	@Autowired
	RateService rateService;

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;

	/**
	 * Start the batch job.
	 *
	 * @return the string
	 */
	@RequestMapping("/batch/start")
	public String startTask() {

		try {
			startJob();
			return "job started";

		} catch (Exception e) {
			e.printStackTrace();
			return "job error";
		}
	}

	public boolean startJob() throws Exception {

		boolean result = false;

		try {

			final JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.nanoTime())
					.toJobParameters();

			final JobExecution execution = jobLauncher.run(job, jobParameters);
			final ExitStatus status = execution.getExitStatus();

			if (ExitStatus.COMPLETED.getExitCode().equals(status)) {
				result = true;
			}

		} catch (Exception ex) {
			System.err.println("errore job " + ex);
		}

		return result;
	}

	@RequestMapping("/")
	public String welcome() {

		return "Welcome to RestTemplate Example.";
	}


	@RequestMapping("/rest/rates/{date}")
	public RateList getRatesByDate(@PathVariable String date) {

		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = sf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Rate> rList = rateService.getRatesByDate(d);
		RateList rateList = new RateList();
		rateList.setRateList(rList);

		return rateList;

	}

	@RequestMapping("/rest/rates/all")
	public RateList getAllRates() {

		List<Rate> rList = rateService.getAllRates();
		RateList rateList = new RateList();
		rateList.setRateList(rList);

		return rateList;

	}

	
	@RequestMapping(value = "/rest/rates/{id}", method = RequestMethod.DELETE)
	public String deleteRate(@PathVariable("id") int id, Model model, HttpServletResponse response) {

		boolean ok = rateService.deleteById(id);
		if (!ok) {
			return "no rates deleted";
		}

		return "rate deleted";
	}
	
	
	@RequestMapping(value = "/rest/rates/save", method = RequestMethod.POST, consumes = "application/json")
    public String insertOrUpdateRate(@RequestBody Rate rate) {
		rateService.insertOrUpdateRate(rate);
		return "rate saved";
	}

}


