/**
 * 
 */
package edu.depaul.armada.service;

import java.util.ArrayList;
import java.util.List;

import edu.depaul.armada.dao.ContainerDao;
import edu.depaul.armada.dao.ContainerLogDao;
import edu.depaul.armada.dao.PreferenceDao;
import edu.depaul.armada.model.Metric;
import edu.depaul.armada.model.ThresholdMetric;

/**
 * Used by log controller to provide metrics over time
 * 
 * @author ptrzyna
 */
public class MetricServiceImpl implements MetricService {
	
	private static final String MEM_THRESHOLD = "memory_threshold";
	private static final String CPU_THRESHOLD = "cpu_threshold";
	private static final String DISK_THRESHOLD = "disk_threshold";
	
	private ContainerDao containerDao;
	private ContainerLogDao containerLogDao;
	private PreferenceDao preferenceDao;
	
	public void setContainerDao(ContainerDao containerDao) {
		this.containerDao = containerDao;
	}
	
	public void setContainerLogDao(ContainerLogDao containerLogDao) {
		this.containerLogDao = containerLogDao;
	}
	
	public void setPreferenceDao(PreferenceDao preferenceDao) {
		this.preferenceDao = preferenceDao;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.armada.service.MetricService#getContainerMetrics(int)
	 */
	@Override
	public List<ThresholdMetric> getMetrics(int periodInHours) {
		long mem = preferenceDao.findWithPreferenceName(MEM_THRESHOLD).getValue();
		long cpu = preferenceDao.findWithPreferenceName(CPU_THRESHOLD).getValue();
		long disk = preferenceDao.findWithPreferenceName(DISK_THRESHOLD).getValue();
		
		List<Metric> containerCount = getContainerCount(periodInHours);

		List<Metric> warn = containerLogDao.getStateCounts(Double.valueOf(mem*0.75).longValue(), 
				Double.valueOf(cpu*0.75).longValue(), 
				Double.valueOf(disk*0.75).longValue(), 
				periodInHours);	// breached warning threshold
		List<Metric> err = containerLogDao.getStateCounts(mem, cpu, disk, periodInHours);	// breached threshold

		List<ThresholdMetric> metrics = new ArrayList<ThresholdMetric>(periodInHours);
		for(int i=0; i<periodInHours; i++) {
			ThresholdMetric temp = new ThresholdMetric();
			Metric errTemp = err.get(i);
			Metric warnTemp = warn.get(i);
			temp.setPeriod(errTemp.getHour());
			temp.setError(errTemp.getValue());
			temp.setWarn(warnTemp.getValue() - errTemp.getValue());
			temp.setOk(containerCount.get(i).getValue() - (temp.getError() + temp.getWarn()));
			metrics.add(temp);
		}
		
		return metrics;
	}

	/* (non-Javadoc)
	 * @see edu.depaul.armada.service.MetricService#getContainerCount(int)
	 */
	@Override
	public List<Metric> getContainerCount(int periodInHours) {
		List<Metric> metrics = containerLogDao.getContainerCounts(periodInHours);
		return metrics;
	}

}
