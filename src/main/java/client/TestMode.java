package client;

import measurements.BasicMeasurements;
import measurements.Measurements;

public class TestMode {
	public void setAllDataToNull(BasicMeasurements obj) {
		if (obj.getClass().equals(new Measurements(0, null).getClass())) {
			Measurements m = (Measurements) obj;
			m.setActivity(null);
			m.setEmotion(null);
			m.setIntesitity(0);
//			m.setProductivity(0);
		}
	}
}
