package com.gd.test.web.chat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gd.test.common.CommonProperties;
import com.gd.test.web.chat.service.IChatService;

@Controller
public class ChatController {
	@Autowired
	public IChatService iChatService;

	@RequestMapping(value = "/chatLogin")
	public ModelAndView chatLogin(HttpSession session, ModelAndView modelAndView) {
		if (session.getAttribute("userNick") != null) {
			modelAndView.setViewName("redirect:chat");
		} else {
			modelAndView.setViewName("chat/chatLogin");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/joinChat")
	public ModelAndView joinChat(HttpServletRequest request, HttpSession session, ModelAndView modelAndView) {
		session.setAttribute("userNick", request.getParameter("userNick"));

		modelAndView.setViewName("redirect:chat");

		return modelAndView;
	}

	@RequestMapping(value = "/outChat")
	public ModelAndView outChat(HttpSession session, ModelAndView modelAndView) {
		session.invalidate();

		modelAndView.setViewName("redirect:chatLogin");

		return modelAndView;
	}

	@RequestMapping(value = "/chat")
	public ModelAndView chat(HttpSession session, ModelAndView modelAndView) throws Throwable {
		if (session.getAttribute("userNick") == null) {
			modelAndView.setViewName("redirect:chatLogin");
		} else {
			int maxNo = iChatService.getMaxNo();

			modelAndView.addObject("maxNo", maxNo);

			modelAndView.setViewName("chat/chat");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/insertChat", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String insertChat(@RequestParam HashMap<String, String> params, HttpServletRequest request,
			ModelAndView modelAndView) throws Throwable {

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> modelMap = new HashMap<String, Object>();

		try {
			iChatService.insertChat(params);

			modelMap.put("message", CommonProperties.RESULT_SUCCESS);
		} catch (Exception e) {
			modelMap.put("message", CommonProperties.RESULT_ERROR);
			modelMap.put("errorMessage", e.getMessage());
		}

		return mapper.writeValueAsString(modelMap);
	}

	@RequestMapping(value = "/getChatList", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String getChatList(HttpServletRequest request, ModelAndView modelAndView) throws Throwable {

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int lastChatNo = Integer.parseInt(request.getParameter("lastChatNo"));

		try {
			List<HashMap<String, String>> list = iChatService.getChatList(lastChatNo);

			modelMap.put("list", list);
			modelMap.put("message", CommonProperties.RESULT_SUCCESS);
		} catch (Exception e) {
			modelMap.put("message", CommonProperties.RESULT_ERROR);
			modelMap.put("errorMessage", e.getMessage());
		}

		return mapper.writeValueAsString(modelMap);
	}
}
