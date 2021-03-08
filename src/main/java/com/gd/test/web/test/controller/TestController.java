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
import com.gd.test.web.test.service.ITestService;

@Controller
public class TestController {
	@Autowired
	public ITestService iTestService;
	
	@Autowired
	public IPagingService iPagingService;

	// 주소할당을 위해서 맨 처음 해줘야할 @RequestMapping
	// 조회때는 받아오지 않았지만 검색기능을 추가하며 @RequestParam 이 필요함.
	@RequestMapping(value = "/test1")
	public ModelAndView test1(@RequestParam HashMap<String, String> params,
		   ModelAndView mav) throws Throwable { 
		int page = 1; // 시작페이지 기본값
		
		if(params.get("page") != null) {
			page = Integer.parseInt(params.get("page"));
		}
		
		int cnt = iTestService.getMemCnt(params); // 페이징 처리, 카운트 처리 완료
		       
		PagingBean pb = iPagingService.getPagingBean(page, cnt, 5, 3); // 페이징뷰 pb생성, 한 번에 페이징 3개를 보고 5개씩 보여준다.
		
		params.put("startCnt", Integer.toString(pb.getStartCount()));
		params.put("endCnt", Integer.toString(pb.getEndCount()));
		
		List<HashMap<String, String>> list = iTestService.getMem(params); // 원래는 getMem() 의 형태로 인자가 없었으나, @ReaquestParam으로 값을 받아오면서 인자가 필요해짐.
		
		mav.addObject("list", list);
		mav.addObject("pb", pb);
		mav.addObject("page", page);
		
		mav.setViewName("test/test1");
		
		return mav;
	}
	@RequestMapping(value="/test1Detail") // 속성값은 오타나면 안되니까 복붙하는 것이 좋다.
	public ModelAndView test1Detail(
			@RequestParam HashMap<String, String> params, ModelAndView mav) throws Throwable{
		
		HashMap<String, String> data = iTestService.getMemDetail(params);
		
		mav.addObject("data", data);
		
		mav.setViewName("test/test1Detail");
		
		return mav;
	} 
	
	@RequestMapping(value = "/test1Add")
	public ModelAndView test1Add(ModelAndView mav) {
		mav.setViewName("test/test1Add");
		
		return mav;
	}
	
	@RequestMapping(value = "/test1Adds") // 주소가 새로 생겼으니까 controller로 와서 연결시켜줘야지.
	public ModelAndView test1Adds(@RequestParam HashMap<String, String> params,
									ModelAndView mav) throws Throwable{
		try {
			iTestService.addMem(params);
			// redirect : 주소 ==> 주소에 해당하는 컨트롤러 매핑으로 이동한다. 단, get방식만 지원
			mav.setViewName("redirect:test1");
		} catch (Exception e) {
			e.printStackTrace();
			
			mav.setViewName("test/test1Adds");
		}
		
		return mav;
	}
	@RequestMapping(value="/test1Update") 
	public ModelAndView test1Update(
			@RequestParam HashMap<String, String> params, 
						ModelAndView mav) throws Throwable{
		
		HashMap<String, String> data = iTestService.getMemDetail(params);
		
		mav.addObject("data", data);
		
		mav.setViewName("test/test1Update");
		
		return mav;
	} 
	@RequestMapping(value = "/test1Updates")
	public ModelAndView test1Updates(@RequestParam HashMap<String, String> params,
									ModelAndView mav) throws Throwable{
		try {
			int cnt = iTestService.updateMem(params);
			if(cnt > 0) {
				mav.addObject("res", "success");
			} else {
				mav.addObject("res", "failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("res", "failed");
		}
		
		mav.setViewName("test/test1Updates");
		
		return mav;
	}
	
	@RequestMapping(value = "/test1Delete")
	public ModelAndView test1Delete(@RequestParam HashMap<String, String> params,
									ModelAndView mav) throws Throwable{
		try {
			int cnt = iTestService.deleteMem(params);
			if(cnt > 0) {
				mav.addObject("res", "success");
			} else {
				mav.addObject("res", "failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("res", "failed");
		}
		
		mav.setViewName("test/test1Delete");
		
		return mav;
	}
	
	
	@RequestMapping(value="/test2")
	public ModelAndView test2(@RequestParam HashMap<String, String> params,
			ModelAndView mav) throws Throwable {
		
		int page = 1;
		
		if(params.get("page") != null) {
			page = Integer.parseInt(params.get("page"));
		}
		int cnt = iTestService.getBCnt(params);
		
		PagingBean pb = iPagingService.getPagingBean(page, cnt);
		
		params.put("startCnt", Integer.toString(pb.getStartCount()));
		params.put("endCnt", Integer.toString(pb.getEndCount()));
		
		
		List<HashMap<String, String>> list = iTestService.getB(params);
		
		mav.addObject("list", list);
		mav.addObject("pb", pb);
		mav.addObject("page", page);
		
		mav.setViewName("test/test2");
		
		return mav;
	}
	
	@RequestMapping(value = "/test2Detail")
	public ModelAndView test2Detail(
			@RequestParam HashMap<String, String> params, ModelAndView mav) throws Throwable{
		// DB를 받을 것이기 때문에 예외처리 해준다.
		HashMap<String, String> data = iTestService.getBDetail(params);
		
		mav.addObject("data", data); // 데이터를 돌려준다.
		
		mav.setViewName("test/test2Detail");
		
		return mav;
	}
	
	@RequestMapping(value = "/test2Add")
	public ModelAndView test2Add(ModelAndView mav) { // 여기에서는 아무것도 안 받을거니까 mav만 있으면 된다.
		mav.setViewName("test/test2Add");
		
		return mav;
	}
	
	@RequestMapping(value = "/test2Adds")
	public ModelAndView test2Adds(@RequestParam HashMap<String, String> params,
									ModelAndView mav) throws Throwable{
		try {
			iTestService.addB(params);
			mav.setViewName("redirect:test2");
		} catch (Exception e) {
			e.printStackTrace();
			
			mav.setViewName("test/test2Adds");
		}
		
		return mav;
	}
	
	@RequestMapping("/test2Update")
	public ModelAndView test2Update(@RequestParam HashMap<String, String> params, 
									ModelAndView mav) throws Throwable {

			HashMap<String, String> data = iTestService.getBDetail(params);
			
			mav.addObject("data", data);
			
			mav.setViewName("test/test2Update");
			
			return mav;
	}
	@RequestMapping(value="/test2Updates")
	public ModelAndView test2Updates(@RequestParam HashMap<String, String> params,
									ModelAndView mav) throws Throwable {
		try {
				int cnt = iTestService.updateB(params);
				if(cnt > 0) {
					mav.addObject("res", "success");
				} else {
					mav.addObject("res", "failed");
				}
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("res", "failed");
		}
		
		mav.setViewName("test/test2Updates");
		
		return mav;
	}
	
	@RequestMapping(value = "/test2Delete")
	public ModelAndView test2Delete(@RequestParam HashMap<String, String> params,
									ModelAndView mav) throws Throwable{
		try {
			int cnt = iTestService.deleteB(params);
			if(cnt > 0) {
				mav.addObject("res", "success");
			} else {
				mav.addObject("res", "failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("res", "failed");
		}
		
		mav.setViewName("test/test2Delete");
		
		return mav;
	}
}




















