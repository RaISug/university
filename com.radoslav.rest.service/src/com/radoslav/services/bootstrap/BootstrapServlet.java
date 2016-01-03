package com.radoslav.services.bootstrap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.radoslav.services.email.EmailListener;

public class BootstrapServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		new EmailListener("imap.gmail.com", "993", "imaps", "radoslav1", "raisug93@gmail.com").start();
	}
}
