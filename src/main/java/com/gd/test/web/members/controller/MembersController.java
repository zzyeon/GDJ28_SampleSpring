package com.gd.test.web.members.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gd.test.common.bean.PagingBean;
import com.gd.test.common.service.IPagingService;
import com.gd.test.web.members.service.IMembersService;

@Controller
public class MembersController {
	@Autowired
	public IMembersService iMembersService;
	
	@Autowired
	public IPagingService iPagingService;

	@RequestMapping(value = "/memberList")
	public ModelAndView memberList(@RequestParam HashMap<String, String> params,
								ModelAndView mav) throws Throwable {
		int page = 1;
		
		if(params.get("page") != null && !params.get("page").equals("")) {
			page = Integer.parseInt(params.get("page"));
		}
		
		int cnt = iMembersService.getMemberCnt(params);
		
		PagingBean pb = iPagingService.getPagingBean(page, cnt, 10, 5);
		
		params.put("startCnt", Integer.toString(pb.getStartCount()));
		params.put("endCnt", Integer.toString(pb.getEndCount()));
		
		List<HashMap<String, String>> list = iMembersService.getMemberList(params);
		
		mav.addObject("page", page);
		mav.addObject("cnt", cnt);
		mav.addObject("pb", pb);
		mav.addObject("list", list);
		
		mav.setViewName("members/memberList");
		
		return mav;
	}
	
	@RequestMapping(value = "/memberAdd")
	public ModelAndView memberAdd(ModelAndView mav) {
		
		mav.setViewName("members/memberAdd");
		
		return mav;
	}
	
	@RequestMapping(value = "/memberAdds")
	public ModelAndView memberAdds(@RequestParam HashMap<String, String> params,
								ModelAndView mav) throws Throwable {
		try {
			iMembersService.memberAdd(params);
			
			mav.setViewName("redirect:memberList");
		} catch (Exception e) {
			e.printStackTrace();
			mav.setViewName("members/memberAdds");
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/memberDetail")
	public ModelAndView memberDetail(@RequestParam HashMap<String, String> params, 
								ModelAndView mav) throws Throwable {
		HashMap<String, String> member = iMembersService.getMember(params);
		
		mav.addObject("member", member);
		
		mav.setViewName("members/memberDetail");
		
		return mav;
	}
	
	@RequestMapping(value = "/memberUpdate")
	public ModelAndView memberUpdate(@RequestParam HashMap<String, String> params, 
			ModelAndView mav) throws Throwable {
		HashMap<String, String> member = iMembersService.getMember(params);
		
		mav.addObject("member", member);
		
		mav.setViewName("members/memberUpdate");
		
		return mav;
	}
	
	@RequestMapping(value = "/memberUpdates")
	public ModelAndView memberUpdates(@RequestParam HashMap<String, String> params,
								ModelAndView mav) throws Throwable {
		try {
			int cnt = iMembersService.memberUpdate(params);
			
			if(cnt > 0) {
				mav.addObject("res", "SUCCESS");
			} else {
				mav.addObject("res", "FAILED");
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			mav.addObject("res", "ERROR");
		}
		
		mav.setViewName("members/memberUpdates");
		
		return mav;
	}
	
	@RequestMapping(value = "/memberDelete")
	public ModelAndView memberDelete(@RequestParam HashMap<String, String> params,
				ModelAndView mav) throws Throwable {
		try {
			int cnt = iMembersService.memberDelete(params);
			
			if(cnt > 0) {
				mav.addObject("res", "SUCCESS");
			} else {
				mav.addObject("res", "FAILED");
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			mav.addObject("res", "ERROR");
		}
		
		mav.setViewName("members/memberDelete");
		
		return mav;
	}
	
	@RequestMapping(value = "/memberLogin") // ????????? ??? ??? ???????????? ?????? ????????? url ????????? ?????? ?????? ????????????????????? ?????????
	public ModelAndView memberLogin(HttpSession session, ModelAndView mav) {
		if(session.getAttribute("sMno") != null) {
			mav.setViewName("redirect:memberList");
		} else {			
			mav.setViewName("members/memberLogin");
		} // ????????? ?????? ????????????????????? ???????????? ???????????? ?????? ?????? list??? ???????????? ??????, ??? ????????? ????????? ????????? ???????????? ??? ??????.
		
		return mav;
	}
	
	@RequestMapping(value = "/memberLoginAjax")
	@ResponseBody // ???????????? view???????????? ?????? , jsp???????????? ???! String??? view?????? ??????????????? ^^ ?????? ???????????????.  
	public String memberLoginAjax(HttpSession session,
								@RequestParam HashMap<String, String> params) throws Throwable {
		ObjectMapper mapper = new ObjectMapper(); // ????????? ???????????? ????????????
		Map<String, Object> modelMap = new HashMap<String, Object>(); // ????????? ??? ?????????
		HashMap<String, String> member = iMembersService.getLoginInfo(params);
		
		if(member == null) {
			modelMap.put("res", "0"); // 0 - ???????????? ??????
		} else if(member.get("MPW_RES").contentEquals("FALSE")) {
			modelMap.put("res", "1"); // 1 - ??????????????? ??????
		} else {
			//HttpSession session = req.getSession(); // ??????????????? ?????? ?????? ??????
			session.setAttribute("sMno", member.get("MNO")); // ?????? sMno??? ?????? ?????? MNO??? ????????????.
			session.setAttribute("sMnm", member.get("MNM"));
		}
		
		return mapper.writeValueAsString(modelMap); // map??? json String?????? ??????
	}
	
	@RequestMapping(value= "/memberLogins") 
	public ModelAndView memberLogins(
								//HttpServletRequest req, 
								HttpSession session,
								@RequestParam HashMap<String, String> params,
								ModelAndView mav) throws Throwable {
		HashMap<String, String> member = iMembersService.getLoginInfo(params);
		
		if(member == null) {
			mav.addObject("res", "0"); // 0 - ???????????? ??????
			mav.setViewName("members/memberLogins");
		} else if(member.get("MPW_RES").contentEquals("FALSE")) {
			mav.addObject("res", "1"); // 1 - ??????????????? ??????
			mav.setViewName("members/memberLogins");
		} else {
			//HttpSession session = req.getSession(); // ??????????????? ?????? ?????? ??????
			session.setAttribute("sMno", member.get("MNO")); // ?????? sMno??? ?????? ?????? MNO??? ????????????.
			session.setAttribute("sMnm", member.get("MNM"));
			
			System.out.println(session.getAttribute("sMnm"));
			
			mav.setViewName("redirect:memberList");
		}
		
		return mav;
	}
	@RequestMapping(value= "/memberLogout")
	public ModelAndView memberLogout(HttpSession session,
			ModelAndView mav) {
		session.invalidate(); // session ?????????
		
		mav.setViewName("redirect:memberLogin");
		
	return mav;
	}
	
	@RequestMapping(value = "/loginTop")
	public ModelAndView loginTop(ModelAndView mav) {
		mav.setViewName("members/loginTop");
		
		return mav;
	}
}














