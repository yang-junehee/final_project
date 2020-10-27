package com.icia.DJ.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.icia.DJ.dao.MemberDAO;
import com.icia.DJ.dto.MemberDTO;

@Service
public class MemberService {

	@Autowired
	MemberDAO mdao;

	@Autowired
	HttpSession session;

	ModelAndView mav = new ModelAndView();

	// 회원가입
	public ModelAndView memberJoin(MemberDTO member) {
		int joinResult = mdao.memberJoin(member);

		if (joinResult > 0) { // 회원가입 성공시
			mav.setViewName("Login");

		} else { // 회원가입 실패시
			mav.setViewName("Join");
		}

		return mav;
	}

	// 아이디 중복확인
	public String idOverlap(String mid) {
		String idCheck = mdao.idOverlap(mid);
		String resultMsg = null;

		if (idCheck == null) {
			resultMsg = "OK";
		} else {
			resultMsg = "NO";
		}
		return resultMsg;
	}

	// 일반 로그인
	public ModelAndView memberLogin(MemberDTO member) {

		String loginID = mdao.memberLogin(member);
		System.out.println("dao에서 리턴된 값 : " + loginID);

		if (loginID != null) {
			session.setAttribute("loginID", loginID);
			mav.setViewName("redirect:/djuneheeLandMain");

		} else {
			mav.setViewName("Login");
		}

		return mav;
	}

	// 아이디 찾기
	public ModelAndView memberFindId(MemberDTO member) {
		String findID = mdao.memberFindId(member);

		mav.addObject("findId", findID);
		mav.setViewName("SearchId");

		return mav;
	}

	// 비밀번호 찾기
	public ModelAndView memberFindPw(MemberDTO member) {
		MemberDTO memberFindView = mdao.memberFindView(member);

		// setAttribute
		mav.addObject("memberFind", memberFindView);
		// dispatcher
		mav.setViewName("SearchPw");
		return mav;
	}

	// 마이페이지(상세보기)
	public ModelAndView memberView(String mid) {
		MemberDTO memberView = mdao.memberView(mid);
		String admin = "admin";
		// setAttribute

		System.out.println("--------------------------id 확인----------------- : " + memberView.getMid());
		System.out.println("---------------------------id 확인2-------------- : " + admin);

		// dispatcher
		if (admin.equals(memberView.getMid())) { // 관리자 마이페이지
			mav.addObject("memberView", memberView);
			mav.setViewName("MypageAdmin");

		} else { // 일반회원 마이페이지
			mav.addObject("memberView", memberView);
			mav.setViewName("Mypage");
		}
		return mav;

	}

	// 회원탈퇴
	public ModelAndView memberOut(String mid) {
		int outResult = mdao.memberOut(mid);
		if (outResult > 0) {// 탈퇴성공
			session.invalidate();
			mav.setViewName("Index2");

		} else { // 탈퇴 실패시
			mav.setViewName("Mypage");
		}
		return mav;
	}

	// 회원정보 수정할 정보 불러오기
	public ModelAndView memberModify(String mid) {
		MemberDTO memberModfiy = mdao.memberView(mid);
		if (memberModfiy != null) {
			mav.addObject("memberModfiy", memberModfiy);
			mav.setViewName("MemberModify");
		} else {
			mav.setViewName("Mypage");
		}

		return mav;
	}

	// 회원 정보 수정
	public ModelAndView MemberProcess(MemberDTO member) {
		int result = mdao.MemberProcess(member);

		String sessionID = member.getMid();

		if (result > 0) {
			mav.setViewName("redirect:/mypage?memberId=" + sessionID);
		} else {
			mav.setViewName("MemberModfiy");
		}
		return mav;
	}
	
	/*
	 * // 로그인시 아이디 비교 public String submitLogin(String mid, String mpassword) {
	 * String loginCheck = mdao.submitLogin(mid, mpassword);
	 * 
	 * System.out.println(); System.out.println("======================");
	 * System.out.println("loginCheck : " + loginCheck);
	 * System.out.println("======================"); System.out.println();
	 * 
	 * String resultMsg = null;
	 * 
	 * if (loginCheck == null) { // 그 아이디가 없을 경우 resultMsg = "NO"; } else { // 그
	 * 아이디가 존재할 경우 resultMsg = "OK"; } return resultMsg; }
	 */

}
