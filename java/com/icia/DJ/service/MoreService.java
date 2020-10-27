package com.icia.DJ.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.icia.DJ.dao.CommentDAO;
import com.icia.DJ.dao.MoreDAO;
import com.icia.DJ.dto.CommentDTO;
import com.icia.DJ.dto.EventDTO;
import com.icia.DJ.dto.FaqDTO;
import com.icia.DJ.dto.NoticeDTO;
import com.icia.DJ.dto.PageDTO;

@Service
public class MoreService {
	
	@Autowired
	MoreDAO moredao;
	
	@Autowired
	CommentDAO cdao;
	
	@Autowired
	HttpSession session;
	
	ModelAndView mav = new ModelAndView();
	
	//faq리스트 불러오기 
	public ModelAndView faqList() {
		List<FaqDTO>faqList=moredao.faqList();
		mav.addObject("faqList", faqList);
		mav.setViewName("FAQ");
		return mav;
	}
	//관리자페이지로 faq리스트 불러오기
	public ModelAndView faqManage() {
		List<FaqDTO>faqList=moredao.faqList();
		mav.addObject("faqList", faqList);
		mav.setViewName("FaqManage");
		return mav;
	}
	
	// 이벤트 리스트 불러오기
		public ModelAndView eventList() {
			List<EventDTO> eventList = moredao.eventList();
			mav.addObject("eventList", eventList);
			System.out.println("------------------------------------------");
			System.out.println("eventList는 어떻게 나올까?" + eventList);
			mav.setViewName("Events");
			return mav;
		}

	// 이벤트 상세 페이지로 이동 (2020/10/15)
		public ModelAndView eventsView(int evnumber) {
			EventDTO event = moredao.eventView(evnumber);
			mav.addObject("event", event);
			System.out.println("------------------------------------------");
			System.out.println("event는 어떻게 나올까?" + event);

			// Commment(댓글)로 인해 추가한 부분 1 (각 게시글별 댓글)
			List<CommentDTO> commentList = cdao.commentList(evnumber);
			mav.addObject("commentList", commentList);

			System.out.println("------------------------------------------");
			System.out.println("commentList는 어떻게 나올까?" + commentList);

			// Commment(댓글)로 인해 추가한 부분 2 (각 게시글별 댓글 갯수)
			mav.addObject("commentSize", commentList.size());

			System.out.println("------------------------------------------");
			System.out.println("commentSize는 어떻게 나올까?" + commentList.size());

			mav.setViewName("EventsView");
			return mav;
		}

	
	// 관리자페이지로 이벤트 불러오기
	public ModelAndView eventsManage() {
		List<EventDTO> eventList = moredao.eventList();
			mav.addObject("eventList", eventList);
			mav.setViewName("EventsManage");
			return mav;
		}

		// notice 목록
		public ModelAndView boardList() {
			List<NoticeDTO> boardlist = moredao.boardList();
			mav.addObject("board", boardlist);
			mav.setViewName("boardList");
			return mav;
		}

		// notice 페페페페페이징

		private static final int PAGE_LIMIT = 10; // 한 페이지에 보여줄 글 갯수
		private static final int BLOCK_LIMIT = 5; // 회면에 보여줄 페이지 번호

		public ModelAndView pagingList(int page) {
			PageDTO paging = new PageDTO();

			// 게시글 갯수 조회
			int listcount = moredao.listCount();

			int startrow = (page - 1) * PAGE_LIMIT + 1;
			int endrow = page * PAGE_LIMIT;

			paging.setPage(page);
			paging.setStartrow(startrow);
			paging.setEndrow(endrow);

			int maxpage = (int) (Math.ceil((double) listcount / PAGE_LIMIT));
			int startpage = (((int) (Math.ceil((double) page / BLOCK_LIMIT))) - 1) * BLOCK_LIMIT + 1;
			int endpage = startpage + BLOCK_LIMIT - 1;

			if (endpage > maxpage) {
				endpage = maxpage;
			}
			/*
			 * (1) 올림 : Math.ceil (2) 내림 : Math.floor (3) 반올림 : Math.round
			 */

			paging.setMaxpage(maxpage);
			paging.setStartpage(startpage);
			paging.setEndpage(endpage);

			List<NoticeDTO> paginglist = moredao.paginglist(paging);
			mav.addObject("paginglist", paginglist);
			mav.addObject("paging", paging);
			mav.setViewName("pagingList");

			return mav;
		}

		// notice 상세보기
		public ModelAndView boardView(int nnum, int page) {
			NoticeDTO bView = moredao.boardView(nnum);

			mav.addObject("bView", bView);
			mav.addObject("page", page);
			mav.setViewName("boardView");

			return mav;
		}

		// notice 조회수
		public void boardhit(int nnum) {
			moredao.boardhit(nnum);
		}

		// notice 검색
		public ModelAndView boardSearch(String type, String keyword) {
			List<NoticeDTO> searchList = moredao.boardSearch(type, keyword);
			mav.addObject("paginglist", searchList);
			mav.setViewName("pagingList");

			return mav;
		}
	
		// 랜드 이용권
		@RequestMapping(value = "/landReservation", method = RequestMethod.GET)
		public String LandReservation() {
			return "LandReservation";
		}
	
}
