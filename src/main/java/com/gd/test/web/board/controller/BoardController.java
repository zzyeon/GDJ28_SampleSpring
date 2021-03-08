package com.gd.test.web.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gd.test.common.bean.PagingBean;
import com.gd.test.common.service.IPagingService;
import com.gd.test.web.board.service.IBoardService;

@Controller
public class BoardController {
	@Autowired
	public IBoardService iBoardService;
	
	@Autowired
	public IPagingService iPagingService;
	
	@RequestMapping(value = "/boardList")
	public ModelAndView boardList(@RequestParam HashMap<String, String> params,
								ModelAndView mav) throws Throwable {
		int page = 1;
		
		if(params.get("page") != null) {
			page = Integer.parseInt(params.get("page"));
		}
		
		int cnt = iBoardService.getBoardCnt(params);
		
		PagingBean pb = iPagingService.getPagingBean(page, cnt, 10, 10);
		
		params.put("startCnt", Integer.toString(pb.getStartCount()));
		params.put("endCnt", Integer.toString(pb.getEndCount()));
		
		List<HashMap<String, String>> list = iBoardService.getBoardList(params);
		
		mav.addObject("page", page);
		mav.addObject("pb", pb);
		mav.addObject("list", list);
		
		mav.setViewName("board/boardList");
		
		return mav;
	}
	
	@RequestMapping(value = "/boardAdd")
	public ModelAndView boardAdd(ModelAndView mav) {
		
		mav.setViewName("board/boardAdd");
		
		return mav;
	}
	
	@RequestMapping(value = "/boardAdds")
	public ModelAndView boardAdds(@RequestParam HashMap<String, String> params,
			ModelAndView mav) throws Throwable {
		
		try {
			iBoardService.boardAdd(params);
			
			mav.setViewName("redirect:boardList");
		} catch (Exception e) {
			e.printStackTrace();
			mav.setViewName("board/boardAdds");
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/boardDetail")
	public ModelAndView boardDetail(@RequestParam HashMap<String, String> params,
			ModelAndView mav) throws Throwable {
		iBoardService.updateBoardHit(params);
		
		HashMap<String, String> board = iBoardService.getBoard(params);
		
		mav.addObject("board", board);
		
		mav.setViewName("board/boardDetail");
		
		return mav;
	}
	
	@RequestMapping(value = "/boardUpdate")
	public ModelAndView boardUpdate(@RequestParam HashMap<String, String> params,
			ModelAndView mav) throws Throwable {
		iBoardService.updateBoardHit(params);
		
		HashMap<String, String> board = iBoardService.getBoard(params);
		
		mav.addObject("board", board);
		
		mav.setViewName("board/boardUpdate");
		
		return mav;
	}
	
	@RequestMapping(value = "/boardUpdates")
	public ModelAndView boardUpdates(@RequestParam HashMap<String, String> params,
			ModelAndView mav) throws Throwable {
		
		try {
			int cnt = iBoardService.boardUpdate(params);
			if(cnt > 0) {
				mav.addObject("res", "SUCCESS");
			} else {
				mav.addObject("res", "FAILED");
			}
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("res", "EXCEPTION");
		}
		mav.setViewName("board/boardUpdates");
		
		return mav;
	}
	
	@RequestMapping(value = "/boardDelete")
	public ModelAndView boardDelete(@RequestParam HashMap<String, String> params,
			ModelAndView mav) throws Throwable {
		
		try {
			int cnt = iBoardService.boardDelete(params);
			if(cnt > 0) {
				mav.addObject("res", "SUCCESS");
			} else {
				mav.addObject("res", "FAILED");
			}
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("res", "EXCEPTION");
		}
		mav.setViewName("board/boardDelete");
		
		return mav;
	}
	
	@RequestMapping(value = "/aBoardList")
	public ModelAndView aBoardList(@RequestParam HashMap<String, String> params,
									ModelAndView mav) {
		int page = 1;
		
		if(params.get("page") != null) {
			page = Integer.parseInt(params.get("page"));
		}
		
		mav.addObject("page", page);
		
		mav.setViewName("board/aBoardList");
		
		return mav;
	}
	
	@RequestMapping(value = "/aBoardListAjax", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String aBoardListAjax(@RequestParam HashMap<String, String> params) 
							throws Throwable {
		ObjectMapper mapper = new ObjectMapper();
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		int cnt = iBoardService.getBoardCnt(params);
		
		PagingBean pb 
		= iPagingService.getPagingBean(Integer.parseInt(params.get("page")), cnt, 10, 10);
		
		params.put("startCnt", Integer.toString(pb.getStartCount()));
		params.put("endCnt", Integer.toString(pb.getEndCount()));
		
		List<HashMap<String, String>> list = iBoardService.getBoardList(params);
		
		modelMap.put("pb", pb);
		modelMap.put("list", list);
		modelMap.put("cnt", cnt);
		
		return mapper.writeValueAsString(modelMap);
	}
	
	@RequestMapping(value = "/aBoardAdd")
	public ModelAndView aBoardAdd(ModelAndView mav) {
		
		mav.setViewName("board/aBoardAdd");
		
		return mav;
	}
	
	@RequestMapping(value = "/aBoardAddAjax" ,
					method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String aBoardAddAjax(@RequestParam HashMap<String, String> params) 
																throws Throwable {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		try {
			iBoardService.boardAdd(params);
			
			modelMap.put("res", "SUCCESS");
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("res", "FAILED");
		}
		
		return mapper.writeValueAsString(modelMap);
	}
	
	@RequestMapping(value = "/oBoard")
	public ModelAndView oBoard(ModelAndView mav) {
		
		mav.setViewName("board/oBoard");
		
		return mav;
	}
	
	@RequestMapping(value = "/oBoardListAjax", 
					method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String oBoardListAjax(
				@RequestParam HashMap<String, String> params) throws Throwable {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		int cnt = iBoardService.getOBoardCnt(params);
		
		PagingBean pb = iPagingService.getPagingBean(Integer.parseInt(params.get("page")), 
					cnt, 5, 3);
		
		params.put("startCnt", Integer.toString(pb.getStartCount()));
		params.put("endCnt", Integer.toString(pb.getEndCount()));
		
		List<HashMap<String, String>> list = iBoardService.getOBoardList(params);
		
		modelMap.put("list", list);
		modelMap.put("pb", pb);
		modelMap.put("cnt", cnt);
		
		return mapper.writeValueAsString(modelMap);
	}
	
	@RequestMapping(value = "/oBoardAddAjax", 
			method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String oBoardAddAjax(HttpSession session,
			@RequestParam HashMap<String, String> params) throws Throwable {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		params.put("mno", String.valueOf(session.getAttribute("sMno")));
		
		try {
			iBoardService.oBoardAdd(params);
			
			modelMap.put("res", "SUCCESS");
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("res", "EXCEPTION");
		}
		
		return mapper.writeValueAsString(modelMap);
	}
	
	@RequestMapping(value = "/oBoardUpdateAjax", 
			method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String oBoardUpdateAjax(HttpSession session,
			@RequestParam HashMap<String, String> params) throws Throwable {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		params.put("mno", String.valueOf(session.getAttribute("sMno")));
		
		try {
			int cnt = iBoardService.oBoardUpdate(params);
			
			if(cnt > 0) {
				modelMap.put("res", "SUCCESS");
			} else {
				modelMap.put("res", "FAILED");
			}
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("res", "EXCEPTION");
		}
		
		return mapper.writeValueAsString(modelMap);
	}
	
	@RequestMapping(value = "/oBoardDeleteAjax", 
			method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String oBoardDeleteAjax(HttpSession session,
			@RequestParam HashMap<String, String> params) throws Throwable {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		params.put("mno", String.valueOf(session.getAttribute("sMno")));
		
		try {
			int cnt = iBoardService.oBoardDelete(params);
			
			if(cnt > 0) {
				modelMap.put("res", "SUCCESS");
			} else {
				modelMap.put("res", "FAILED");
			}
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("res", "EXCEPTION");
		}
		
		return mapper.writeValueAsString(modelMap);
	}
}


















