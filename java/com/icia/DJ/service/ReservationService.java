package com.icia.DJ.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.icia.DJ.dao.ReservationDAO;
import com.icia.DJ.dto.ReservationDTO;


@Service
public class ReservationService {
	ModelAndView mav = new ModelAndView();
	
	@Autowired
	private ReservationDAO rvdao;
	

	
	@Autowired
	HttpSession session;
	

	
	
	

	//예약 성공시 
	public ModelAndView reservationInsert(ReservationDTO reservation) {
		int checkResult = rvdao.reservationInsert(reservation);
		
		if(checkResult>0) {
			ReservationDTO rsv = rvdao.reservationSelect(reservation);
			mav.addObject("reservation",rsv);
			mav.setViewName("ReservationCheck");

		}else {
			
			mav.setViewName("Reservation");
		}
		
		return mav;


	}
	//예약후 결제 성공시 
	public ModelAndView reservationUpdate(String rvphone) {
		int UpdateResult = rvdao.reservationUpdate(rvphone);
		
		
		if(UpdateResult > 0 ) {
			
			mav.addObject("reservation",UpdateResult); 
			mav.setViewName("ReservationSuccess");
		}else {
			
			mav.setViewName("Reservation");
		}
		return mav;
	}
	//결제성공후 나타내기
	public ModelAndView reservationSelect(ReservationDTO reservation) {
		ReservationDTO rsv = rvdao.reservationSelect(reservation);
		session.setAttribute("reservation", rsv);
		mav.setViewName("ReservationSelect");
		return mav;
	}
	
	
}

