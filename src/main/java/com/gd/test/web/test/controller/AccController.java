package com.gd.test.web.test.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gd.test.common.bean.PagingBean;
import com.gd.test.common.service.IPagingService;
import com.gd.test.web.test.service.IAccService;

@Controller
public class AccController {
	@Autowired
	public IAccService iAccService;
	
	@Autowired
	public IPagingService iPagingService;
	
	@RequestMapping(value = "/accList")
	public ModelAndView accList(@RequestParam HashMap<String, String> params, 
								 ModelAndView mav) throws Throwable {
		int page = 1;
		
		if(params.get("page") != null) {
			page = Integer.parseInt(params.get("page"));
		}
		
		int cnt = iAccService.getAccCnt(params);
		
		PagingBean pb = iPagingService.getPagingBean(page, cnt, 3, 5);
		
		params.put("startCnt", Integer.toString(pb.getStartCount()));
		params.put("endCnt", Integer.toString(pb.getEndCount()));
		
		List<HashMap<String, String>> list = iAccService.getAccList(params);
		
		mav.addObject("list", list);
		mav.addObject("page", page);
		mav.addObject("pb", pb);
		
		mav.setViewName("test/accList");
		
		return mav;
	}
	
	@RequestMapping(value = "/accDetail")
	public ModelAndView accDetail(@RequestParam HashMap<String, String> params, 
			ModelAndView mav) throws Throwable {
		HashMap<String, String> data = iAccService.getAcc(params);
		
		mav.addObject("data", data);
		
		mav.setViewName("test/accDetail");
		
		return mav;
	}
	
	@RequestMapping(value = "/addAcc")
	public ModelAndView addAcc(ModelAndView mav) {
		
		mav.setViewName("test/addAcc");
		
		return mav;
	}
	
	@RequestMapping(value = "/addAccs")
	public ModelAndView addAccs(@RequestParam HashMap<String, String> params, 
			ModelAndView mav) throws Throwable {
		try {
			iAccService.addAcc(params);
			// redirect:주소 ==> 주소에 해당하는 컨트롤러 매핑으로 이동한다. 단, get방식만 지원
			mav.setViewName("redirect:accList");
		} catch (Exception e) {
			e.printStackTrace();
			
			mav.setViewName("test/addAccs");
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/updateAcc")
	public ModelAndView updateAcc(@RequestParam HashMap<String, String> params, 
			ModelAndView mav) throws Throwable {
		
		HashMap<String, String> data = iAccService.getAcc(params);
		
		mav.addObject("data", data);
		
		mav.setViewName("test/updateAcc");
		
		return mav;
	}
	
	@RequestMapping(value = "/updateAccs")
	public ModelAndView updateAccs(@RequestParam HashMap<String, String> params, 
			ModelAndView mav) throws Throwable {
		try {
			int cnt = iAccService.updateAcc(params);
			
			if(cnt > 0) {
				mav.addObject("res", "success");
			} else {
				mav.addObject("res", "failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("res", "failed");
		}
		
		mav.setViewName("test/updateAccs");
		
		return mav;
	}
	
	@RequestMapping(value = "/deleteAcc")
	public ModelAndView deleteAcc(@RequestParam HashMap<String, String> params, 
			ModelAndView mav) throws Throwable {
		try {
			int cnt = iAccService.deleteAcc(params);
			
			if(cnt > 0) {
				mav.addObject("res", "success");
			} else {
				mav.addObject("res", "failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("res", "failed");
		}
		
		mav.setViewName("test/deleteAcc");
		
		return mav;
	}
}
