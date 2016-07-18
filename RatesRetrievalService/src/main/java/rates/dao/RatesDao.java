package rates.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import rates.entity.Rate;

@Repository("ratesDao")
public class RatesDao extends AbstractDao<Rate>{
	 
	@SuppressWarnings("unchecked")
    public List<Rate> findAllRates() {
		Criteria criteria = getSession().createCriteria(Rate.class);        
        return (List<Rate>) criteria.list();
    }

	public List<Rate> findRatesByDate(Date date) {
		Criteria criteria = getSession().createCriteria(Rate.class); 
		criteria.add(Restrictions.ge("validDate", getFormattedFromDateTime(date)));
		criteria.add(Restrictions.le("validDate", getFormattedToDateTime(date)));
        return (List<Rate>) criteria.list();
	}
	
	private Date getFormattedFromDateTime(Date date) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    return cal.getTime();
	}

	private Date getFormattedToDateTime(Date date) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.set(Calendar.HOUR_OF_DAY, 23);
	    cal.set(Calendar.MINUTE, 59);
	    cal.set(Calendar.SECOND, 59);
	    return cal.getTime();
	}

	public boolean deleteById(int id) {
		String hql = "delete from Rate where id = :id";
		Query query = getSession().createQuery(hql);
		query.setInteger("id", id);
		int deleted = query.executeUpdate();	
		if(deleted == 1) {
			return true;
		}
		return false;
	}
	
}
