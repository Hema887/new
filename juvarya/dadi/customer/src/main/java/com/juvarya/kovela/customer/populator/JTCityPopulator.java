package com.juvarya.kovela.customer.populator;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.juvarya.kovela.customer.dto.JTCityDTO;
import com.juvarya.kovela.customer.dto.JTDistrictDTO;
import com.juvarya.kovela.customer.model.JTCityModel;
import com.juvarya.kovela.utils.converter.Populator;

@Component("jtCityPopulator")
public class JTCityPopulator implements Populator<JTCityModel, JTCityDTO> {
	@Resource(name = "jtDistrictPopulator")
	private JTDistrictPopulator districtPopulator;

	@Override
	public void populate(JTCityModel source, JTCityDTO target) {
		target.setId(source.getId());
		target.setName(source.getName());
		target.setIsoCode(source.getIsoCode());
		target.setHasAirTransport(source.getHasAirTransport());
		target.setHasBusTransport(source.getHasBusTransport());
		target.setHasRailTransport(source.getHasRailTransport());
		if (Boolean.FALSE.equals(target.getHasBusTransport())) {
			JTCityModel nearestCity = source.getNearestBusTransportCity();
			JTCityDTO nearesBusTransport = new JTCityDTO();
			nearesBusTransport.setId(nearestCity.getId());
			nearesBusTransport.setName(nearestCity.getName());
			nearesBusTransport.setIsoCode(nearestCity.getIsoCode());
			target.setNearestBusTransport(nearesBusTransport);
		}
		if (Boolean.FALSE.equals(target.getHasAirTransport())) {
			JTCityModel nearestCity = source.getNearestAirTransportCity();
			JTCityDTO nearesAirTransport = new JTCityDTO();
			nearesAirTransport.setId(nearestCity.getId());
			nearesAirTransport.setName(nearestCity.getName());
			nearesAirTransport.setIsoCode(nearestCity.getIsoCode());
			target.setNearestAirTransport(nearesAirTransport);
		}
		if (Boolean.FALSE.equals(target.getHasRailTransport())) {
			JTCityModel nearestCity = source.getNearestRailTransportCity();
			JTCityDTO nearesRailTransport = new JTCityDTO();
			nearesRailTransport.setId(nearestCity.getId());
			nearesRailTransport.setName(nearestCity.getName());
			nearesRailTransport.setIsoCode(nearestCity.getIsoCode());
			target.setNearestRailTransport(nearesRailTransport);
		}

		JTDistrictDTO districtDTO = new JTDistrictDTO();
		if (null != source.getDistrict()) {
			districtPopulator.populate(source.getDistrict(), districtDTO);
			target.setDistrict(districtDTO);
		}

	}
}
